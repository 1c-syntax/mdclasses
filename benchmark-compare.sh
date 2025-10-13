#!/bin/bash

set -e

# Конфигурация для CI
if [ -n "$GITHUB_HEAD_REF" ]; then
    # В GitHub Actions
    OLD_BRANCH=${1:-"develop"}
    NEW_BRANCH=${2:-"$GITHUB_HEAD_REF"}
else
    # Локально
    OLD_BRANCH=${1:-"develop"}
    NEW_BRANCH=${2:-$(git branch --show-current)}
fi

RESULTS_DIR="benchmark-results"
BUILD_DIR="build/libs"

echo "🔨 Начало сравнения производительности MDClasses"
echo "================================================"

rm -rf $RESULTS_DIR
mkdir -p $RESULTS_DIR

# Функция для проверки является ли параметром JAR файл
is_jar_file() {
    local param=$1
    if [[ "$param" == *.jar ]] && [[ -f "$param" ]]; then
        return 0  # true - это файл JAR
    else
        return 1  # false - не JAR файл
    fi
}

# Функция для проверки является ли параметром ветка Git
is_git_branch() {
    local param=$1
    if git rev-parse --verify "$param" >/dev/null 2>&1; then
        return 0  # true - это существующая ветка
    else
        # Проверяем, существует ли ветка удаленно
        if git ls-remote --heads origin "$param" | grep -q "$param"; then
            return 0
        else
            return 1  # false - не ветка
        fi
    fi
}

# Функция для поиска JAR файла бенчмарков
find_benchmark_jar() {
    local build_dir=$1
    find "$build_dir" -name "*jmh*.jar" -type f | head -1
}

# Функция для использования готового JAR файла
use_existing_jar() {
    local jar_path=$1
    local version_name=$2

    echo "📦 Используем готовый JAR файл для $version_name..."
    echo "   Файл: $jar_path"

    if [[ ! -f "$jar_path" ]]; then
        echo "❌ JAR файл не найден: $jar_path"
        exit 1
    fi

    cp "$jar_path" "$RESULTS_DIR/$version_name.jar"
    echo "   ✅ Скопирован как: $RESULTS_DIR/$version_name.jar"
}

# Функция для сборки версии из ветки Git
build_from_branch() {
    local branch=$1
    local version_name=$2

    echo "📦 Сборка $version_name из ветки $branch..."

    # Проверяем существование ветки
    if ! git rev-parse --verify "$branch" >/dev/null 2>&1; then
        echo "❌ Ветка $branch не найдена"
        echo "   Доступные ветки:"
        git branch -a | head -10
        exit 1
    fi

    git checkout "$branch" --quiet

    echo "   Сборка Gradle..."
    ./gradlew clean jmhJar --quiet

    git log -1 --oneline > "$RESULTS_DIR/$version_name-commit.txt"
    git rev-parse HEAD > "$RESULTS_DIR/$version_name-hash.txt"

    sleep 2

    local jar_file
    jar_file=$(find_benchmark_jar "$BUILD_DIR")

    if [[ -z "$jar_file" ]]; then
        echo "   ❌ Не удалось найти JAR файл в $BUILD_DIR"
        ls -la "$BUILD_DIR"/*.jar 2>/dev/null || echo "      Нет JAR файлов"
        exit 1
    fi

    echo "   ✅ Найден JAR: $(basename "$jar_file")"
    cp "$jar_file" "$RESULTS_DIR/$version_name.jar"
}

# Основная логика определения способа получения версий
echo "🔍 Анализ параметров..."
echo "   Прошлая версия: $OLD_BRANCH"
echo "   Новая версия: $NEW_BRANCH"

# Обрабатываем старую версию
if is_jar_file "$OLD_BRANCH"; then
    use_existing_jar "$OLD_BRANCH" "old-version"
    OLD_SOURCE="JAR файл: $(basename "$OLD_BRANCH")"
elif is_git_branch "$OLD_BRANCH"; then
    build_from_branch "$OLD_BRANCH" "old-version"
    OLD_SOURCE="Ветка: $OLD_BRANCH ($(cat $RESULTS_DIR/old-version-commit.txt))"
else
    echo "❌ Первый параметр не является ни JAR файлом, ни существующей веткой: $OLD_BRANCH"
    echo "   Использование:"
    echo "     ./benchmark-compare.sh <old_jar_file> <new_branch>"
    echo "     ./benchmark-compare.sh <old_branch> <new_branch>"
    echo "     ./benchmark-compare.sh <old_branch> <new_jar_file>"
    echo "     ./benchmark-compare.sh <old_jar_file> <new_jar_file>"
    exit 1
fi

# Обрабатываем новую версию
if is_jar_file "$NEW_BRANCH"; then
    use_existing_jar "$NEW_BRANCH" "new-version"
    NEW_SOURCE="JAR файл: $(basename "$NEW_BRANCH")"
elif is_git_branch "$NEW_BRANCH"; then
    build_from_branch "$NEW_BRANCH" "new-version"
    NEW_SOURCE="Ветка: $NEW_BRANCH ($(cat $RESULTS_DIR/new-version-commit.txt))"
else
    echo "❌ Второй параметр не является ни JAR файлом, ни существующей веткой: $NEW_BRANCH"
    exit 1
fi

# Сохраняем информацию об источниках
echo "Прошлая версия: $OLD_SOURCE" > "$RESULTS_DIR/sources.txt"
echo "Новая версия: $NEW_SOURCE" >> "$RESULTS_DIR/sources.txt"

# Проверяем что JAR файлы созданы
check_jar_exists() {
    local jar_path=$1
    local version_name=$2

    if [[ ! -f "$jar_path" ]]; then
        echo "❌ JAR файл для $version_name не найден: $jar_path"
        exit 1
    fi
}

check_jar_exists "$RESULTS_DIR/old-version.jar" "прошлой версии"
check_jar_exists "$RESULTS_DIR/new-version.jar" "новой версии"

# Если использовались ветки, возвращаемся к новой версии для дальнейшей работы
if ! is_jar_file "$NEW_BRANCH" && is_git_branch "$NEW_BRANCH"; then
    echo "🔄 Возвращаемся к ветке $NEW_BRANCH..."
    git checkout "$NEW_BRANCH" --quiet
fi

echo ""
echo "🎯 ИСТОЧНИКИ ВЕРСИЙ:"
echo "   Прошлая версия: $OLD_SOURCE"
echo "   Новая версия: $NEW_SOURCE"
echo ""

echo "📊 Запуск бенчмарков для прошлой версии..."
java -jar "$RESULTS_DIR/old-version.jar" -prof com.github._1c_syntax.bsl.mdclasses.benchmark.MemoryProfiler -rf json -rff "$RESULTS_DIR/old-results.json"

echo "📊 Запуск бенчмарков для новой версии..."
java -jar "$RESULTS_DIR/new-version.jar" -prof com.github._1c_syntax.bsl.mdclasses.benchmark.MemoryProfiler -rf json -rff "$RESULTS_DIR/new-results.json"

# Обновленная функция анализа для использования правильных источников
analyze_results() {
    local old_file="$1"
    local new_file="$2"

    # Загружаем информацию об источниках
    if [[ -f "$RESULTS_DIR/sources.txt" ]]; then
        source . "$RESULTS_DIR/sources.txt"
    else
        OLD_SOURCE="Ветка: $OLD_BRANCH"
        NEW_SOURCE="Ветка: $NEW_BRANCH"
    fi

    echo "📈 АНАЛИЗ РЕЗУЛЬТАТОВ (Производительность + Память)"
    echo "==================================================="

    if ! command -v jq &> /dev/null; then
        echo "ℹ️  Для детального анализа установите jq: sudo apt-get install jq"
        echo "📊 Результаты сохранены в JSON файлах"
        return
    fi

    # Проверяем структуру JSON файлов
    if [[ ! -f "$old_file" || ! -f "$new_file" ]]; then
        echo "❌ Файлы результатов не найдены"
        return
    fi

    echo ""
    echo "🚀 СРАВНЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ (время, меньше - лучше):"
    echo "---------------------------------------------------"
    echo "Бенчмарк | Старая версия | Новая версия | Изменение"
    echo "---------|---------------|--------------|----------"

    # Анализ основных метрик производительности
    jq -r '.[] | select(.benchmark) | "\(.benchmark)|\(.primaryMetric.score)|\(.primaryMetric.scoreUnit)"' "$old_file" > "$RESULTS_DIR/old-perf.txt"
    jq -r '.[] | select(.benchmark) | "\(.benchmark)|\(.primaryMetric.score)|\(.primaryMetric.scoreUnit)"' "$new_file" > "$RESULTS_DIR/new-perf.txt"

    # Создаем ассоциативные массивы
    declare -A old_scores
    declare -A new_scores
    declare -A units

    # Читаем старые результаты производительности
    while IFS='|' read -r benchmark score unit; do
        if [[ -n "$benchmark" && -n "$score" ]]; then
            old_scores["$benchmark"]="$score"
            units["$benchmark"]="$unit"
        fi
    done < "$RESULTS_DIR/old-perf.txt"

    # Читаем новые результаты производительности
    while IFS='|' read -r benchmark score unit; do
        if [[ -n "$benchmark" && -n "$score" ]]; then
            new_scores["$benchmark"]="$score"
        fi
    done < "$RESULTS_DIR/new-perf.txt"

    # Сравниваем производительность
    for benchmark in "${!old_scores[@]}"; do
        old_score="${old_scores[$benchmark]}"
        new_score="${new_scores[$benchmark]}"
        unit="${units[$benchmark]}"

        if [[ -n "$new_score" ]]; then
            short_name=$(echo "$benchmark" | sed 's/.*\.//')

            # Вычисляем изменение
            if command -v bc &> /dev/null && [[ "$old_score" != "0" ]]; then
                change=$(echo "scale=2; (($old_score - $new_score) / $old_score) * 100" | bc 2>/dev/null)

                if [[ -n "$change" ]]; then
                    if (( $(echo "$change > 0" | bc -l 2>/dev/null) )); then
                        change_display="+${change}% ✅"
                    elif (( $(echo "$change < 0" | bc -l 2>/dev/null) )); then
                        change_display="${change}% ❌"
                    else
                        change_display="${change}% ➖"
                    fi
                else
                    change_display="N/A"
                fi
            else
                change_display="(установи bc)"
            fi

            printf "%-30s | %-10.2f %-2s | %-10.2f %-2s | %-10s\n" \
                "$short_name" "$old_score" "$unit" "$new_score" "$unit" "$change_display"
        fi
    done

    # Анализ метрик памяти
    echo ""
    echo "🧠 СРАВНЕНИЕ ПОТРЕБЛЕНИЯ ПАМЯТИ (меньше - лучше):"
    echo "------------------------------------------------"
    echo "Метрика памяти | Старая версия | Новая версия | Изменение"
    echo "--------------|---------------|--------------|----------"

    # Извлекаем метрики памяти
    jq -r '.[] | select(.benchmark) | .benchmark as $bench | .secondaryMetrics | to_entries[] | "\($bench)|\(.key)|\(.value.score)|\(.value.scoreUnit)"' "$old_file" | grep -E "(memory|gc)" > "$RESULTS_DIR/old-memory.txt" || true

    jq -r '.[] | select(.benchmark) | .benchmark as $bench | .secondaryMetrics | to_entries[] | "\($bench)|\(.key)|\(.value.score)|\(.value.scoreUnit)"' "$new_file" | grep -E "(memory|gc)" > "$RESULTS_DIR/new-memory.txt" || true

    # Агрегируем метрики памяти по всем бенчмаркам
    declare -A old_memory_metrics
    declare -A new_memory_metrics
    declare -A memory_units

    # Старые метрики памяти
    while IFS='|' read -r benchmark metric score unit; do
        if [[ -n "$metric" && -n "$score" ]]; then
            old_memory_metrics["$metric"]=$(echo "${old_memory_metrics["$metric"]:-0} + $score" | bc -l 2>/dev/null || echo "0")
            memory_units["$metric"]="$unit"
        fi
    done < "$RESULTS_DIR/old-memory.txt"

    # Новые метрики памяти
    while IFS='|' read -r benchmark metric score unit; do
        if [[ -n "$metric" && -n "$score" ]]; then
            new_memory_metrics["$metric"]=$(echo "${new_memory_metrics["$metric"]:-0} + $score" | bc -l 2>/dev/null || echo "0")
        fi
    done < "$RESULTS_DIR/new-memory.txt"

    # Выводим ключевые метрики памяти
    important_metrics=(
        "memory.heap.used"
        "memory.total.used"
        "gc.collections"
        "gc.time"
    )

    for metric in "${important_metrics[@]}"; do
        if [[ -n "${old_memory_metrics[$metric]}" && -n "${new_memory_metrics[$metric]}" ]]; then
            old_val="${old_memory_metrics[$metric]}"
            new_val="${new_memory_metrics[$metric]}"
            unit="${memory_units[$metric]}"

            # Для агрегированных значений вычисляем среднее
            bench_count=$(jq -r '[.[] | select(.benchmark)] | length' "$old_file")
            if [[ "$bench_count" -gt 0 ]]; then
                old_val=$(echo "scale=2; $old_val / $bench_count" | bc -l 2>/dev/null || echo "$old_val")
                new_val=$(echo "scale=2; $new_val / $bench_count" | bc -l 2>/dev/null || echo "$new_val")
            fi

            if command -v bc &> /dev/null && [[ "$old_val" != "0" ]]; then
                change=$(echo "scale=2; (($old_val - $new_val) / $old_val) * 100" | bc 2>/dev/null)

                # Для памяти и GC улучшение - уменьшение значений
                if [[ "$metric" == memory* ]]; then
                    if (( $(echo "$change > 0" | bc -l 2>/dev/null) )); then
                        change_display="+${change}% ✅"
                    elif (( $(echo "$change < 0" | bc -l 2>/dev/null) )); then
                        change_display="${change}% ❌"
                    else
                        change_display="${change}% ➖"
                    fi
                else # GC метрики
                    if (( $(echo "$change < 0" | bc -l 2>/dev/null) )); then
                        change_display="${change}% ✅"
                    elif (( $(echo "$change > 0" | bc -l 2>/dev/null) )); then
                        change_display="+${change}% ❌"
                    else
                        change_display="${change}% ➖"
                    fi
                fi
            else
                change_display="N/A"
            fi

            printf "%-30s | %-10.2f %-2s | %-10.2f %-2s | %-10s\n" \
                "$metric" "$old_val" "$unit" "$new_val" "$unit" "$change_display"
        fi
    done

    # Удаляем временные файлы
    rm -f "$RESULTS_DIR/old-perf.txt" "$RESULTS_DIR/new-perf.txt" \
          "$RESULTS_DIR/old-memory.txt" "$RESULTS_DIR/new-memory.txt"
}

# Простой анализ для случаев когда jq не работает
simple_analysis() {
    local old_file="$1"
    local new_file="$2"

    echo ""
    echo "📊 ПРОСТОЙ АНАЛИЗ РЕЗУЛЬТАТОВ"
    echo "============================="

    if command -v jq &> /dev/null; then
        echo "Основные метрики производительности:"
        jq -r '.[] | "  \(.benchmark | split(".")[-1]): \(.primaryMetric.score) \(.primaryMetric.scoreUnit)"' "$old_file" 2>/dev/null | head -5

        echo ""
        echo "Метрики памяти (первые 5):"
        jq -r '.[] | .secondaryMetrics | to_entries[] | select(.key | test("memory|gc")) | "  \(.key): \(.value.score) \(.value.scoreUnit)"' "$old_file" 2>/dev/null | head -5
    else
        echo "Установите jq для просмотра результатов"
    fi
}

# Генерация отчета
generate_report() {
    echo "📈 ПОЛНЫЙ ОТЧЕТ СРАВНЕНИЯ MDClasses" > "$RESULTS_DIR/comparison-report.txt"
    echo "===================================" >> "$RESULTS_DIR/comparison-report.txt"
    echo "Старая версия: $OLD_BRANCH ($(cat $RESULTS_DIR/old-version-commit.txt))" >> "$RESULTS_DIR/comparison-report.txt"
    echo "Новая версия: $NEW_BRANCH ($(cat $RESULTS_DIR/new-version-commit.txt))" >> "$RESULTS_DIR/comparison-report.txt"
    echo "" >> "$RESULTS_DIR/comparison-report.txt"

    # Пробуем детальный анализ
    if analyze_results "$RESULTS_DIR/old-results.json" "$RESULTS_DIR/new-results.json" >> "$RESULTS_DIR/comparison-report.txt" 2>/dev/null; then
        echo "✅ Детальный анализ выполнен"
    else
        echo "⚠️  Детальный анализ не удался, используем простой"
        simple_analysis "$RESULTS_DIR/old-results.json" "$RESULTS_DIR/new-results.json" >> "$RESULTS_DIR/comparison-report.txt"
    fi
}

generate_report

echo ""
echo "✅ Сравнение завершено!"
echo "📄 Отчет: $RESULTS_DIR/comparison-report.txt"
echo "📊 Данные: $RESULTS_DIR/old-results.json и $RESULTS_DIR/new-results.json"
echo "🎯 Сравнение: $OLD_SOURCE → $NEW_SOURCE"

# Показываем краткий анализ в консоли
echo ""
echo "📋 КРАТКИЕ РЕЗУЛЬТАТЫ:"
analyze_results "$RESULTS_DIR/old-results.json" "$RESULTS_DIR/new-results.json" 2>/dev/null || \
simple_analysis "$RESULTS_DIR/old-results.json" "$RESULTS_DIR/new-results.json"

# Бенчмарки

Проект включает JMH-бенчмарки для замера производительности и потребления памяти.

## Быстрый старт

```bash
# Быстрый замер текущего состояния:
./benchmark-compare.sh HEAD HEAD --quick --label my-feature

# Полное сравнение с develop:
./benchmark-compare.sh develop HEAD
```

## Форматы отчётов

| Формат | Назначение | Как открыть |
|---|---|---|
| `build/jmh-results.json` | Машинное чтение, CI | `jq`, python |
| `benchmark-results/comparison-report.txt` | Терминал, commit message | `cat` |
| `benchmark-results/report.html` | Визуальный просмотр | браузер |
| `benchmark-results/comprehensive-analysis-with-values.png` | Быстрый взгляд | картинка |

## Использование

### Сравнение веток

```bash
./benchmark-compare.sh <old-branch> <new-branch>
```

Скрипт собирает JAR из каждой ветки, запускает JMH-бенчмарки и строит сравнение.

### Сравнение JAR-файла с веткой

```bash
./benchmark-compare.sh old-release.jar feature/optimization
```

### Кастомные фикстуры

Скопируйте и отредактируйте `.env.benchmark.example` → `.env.benchmark`:

```bash
cp .env.benchmark.example .env.benchmark
# отредактируйте пути к конфигурациям EDT/Designer
./benchmark-compare.sh develop HEAD
```

Параметры `.env.benchmark`:

- `BENCH_EDT_PATH` — путь к конфигурации EDT
- `BENCH_DESIGNER_PATH` — путь к конфигурации Designer
- `BENCH_JVM_ARGS` — JVM-аргументы (например, `-Xms2g -Xmx4g`)
- `BENCH_PROFILERS` — профилировщики JMH через запятую

### Параметры командной строки

| Параметр | Описание |
|---|---|
| `--quick` | Быстрый режим: 1 fork, 2 warmup, 3 iterations |
| `--label <name>` | Именование файлов результатов вместо new-version |
| `--jvm-args "<args>"` | JVM-аргументы для JMH |

### Анализ результатов вручную

```bash
python3 benchmark-analyze-results.py \
  --old benchmark-results/old-results.json \
  --new benchmark-results/new-results.json \
  --output benchmark-results \
  --html
```

Параметры скрипта:

| Параметр | По умолчанию | Описание |
|---|---|---|
| `--old` | `benchmark-results/old-results.json` | JSON старой версии |
| `--new` | `benchmark-results/new-results.json` | JSON новой версии |
| `--output` | `benchmark-results` | Директория результатов |
| `--html` | `true` | Генерировать HTML-отчёт |

## Описание бенчмарков

Все бенчмарки находятся в `src/jmh/java/` и используют JMH (Java Microbenchmark Harness).

### MDClassesBenchmark

Четыре сценария:

| Сценарий | Описание |
|---|---|
| EDT × SkipSupport=false | Чтение EDT-конфигурации без пропуска |
| EDT × SkipSupport=true | Чтение EDT-конфигурации с пропуском |
| Designer × SkipSupport=false | Чтение Designer-конфигурации без пропуска |
| Designer × SkipSupport=true | Чтение Designer-конфигурации с пропуском |

### MemoryProfiler

Кастомный профилировщик, измеряющий:

- Использование heap/non-heap/total памяти
- Статистику GC (количество сборок, время)
- Метрики по memory pools

Включается автоматически через `build.gradle.kts`.

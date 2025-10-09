import json
import os
import matplotlib.pyplot as plt
import numpy as np
from collections import defaultdict

def load_and_analyze():
    """Загружает и анализирует результаты с правильной фильтрацией"""

    old_file = 'benchmark-results/old-results.json'
    new_file = 'benchmark-results/new-results.json'

    print("🔍 ЗАГРУЗКА РЕЗУЛЬТАТОВ С ПРАВИЛЬНОЙ ФИЛЬТРАЦИЕЙ")
    print("=" * 60)

    # Загружаем данные
    with open(old_file, 'r') as f:
        old_data = json.load(f)
    with open(new_file, 'r') as f:
        new_data = json.load(f)

    # Извлекаем данные для графиков
    performance_data = []
    memory_data = []
    gc_data = []

    for old_bench, new_bench in zip(old_data, new_data):
        bench_name = old_bench['benchmark'].split('.')[-1]

        # Производительность
        old_time = old_bench['primaryMetric']['score']
        new_time = new_bench['primaryMetric']['score']
        change = ((old_time - new_time) / old_time) * 100

        performance_data.append({
            'name': bench_name,
            'old': old_time,
            'new': new_time,
            'change': change
        })

        # Память и GC из secondaryMetrics - УПРОЩЕННАЯ ФИЛЬТРАЦИЯ
        old_secondary = old_bench.get('secondaryMetrics', {})
        new_secondary = new_bench.get('secondaryMetrics', {})

        print(f"\n📋 Бенчмарк: {bench_name}")
        print(f"   Вторичных метрик: {len(old_secondary)}")

        for metric_name, old_metric in old_secondary.items():
            if metric_name in new_secondary:
                new_metric = new_secondary[metric_name]

                # ПРОСТАЯ ФИЛЬТРАЦИЯ - смотрим на фактические имена метрик
                print(f"   🔍 Анализ метрики: '{metric_name}'")

                # Метрики памяти - берем ВСЕ метрики кроме gc
                if 'gc.' not in metric_name and ('memory' in metric_name or 'usedmemory' in metric_name.lower()):
                    memory_data.append({
                        'benchmark': bench_name,
                        'metric': metric_name,
                        'short_metric': metric_name.split('.')[-1],
                        'category': get_memory_category(metric_name),
                        'old': old_metric['score'],
                        'new': new_metric['score'],
                        'unit': old_metric.get('scoreUnit', ''),
                        'change': ((old_metric['score'] - new_metric['score']) / old_metric['score']) * 100 if old_metric['score'] != 0 else 0
                    })
                    print(f"      ✅ ДОБАВЛЕНО В ПАМЯТЬ: {old_metric['score']} → {new_metric['score']}")

                # Метрики GC
                elif 'gc.' in metric_name:
                    gc_data.append({
                        'benchmark': bench_name,
                        'metric': metric_name,
                        'short_metric': metric_name.split('.')[-1],
                        'old': old_metric['score'],
                        'new': new_metric['score'],
                        'unit': old_metric.get('scoreUnit', ''),
                        'change': ((old_metric['score'] - new_metric['score']) / old_metric['score']) * 100 if old_metric['score'] != 0 else 0
                    })
                    print(f"      ✅ ДОБАВЛЕНО В GC: {old_metric['score']} → {new_metric['score']}")
                else:
                    print(f"      ❓ НЕ ОПРЕДЕЛЕНО: {metric_name}")

    print(f"\n📊 ИТОГО ИЗВЛЕЧЕНО:")
    print(f"🚀 Производительность: {len(performance_data)}")
    print(f"🧠 Память: {len(memory_data)}")
    print(f"🗑️  GC: {len(gc_data)}")

    # Покажем все найденные метрики памяти
    if memory_data:
        print(f"\n📋 ВСЕ метрики памяти:")
        memory_metrics = set(item['metric'] for item in memory_data)
        for i, metric in enumerate(memory_metrics):
            print(f"   {i+1}. {metric}")

    return performance_data, memory_data, gc_data

def get_memory_category(metric_name):
    """Определяет категорию метрики памяти"""
    metric_lower = metric_name.lower()
    if 'heap' in metric_lower:
        return 'heap'
    elif 'nonheap' in metric_lower:
        return 'nonheap'
    elif 'total' in metric_lower:
        return 'total'
    elif 'pool' in metric_lower:
        return 'pool'
    elif 'usedmemory' in metric_lower:
        return 'usedmemory'
    else:
        return 'other'

def add_value_labels(ax, bars, values, format_str='{:.1f}', offset_factor=0.01):
    """Добавляет значения на столбцы графика"""
    for bar, value in zip(bars, values):
        height = bar.get_height()
        ax.text(bar.get_x() + bar.get_width()/2., height + abs(height) * offset_factor,
                format_str.format(value),
                ha='center', va='bottom', fontsize=8, fontweight='bold')

def add_comparison_labels(ax, bars1, bars2, values1, values2, format_str='{:.1f}'):
    """Добавляет значения для двух наборов столбцов (старая/новая версия)"""
    # Для старых значений
    for bar, value in zip(bars1, values1):
        height = bar.get_height()
        ax.text(bar.get_x() + bar.get_width()/2., height/2,
                format_str.format(value),
                ha='center', va='center', fontsize=8, fontweight='bold', color='white')

    # Для новых значений
    for bar, value in zip(bars2, values2):
        height = bar.get_height()
        ax.text(bar.get_x() + bar.get_width()/2., height/2,
                format_str.format(value),
                ha='center', va='center', fontsize=8, fontweight='bold', color='white')

def create_visualizations(performance_data, memory_data, gc_data):
    """Создает визуализации с значениями на графиках"""

    fig = plt.figure(figsize=(16, 12))
    fig.suptitle('Сравнение производительности и потребления памяти', fontsize=16, fontweight='bold')

    # Если нет данных памяти, создаем упрощенный график
    if not memory_data and not gc_data:
        print("⚠️  Нет данных памяти и GC, создаем упрощенный график")

        # Только производительность
        if performance_data:
            # График производительности
            ax1 = plt.subplot(1, 2, 1)
            names = [p['name'] for p in performance_data]
            old_times = [p['old'] for p in performance_data]
            new_times = [p['new'] for p in performance_data]

            x = np.arange(len(names))
            width = 0.35

            bars1 = ax1.bar(x - width/2, old_times, width, label='Старая', alpha=0.7, color='blue')
            bars2 = ax1.bar(x + width/2, new_times, width, label='Новая', alpha=0.7, color='orange')
            ax1.set_title('Производительность (время, мс)', fontsize=12, fontweight='bold')
            ax1.set_ylabel('Время (мс)')
            ax1.set_xticks(x)
            ax1.set_xticklabels(names, rotation=45, ha='right')
            ax1.legend()
            ax1.grid(True, alpha=0.3)

            # Добавляем значения на столбцы
            add_comparison_labels(ax1, bars1, bars2, old_times, new_times, '{:.1f}ms')

        # Изменения производительности
        if performance_data:
            ax2 = plt.subplot(1, 2, 2)
            changes = [p['change'] for p in performance_data]
            colors = ['green' if c > 0 else 'red' for c in changes]

            bars = ax2.bar(names, changes, color=colors, alpha=0.7)
            ax2.set_title('Изменение производительности (%)', fontsize=12, fontweight='bold')
            ax2.set_ylabel('Изменение (%)')
            ax2.set_xticklabels(names, rotation=45, ha='right')
            ax2.axhline(y=0, color='black', linestyle='-', alpha=0.3)
            ax2.grid(True, alpha=0.3)

            # Добавляем значения изменений
            add_value_labels(ax2, bars, changes, '{:+.1f}%')

        plt.tight_layout()
        plt.savefig('benchmark-results/performance-only.png', dpi=150, bbox_inches='tight')
        plt.show()
        return

    # Полная версия с памятью и GC

    # 1. График производительности
    if performance_data:
        ax1 = plt.subplot(2, 3, 1)
        names = [p['name'] for p in performance_data]
        old_times = [p['old'] for p in performance_data]
        new_times = [p['new'] for p in performance_data]

        x = np.arange(len(names))
        width = 0.35

        bars1 = ax1.bar(x - width/2, old_times, width, label='Старая', alpha=0.7, color='blue')
        bars2 = ax1.bar(x + width/2, new_times, width, label='Новая', alpha=0.7, color='orange')
        ax1.set_title('Производительность (время)', fontsize=12, fontweight='bold')
        ax1.set_ylabel('Время (мс)')
        ax1.set_xticks(x)
        ax1.set_xticklabels(names, rotation=45, ha='right')
        ax1.legend()
        ax1.grid(True, alpha=0.3)

        # Добавляем значения
        add_comparison_labels(ax1, bars1, bars2, old_times, new_times, '{:.1f}ms')

    # 2. График изменений производительности
    if performance_data:
        ax2 = plt.subplot(2, 3, 2)
        changes = [p['change'] for p in performance_data]
        colors = ['green' if c > 0 else 'red' for c in changes]

        bars = ax2.bar(names, changes, color=colors, alpha=0.7)
        ax2.set_title('Изменение производительности', fontsize=12, fontweight='bold')
        ax2.set_ylabel('Изменение (%)')
        ax2.set_xticklabels(names, rotation=45, ha='right')
        ax2.axhline(y=0, color='black', linestyle='-', alpha=0.3)
        ax2.grid(True, alpha=0.3)

        # Добавляем значения изменений
        add_value_labels(ax2, bars, changes, '{:+.1f}%')

    # 3. График ключевых метрик памяти
    if memory_data:
        ax3 = plt.subplot(2, 3, 3)

        # Выбираем ключевые метрики памяти для отображения
        key_metrics = ['usedMemory.heap', 'usedMemory.total', 'memory.heap.used', 'memory.total.used']
        display_data = []

        for metric_name in key_metrics:
            for item in memory_data:
                if item['metric'] == metric_name:
                    display_data.append(item)
                    break

        # Если ключевых нет, берем первые 4
        if not display_data:
            display_data = memory_data[:4]

        metric_labels = [f"{item['short_metric']}\n({item['benchmark']})" for item in display_data]
        old_values = [item['old'] for item in display_data]
        new_values = [item['new'] for item in display_data]
        units = [item['unit'] for item in display_data]

        x = np.arange(len(metric_labels))
        width = 0.35

        bars1 = ax3.bar(x - width/2, old_values, width, label='Старая', alpha=0.7, color='blue')
        bars2 = ax3.bar(x + width/2, new_values, width, label='Новая', alpha=0.7, color='orange')
        ax3.set_title('Ключевые метрики памяти', fontsize=12, fontweight='bold')
        ax3.set_ylabel(f'Память ({units[0] if units else "MB"})')
        ax3.set_xticks(x)
        ax3.set_xticklabels(metric_labels, rotation=45, ha='right')
        ax3.legend()
        ax3.grid(True, alpha=0.3)

        # Добавляем значения
        add_comparison_labels(ax3, bars1, bars2, old_values, new_values, '{:.1f}')

    else:
        ax3 = plt.subplot(2, 3, 3)
        ax3.text(0.5, 0.5, 'Нет данных памяти',
                ha='center', va='center', transform=ax3.transAxes, fontsize=12)
        ax3.set_title('Метрики памяти', fontsize=12, fontweight='bold')

    # 4. График изменений памяти
    if memory_data:
        ax4 = plt.subplot(2, 3, 4)

        # Группируем по категориям и усредняем изменения
        categories = defaultdict(list)
        for item in memory_data:
            categories[item['category']].append(item['change'])

        avg_changes = {cat: sum(changes)/len(changes) for cat, changes in categories.items()}

        if avg_changes:
            cat_names = list(avg_changes.keys())
            changes = list(avg_changes.values())
            colors = ['green' if c > 0 else 'red' for c in changes]

            bars = ax4.bar(cat_names, changes, color=colors, alpha=0.7)
            ax4.set_title('Изменение памяти по категориям', fontsize=12, fontweight='bold')
            ax4.set_ylabel('Среднее изменение (%)')
            ax4.set_xticklabels(cat_names, rotation=45, ha='right')
            ax4.axhline(y=0, color='black', linestyle='-', alpha=0.3)
            ax4.grid(True, alpha=0.3)

            # Добавляем значения изменений
            add_value_labels(ax4, bars, changes, '{:+.1f}%')

    else:
        ax4 = plt.subplot(2, 3, 4)
        ax4.text(0.5, 0.5, 'Нет данных памяти',
                ha='center', va='center', transform=ax4.transAxes, fontsize=12)
        ax4.set_title('Память по категориям', fontsize=12, fontweight='bold')

    # 5. График GC метрик
    if gc_data:
        ax5 = plt.subplot(2, 3, 5)

        # Берем первые 4 метрики GC
        display_gc = gc_data[:4]

        metric_labels = [f"{item['short_metric']}\n({item['benchmark']})" for item in display_gc]
        old_values = [item['old'] for item in display_gc]
        new_values = [item['new'] for item in display_gc]
        units = [item['unit'] for item in display_gc]

        x = np.arange(len(metric_labels))
        width = 0.35

        bars1 = ax5.bar(x - width/2, old_values, width, label='Старая', alpha=0.7, color='blue')
        bars2 = ax5.bar(x + width/2, new_values, width, label='Новая', alpha=0.7, color='orange')
        ax5.set_title('Метрики сборщика мусора', fontsize=12, fontweight='bold')
        ax5.set_ylabel(f'Значение ({units[0] if units else ""})')
        ax5.set_xticks(x)
        ax5.set_xticklabels(metric_labels, rotation=45, ha='right')
        ax5.legend()
        ax5.grid(True, alpha=0.3)

        # Добавляем значения
        add_comparison_labels(ax5, bars1, bars2, old_values, new_values, '{:.1f}')

    else:
        ax5 = plt.subplot(2, 3, 5)
        ax5.text(0.5, 0.5, 'Нет данных GC',
                ha='center', va='center', transform=ax5.transAxes, fontsize=12)
        ax5.set_title('Метрики GC', fontsize=12, fontweight='bold')

    # 6. Сводная статистика
    ax6 = plt.subplot(2, 3, 6)
    ax6.axis('off')  # Отключаем оси

    # Собираем статистику
    stats_text = "📊 СВОДНАЯ СТАТИСТИКА\n\n"
    stats_text += f"Всего бенчмарков: {len(performance_data)}\n\n"

    if performance_data:
        perf_improvements = sum(1 for p in performance_data if p['change'] > 5)
        perf_regressions = sum(1 for p in performance_data if p['change'] < -5)
        avg_perf_change = sum(p['change'] for p in performance_data) / len(performance_data)
        stats_text += f"Производительность:\n"
        stats_text += f"• Улучшений: {perf_improvements}\n"
        stats_text += f"• Ухудшений: {perf_regressions}\n"
        stats_text += f"• Среднее: {avg_perf_change:+.1f}%\n\n"

    if memory_data:
        mem_improvements = sum(1 for m in memory_data if m['change'] > 5)
        mem_regressions = sum(1 for m in memory_data if m['change'] < -5)
        avg_mem_change = sum(m['change'] for m in memory_data) / len(memory_data)
        stats_text += f"Память:\n"
        stats_text += f"• Улучшений: {mem_improvements}\n"
        stats_text += f"• Ухудшений: {mem_regressions}\n"
        stats_text += f"• Среднее: {avg_mem_change:+.1f}%\n\n"

    if gc_data:
        gc_improvements = sum(1 for g in gc_data if g['change'] < 0)
        gc_regressions = sum(1 for g in gc_data if g['change'] > 0)
        avg_gc_change = sum(g['change'] for g in gc_data) / len(gc_data)
        stats_text += f"Сборка мусора:\n"
        stats_text += f"• Улучшений: {gc_improvements}\n"
        stats_text += f"• Ухудшений: {gc_regressions}\n"
        stats_text += f"• Среднее: {avg_gc_change:+.1f}%"

    ax6.text(0.1, 0.9, stats_text, transform=ax6.transAxes, fontsize=10,
             verticalalignment='top', linespacing=1.5, fontweight='bold')

    plt.tight_layout()
    plt.savefig('benchmark-results/comprehensive-analysis-with-values.png', dpi=150, bbox_inches='tight')
    print(f"✅ График с значениями сохранен: benchmark-results/comprehensive-analysis-with-values.png")
    plt.show()

if __name__ == "__main__":
    performance_data, memory_data, gc_data = load_and_analyze()
    create_visualizations(performance_data, memory_data, gc_data)

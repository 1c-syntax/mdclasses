import argparse
import json
import os
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import numpy as np
from collections import defaultdict

ALLOCATION_METRICS = {'+gc.alloc.rate', 'gc.alloc.rate', 'alloc.rate'}


def parse_args():
    parser = argparse.ArgumentParser(description='Анализ результатов JMH-бенчмарков')
    parser.add_argument('--old', default='benchmark-results/old-results.json',
                        help='Путь к JSON с результатами старой версии')
    parser.add_argument('--new', default='benchmark-results/new-results.json',
                        help='Путь к JSON с результатами новой версии')
    parser.add_argument('--output', default='benchmark-results',
                        help='Директория для выходных файлов')
    parser.add_argument('--html', action='store_true', default=True,
                        help='Генерировать HTML-отчёт')
    return parser.parse_args()


def load_data(old_file, new_file):
    with open(old_file, 'r') as f:
        old_data = json.load(f)
    with open(new_file, 'r') as f:
        new_data = json.load(f)
    return old_data, new_data


def extract_metrics(old_data, new_data):
    performance_data = []
    memory_data = []
    gc_data = []
    allocation_data = []

    for old_bench, new_bench in zip(old_data, new_data):
        bench_name = old_bench['benchmark'].split('.')[-1]

        old_time = old_bench['primaryMetric']['score']
        new_time = new_bench['primaryMetric']['score']
        change = ((old_time - new_time) / old_time) * 100 if old_time != 0 else 0

        performance_data.append({
            'name': bench_name,
            'old': old_time,
            'new': new_time,
            'change': change
        })

        old_secondary = old_bench.get('secondaryMetrics', {})
        new_secondary = new_bench.get('secondaryMetrics', {})

        for metric_name in old_secondary:
            if metric_name not in new_secondary:
                continue
            old_metric = old_secondary[metric_name]
            new_metric = new_secondary[metric_name]

            entry = {
                'benchmark': bench_name,
                'metric': metric_name,
                'short_metric': metric_name.split('.')[-1],
                'old': old_metric['score'],
                'new': new_metric['score'],
                'unit': old_metric.get('scoreUnit', ''),
                'change': ((old_metric['score'] - new_metric['score']) / old_metric['score']) * 100 if old_metric['score'] != 0 else 0
            }

            if 'gc.' in metric_name:
                if metric_name in ALLOCATION_METRICS or 'alloc' in metric_name.lower():
                    allocation_data.append(entry)
                else:
                    gc_data.append(entry)
            elif 'memory' in metric_name:
                memory_data.append(entry)
            elif metric_name in ALLOCATION_METRICS or 'alloc' in metric_name.lower():
                allocation_data.append(entry)

    return performance_data, memory_data, gc_data, allocation_data


def get_memory_category(metric_name):
    metric_lower = metric_name.lower()
    if 'heap' in metric_lower:
        return 'heap'
    elif 'nonheap' in metric_lower:
        return 'nonheap'
    elif 'total' in metric_lower:
        return 'total'
    elif 'pool' in metric_lower:
        return 'pool'
    else:
        return 'other'


def add_value_labels(ax, bars, values, fmt='{:.1f}', offset_factor=0.01):
    for bar, value in zip(bars, values):
        height = bar.get_height()
        ax.text(bar.get_x() + bar.get_width() / 2., height + abs(height) * offset_factor,
                fmt.format(value),
                ha='center', va='bottom', fontsize=8, fontweight='bold')


def add_comparison_labels(ax, bars1, bars2, values1, values2, fmt='{:.1f}'):
    for bar, value in zip(bars1, values1):
        height = bar.get_height()
        ax.text(bar.get_x() + bar.get_width() / 2., height / 2,
                fmt.format(value),
                ha='center', va='center', fontsize=8, fontweight='bold', color='white')
    for bar, value in zip(bars2, values2):
        height = bar.get_height()
        ax.text(bar.get_x() + bar.get_width() / 2., height / 2,
                fmt.format(value),
                ha='center', va='center', fontsize=8, fontweight='bold', color='white')


def create_visualizations(performance_data, memory_data, gc_data, allocation_data, output_dir):
    has_memory = bool(memory_data)
    has_gc = bool(gc_data)
    has_allocation = bool(allocation_data)

    if not has_memory and not has_gc and not has_allocation:
        fig = plt.figure(figsize=(12, 5))
        fig.suptitle('Сравнение производительности', fontsize=16, fontweight='bold')

        ax1 = plt.subplot(1, 2, 1)
        names = [p['name'] for p in performance_data]
        old_times = [p['old'] for p in performance_data]
        new_times = [p['new'] for p in performance_data]
        x = np.arange(len(names))
        width = 0.35
        bars1 = ax1.bar(x - width / 2, old_times, width, label='Старая', alpha=0.7, color='#4A90D9')
        bars2 = ax1.bar(x + width / 2, new_times, width, label='Новая', alpha=0.7, color='#E8A838')
        ax1.set_title('Производительность (время, мс)', fontsize=12, fontweight='bold')
        ax1.set_ylabel('Время (мс)')
        ax1.set_xticks(x)
        ax1.set_xticklabels(names, rotation=45, ha='right')
        ax1.legend()
        ax1.grid(True, alpha=0.3)
        add_comparison_labels(ax1, bars1, bars2, old_times, new_times)

        ax2 = plt.subplot(1, 2, 2)
        changes = [p['change'] for p in performance_data]
        colors = ['#27AE60' if c > 0 else '#E74C3C' for c in changes]
        bars = ax2.bar(names, changes, color=colors, alpha=0.7)
        ax2.set_title('Изменение производительности (%)', fontsize=12, fontweight='bold')
        ax2.set_ylabel('Изменение (%)')
        ax2.set_xticklabels(names, rotation=45, ha='right')
        ax2.axhline(y=0, color='black', linestyle='-', alpha=0.3)
        ax2.grid(True, alpha=0.3)
        add_value_labels(ax2, bars, changes, '{:+.1f}%')

        plt.tight_layout()
        png_path = os.path.join(output_dir, 'comprehensive-analysis-with-values.png')
        plt.savefig(png_path, dpi=150, bbox_inches='tight')
        plt.close()
        print(f"✅ График сохранён: {png_path}")
        return png_path

    n_plots = 2
    if has_memory:
        n_plots += 2
    if has_gc:
        n_plots += 1
    if has_allocation:
        n_plots += 1

    ncols = min(3, n_plots)
    nrows = (n_plots + ncols - 1) // ncols
    fig = plt.figure(figsize=(6 * ncols, 4 * nrows))
    fig.suptitle('Сравнение производительности и потребления памяти', fontsize=16, fontweight='bold')

    plot_idx = 1

    if performance_data:
        ax = plt.subplot(nrows, ncols, plot_idx)
        plot_idx += 1
        names = [p['name'] for p in performance_data]
        old_times = [p['old'] for p in performance_data]
        new_times = [p['new'] for p in performance_data]
        x = np.arange(len(names))
        width = 0.35
        bars1 = ax.bar(x - width / 2, old_times, width, label='Старая', alpha=0.7, color='#4A90D9')
        bars2 = ax.bar(x + width / 2, new_times, width, label='Новая', alpha=0.7, color='#E8A838')
        ax.set_title('Производительность (время)', fontsize=12, fontweight='bold')
        ax.set_ylabel('Время (мс)')
        ax.set_xticks(x)
        ax.set_xticklabels(names, rotation=45, ha='right')
        ax.legend()
        ax.grid(True, alpha=0.3)
        add_comparison_labels(ax, bars1, bars2, old_times, new_times)

        ax = plt.subplot(nrows, ncols, plot_idx)
        plot_idx += 1
        changes = [p['change'] for p in performance_data]
        colors = ['#27AE60' if c > 0 else '#E74C3C' for c in changes]
        bars = ax.bar(names, changes, color=colors, alpha=0.7)
        ax.set_title('Изменение производительности', fontsize=12, fontweight='bold')
        ax.set_ylabel('Изменение (%)')
        ax.set_xticklabels(names, rotation=45, ha='right')
        ax.axhline(y=0, color='black', linestyle='-', alpha=0.3)
        ax.grid(True, alpha=0.3)
        add_value_labels(ax, bars, changes, '{:+.1f}%')

    if has_memory:
        ax = plt.subplot(nrows, ncols, plot_idx)
        plot_idx += 1
        key_metrics_names = ['memory.heap.used', 'memory.total.used', 'memory.nonHeap.used']
        display_data = []
        for mn in key_metrics_names:
            for item in memory_data:
                if item['metric'] == mn:
                    display_data.append(item)
                    break
        if not display_data:
            display_data = memory_data[:4]

        metric_labels = [f"{item['short_metric']}\n({item['benchmark']})" for item in display_data]
        old_values = [item['old'] for item in display_data]
        new_values = [item['new'] for item in display_data]
        x = np.arange(len(metric_labels))
        width = 0.35
        bars1 = ax.bar(x - width / 2, old_values, width, label='Старая', alpha=0.7, color='#4A90D9')
        bars2 = ax.bar(x + width / 2, new_values, width, label='Новая', alpha=0.7, color='#E8A838')
        ax.set_title('Ключевые метрики памяти', fontsize=12, fontweight='bold')
        ax.set_ylabel('Память (MB)')
        ax.set_xticks(x)
        ax.set_xticklabels(metric_labels, rotation=45, ha='right')
        ax.legend()
        ax.grid(True, alpha=0.3)
        add_comparison_labels(ax, bars1, bars2, old_values, new_values)

        ax = plt.subplot(nrows, ncols, plot_idx)
        plot_idx += 1
        categories = defaultdict(list)
        for item in memory_data:
            cat = get_memory_category(item['metric'])
            categories[cat].append(item['change'])
        avg_changes = {cat: sum(changes) / len(changes) for cat, changes in categories.items()}
        if avg_changes:
            cat_names = list(avg_changes.keys())
            changes_vals = list(avg_changes.values())
            colors = ['#27AE60' if c > 0 else '#E74C3C' for c in changes_vals]
            bars = ax.bar(cat_names, changes_vals, color=colors, alpha=0.7)
            ax.set_title('Изменение памяти по категориям', fontsize=12, fontweight='bold')
            ax.set_ylabel('Среднее изменение (%)')
            ax.set_xticklabels(cat_names, rotation=45, ha='right')
            ax.axhline(y=0, color='black', linestyle='-', alpha=0.3)
            ax.grid(True, alpha=0.3)
            add_value_labels(ax, bars, changes_vals, '{:+.1f}%')

    if has_gc:
        ax = plt.subplot(nrows, ncols, plot_idx)
        plot_idx += 1
        display_gc = gc_data[:4]
        metric_labels = [f"{item['short_metric']}\n({item['benchmark']})" for item in display_gc]
        old_values = [item['old'] for item in display_gc]
        new_values = [item['new'] for item in display_gc]
        x = np.arange(len(metric_labels))
        width = 0.35
        bars1 = ax.bar(x - width / 2, old_values, width, label='Старая', alpha=0.7, color='#4A90D9')
        bars2 = ax.bar(x + width / 2, new_values, width, label='Новая', alpha=0.7, color='#E8A838')
        ax.set_title('Метрики GC', fontsize=12, fontweight='bold')
        ax.set_ylabel('Значение')
        ax.set_xticks(x)
        ax.set_xticklabels(metric_labels, rotation=45, ha='right')
        ax.legend()
        ax.grid(True, alpha=0.3)
        add_comparison_labels(ax, bars1, bars2, old_values, new_values)

    if has_allocation:
        ax = plt.subplot(nrows, ncols, plot_idx)
        plot_idx += 1
        display_alloc = allocation_data[:4]
        metric_labels = [f"{item['short_metric']}\n({item['benchmark']})" for item in display_alloc]
        old_values = [item['old'] for item in display_alloc]
        new_values = [item['new'] for item in display_alloc]
        x = np.arange(len(metric_labels))
        width = 0.35
        bars1 = ax.bar(x - width / 2, old_values, width, label='Старая', alpha=0.7, color='#4A90D9')
        bars2 = ax.bar(x + width / 2, new_values, width, label='Новая', alpha=0.7, color='#E8A838')
        ax.set_title('Allocation Rate (MB/sec)', fontsize=12, fontweight='bold')
        ax.set_ylabel('MB/sec')
        ax.set_xticks(x)
        ax.set_xticklabels(metric_labels, rotation=45, ha='right')
        ax.legend()
        ax.grid(True, alpha=0.3)
        add_comparison_labels(ax, bars1, bars2, old_values, new_values)

    # Сводная статистика
    if plot_idx <= nrows * ncols:
        ax = plt.subplot(nrows, ncols, plot_idx)
        plot_idx += 1
    else:
        ax = None

    if ax:
        ax.axis('off')
        stats_text = "📊 СВОДНАЯ СТАТИСТИКА\n\n"
        stats_text += f"Всего бенчмарков: {len(performance_data)}\n\n"

        if performance_data:
            perf_improvements = sum(1 for p in performance_data if p['change'] > 5)
            perf_regressions = sum(1 for p in performance_data if p['change'] < -5)
            avg_perf_change = sum(p['change'] for p in performance_data) / len(performance_data)
            stats_text += "Производительность:\n"
            stats_text += f"• Улучшений: {perf_improvements}\n"
            stats_text += f"• Ухудшений: {perf_regressions}\n"
            stats_text += f"• Среднее: {avg_perf_change:+.1f}%\n\n"

        if memory_data:
            mem_improvements = sum(1 for m in memory_data if m['change'] > 5)
            mem_regressions = sum(1 for m in memory_data if m['change'] < -5)
            avg_mem_change = sum(m['change'] for m in memory_data) / len(memory_data)
            stats_text += "Память:\n"
            stats_text += f"• Улучшений: {mem_improvements}\n"
            stats_text += f"• Ухудшений: {mem_regressions}\n"
            stats_text += f"• Среднее: {avg_mem_change:+.1f}%\n\n"

        if gc_data:
            gc_improvements = sum(1 for g in gc_data if g['change'] > 0)
            gc_regressions = sum(1 for g in gc_data if g['change'] < 0)
            avg_gc_change = sum(g['change'] for g in gc_data) / len(gc_data)
            stats_text += "Сборка мусора:\n"
            stats_text += f"• Улучшений: {gc_improvements}\n"
            stats_text += f"• Ухудшений: {gc_regressions}\n"
            stats_text += f"• Среднее: {avg_gc_change:+.1f}%"

        if allocation_data:
            alloc_improvements = sum(1 for a in allocation_data if a['change'] > 0)
            alloc_regressions = sum(1 for a in allocation_data if a['change'] < 0)
            avg_alloc_change = sum(a['change'] for a in allocation_data) / len(allocation_data)
            stats_text += "\n\nAllocation Rate:\n"
            stats_text += f"• Улучшений: {alloc_improvements}\n"
            stats_text += f"• Ухудшений: {alloc_regressions}\n"
            stats_text += f"• Среднее: {avg_alloc_change:+.1f}%"

        ax.text(0.1, 0.9, stats_text, transform=ax.transAxes, fontsize=10,
                verticalalignment='top', linespacing=1.5, fontweight='bold')

    plt.tight_layout()
    png_path = os.path.join(output_dir, 'comprehensive-analysis-with-values.png')
    plt.savefig(png_path, dpi=150, bbox_inches='tight')
    plt.close()
    print(f"✅ График сохранён: {png_path}")
    return png_path


def generate_html_report(performance_data, memory_data, gc_data, allocation_data, output_dir, png_path):
    rows = []
    for p in performance_data:
        cls = 'positive' if p['change'] > 0 else ('negative' if p['change'] < 0 else 'neutral')
        rows.append(f"""<tr>
            <td>{p['name']}</td>
            <td>{p['old']:.3f} ms</td>
            <td>{p['new']:.3f} ms</td>
            <td class="{cls}">{p['change']:+.2f}%</td>
        </tr>""")

    mem_rows = ""
    for m in memory_data:
        cls = 'positive' if m['change'] > 0 else ('negative' if m['change'] < 0 else 'neutral')
        mem_rows += f"""<tr>
            <td>{m['benchmark']}</td>
            <td>{m['metric']}</td>
            <td>{m['old']:.2f} {m['unit']}</td>
            <td>{m['new']:.2f} {m['unit']}</td>
            <td class="{cls}">{m['change']:+.2f}%</td>
        </tr>"""

    gc_rows = ""
    for g in gc_data:
        cls = 'positive' if g['change'] > 0 else ('negative' if g['change'] < 0 else 'neutral')
        gc_rows += f"""<tr>
            <td>{g['benchmark']}</td>
            <td>{g['metric']}</td>
            <td>{g['old']:.2f} {g['unit']}</td>
            <td>{g['new']:.2f} {g['unit']}</td>
            <td class="{cls}">{g['change']:+.2f}%</td>
        </tr>"""

    alloc_rows = ""
    for a in allocation_data:
        cls = 'positive' if a['change'] > 0 else ('negative' if a['change'] < 0 else 'neutral')
        alloc_rows += f"""<tr>
            <td>{a['benchmark']}</td>
            <td>{a['metric']}</td>
            <td>{a['old']:.2f} {a['unit']}</td>
            <td>{a['new']:.2f} {a['unit']}</td>
            <td class="{cls}">{a['change']:+.2f}%</td>
        </tr>"""

    png_rel = os.path.relpath(png_path, output_dir)

    perf_avg = sum(p['change'] for p in performance_data) / len(performance_data) if performance_data else 0
    mem_avg = sum(m['change'] for m in memory_data) / len(memory_data) if memory_data else 0
    gc_avg = sum(g['change'] for g in gc_data) / len(gc_data) if gc_data else 0
    alloc_avg = sum(a['change'] for a in allocation_data) / len(allocation_data) if allocation_data else 0

    html = f"""<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Benchmark Comparison Report</title>
<style>
    body {{ font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; margin: 20px; background: #f5f5f5; color: #333; }}
    h1, h2, h3 {{ color: #2c3e50; }}
    .container {{ max-width: 1400px; margin: 0 auto; }}
    .chart {{ background: #fff; border-radius: 8px; padding: 20px; margin: 20px 0; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }}
    .chart img {{ max-width: 100%; height: auto; }}
    table {{ width: 100%; border-collapse: collapse; background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }}
    th, td {{ padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }}
    th {{ background: #4A90D9; color: #fff; font-weight: 600; }}
    tr:hover {{ background: #f0f7ff; }}
    .positive {{ color: #27AE60; font-weight: bold; }}
    .negative {{ color: #E74C3C; font-weight: bold; }}
    .neutral {{ color: #f39c12; }}
    .summary {{ display: flex; gap: 20px; flex-wrap: wrap; }}
    .summary-card {{ background: #fff; border-radius: 8px; padding: 20px; flex: 1; min-width: 200px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }}
    .summary-card h3 {{ margin-top: 0; }}
    .summary-card .value {{ font-size: 2em; font-weight: bold; }}
    .summary-card .value.good {{ color: #27AE60; }}
    .summary-card .value.bad {{ color: #E74C3C; }}
</style>
</head>
<body>
<div class="container">
<h1>📊 Benchmark Comparison Report</h1>

<div class="summary">
    <div class="summary-card">
        <h3>🚀 Производительность</h3>
        <div class="value {'good' if perf_avg > 0 else 'bad'}">{perf_avg:+.1f}%</div>
        <div>Среднее изменение</div>
    </div>
    <div class="summary-card">
        <h3>🧠 Память</h3>
        <div class="value {'good' if mem_avg > 0 else 'bad'}">{mem_avg:+.1f}%</div>
        <div>Среднее изменение</div>
    </div>
    <div class="summary-card">
        <h3>🗑️ GC</h3>
        <div class="value {'good' if gc_avg > 0 else 'bad'}">{gc_avg:+.1f}%</div>
        <div>Среднее изменение</div>
    </div>
    <div class="summary-card">
        <h3>📦 Allocation</h3>
        <div class="value {'good' if alloc_avg > 0 else 'bad'}">{alloc_avg:+.1f}%</div>
        <div>Среднее изменение</div>
    </div>
</div>

<div class="chart">
    <h2>График сравнения</h2>
    <img src="{png_rel}" alt="Benchmark chart">
</div>

<div class="chart">
    <h2>🚀 Производительность</h2>
    <table>
        <thead><tr><th>Бенчмарк</th><th>Старая версия</th><th>Новая версия</th><th>Изменение</th></tr></thead>
        <tbody>{"".join(rows)}</tbody>
    </table>
</div>
"""

    if memory_data:
        html += f"""<div class="chart">
        <h2>🧠 Память</h2>
        <table>
            <thead><tr><th>Бенчмарк</th><th>Метрика</th><th>Старая версия</th><th>Новая версия</th><th>Изменение</th></tr></thead>
            <tbody>{mem_rows}</tbody>
        </table>
    </div>"""

    if gc_data:
        html += f"""<div class="chart">
        <h2>🗑️ Сборка мусора</h2>
        <table>
            <thead><tr><th>Бенчмарк</th><th>Метрика</th><th>Старая версия</th><th>Новая версия</th><th>Изменение</th></tr></thead>
            <tbody>{gc_rows}</tbody>
        </table>
    </div>"""

    if allocation_data:
        html += f"""<div class="chart">
        <h2>📦 Allocation Rate</h2>
        <table>
            <thead><tr><th>Бенчмарк</th><th>Метрика</th><th>Старая версия</th><th>Новая версия</th><th>Изменение</th></tr></thead>
            <tbody>{alloc_rows}</tbody>
        </table>
    </div>"""

    html += "</div></body></html>"

    html_path = os.path.join(output_dir, 'report.html')
    with open(html_path, 'w', encoding='utf-8') as f:
        f.write(html)
    print(f"✅ HTML-отчёт сохранён: {html_path}")


def print_report(performance_data, memory_data, gc_data, allocation_data):
    print("\n🚀 СРАВНЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ:")
    print(f"{'Бенчмарк':<35} {'Старая':<12} {'Новая':<12} {'Изменение':<10}")
    print("-" * 70)
    for p in performance_data:
        cls = '✅' if p['change'] > 0 else ('❌' if p['change'] < 0 else '➖')
        print(f"{p['name']:<35} {p['old']:<8.2f}ms  {p['new']:<8.2f}ms  {p['change']:+.2f}% {cls}")

    if memory_data:
        print("\n🧠 СРАВНЕНИЕ ПАМЯТИ:")
        print(f"{'Метрика':<35} {'Старая':<12} {'Новая':<12} {'Изменение':<10}")
        print("-" * 70)
        for m in memory_data:
            print(f"{m['metric']:<35} {m['old']:<8.2f}{m['unit']:<4} {m['new']:<8.2f}{m['unit']:<4} {m['change']:+.2f}%")

    if gc_data:
        print("\n🗑️ СРАВНЕНИЕ GC:")
        print(f"{'Метрика':<35} {'Старая':<12} {'Новая':<12} {'Изменение':<10}")
        print("-" * 70)
        for g in gc_data:
            print(f"{g['metric']:<35} {g['old']:<8.2f}{g['unit']:<4} {g['new']:<8.2f}{g['unit']:<4} {g['change']:+.2f}%")

    if allocation_data:
        print("\n📦 ALLOCATION RATE (MB/sec):")
        print(f"{'Метрика':<35} {'Старая':<12} {'Новая':<12} {'Изменение':<10}")
        print("-" * 70)
        for a in allocation_data:
            print(f"{a['metric']:<35} {a['old']:<8.2f}{a['unit']:<4} {a['new']:<8.2f}{a['unit']:<4} {a['change']:+.2f}%")


def main():
    args = parse_args()
    os.makedirs(args.output, exist_ok=True)

    old_data, new_data = load_data(args.old, args.new)
    performance_data, memory_data, gc_data, allocation_data = extract_metrics(old_data, new_data)

    print_report(performance_data, memory_data, gc_data, allocation_data)

    png_path = create_visualizations(performance_data, memory_data, gc_data, allocation_data, args.output)

    if args.html:
        generate_html_report(performance_data, memory_data, gc_data, allocation_data, args.output, png_path)


if __name__ == '__main__':
    main()

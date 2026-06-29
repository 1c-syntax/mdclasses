# Benchmarks

This project includes JMH benchmarks for measuring performance and memory usage.

## Quick Start

```bash
# Quick single measurement:
./benchmark-compare.sh HEAD HEAD --quick --label my-feature

# Full comparison with develop:
./benchmark-compare.sh develop HEAD
```

## Report Formats

| Format | Purpose | How to Open |
|---|---|---|
| `build/jmh-results.json` | Machine reading, CI | `jq`, python |
| `benchmark-results/comparison-report.txt` | Terminal, commit message | `cat` |
| `benchmark-results/report.html` | Visual review | browser |
| `benchmark-results/comprehensive-analysis-with-values.png` | Quick glance | image viewer |

## Usage

### Branch comparison

```bash
./benchmark-compare.sh <old-branch> <new-branch>
```

### Compare JAR with branch

```bash
./benchmark-compare.sh old-release.jar feature/optimization
```

### Custom fixtures

Copy and edit `.env.benchmark.example` → `.env.benchmark`:

```bash
cp .env.benchmark.example .env.benchmark
# edit EDT/Designer paths
./benchmark-compare.sh develop HEAD
```

### CLI options

| Option | Description |
|---|---|
| `--quick` | Quick mode: 1 fork, 2 warmup, 3 iterations |
| `--label <name>` | Label result files instead of new-version |
| `--jvm-args "<args>"` | JVM arguments for JMH |

For details see the [Russian documentation](../ru/benchmark.md) (in Russian).

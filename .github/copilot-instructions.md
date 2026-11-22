# MDClasses Repository Instructions

## Overview

MDClasses is a Java library for reading and analyzing metadata from 1C:Enterprise 8 platform. The library works with configurations exported in XML format (via configurator or merge/comparison tools) or in EDT format.

**Project Type**: Java library  
**Build Tool**: Gradle with Kotlin DSL  
**Primary Language**: Java 21+  
**Target Runtime**: JVM (Java 21, 25 tested)  
**Repository Size**: Medium-sized Java project with extensive test coverage

## Build and Test Instructions

### Prerequisites
- Java 21 or higher (tested on Java 21, and 25)
- Git with LFS support for test data
- No additional environment setup required

### Bootstrap and Build Commands

**Always initialize Git submodules and LFS when cloning:**
```bash
git submodule update --init --recursive
git lfs pull
```

**Build the project:**
```bash
./gradlew build
```

**Run tests only:**
```bash
./gradlew test
```

**Run quality checks (includes tests and code quality):**
```bash
./gradlew check
```

**Generate Javadoc:**
```bash
./gradlew javadoc
```

**Run all precommit checks:**
```bash
./gradlew precommit
```
This runs tests and updates license headers.

**Update license headers:**
```bash
./gradlew updateLicenses
```

**Clean build artifacts:**
```bash
./gradlew clean
```

### Build Notes
- The build uses Gradle wrapper (`./gradlew` on Unix, `gradlew.bat` on Windows)
- Always use the wrapper to ensure consistent Gradle version
- Test results are located in `build/reports/tests/test/`
- JaCoCo coverage reports are in `build/reports/jacoco/test/`
- Build time: typically 1-2 minutes for full build with tests

### Common Build Issues
- If tests fail due to missing test data, ensure Git LFS is properly initialized
- Lombok annotations are used extensively - IDE should have Lombok plugin installed
- If seeing "out of memory" errors, set `org.gradle.jvmargs=-Xmx2048m` in `gradle.properties`

## Project Structure

### Key Directories
- `src/main/java/` - Main source code
  - `com/github/_1c_syntax/bsl/reader/` - Core readers for different formats
  - `com/github/_1c_syntax/bsl/mdo/` - Metadata object model classes
  - `com/github/_1c_syntax/bsl/mdclasses/` - Main library API
- `src/test/java/` - Test code
- `src/test/resources/` - Test fixtures and sample configurations
- `src/main/resources/` - XSD schemas and converters
- `docs/` - Documentation (MkDocs format, Russian and English)
- `.github/workflows/` - CI/CD pipelines

### Key Configuration Files
- `build.gradle.kts` - Main build configuration (Gradle Kotlin DSL)
- `settings.gradle.kts` - Gradle settings
- `gradle.properties` - Build properties
- `lombok.config` - Lombok configuration
- `mkdocs.yml` / `mkdocs.en.yml` - Documentation configuration
- `.github/workflows/java-ci.yml` - Main CI pipeline
- `.github/workflows/gh-pages.yml` - Documentation deployment
- `license/HEADER.txt` - License header template

### Main Entry Points
- `com.github._1c_syntax.bsl.mdclasses.MDClasses` - Main API class for loading configurations
- `com.github._1c_syntax.bsl.mdo.Configuration` - Root configuration object
- `com.github._1c_syntax.bsl.reader.designer.DesignerReader` - XML format reader
- `com.github._1c_syntax.bsl.reader.edt.EDTReader` - EDT format reader

## CI/CD Validation

### GitHub Actions Workflows
1. **Java CI** (`.github/workflows/java-ci.yml`)
   - Runs on: push and pull request
   - Tests matrix: Java 21, 25 on Ubuntu, Windows, macOS
   - Command: `./gradlew check --stacktrace`
   - Duration: ~5-10 minutes
   - Artifacts: Test results uploaded for all matrix combinations

2. **GitHub Pages** (`.github/workflows/gh-pages.yml`)
   - Runs on: push to master/develop when docs change
   - Builds Javadoc and MkDocs documentation
   - Requires Python 3.10+ and Java 21

### Pre-commit Validation Steps
Before submitting a PR, ensure:
1. All tests pass: `./gradlew test`
2. Code quality checks pass: `./gradlew check`
3. License headers are up to date: `./gradlew updateLicenses`
4. If touching docs, verify locally: `pip install mkdocs mkdocs-material pygments-bsl && mkdocs serve`

## Code Conventions

### Java Code Style
- Target Java 21, use modern Java features where appropriate
- Use Lombok annotations for reducing boilerplate (`@Getter`, `@Setter`, `@Builder`, etc.)
- Package naming: `com.github._1c_syntax.bsl.*`
- Follow standard Java naming conventions
- UTF-8 encoding for all files
- License headers required on all Java files (automatically updated via Gradle task)

### Project-Specific Patterns
- This is a **read-only** library - it does not modify configurations
- Metadata objects follow 1C:Enterprise naming and structure
- XStream is used for XML serialization/deserialization
- Use `@Nullable` and `@NonNull` annotations from `org.jspecify`
- Converters pattern used for transforming between different representations
- Extensive use of ClassGraph for runtime class discovery

### Testing Guidelines
- JUnit 5 is used for testing
- AssertJ for fluent assertions
- Test resources include real 1C configuration samples
- Tests are organized by functionality/reader type
- Mock objects avoided in favor of integration tests with real data

## Dependencies

### Key Runtime Dependencies
- `bsl-common-library` - Common 1C:Enterprise language utilities
- `utils` - General utilities
- `supportconf` - Support configuration handling
- XStream - XML processing
- Apache Commons Collections 4 - Collection utilities
- Commons IO - File operations
- ClassGraph - Runtime class discovery
- SLF4J - Logging facade

### Test Dependencies
- JUnit 5 - Testing framework
- AssertJ - Fluent assertions
- JSONAssert - JSON comparison in tests
- Reload4j - Logging implementation for tests

## Additional Notes

### Working with 1C:Enterprise
- The library handles two main formats:
  1. **Designer (XML)** format - exported via 1C:Enterprise configurator
  2. **EDT** format - modern development environment format
- Metadata structure mirrors 1C:Enterprise concepts (Catalogs, Documents, Registers, etc.)
- Russian language is primary for domain concepts (alternate English names often available)

### Documentation
- Dual-language documentation (Russian primary, English available)
- Javadoc is comprehensive and should be maintained
- MkDocs for user-facing documentation
- Update docs when changing public API

### Performance
- Benchmark tools available (`src/jmh/`)
- JMH benchmarks for performance testing
- Results stored in `build/jmh-results.json`

### Integration
- Library is designed to be used by BSL Language Server
- Maven Central releases for public consumption
- Snapshot builds deployed to Sonatype for testing

## Trust These Instructions

These instructions have been validated against the current codebase. Trust them as accurate and only search for additional information if something is incomplete or contradicts observed behavior.

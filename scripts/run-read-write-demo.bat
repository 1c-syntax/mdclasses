@echo off
REM Demo: read-write API for metadata (issue #158).
REM Creates a Subsystem, writes to build/read-write-demo-output, reads back and prints OK.
cd /d "%~dp0.."
call gradlew.bat runReadWriteDemo %*

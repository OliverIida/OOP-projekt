@echo off
setlocal enabledelayedexpansion

REM Tuvasta JAVA_HOME automaatselt, kui see pole seatud.
if not defined JAVA_HOME (
    for /f "tokens=2 delims==" %%a in ('java -XshowSettings:properties -version 2^>^&1 ^| findstr /c:"java.home"') do (
        set "JH=%%a"
    )
    if defined JH (
        set "JAVA_HOME=!JH:~1!"
    )
)

if not defined JAVA_HOME (
    echo ERROR: Java pole leitav PATH-ist.
    echo Veendu, et Java 17+ on installitud.
    exit /b 1
)

echo Kasutan JAVA_HOME=%JAVA_HOME%
call "%~dp0mvnw.cmd" javafx:run %*

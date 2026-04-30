@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

SET "BASE_DIR=%~dp0"
IF "%BASE_DIR:~-1%"=="\" SET "BASE_DIR=%BASE_DIR:~0,-1%"
SET "WRAPPER_DIR=%BASE_DIR%\.mvn\wrapper"
SET "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
SET "WRAPPER_PROPERTIES=%WRAPPER_DIR%\maven-wrapper.properties"

IF NOT EXIST "%WRAPPER_JAR%" (
  FOR /F "tokens=1,* delims==" %%A IN ('TYPE "%WRAPPER_PROPERTIES%"') DO (
    IF "%%A"=="wrapperUrl" SET "WRAPPER_URL=%%B"
  )

  IF NOT EXIST "%WRAPPER_DIR%" MKDIR "%WRAPPER_DIR%"
  powershell -Command "Invoke-WebRequest -UseBasicParsing -Uri '!WRAPPER_URL!' -OutFile '%WRAPPER_JAR%'"
)

java "-Dmaven.multiModuleProjectDirectory=%BASE_DIR%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*

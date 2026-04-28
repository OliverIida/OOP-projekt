#!/bin/sh
set -eu

BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_PROPERTIES="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$WRAPPER_JAR" ]; then
  if ! command -v curl >/dev/null 2>&1; then
    echo "curl is required to download Maven Wrapper." >&2
    exit 1
  fi

  WRAPPER_URL=$(grep '^wrapperUrl=' "$WRAPPER_PROPERTIES" | cut -d= -f2-)
  mkdir -p "$WRAPPER_DIR"
  curl -fsSL "$WRAPPER_URL" -o "$WRAPPER_JAR"
fi

exec java \
  -Dmaven.multiModuleProjectDirectory="$BASE_DIR" \
  -cp "$WRAPPER_JAR" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"

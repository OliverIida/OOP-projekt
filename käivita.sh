#!/bin/bash
# Tuvasta JAVA_HOME automaatselt, kui see pole seatud.
if [ -z "$JAVA_HOME" ]; then
    JAVA_HOME=$(java -XshowSettings:properties -version 2>&1 | awk -F'= ' '/java.home/ {print $2}')
    export JAVA_HOME
fi

if [ -z "$JAVA_HOME" ]; then
    echo "ERROR: Java pole leitav PATH-ist."
    echo "Veendu, et Java 17+ on installitud."
    exit 1
fi

echo "Kasutan JAVA_HOME=$JAVA_HOME"
DIR="$(cd "$(dirname "$0")" && pwd)"
MVNW="$DIR/mvnw"

if [ ! -f "$MVNW" ]; then
    echo "ERROR: Maven wrapperit ei leitud: $MVNW"
    exit 1
fi

if [ -x "$MVNW" ]; then
    exec "$MVNW" javafx:run "$@"
else
    echo "Märkus: mvnw ei ole käivitatav; kasutan 'sh mvnw'."
    exec sh "$MVNW" javafx:run "$@"
fi

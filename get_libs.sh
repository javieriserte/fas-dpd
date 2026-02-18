#!/usr/bin/env bash

set -e

VERSION="1.8.2"
GROUP_PATH="org/junit/platform"
ARTIFACT="junit-platform-console-standalone"
JAR_NAME="${ARTIFACT}-${VERSION}.jar"

BASE_URL="https://repo1.maven.org/maven2"
URL="${BASE_URL}/${GROUP_PATH}/${ARTIFACT}/${VERSION}/${JAR_NAME}"

LIB_DIR="lib"

echo "Descargando ${JAR_NAME}..."
mkdir -p "$LIB_DIR"

if command -v curl >/dev/null 2>&1; then
    curl -L -o "${LIB_DIR}/${JAR_NAME}" "$URL"
elif command -v wget >/dev/null 2>&1; then
    wget -O "${LIB_DIR}/${JAR_NAME}" "$URL"
else
    echo "Error: necesitas curl o wget instalado."
    exit 1
fi

echo "Descarga completada en ${LIB_DIR}/${JAR_NAME}"

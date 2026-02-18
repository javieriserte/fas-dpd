#!/usr/bin/env bash
set -euo pipefail

root_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$root_dir"

if ! command -v javac >/dev/null 2>&1; then
  echo "Error: javac not found in PATH. Install JDK 15+ and retry." >&2
  exit 1
fi

junit_jar="$root_dir/lib/junit-platform-console-standalone-1.8.2.jar"
if [[ ! -f "$junit_jar" ]]; then
  echo "Error: missing $junit_jar" >&2
  exit 1
fi

out_dir="$root_dir/bin-test"
mkdir -p "$out_dir"

classpath="$root_dir/lib/cmdGetArg_2.1.2.jar:$junit_jar"

argfile="$(mktemp)"
trap 'rm -f "$argfile"' EXIT

find "$root_dir/src" -type f -name '*.java' -print > "$argfile"

if [[ ! -s "$argfile" ]]; then
  echo "Error: no Java sources found under src." >&2
  exit 1
fi

javac \
  -d "$out_dir" \
  -cp "$classpath" \
  @"$argfile"

if [[ -d "$root_dir/src/resources" ]]; then
  cp -a "$root_dir/src/resources/." "$out_dir/"
fi

java -jar "$junit_jar" --class-path "$out_dir:$root_dir/lib/cmdGetArg_2.1.2.jar" --scan-class-path


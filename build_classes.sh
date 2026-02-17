#!/usr/bin/env bash
set -euo pipefail

root_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$root_dir"

if ! command -v javac >/dev/null 2>&1; then
  echo "Error: javac not found in PATH. Install JDK 15+ and retry." >&2
  exit 1
fi

out_dir="$root_dir/bin"
mkdir -p "$out_dir"

classpath="$root_dir/lib/cmdGetArg_2.1.2.jar"
if [[ -f "$root_dir/lib/junit-platform-console-standalone-1.8.2.jar" ]]; then
  classpath="$classpath:$root_dir/lib/junit-platform-console-standalone-1.8.2.jar"
fi

# If JUnit is missing, skip test sources to avoid compilation failures.
if [[ -f "$root_dir/lib/junit-platform-console-standalone-1.8.2.jar" ]]; then
  src_paths=("$root_dir/src")
else
  src_paths=("$root_dir/src" -not -path "$root_dir/src/tests/*")
fi

# Collect sources into an argfile to avoid command length limits.
argfile="$(mktemp)"
trap 'rm -f "$argfile"' EXIT

# shellcheck disable=SC2068
find "${src_paths[@]}" -type f -name '*.java' -print > "$argfile"

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

echo "Compiled classes to $out_dir"

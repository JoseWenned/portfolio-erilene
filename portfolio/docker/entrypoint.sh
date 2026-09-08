#!/bin/sh
set -eu

if [ -n "${DATABASE_URL:-}" ]; then
    case "$DATABASE_URL" in
        postgres://*)
            export SPRING_DATASOURCE_URL="jdbc:postgresql://${DATABASE_URL#postgres://}"
            ;;
        postgresql://*)
            export SPRING_DATASOURCE_URL="jdbc:${DATABASE_URL}"
            ;;
    esac
fi

exec sh -c "java ${JAVA_OPTS} -Dserver.port=${PORT:-8080} -jar /app/app.jar"
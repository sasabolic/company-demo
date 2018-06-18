#!/usr/bin/env bash

echo "***********************************"
echo "Building company-demo docker image..."
echo "***********************************"

./gradlew clean buildDocker

echo "***********************************"
echo "Run docker compose..."
echo "***********************************"

docker-compose up
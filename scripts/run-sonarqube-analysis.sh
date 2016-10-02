#!/bin/bash

# avoid virtual desktop jumps at mac terminal on test run
export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'

./gradlew clean :app:assembleDebug :app:testDebugUnitTest :app:sonarqube

echo "SonarQube reports: http://docker:9000"

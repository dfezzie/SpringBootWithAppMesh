#!/bin/bash

set -ex

if [ -z AWS_ACCOUNT_ID ]; then
    echo "AWS_ACCOUNT_ID environment variable is not set"
    exit 1
fi

if [ -z AWS_DEFAULT_REGION ]; then
    echo "AWS_DEFAULT_REGION environment variable is not set"
    exit 1
fi

if [ -z COLOR_TELLER_IMAGE_NAME ]; then
    echo "COLOR_TELLER_IMAGE_NAME environment variable is not set"
    exit 1
fi

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

# Run commands from DIR
pushd $DIR
# Build with gradle
$DIR/gradlew build

# Create build directory and unpack fat jar
mkdir -p $DIR/build/dependency && (cd $DIR/build/dependency; jar -xf $DIR/build/libs/*.jar)

IMAGE="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${COLOR_TELLER_IMAGE_NAME}:latest"

# Build Docker Image
docker build -t $IMAGE $DIR --build-arg DEPENDENCY=build/dependency

# Push image to ECR
$(aws ecr get-login --no-include-email)
docker push $IMAGE
# Return to previous directory
popd

#!/bin/bash
IMAGE_NAME="scheduler-service"
TAG="dev"
DOCKERHUB_NAME="itjobmarketanalytics"
FULL_NAME="${DOCKERHUB_NAME}/${IMAGE_NAME}:${TAG}"
docker build -t "${FULL_NAME}" .
docker push "${FULL_NAME}"
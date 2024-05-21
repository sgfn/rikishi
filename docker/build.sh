#!/bin/bash

cd ../frontend || exit

# npm install

cd ..

docker build -t rikishi-docker -f docker/Dockerfile .
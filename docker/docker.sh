#!/bin/bash

start_backend() {
    cd /app/rikishi/backend || exit
    ./gradlew run
}

start_frontend() {
    cd /app/rikishi/frontend || exit
    npm start
}

start_backend & start_frontend

wait
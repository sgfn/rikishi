#!/bin/bash

docker run -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=$DISPLAY -h $HOSTNAME -p 8080:8080 rikishi-docker
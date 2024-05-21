FROM fedora:40
WORKDIR /app

COPY docker/docker-init.sh .
COPY docker/docker.sh .

COPY frontend rikishi/frontend
COPY backend rikishi/backend

RUN chmod +x docker-init.sh docker.sh
RUN ./docker-init.sh

# npm install takes forever inside docker
# so use prebuilt modules instead
# COPY frontend/node_modules node_modules

USER user

EXPOSE 8080

ENTRYPOINT ["/app/docker.sh"]
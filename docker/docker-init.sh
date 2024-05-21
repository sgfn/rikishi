#!/bin/bash

echo "=== Docker Init ==="

ls rikishi/frontend

sleep 5s

dnf install -y \
    java-17-openjdk \
    nodejs

useradd -d /app user

# electron should not be started as root
chown -R user:user /app

# The SUID sandbox helper binary was found, but is not configured correctly.
# Rather than run without sandboxing I'm aborting now. You need to make sure that
# /app/rikishi/frontend/node_modules/electron/dist/chrome-sandbox is owned by root
# and has mode 4755
chown root /app/rikishi/frontend/node_modules/electron/dist/chrome-sandbox
chmod 4755 /app/rikishi/frontend/node_modules/electron/dist/chrome-sandbox
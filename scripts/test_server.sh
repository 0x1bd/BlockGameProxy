#!/bin/bash

SERVER_DIR="test_server"
SERVER_JAR="server.jar"
DOWNLOAD_URL="https://piston-data.mojang.com/v1/objects/4707d00eb834b446575d89a61a11b5d548d8c001/server.jar"

mkdir -p "$SERVER_DIR"
cd "$SERVER_DIR" || exit

if [ -f "$SERVER_JAR" ]; then
    echo "$SERVER_JAR already exists. Skipping download."
else
    echo "Downloading Minecraft server jar..."
    wget -O "$SERVER_JAR" "$DOWNLOAD_URL"
fi

echo "eula=true" > eula.txt

cat <<EOL > server.properties
online-mode=false
level-name=world
gamemode=creative
difficulty=easy
max-players=20
EOL

java -Xmx1024M -Xms1024M -jar "$SERVER_JAR" nogui
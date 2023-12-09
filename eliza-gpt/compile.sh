#!/bin/bash
#Lancer la commande mvn compile si un fichier du dossier src/main/java est modifié

DIRECTORY="src/main/java"

# Check if inotifywait is installed
if ! command -v inotifywait > /dev/null; then
    echo "inotifywait is not installed. Please install inotify-tools."
    exit 1
fi

# Monitor the specified directory for changes
while inotifywait -e modify -r "$DIRECTORY"; do
    echo "Change detected in $DIRECTORY. Running mvn compile..."
    mvn compile
done

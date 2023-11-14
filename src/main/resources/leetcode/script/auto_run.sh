#!/bin/bash

# Set the working directory to the current directory
cd "$(dirname "$0")" || exit

# Check if 'logs.txt' file exists, and if not, create it
if [ ! -f logs.txt ]; then
  touch logs.txt
fi

# Run the Java application and append both stdout and stderr to 'logs.txt'
java -cp . Processor >> logs.txt 2>&1

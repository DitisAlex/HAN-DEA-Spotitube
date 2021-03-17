#!/bin/sh
mvn clean package && docker build -t han.oose.dea/DEA Spotitube .
docker rm -f DEA Spotitube || true && docker run -d -p 8080:8080 -p 4848:4848 --name DEA Spotitube han.oose.dea/DEA Spotitube 

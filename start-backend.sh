#!/bin/bash
cd /home/esti/apps/backend-esti
export $(cat .env | xargs)
exec ./mvnw spring-boot:run

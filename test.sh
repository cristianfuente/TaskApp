#!/bin/bash

# Compila el proyecto

cd /task || exit

# Complila el proyecto

./mvnw clean install

# Ejecuta las pruebas

./mvnw test



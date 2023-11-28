#!/bin/bash

THIS_FILE_DIR=$(dirname "$0")

# Compila a aplicação
$THIS_FILE_DIR/mvnw clean javafx:jlink

# Executa a aplicação
$THIS_FILE_DIR/target/executavel/bin/crud-funcionarios-launcher

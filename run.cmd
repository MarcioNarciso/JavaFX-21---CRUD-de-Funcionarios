@REM Compila a aplicação
call mvnw.cmd clean javafx:jlink

@REM Executa a aplicação
cd "target/executavel/bin/"
call crud-funcionarios-launcher.cmd

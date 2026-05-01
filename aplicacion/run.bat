@echo off
echo =======================================
echo  Motor Grafico - Taller 2 UCE
echo =======================================
echo.

REM Verificar si existe el JAR ya compilado
if exist "target\motor-grafico.jar" (
    echo Iniciando servidor...
    echo Abriendo http://localhost:8080 en el navegador...
    start http://localhost:8080
    java -jar target\motor-grafico.jar
) else (
    echo JAR no encontrado. Compilando con Maven...
    echo.
    call mvn package -q
    if %errorlevel% neq 0 (
        echo ERROR: Fallo la compilacion. Asegurate de tener Maven y Java 17+ instalados.
        pause
        exit /b 1
    )
    echo Iniciando servidor...
    start http://localhost:8080
    java -jar target\motor-grafico.jar
)
pause

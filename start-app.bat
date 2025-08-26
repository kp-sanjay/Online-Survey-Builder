@echo off
echo ========================================
echo    Online Survey Builder - Starter
echo ========================================
echo.

echo Starting Spring Boot Backend...
echo.
cd springapp
start "Spring Boot Backend" cmd /k "mvnw.cmd spring-boot:run"
cd ..

echo.
echo Waiting for backend to start...
timeout /t 10 /nobreak >nul

echo.
echo Starting React Frontend...
echo.
cd reactapp
start "React Frontend" cmd /k "npm start"
cd ..

echo.
echo ========================================
echo    Applications Starting...
echo ========================================
echo.
echo Backend:  http://localhost:8080
echo Frontend: http://localhost:8081
echo H2 Console: http://localhost:8080/h2-console
echo.
echo Press any key to close this window...
pause >nul

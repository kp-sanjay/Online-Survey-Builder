@echo off
echo Starting Online Survey Builder on localhost...
echo.

echo Starting Spring Boot Backend on port 8080...
start "Backend" cmd /k "cd springapp && mvn spring-boot:run"

echo Waiting for backend to start...
timeout /t 10 /nobreak > nul

echo Starting React Frontend on port 8081...
start "Frontend" cmd /k "cd reactapp && npm start"

echo.
echo Application starting...
echo Backend: http://localhost:8080
echo Frontend: http://localhost:8081
echo H2 Console: http://localhost:8080/h2-console
echo.
echo Press any key to exit this launcher...
pause > nul

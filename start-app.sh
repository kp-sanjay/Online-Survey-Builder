#!/bin/bash

echo "========================================"
echo "   Online Survey Builder - Starter"
echo "========================================"
echo

echo "Starting Spring Boot Backend..."
echo
cd springapp
gnome-terminal --title="Spring Boot Backend" -- bash -c "./mvnw spring-boot:run; exec bash" &
cd ..

echo
echo "Waiting for backend to start..."
sleep 10

echo
echo "Starting React Frontend..."
echo
cd reactapp
gnome-terminal --title="React Frontend" -- bash -c "npm start; exec bash" &
cd ..

echo
echo "========================================"
echo "   Applications Starting..."
echo "========================================"
echo
echo "Backend:  http://localhost:8080"
echo "Frontend: http://localhost:8081"
echo "H2 Console: http://localhost:8080/h2-console"
echo
echo "Applications are starting in separate terminals..."
echo "You can close this window once both are running."
echo

# Alternative for non-gnome systems
echo "If gnome-terminal is not available, you can manually run:"
echo "1. Backend: cd springapp && ./mvnw spring-boot:run"
echo "2. Frontend: cd reactapp && npm start"
echo

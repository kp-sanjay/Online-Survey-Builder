# 🏠 Localhost Setup Guide

## Quick Start (Windows)

### Option 1: Start Both Services at Once
```bash
# Double-click or run:
start-local.bat
```

### Option 2: Start Services Separately

**Start Backend:**
```bash
# Double-click or run:
start-backend.bat

# Or manually:
cd springapp
mvn spring-boot:run
```

**Start Frontend:**
```bash
# Double-click or run:
start-frontend.bat

# Or manually:
cd reactapp
npm start
```

## 🌐 Access URLs

- **Frontend Application**: http://localhost:8081
- **Backend API**: http://localhost:8080
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:surveydb`
  - Username: `sa`
  - Password: (leave empty)

## 🔧 Prerequisites

1. **Java 11+** installed
2. **Node.js 14+** installed
3. **Maven** installed
4. **npm** (comes with Node.js)

## 📋 Manual Setup Steps

### Backend Setup
```bash
cd springapp
mvn clean install
mvn spring-boot:run
```

### Frontend Setup
```bash
cd reactapp
npm install
npm start
```

## 🐛 Troubleshooting

### Port Already in Use
- Backend (8080): Change `server.port` in `application.properties`
- Frontend (8081): Change `PORT=8081` in `package.json`

### Database Issues
- H2 console: http://localhost:8080/h2-console
- Check logs for database connection errors

### CORS Issues
- Ensure frontend is running on http://localhost:8081
- Check CORS configuration in `application.properties`

## 📝 Features Available

- ✅ Create surveys with multiple questions
- ✅ View all surveys
- ✅ Submit survey responses
- ✅ View survey statistics
- ✅ H2 database for development

## 🧪 Testing

### Backend Tests
```bash
cd springapp
mvn test
```

### Frontend Tests
```bash
cd reactapp
npm test
```

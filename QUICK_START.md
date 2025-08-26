# Quick Start Guide

## 🚀 Get Running in 5 Minutes

### Option 1: Use the Starter Scripts (Recommended)

**Windows Users:**
```bash
start-app.bat
```

**Mac/Linux Users:**
```bash
./start-app.sh
```

### Option 2: Manual Start

#### 1. Start Backend
```bash
cd springapp
./mvnw spring-boot:run
# or on Windows: mvnw.cmd spring-boot:run
```

#### 2. Start Frontend (in new terminal)
```bash
cd reactapp
npm install
npm start
```

## 🌐 Access Points

- **Frontend Application:** http://localhost:8081
- **Backend API:** http://localhost:8080
- **Database Console:** http://localhost:8080/h2-console

## 📱 What You'll See

1. **Dashboard** - Overview with statistics and recent surveys
2. **Create Survey** - Build surveys with custom questions
3. **Survey List** - Manage all your surveys
4. **Sample Data** - 3 pre-created surveys to explore

## 🔧 Troubleshooting

- **Port 8080 busy?** Change in `springapp/src/main/resources/application.properties`
- **Port 8081 busy?** Change in `reactapp/package.json` start script
- **Backend not starting?** Check Java version (requires Java 11+)
- **Frontend not starting?** Run `npm install` first

## 📚 Next Steps

- Read the full [README.md](README.md) for detailed documentation
- Explore the API endpoints
- Customize the styling
- Add new features

---

**Need help?** Check the troubleshooting section in the main README!

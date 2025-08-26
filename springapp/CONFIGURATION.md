# Configuration Guide

## 📁 Configuration Files

This project now has a **simplified configuration structure**:

### **Main Configuration**
- **File**: `src/main/resources/application.properties`
- **Purpose**: Production/Development configuration
- **Database**: H2 In-Memory Database
- **Usage**: Default configuration for running the application

### **Test Configuration**
- **File**: `src/test/resources/application.properties`
- **Purpose**: Testing configuration
- **Database**: MySQL Database
- **Usage**: Only used during testing

## 🚀 Running the Application

### **Development Mode**
```bash
cd springapp
./mvnw spring-boot:run
```

### **Testing**
```bash
cd springapp
./mvnw test
```

## 🗄️ Database Access

### **H2 Console (Development)**
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:surveydb`
- **Username**: `sa`
- **Password**: (empty)

### **MySQL (Testing)**
- **Host**: localhost:3306
- **Database**: app_db
- **Username**: root
- **Password**: kpstt@05

## ⚙️ Key Configuration Settings

### **Development (H2)**
- In-memory database
- Auto-create tables on startup
- H2 console enabled
- Detailed logging

### **Testing (MySQL)**
- Persistent MySQL database
- Auto-create tables
- Minimal logging
- Random port assignment

## 🔧 Troubleshooting

If you encounter database issues:
1. Ensure no other applications are using port 8080
2. Check that H2 console is accessible
3. Verify the application starts without errors
4. Check logs for any configuration issues

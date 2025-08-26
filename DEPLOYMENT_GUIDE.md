# Deployment Guide for Online Survey Builder

## Overview
This project consists of two parts:
- **Frontend**: React.js application (deploy to Vercel)
- **Backend**: Spring Boot application (deploy to Railway/Render/Heroku)

## Frontend Deployment (Vercel)

### 1. Vercel Configuration
The project is already configured for Vercel deployment with:
- `vercel.json` - Build configuration
- Environment variables for API URL

### 2. Deploy to Vercel
1. Go to [Vercel Dashboard](https://vercel.com/dashboard)
2. Import your GitHub repository: `https://github.com/kp-sanjay/Online-Survey-Builder.git`
3. Configure the following settings:
   - **Framework Preset**: Create React App
   - **Root Directory**: `reactapp`
   - **Build Command**: `npm run build`
   - **Output Directory**: `build`

### 3. Environment Variables
In Vercel dashboard, add these environment variables:
```
REACT_APP_API_URL=https://your-backend-url.railway.app
```

## Backend Deployment (Railway)

### 1. Deploy to Railway
1. Go to [Railway](https://railway.app/)
2. Create a new project
3. Connect your GitHub repository
4. Select the `springapp` directory
5. Railway will automatically detect it's a Java/Maven project

### 2. Environment Variables
Add these environment variables in Railway:
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=your-database-url
JWT_SECRET=your-jwt-secret
```

### 3. Database Setup
- Use Railway's PostgreSQL service
- Or connect to an external database (MongoDB Atlas, etc.)

## Alternative Backend Deployment Options

### Render
1. Go to [Render](https://render.com/)
2. Create a new Web Service
3. Connect your GitHub repository
4. Set build command: `cd springapp && ./mvnw clean install`
5. Set start command: `cd springapp && java -jar target/springapp-0.0.1-SNAPSHOT.jar`

### Heroku
1. Create a `Procfile` in the root:
```
web: cd springapp && java -jar target/springapp-0.0.1-SNAPSHOT.jar
```
2. Deploy using Heroku CLI or GitHub integration

## Post-Deployment Steps

### 1. Update Frontend API URL
After deploying the backend, update the `REACT_APP_API_URL` in Vercel with your actual backend URL.

### 2. Test the Application
1. Test user registration/login
2. Test survey creation
3. Test survey responses
4. Verify all API endpoints work

### 3. CORS Configuration
Make sure your backend allows requests from your Vercel domain:
```java
@CrossOrigin(origins = {"https://your-app.vercel.app", "http://localhost:3000"})
```

## Troubleshooting

### Common Issues
1. **CORS Errors**: Update backend CORS configuration
2. **API Connection**: Verify environment variables are set correctly
3. **Build Failures**: Check package.json and dependencies
4. **Database Connection**: Verify database URL and credentials

### Debugging
- Check Vercel build logs
- Check Railway/Render deployment logs
- Use browser developer tools to debug API calls
- Verify environment variables are loaded correctly

## URLs After Deployment
- **Frontend**: `https://your-app.vercel.app`
- **Backend**: `https://your-backend.railway.app`

## Security Considerations
1. Use HTTPS for all communications
2. Set strong JWT secrets
3. Configure proper CORS policies
4. Use environment variables for sensitive data
5. Regularly update dependencies

import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import SurveyList from './components/SurveyList';
import CreateSurvey from './components/CreateSurvey';
import Login from './pages/Login';
import Register from './pages/Register';
import { isAuthenticated, logout } from './services/api';
import { ReactComponent as Logo } from './logo.svg';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        {/* Navigation Header */}
        <nav className="netflix-card" style={{
          position: 'sticky',
          top: 0,
          zIndex: 1000,
          margin: '20px',
          padding: '20px 30px',
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          background: 'rgba(20, 20, 20, 0.9)',
          backdropFilter: 'blur(20px)',
          border: '1px solid rgba(255, 255, 255, 0.1)'
        }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
            <Logo style={{ 
              width: '32px', 
              height: '32px', 
              color: '#e50914',
              flexShrink: 0,
              alignSelf: 'center'
            }} />
            <h1 style={{
              margin: 0,
              color: '#ffffff',
              fontSize: '2rem',
              fontWeight: 800,
              lineHeight: '1',
              display: 'flex',
              alignItems: 'center',
              background: 'linear-gradient(135deg, #ffffff 0%, #e50914 100%)',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent',
              backgroundClip: 'text'
            }}>
              Survey Builder
            </h1>
          </div>
          
          <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>
            <Link to="/" className="btn-secondary-netflix" style={{ textDecoration: 'none' }}>
              Dashboard
            </Link>
            <Link to="/surveys" className="btn-secondary-netflix" style={{ textDecoration: 'none' }}>
              Surveys
            </Link>
            <Link to="/create" className="btn-netflix" style={{ textDecoration: 'none' }}>
              Create Survey
            </Link>
            {isAuthenticated() ? (
              <button onClick={() => { logout(); window.location.href = '/login'; }} className="btn-secondary-netflix">Logout</button>
            ) : (
              <>
                <Link to="/login" className="btn-secondary-netflix" style={{ textDecoration: 'none' }}>
                  Login
                </Link>
                <Link to="/register" className="btn-netflix" style={{ textDecoration: 'none' }}>
                  Sign Up
                </Link>
              </>
            )}
          </div>
        </nav>

        {/* Main Content */}
        <main>
          <Routes>
            <Route path="/" element={<Protected><Dashboard /></Protected>} />
            <Route path="/dashboard" element={<Protected><Dashboard /></Protected>} />
            <Route path="/surveys" element={<Protected><SurveyList /></Protected>} />
            <Route path="/create" element={<Protected><CreateSurvey /></Protected>} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Routes>
        </main>

        {/* Footer */}
        <footer style={{
          margin: '40px 20px 20px',
          padding: '30px',
          textAlign: 'center',
          background: 'rgba(20, 20, 20, 0.6)',
          borderRadius: '20px',
          border: '1px solid rgba(255, 255, 255, 0.1)',
          backdropFilter: 'blur(20px)'
        }}>
          <p style={{ margin: 0, color: '#b3b3b3', fontSize: '16px' }}>
            © 2025 Survey Builder By Sanjay. Built with React & Spring Boot. 
            <span style={{ color: '#e50914', marginLeft: '10px' }}>❤️</span>
          </p>
        </footer>
      </div>
    </Router>
  );
}

export default App;

function Protected({ children }) {
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

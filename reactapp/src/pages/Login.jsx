import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { login } from '../services/api';
import './Dashboard.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await login({ email, password });
      if (res && res.token) {
        localStorage.setItem('authToken', res.token);
        localStorage.setItem('authEmail', res.email || email);
        navigate('/dashboard');
      } else {
        setError('Invalid response from server');
      }
    } catch (err) {
      setError('Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '70vh', padding: '20px' }}>
      <div className="netflix-card" style={{ maxWidth: 420, width: '100%', padding: 32 }}>
        <h2 style={{ marginBottom: 20, fontSize: 28, fontWeight: 800, textAlign: 'center' }}>Welcome back</h2>
        <p style={{ marginBottom: 24, color: '#b3b3b3', textAlign: 'center' }}>Sign in to continue</p>
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: 16 }}>
            <label style={{ display: 'block', marginBottom: 8 }}>Email</label>
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="you@example.com" style={{ width: '100%', padding: '12px 14px' }} required />
          </div>
          <div style={{ marginBottom: 12 }}>
            <label style={{ display: 'block', marginBottom: 8 }}>Password</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="••••••••" style={{ width: '100%', padding: '12px 14px' }} required />
          </div>
          {error && (
            <div style={{ color: '#ff6b6b', marginBottom: 12 }}>{error}</div>
          )}
          <button className="btn-netflix" type="submit" style={{ width: '100%' }} disabled={loading}>
            {loading ? 'Signing in...' : 'Sign In'}
          </button>
        </form>
        <div style={{ marginTop: 16, textAlign: 'center', color: '#b3b3b3' }}>
          New here? <Link to="/register" style={{ color: '#e50914', fontWeight: 600 }}>Create an account</Link>
        </div>
      </div>
    </div>
  );
};

export default Login;



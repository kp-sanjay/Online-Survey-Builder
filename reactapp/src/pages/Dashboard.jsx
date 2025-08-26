import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getAllSurveys, getSurveyResponseCount } from '../services/api';
import './Dashboard.css';

const Dashboard = () => {
  const [surveys, setSurveys] = useState([]);
  const [stats, setStats] = useState({
    totalSurveys: 0,
    totalQuestions: 0,
    totalResponses: 0,
    avgEngagement: 0
  });
  const [recentSurveys, setRecentSurveys] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      setLoading(true);
      const surveysData = await getAllSurveys();
      setSurveys(surveysData);

      // Calculate stats
      const totalSurveys = surveysData.length;
      const totalQuestions = surveysData.reduce((sum, survey) => {
        try {
          const questions = JSON.parse(survey.questionsJson || '[]');
          return sum + (Array.isArray(questions) ? questions.length : 0);
        } catch {
          return sum;
        }
      }, 0);

      // Get response counts for each survey
      let totalResponses = 0;
      try {
        const responseCounts = await Promise.all(
          surveysData.map(survey => getSurveyResponseCount(survey.id))
        );
        totalResponses = responseCounts.reduce((sum, count) => {
          // Handle both number and object responses
          if (typeof count === 'number') return sum + count;
          if (count && typeof count === 'object' && count.count) return sum + count.count;
          return sum;
        }, 0);
      } catch (err) {
        console.warn('Could not fetch response counts:', err);
        totalResponses = 0;
      }

      // Calculate average engagement safely
      const avgEngagement = totalSurveys > 0 && totalResponses > 0 
        ? Math.round((totalResponses / totalSurveys) * 10) / 10 
        : 0;

      setStats({
        totalSurveys,
        totalQuestions,
        totalResponses,
        avgEngagement
      });

      // Get recent surveys (last 3)
      const recent = surveysData.slice(0, 3);
      setRecentSurveys(recent);
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
      // Set default values on error
      setStats({
        totalSurveys: 0,
        totalQuestions: 0,
        totalResponses: 0,
        avgEngagement: 0
      });
    } finally {
      setLoading(false);
    }
  };

  const getQuestionCount = (survey) => {
    try {
      const questions = JSON.parse(survey.questionsJson || '[]');
      return Array.isArray(questions) ? questions.length : 0;
    } catch {
      return 0;
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
      });
    } catch {
      return 'N/A';
    }
  };

  const getStatusColor = (status) => {
    switch (status?.toUpperCase()) {
      case 'ACTIVE':
        return 'status-active';
      case 'DRAFT':
        return 'status-draft';
      case 'ARCHIVED':
        return 'status-archived';
      default:
        return 'status-active';
    }
  };

  if (loading) {
    return (
      <div className="dashboard-loading">
        <div className="loading-spinner"></div>
        <h2>Loading your dashboard...</h2>
        <p>Preparing your survey insights</p>
      </div>
    );
  }

  return (
    <div className="dashboard">
      {/* Header Section */}
      <div className="dashboard-header">
        <div className="header-content">
          <h1>Survey Dashboard</h1>
          <p>Welcome back! Here's an overview of your survey performance and recent activities.</p>
        </div>
        <div className="header-actions">
          <Link to="/create" className="btn-primary">
            <span className="btn-icon">+</span>
            Create Survey
          </Link>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-icon surveys">📊</div>
          <div className="stat-content">
            <h3>Total Surveys</h3>
            <div className="stat-value">{stats.totalSurveys}</div>
            <div className="stat-description">Surveys created</div>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon questions">❓</div>
          <div className="stat-content">
            <h3>Total Questions</h3>
            <div className="stat-value">{stats.totalQuestions}</div>
            <div className="stat-description">Questions across all surveys</div>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon responses">📝</div>
          <div className="stat-content">
            <h3>Total Responses</h3>
            <div className="stat-value">{stats.totalResponses}</div>
            <div className="stat-description">Survey responses received</div>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon engagement">📈</div>
          <div className="stat-content">
            <h3>Avg. Engagement</h3>
            <div className="stat-value">{stats.avgEngagement}</div>
            <div className="stat-description">Responses per survey</div>
          </div>
        </div>
      </div>

      {/* Main Content and Sidebar */}
      <div className="dashboard-content">
        {/* Main Content */}
        <div className="main-content">
          <div className="recent-surveys">
            <div className="section-header">
              <h2>Recent Surveys</h2>
              <Link to="/surveys" className="view-all-btn">View All</Link>
            </div>

            {recentSurveys.length > 0 ? (
              <div className="surveys-list">
                {recentSurveys.map((survey) => (
                  <div key={survey.id} className="survey-card">
                    <div className="survey-info">
                      <h3>{survey.title}</h3>
                      <div className="survey-meta">
                        <div className="meta-item">
                          <span className="meta-icon">📅</span>
                          <span>{formatDate(survey.createdAt)}</span>
                        </div>
                        <div className="meta-item">
                          <span className="meta-icon">❓</span>
                          <span>{getQuestionCount(survey)} questions</span>
                        </div>
                        <div className="meta-item">
                          <span className="meta-icon">👤</span>
                          <span>{survey.creatorEmail || 'Anonymous'}</span>
                        </div>
                      </div>
                      {survey.description && (
                        <p className="survey-description">{survey.description}</p>
                      )}
                      <div className={`status ${getStatusColor(survey.status)}`}>
                        {survey.status || 'ACTIVE'}
                      </div>
                    </div>
                    <div className="survey-actions">
                      <button className="btn-secondary">View</button>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="empty-surveys">
                <div className="empty-icon">📊</div>
                <h3>No Surveys Yet</h3>
                <p>Create your first survey to get started and begin collecting valuable insights from your audience.</p>
                <Link to="/create" className="btn-primary">Create Survey</Link>
              </div>
            )}
          </div>
        </div>

        {/* Sidebar */}
        <div className="sidebar">
          {/* Quick Actions */}
          <div className="quick-actions">
            <h2>Quick Actions</h2>
            <div className="actions-grid">
              <Link to="/create" className="action-card">
                <div className="card-icon">+</div>
                <div className="card-content">
                  <h3>Create Survey</h3>
                  <p>Build a new survey with custom questions</p>
                </div>
              </Link>

              <Link to="/surveys" className="action-card">
                <div className="card-icon">📊</div>
                <div className="card-content">
                  <h3>View Surveys</h3>
                  <p>See all your surveys and responses</p>
                </div>
              </Link>

              <div className="action-card" onClick={() => navigate('/surveys')}>
                <div className="card-icon">📈</div>
                <div className="card-content">
                  <h3>Analytics</h3>
                  <p>View detailed survey analytics</p>
                </div>
              </div>
            </div>
          </div>

          {/* Help Section */}
          <div className="help-section">
            <h2>Need Help?</h2>
            <div className="help-links">
              <a href="#" className="help-link">
                <span className="help-icon">📖</span>
                <span>Documentation</span>
              </a>
              <a href="#" className="help-link">
                <span className="help-icon">🎥</span>
                <span>Video Tutorials</span>
              </a>
              <a href="#" className="help-link">
                <span className="help-icon">💬</span>
                <span>Support Chat</span>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;

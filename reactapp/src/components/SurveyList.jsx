import React, { useState, useEffect } from 'react';
import { getAllSurveys } from '../services/api';
import './SurveyList.css';

const SurveyList = () => {
  const [surveys, setSurveys] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchSurveys();
  }, []);

  const fetchSurveys = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await getAllSurveys();
      setSurveys(data || []);
    } catch (err) {
      console.error('Error fetching surveys:', err);
      alert('Failed to fetch surveys');
      setError('Failed to fetch surveys. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="survey-list">
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <p>Loading surveys...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="survey-list">
        <div className="error-container">
          <h2>Error</h2>
          <p>{error}</p>
          <button onClick={fetchSurveys} className="retry-btn">
            Try Again
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="survey-list">
      <div className="survey-list-container">
        <h1 className="survey-list-title">Survey List</h1>
        
        {surveys.length === 0 ? (
          <div className="no-surveys">
            <p>No surveys available.</p>
          </div>
        ) : (
          <div className="surveys-grid">
            {surveys.map((survey) => (
              <div key={survey.id} className="survey-card">
                <div className="survey-header">
                  <h3 className="survey-title">{survey.title}</h3>
                  <span className="survey-status">{survey.status || 'ACTIVE'}</span>
                </div>
                
                {survey.description && (
                  <p className="survey-description">{survey.description}</p>
                )}
                
                <div className="survey-meta">
                  <span className="survey-creator">
                    Created by: {survey.creatorEmail || 'Unknown'}
                  </span>
                  <span className="survey-date">
                    {survey.createdAt ? new Date(survey.createdAt).toLocaleDateString() : 'N/A'}
                  </span>
                </div>
                
                <div className="survey-actions">
                  <button className="view-btn">View Survey</button>
                  <button className="respond-btn">Take Survey</button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default SurveyList;


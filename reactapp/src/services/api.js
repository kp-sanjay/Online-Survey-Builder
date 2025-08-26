import axios from 'axios';

// Use environment variable for API URL, fallback to localhost for development
const API_BASE = process.env.REACT_APP_API_URL || 
  (process.env.NODE_ENV === 'development' ? "http://localhost:8080" : "");
const SURVEY_BASE = `${API_BASE}/api/surveys`;
const AUTH_BASE = `${API_BASE}/api/auth`;

// Create a new survey
export const createSurvey = async (payload) => {
  const response = await axios.post(`${SURVEY_BASE}/create`, payload, attachAuth());
  return response.data;
};

// Get all surveys
export const getAllSurveys = async () => {
  const response = await axios.get(`${SURVEY_BASE}/all`, attachAuth());
  return response.data;
};

// Get survey by ID
export const getSurveyById = async (id) => {
  const response = await axios.get(`${SURVEY_BASE}/${id}`, attachAuth());
  return response.data;
};

// Update survey
export const updateSurvey = async (id, payload) => {
  const response = await axios.put(`${SURVEY_BASE}/${id}`, payload, attachAuth());
  return response.data;
};

// Delete a survey
export const deleteSurvey = async (surveyId) => {
  const response = await axios.delete(`${SURVEY_BASE}/${surveyId}`, attachAuth());
  return response.data;
};

// Submit survey response
export const submitSurveyResponse = async (surveyId, payload) => {
  const response = await axios.post(`${SURVEY_BASE}/${surveyId}/respond`, payload, attachAuth());
  return response.data;
};

// Get survey responses
export const getSurveyResponses = async (surveyId) => {
  const response = await axios.get(`${SURVEY_BASE}/${surveyId}/responses`, attachAuth());
  return response.data;
};

// Get survey response count
export const getSurveyResponseCount = async (surveyId) => {
  try {
    const response = await axios.get(`${SURVEY_BASE}/${surveyId}/response-count`, attachAuth());
    const data = response.data;
    if (typeof data === 'number') return data;
    if (data && typeof data === 'object' && data.count !== undefined) return data.count;
    if (data && typeof data === 'object' && data.responseCount !== undefined) return data.responseCount;
    return 0;
  } catch (error) {
    return 0;
  }
};

// Auth endpoints
export const register = async (payload) => {
  const response = await axios.post(`${AUTH_BASE}/register`, payload);
  return response.data;
};

export const login = async (payload) => {
  const response = await axios.post(`${AUTH_BASE}/login`, payload);
  return response.data;
};

export const logout = () => {
  localStorage.removeItem('authToken');
  localStorage.removeItem('authEmail');
};

export const isAuthenticated = () => !!localStorage.getItem('authToken');

function attachAuth() {
  const token = localStorage.getItem('authToken');
  if (!token) return {};
  return {
    headers: {
      Authorization: `Bearer ${token}`
    }
  };
}

// Export as surveyAPI object for backward compatibility
export const surveyAPI = {
  createSurvey,
  getAllSurveys,
  getSurveyById,
  updateSurvey,
  deleteSurvey,
  submitSurveyResponse,
  getSurveyResponses,
  getSurveyResponseCount
};

export const authAPI = { register, login, logout, isAuthenticated };

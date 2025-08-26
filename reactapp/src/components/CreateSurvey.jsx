import React, { useState } from 'react';
import { createSurvey } from '../services/api';
import './CreateSurvey.css';

const CreateSurvey = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [questions, setQuestions] = useState(['']);
  const [loading, setLoading] = useState(false);

  const addQuestion = () => {
    setQuestions([...questions, '']);
  };

  const removeQuestion = (index) => {
    if (questions.length > 1) {
      const newQuestions = questions.filter((_, i) => i !== index);
      setQuestions(newQuestions);
    }
  };

  const updateQuestion = (index, value) => {
    const newQuestions = [...questions];
    newQuestions[index] = value;
    setQuestions(newQuestions);
  };

  const validateForm = () => {
    if (!title.trim()) {
      alert('Please fill all fields');
      return false;
    }
    if (questions.length === 0 || questions.every(q => !q.trim())) {
      alert('At least one question is required');
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setLoading(true);

    try {
      const email = localStorage.getItem('email') || 'default@example.com';
      const surveyData = {
        title: title.trim(),
        description: description.trim(),
        questionsJson: JSON.stringify(questions.filter(q => q.trim())),
        creatorEmail: email
      };

      await createSurvey(surveyData);
      alert('Survey created');
      
      // Reset form
      setTitle('');
      setDescription('');
      setQuestions(['']);
      
    } catch (err) {
      alert('Failed to create survey');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="create-survey">
      <div className="create-survey-container">
        <h1 className="create-survey-title">Create Survey</h1>
        
        <form onSubmit={handleSubmit} className="create-survey-form">
          <div className="form-group">
            <label className="form-label">Survey Title:</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Enter survey title"
              className="form-input"
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">Description:</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Enter survey description"
              className="form-textarea"
              rows="3"
            />
          </div>

          <div className="form-group">
            <label className="form-label">Questions:</label>
            <div className="questions-container">
              {questions.map((question, index) => (
                <div key={index} className="question-item">
                  <input
                    type="text"
                    value={question}
                    onChange={(e) => updateQuestion(index, e.target.value)}
                    placeholder={`Question ${index + 1}`}
                    className="form-input question-input"
                    required
                  />
                  {questions.length > 1 && (
                    <button 
                      type="button" 
                      onClick={() => removeQuestion(index)}
                      className="remove-btn"
                    >
                      Remove
                    </button>
                  )}
                </div>
              ))}
              <button 
                type="button" 
                onClick={addQuestion}
                className="add-question-btn"
              >
                Add Question
              </button>
            </div>
          </div>

          <button 
            type="submit" 
            disabled={loading}
            className="submit-btn"
          >
            {loading ? 'Creating...' : 'Create Survey'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateSurvey;


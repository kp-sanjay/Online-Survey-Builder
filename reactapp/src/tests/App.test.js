import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import axios from 'axios';
import App from '../App';
import CreateSurvey from '../components/CreateSurvey';
import SurveyList from '../components/SurveyList';

jest.mock('axios');

describe('ReactSurveyAppTests', () => {

  beforeEach(() => {
    localStorage.setItem('email', 'test@example.com');
  });
  test('testSurveyTitleInputUpdates', () => {
    render(<CreateSurvey />);
    fireEvent.change(screen.getByPlaceholderText('Title'), { target: { value: 'Survey A' } });
    expect(screen.getByDisplayValue('Survey A')).toBeInTheDocument();
  });

  test('testAddSecondQuestionInputField', () => {
    render(<CreateSurvey />);
    fireEvent.click(screen.getByText('Add Question'));
    expect(screen.getAllByPlaceholderText(/Question/).length).toBeGreaterThan(1);
  });

  test('testSubmitValidSurvey', async () => {
    axios.post.mockResolvedValueOnce({ data: {} });
    window.alert = jest.fn();
    render(<CreateSurvey />);
    fireEvent.change(screen.getByPlaceholderText('Title'), { target: { value: 'Survey X' } });
    fireEvent.change(screen.getByPlaceholderText('Question 1'), { target: { value: 'Q1?' } });
    fireEvent.click(screen.getByText('Create'));
    await waitFor(() => expect(window.alert).toHaveBeenCalledWith('Survey created'));
  });

  test('testSubmitEmptySurveyTitleShowsAlert', async () => {
    window.alert = jest.fn();
    render(<CreateSurvey />);
    fireEvent.click(screen.getByText('Create'));
    await waitFor(() => expect(window.alert).toHaveBeenCalled());
  });

  test('testSurveyListDisplaysFetchedSurveys', async () => {
    axios.get.mockResolvedValueOnce({
      data: [
        { id: 1, title: 'Survey A', description: 'Desc A' },
        { id: 2, title: 'Survey B', description: 'Desc B' }
      ],
    });
    render(<SurveyList />);
    expect(await screen.findByText('Survey A')).toBeInTheDocument();
    expect(await screen.findByText('Desc A')).toBeInTheDocument();
    expect(screen.getAllByRole('listitem').length).toBe(2);
  });

  test('testSurveyFetchFailureShowsAlert', async () => {
    axios.get.mockRejectedValueOnce(new Error('API Error'));
    window.alert = jest.fn();
    render(<SurveyList />);
    await waitFor(() => expect(window.alert).toHaveBeenCalledWith('Failed to fetch surveys'));
  });

  test('testControlledQuestionInputUpdatesValue', () => {
    render(<CreateSurvey />);
    fireEvent.change(screen.getByPlaceholderText('Question 1'), { target: { value: 'Updated?' } });
    expect(screen.getByDisplayValue('Updated?')).toBeInTheDocument();
  });

  test('testDefaultEmailUsedWhenLocalStorageMissing', async () => {
    localStorage.removeItem('email');
    axios.post.mockResolvedValueOnce({ data: {} });
    window.alert = jest.fn();
    render(<CreateSurvey />);
    fireEvent.change(screen.getByPlaceholderText('Title'), { target: { value: 'Survey B' } });
    fireEvent.change(screen.getByPlaceholderText('Question 1'), { target: { value: 'Q?' } });
    fireEvent.click(screen.getByText('Create'));
    await waitFor(() =>
      expect(axios.post).toHaveBeenCalledWith(
        expect.stringContaining('/create'),
        expect.objectContaining({ creatorEmail: 'default@example.com' })
      )
    );
  });

  test('testEmptyQuestionShowsAlert', async () => {
    window.alert = jest.fn();
    render(<CreateSurvey />);
    fireEvent.change(screen.getByPlaceholderText('Title'), { target: { value: 'Survey C' } });
    fireEvent.click(screen.getByText('Create'));
    await waitFor(() => expect(window.alert).toHaveBeenCalled());
  });

  test('testMultipleQuestionsRenderCorrectly', () => {
    render(<CreateSurvey />);
    fireEvent.click(screen.getByText('Add Question'));
    fireEvent.click(screen.getByText('Add Question'));
    const inputs = screen.getAllByPlaceholderText(/Question/);
    expect(inputs.length).toBeGreaterThan(2);
  });

 


  test('testCreateSurveyRendersCorrectly', () => {
    render(<CreateSurvey />);
    expect(screen.getByText('Create Survey')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Title')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Question 1')).toBeInTheDocument();
  });

  test('testSurveyListInitialRender', async () => {
    axios.get.mockResolvedValueOnce({ data: [] });
    render(<SurveyList />);
    await waitFor(() => {
      expect(screen.queryAllByRole('listitem').length).toBe(0);
    });
  });

});

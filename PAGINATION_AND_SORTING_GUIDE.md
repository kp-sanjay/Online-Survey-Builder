# Pagination and Sorting Guide

This guide explains how to use the new pagination and sorting features in the Survey API.

## Overview

The API now supports pagination and sorting for all survey and response endpoints. This allows you to:
- Load data in smaller chunks for better performance
- Sort data by any field in ascending or descending order
- Filter surveys by various criteria
- Handle large datasets efficiently

## Pagination Parameters

All paginated endpoints accept the following query parameters:

- `page` (default: 0): The page number (0-based indexing)
- `size` (default: 10): Number of items per page
- `sortBy` (default: "id"): Field to sort by
- `sortDirection` (default: "asc"): Sort direction ("asc" or "desc")

## Available Endpoints

### 1. Get All Surveys (Paginated)
```
GET /api/surveys/paginated?page=0&size=10&sortBy=title&sortDirection=asc
```

### 2. Get Surveys with Filters (Paginated)
```
GET /api/surveys/filtered?title=survey&creatorEmail=user@example.com&status=ACTIVE&page=0&size=10&sortBy=createdAt&sortDirection=desc
```

**Filter Parameters:**
- `title`: Filter by survey title (partial match, case-insensitive)
- `description`: Filter by survey description (partial match, case-insensitive)
- `creatorEmail`: Filter by creator email (exact match)
- `status`: Filter by survey status (exact match)

### 3. Get Surveys by Creator (Paginated)
```
GET /api/surveys/creator/user@example.com/paginated?page=0&size=10&sortBy=title&sortDirection=asc
```

### 4. Get Surveys by Status (Paginated)
```
GET /api/surveys/status/ACTIVE/paginated?page=0&size=10&sortBy=createdAt&sortDirection=desc
```

### 5. Get Surveys by Creator and Status (Paginated)
```
GET /api/surveys/creator/user@example.com/status/ACTIVE/paginated?page=0&size=10&sortBy=title&sortDirection=asc
```

### 6. Get Survey Responses (Paginated)
```
GET /api/surveys/123/responses/paginated?page=0&size=10&sortBy=submittedAt&sortDirection=desc
```

### 7. Get Responses by Respondent (Paginated)
```
GET /api/surveys/responses/respondent/user@example.com/paginated?page=0&size=10&sortBy=submittedAt&sortDirection=desc
```

## Sortable Fields

### Survey Fields
- `id`: Survey ID
- `title`: Survey title
- `description`: Survey description
- `creatorEmail`: Creator's email
- `status`: Survey status
- `createdAt`: Creation timestamp
- `updatedAt`: Last update timestamp

### Response Fields
- `id`: Response ID
- `surveyId`: Survey ID
- `respondentEmail`: Respondent's email
- `submittedAt`: Submission timestamp

## Response Format

Paginated endpoints return a `Page` object with the following structure:

```json
{
  "content": [
    {
      "id": 1,
      "title": "Sample Survey",
      "description": "A sample survey",
      "creatorEmail": "user@example.com",
      "status": "ACTIVE",
      "createdAt": "2024-01-01T10:00:00Z",
      "updatedAt": "2024-01-01T10:00:00Z"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 25,
  "totalPages": 3,
  "last": false,
  "first": true,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 10,
  "size": 10,
  "number": 0,
  "empty": false
}
```

## Usage Examples

### Example 1: Get first page of surveys sorted by title
```bash
curl "http://localhost:8080/api/surveys/paginated?page=0&size=5&sortBy=title&sortDirection=asc"
```

### Example 2: Get active surveys by a specific creator
```bash
curl "http://localhost:8080/api/surveys/filtered?creatorEmail=admin@example.com&status=ACTIVE&page=0&size=10&sortBy=createdAt&sortDirection=desc"
```

### Example 3: Get survey responses with pagination
```bash
curl "http://localhost:8080/api/surveys/123/responses/paginated?page=0&size=20&sortBy=submittedAt&sortDirection=desc"
```

### Example 4: Search surveys by title
```bash
curl "http://localhost:8080/api/surveys/filtered?title=customer&page=0&size=10&sortBy=title&sortDirection=asc"
```

## Frontend Integration

### JavaScript/React Example
```javascript
const fetchSurveys = async (page = 0, size = 10, sortBy = 'id', sortDirection = 'asc') => {
  const response = await fetch(
    `/api/surveys/paginated?page=${page}&size=${size}&sortBy=${sortBy}&sortDirection=${sortDirection}`
  );
  const data = await response.json();
  return data;
};

// Usage
const surveys = await fetchSurveys(0, 10, 'title', 'asc');
console.log(`Total surveys: ${surveys.totalElements}`);
console.log(`Current page: ${surveys.number + 1} of ${surveys.totalPages}`);
console.log(`Surveys on this page: ${surveys.content.length}`);
```

### Pagination Component Example
```javascript
const PaginationComponent = ({ currentPage, totalPages, onPageChange }) => {
  return (
    <div className="pagination">
      <button 
        disabled={currentPage === 0}
        onClick={() => onPageChange(currentPage - 1)}
      >
        Previous
      </button>
      <span>Page {currentPage + 1} of {totalPages}</span>
      <button 
        disabled={currentPage === totalPages - 1}
        onClick={() => onPageChange(currentPage + 1)}
      >
        Next
      </button>
    </div>
  );
};
```

## Best Practices

1. **Page Size**: Use reasonable page sizes (10-50 items) to balance performance and user experience
2. **Sorting**: Always specify a sort order for consistent results
3. **Caching**: Consider caching paginated results for frequently accessed data
4. **Error Handling**: Handle cases where requested pages don't exist
5. **Loading States**: Show loading indicators while fetching paginated data

## Backward Compatibility

All existing endpoints remain unchanged and continue to work as before. The new paginated endpoints are additional endpoints that provide enhanced functionality.

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK`: Successful request
- `400 Bad Request`: Invalid parameters
- `500 Internal Server Error`: Server error

Error responses include descriptive messages to help with debugging.

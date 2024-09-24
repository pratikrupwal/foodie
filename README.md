# foodie
A. Postman requests for testing features to create a new user and fetching all users.

1. Create new user
POST request with URL : http://localhost:8080/api/users/register
{
  "name": "Pratik Rupwal",
  "email": "pratik@example.com",
  "password": "password123",
  "phoneNumber" : "9876543210",
  "userType": "FOODIE"
}

2. Fetch all users.
GET request with URL : http://localhost:8080/api/users/all
sample response:
  {
        "userId": 1,
        "name": "Pratik Rupwal",
        "email": "pratik@example.com",
        "phoneNumber": "9876543210",
        "passwordHash": "password123",
        "userType": "FOODIE",
        "createdAt": "2024-09-24T12:32:10.14653",
        "updatedAt": "2024-09-24T12:32:10.14653"
    }

B. Test Coverage
1. Test for User Registration.
2. Test for Fetch all users.
3. Test for duplicate email address.
4. Test for duplicate phone number.

C. Additional action to be considered for production ready application,
 - Better security can be implemented with integration of JWT token for request authentication and authorization.
 - Cross-cutting concerns like logging and notification can be better managed with Spring AOP.
 - Centralized exception handling can be achieved @ControllerAdvice and @ExceptionHandler.
 - Application monitoring can be better managed with Spring Actuators.
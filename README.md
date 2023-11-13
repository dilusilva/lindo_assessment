# Lendo Assessment - Rest API with Authentication

## Build Project

- go to the application downloaded directory.
- execute "mvn clean install".
- Run the application
- The application will start at http://localhost:8081.
- [Postman Collection](https://github.com/dilusilva/lindo_assessment/blob/master/lendo_auth_postman_collection.postman_collection)


## API Endpoints

### Authenticate User

- Endpoint : http://localhost:8081/authenticate
- Method  : POST
- Request Body :
  ```javascript
  {
  "username": "root",
  "password": "password"
  }
  ```
- Response :

```javascript
  {
  "accessToken": "generated_jwt_token"
  }
```

### Protected Endpoints

Include the obtained JWT token in the Authorization header when testing below endpoints.

Eg.: 'Authorization: Bearer generated_jwt_token'

**GET**

* http://localhost:8081/users
* http://localhost:8081/posts
* http://localhost:8081/comments
* http://localhost:8081/user/{user_id}/posts
* http://localhost:8081/post/{post_id}/comments




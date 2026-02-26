# 📚 Book Management API

A simple Spring Boot REST API for managing a collection of books, supporting **pagination**, **filtering**, **sorting**, and **CRUD operations**.

---

## Features

- **Retrieve books with pagination.**
- **Retrieve books with filtering, sorting, and pagination.**
- **Get a book by its ID.**
- **Update a book entirely.**
- **Partially update a book.**
- **Delete a book by its ID.**

---

## Endpoints

### 1. Get Books with Pagination

```http
GET /books/pagination
```
### 2. Get Books by filtering, sorting, and pagination
```http
GET /books/advanced
```
### 3. Get Books by ID from the list
```http
GET /books/{id}
```
### 4. Update book entirely from the list
```http
PUT /books/{id}

when updating the book make sure the body in postman is filled something like
{
"author": "joe",
"book": "book",
"price": "9.0"
}
```
### 3. Partially update a book from the list
```http
PATCH /books/{id}

when updating the book make sure the body in postman is filled something like
{
"author": "joe",
"book": "book",
"price": "9.0"
}
```
### 4. Delete a book from the list
```http
DELETE /books/{id}

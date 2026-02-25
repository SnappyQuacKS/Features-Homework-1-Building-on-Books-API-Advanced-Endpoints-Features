# 📚 Book Management API

A simple Spring Boot REST API for managing a collection of books, supporting **pagination**, **filtering**, **sorting**, and **CRUD operations**.

---

## Features

- **GET /books/pagination** – Retrieve books with pagination.
- **GET /books/advanced** – Retrieve books with filtering, sorting, and pagination.
- **GET /books/{id}** – Get a book by its ID.
- **PUT /books/{id}** – Update a book entirely.
- **PATCH /books/{id}** – Partially update a book.
- **DELETE /books/{id}** – Delete a book by its ID.

---

## Endpoints

### 1. Get Books with Pagination

```http
GET /books/pagination?page={page}&size={size}

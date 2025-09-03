
# Spring Boot Generative AI Project - Document Summarization

## Overview

This project is a **personal Spring Boot application** that demonstrates the use of **Generative AI (OpenAI)** to extract and summarize text from documents. It supports PDF, DOCX, and TXT files, storing both the original content and the AI-generated summary in **PostgreSQL**. Text extraction is handled via **Apache Tika**.

---

## Features

- Upload documents (PDF, DOCX, TXT) via REST API.
- Automatic text extraction with Apache Tika.
- Summarization using OpenAI GPT models via Spring AI.
- Storage of original content and summary in PostgreSQL.
- Endpoints to list all documents or retrieve by ID.

---

## Technologies Used

- **Java 17** & **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Apache Tika** (text extraction)
- **Spring AI OpenAI Starter** (AI summarization)
- **Maven** for dependency management

---

## Prerequisites

- Docker & Docker Compose installed
- OpenAI API Key ([Get one here](https://platform.openai.com/account/api-keys))
- Maven (optional if you want to run outside Docker)

---

## Setup

### 1. Clone the project

```bash
git clone https://github.com/MohammedBENBAZZA/Text-sum-SpringAI
cd Text-sum-SpringAI
```

### 2. Configure environment variables

Create a `.env` file:

```env
# OpenAI
OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxx

# PostgreSQL
POSTGRES_USER=ai_user
POSTGRES_PASSWORD=ai_password
POSTGRES_DB=ai_project
```

### 3. Docker Compose

The project is containerized using Docker Compose:

```yaml
services:
  spring-ai-app:
    build: .
    container_name: spring-ai-app
    ports:
      - "8080:8080"
    env_file:
      - .env.example
    depends_on:
      - postgres-db

  postgres-db:
    image: postgres:15
    container_name: postgres-db
    ports:
      - "5432:5432"
    env_file:
      - .env.example
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

### 4. Build and run

```bash
docker compose up --build
```

- Application runs on [http://localhost:8080](http://localhost:8080)  
- PostgreSQL is available on port 5432

---

## API Endpoints

### 1. Upload a document

```http
POST /documents/upload
Content-Type: multipart/form-data
Form field: file (your document)
```

**Response:**

```json
{
  "id": 1,
  "filename": "test.pdf",
  "content": "...extracted text...",
  "summary": "...AI generated summary..."
}
```

### 2. List all documents

```http
GET /documents
```

### 3. Get a document by ID

```http
GET /documents/{id}
```

---

## Development Notes

- **Fallback Summary**: If OpenAI API key quota is exceeded, the service can generate a **simulated summary** for testing.  
- **Tika Injection**: Apache Tika is injected via Spring configuration for easier testing and flexibility.  
- **Database**: All documents and summaries are stored in PostgreSQL using Spring Data JPA.

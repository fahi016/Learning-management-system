# Learning Management System (LMS) – Spring Boot Backend

A **production-grade Learning Management System (LMS) backend** built using **Spring Boot**, designed with **real-world industry practices** such as **role-based access control**, **service-layer authorization**, **clean REST APIs**, and **secure data isolation**.

This project demonstrates how Admins, Teachers, and Students interact in a controlled, secure academic platform.

---

## Features Overview

### 🔐 Authentication & Security

* JWT-based authentication
* Role-based authorization (`ADMIN`, `TEACHER`, `STUDENT`)
* Secure password handling
* First-login password reset flow
* Endpoint-level and service-level access validation

---

### 🟣 Admin Features

* Create & manage **students** and **teachers**
* View, update, and delete users
* Unlock locked user accounts
* Create, update, and delete courses
* Full system-level control

---

### 🟡 Teacher Features

* View and update own profile
* View assigned courses
* Create, update, delete **lectures** for owned courses
* Create, update, delete **assignments**
* View student submissions for owned assignments
* Grade submissions securely

> ⚠️ Teachers can access **only their own courses, assignments, and submissions**

---

### 🔵 Student Features

* View and update own profile
* Browse all available courses
* Enroll in courses
* View enrolled courses
* View lectures of enrolled courses only
* View assignments of enrolled courses
* Submit assignments
* View own submissions and grades

> ⚠️ Students cannot access un-enrolled courses or restricted teacher/admin resources

---

## Architecture & Design Principles

* **Controller → Service → Repository** layered architecture
* Strict **service-layer authorization checks**
* DTO-based request/response handling
* Transactional boundaries using `@Transactional`
* Optimized database queries with ownership validation
* Clean separation of responsibilities

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **JWT Authentication**
* **Spring Data JPA / Hibernate**
* **MySQL / PostgreSQL** (configurable)
* **Lombok**
* **Maven**

---

## 🔑 Roles & Access Control

| Role    | Permissions                                        |
| ------- | -------------------------------------------------- |
| ADMIN   | Full system control                                |
| TEACHER | Manage own courses, lectures, assignments, grading |
| STUDENT | Enroll, learn, submit assignments                  |

All sensitive operations validate:

* Role
* Ownership
* Enrollment

---

## 🔄 API Flow Summary

### Authentication

* `POST /api/auth/login`
* `GET /api/auth/me`

### Admin

* `/api/admin/users`
* `/api/admin/students`
* `/api/admin/teachers`
* `/api/admin/courses`

### Teacher

* `/api/teachers/courses`
* `/api/teachers/courses/{courseId}/lectures`
* `/api/teachers/courses/{courseId}/assignments`
* `/api/assignments/{assignmentId}/submissions`
* `/api/submissions/{submissionId}/grade`

### Student

* `/api/courses`
* `/api/courses/enroll/{courseId}`
* `/api/students/assignments`
* `/api/students/submissions`

---

## 🧪 Testing & Validation

* Tested end-to-end using **Postman**
* Verified all role-based restrictions
* Validated forbidden access (403) scenarios
* Ensured no data leakage across roles

---

## 🎥 Demo & Portfolio Usage

This project is **portfolio-ready** and suitable for:

* Backend developer roles
* Spring Boot internships
* Full-stack developer positions

A full **API demo walkthrough** is recorded and shared in the portfolio.

---

## 📌 Future Improvements

* Pagination & filtering
* Assignment deadlines enforcement
* Notifications system
* File storage integration (S3 / Cloudinary)
* Admin analytics dashboard

---

## 👨‍💻 Author

**Mohammed Faheem P**
Backend Developer | Spring Boot | Secure API Design

---

## ⭐ Final Note

This project focuses heavily on **security, correctness, and real-world backend practices**, not just CRUD operations.

If you’re reviewing this as a recruiter — this codebase reflects **how enterprise LMS systems are built**.

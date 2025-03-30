# <p align="center">TechGear E-Commerce</p>

<div align="center">
  <img src="https://github.com/user-attachments/assets/e2f5df73-319b-4950-808d-aa531708ef07" alt="image">
</div>
<div align="center">
  <img src="https://github.com/user-attachments/assets/b74a1c5e-f8ad-4c9a-a0b6-703596cf1b2a" alt="image">
</div>

# Overview

Website Ban Do Cong Nghe is an e-commerce system that allows users to shop for technology products, manage their shopping cart, make payments, and track orders. The system integrates Google login, refresh tokens, online payments via VNPAY, and efficient order management.

# Features

* **JWT Authentication & Authorization** – Secure authentication and authorization system.

* **Google Login** – Quick login using Google accounts.

* **Shopping Cart** – Manage cart, add/edit/remove products.

* **Order Management** – Create orders from the cart, track orders.

* **Online Payment Support** – Integrates VNPAY API for online transactions.

* **Product Reviews & Comments** – Users can leave reviews and feedback on products.

* **Discount & Flash Sale** – Supports discount codes and limited-time promotions.

* **Admin Role Management** – Manage users, product categories, and orders.

* **Revenue & Reporting** – Track revenue, orders, and customers.

# Technologies Used

* **Backend:** Java, Spring Boot

* **Database:** MySQL

* **Authentication:** JWT, Google OAuth

* **Payment Integration:** VNPAY API

* **Frontend:** [React.js](https://github.com/chinhanne/DoAnChuyenNghanh-Frontend.git)

# Installation

## Backend Setup
1. Clone the repository:
```
git clone https://github.com/chinhanne/DoAnChuyenNghanh.git
cd DoAnChuyenNghanh
```
2. Configure MySQL Database in application.yaml.
3. Run Spring Boot:
```
mvn spring-boot:run
```
# API Endpoints
## Authentication
- **POST** /auth/token – User login
- **POST** /auth/outbound/authentication – Google login
- **POST** /auth/refresh – Refresh token
- **POST** /auth/logout – Logout
## Brand Management

- **POST** /brand – Create brand

- **PUT** /brand/{id} – Update brand

- **DELETE** /brand/{id} – Delete brand

- **PUT** /brand/restore/{id} – Restore brand

- **GET** /brand – Get all brands
## Category Management

- **POST** /category/add – Create category

- **PUT** /category/{id} – Update category

- **DELETE** /category/{id} – Delete category

- **PUT** /category/restore/{id} – Restore category

- **GET** /category – Get all categories

## Comment Management

- **POST** /comment – Create comment
  
- **PUT** /comment/{id} – Update comment
  
- **DELETE** /comment/{id} – Delete comment
  
- **GET** /comment/product/{productId} – Get comments by product
  
- **GET** /comment/{id} – Get comment by ID

## OrdersController (/orders)

- **POST** / - Checkout (place an order)
  
- **GET** / - Retrieve all orders
  
- **GET** /{orderId} - Get order details by ID
  
- **GET** /history/orders-user - Get order history of the logged-in user
  
- **PUT** /{orderId} - Update order status
  
- **DELETE** /{orderId} - Delete an order
  
- **POST** /monthly - Get revenue by month

## ProductController (/product)

- **POST** /create - Create a product
  
- **GET** / - Retrieve all products
  
- **GET** /{id} - Get product details by ID
  
- **GET** /search - Search products by multiple criteria
  
- **GET** /header-search - Search products by keyword
  
- **GET** /list-product-delete-soft - Get the list of soft-deleted products
  
- **PUT** /{id} - Update a product
  
- **PUT** /update-quantity-status/{id} - Update product quantity and status
  
- **PUT** /restore/{id} - Restore a soft-deleted product
  
- **DELETE** /{id} - Permanently delete a product
  
- **DELETE** /soft-product/{id} - Soft delete a product

## UserController (/users)

- **POST** / - Create a new user account

- **POST** /create/userInfoLoginGoogle - Update user info after Google login

- **GET** /myInfo - Get logged-in user's information

- **GET** / - Retrieve all users

- **GET** /{id} - Get user details by ID

- **GET** /list-user-delete-soft - Get the list of soft-deleted users

- **PUT** /{id} - Update user information

- **PUT** /myInfo - Update logged-in user’s personal info

- **PUT** /password-myInfo - Update logged-in user’s password

- **PUT** /restore/{id} - Restore a soft-deleted user

- **DELETE** /{id} - Permanently delete a user

- **DELETE** /soft-user/{id} - Soft delete a user

## VNPAYPaymentController (/payment)
- **GET** /vn-pay - Process payment via VNPAY

- **GET** /vn-pay-callback - Handle payment callback

## DiscountController (/discount)
- **POST** / - Create a discount code
  
- **PUT** /{id} - Update a discount code
  
- **PUT** /restore/{id} - Restore a soft-deleted discount code
  
- **GET** / - Retrieve all discount codes
  
- **GET** /{code} - Get a discount code by its code
  
- **GET** /list-discount-delete-soft - Get the list of soft-deleted discount codes
  
- **DELETE** /{id} - Permanently delete a discount code
  
- **DELETE** /soft-discount/{id} - Soft delete a discount code
  

# **Reward Points Calculation API**
### **Spring Boot Application for Managing Customer Transactions and Reward Points**

## 📌 **Overview**
This project is a **Spring Boot application** designed to manage **customers, transactions, and reward points**. It provides a **RESTful API** for:
- **Customer registration, login, and zone assignment**
- **Transaction recording and reward point calculation**
- **Fetching total and monthly reward points for customers**
- **Secure API access using Spring Security (Basic Authentication)**
- **Database management using PostgreSQL**

---

## ⚡ **Features**
✔️ **Customer Management**: Register, login, and associate customers with zones.  
✔️ **Transaction Processing**: Record customer transactions and calculate reward points.  
✔️ **Reward Calculation**: Points are awarded based on transaction amount.  
✔️ **Security**: Uses **Spring Security with Basic Authentication**.  
✔️ **Database**: PostgreSQL is used for persistent storage.  
✔️ **Swagger API Documentation**: API endpoints are documented with OpenAPI (Swagger).  
✔️ **Logging**: Logs are managed using SLF4J and stored in a log file.  

---

## 🏗 **Technology Stack**
| Technology | Description |
|------------|------------|
| **Java 17** | Backend programming language |
| **Spring Boot 3** | Backend framework for creating REST APIs |
| **Spring Security** | Authentication and authorization |
| **Spring Data JPA** | ORM for database interaction |
| **PostgreSQL** | Database management |
| **Lombok** | Reduces boilerplate code |
| **Swagger (OpenAPI)** | API documentation |
| **JUnit & Mockito** | Unit testing |

---

## 🚀 **Project Setup**
### **Prerequisites**
✅ Java 17 or higher  
✅ Maven installed  
✅ PostgreSQL database setup  

### **Steps to Run the Project**
1️⃣ **Clone the Repository**  
   ```bash
   git clone https://github.com/DeepAatekar/Deep-UIAssignment
   cd reward-points
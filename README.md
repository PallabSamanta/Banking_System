# 🏦 Banking System (Java + MySQL)

A simple console-based Banking System built in **Java** with **MySQL** as the backend database.  
Supports **registration, login, account creation, debit, credit, transfer, and balance check**.

---

## 💡 Features
- User registration & login
- Open new bank account
- Debit money
- Credit money
- Transfer money between accounts
- Check account balance

---

## 📂 Project Structure
```
Banking_System/
├── .idea/
├── out/
├── src/
│   ├── BankingApp.java
│   ├── User.java
│   ├── Accounts.java
│   ├── AccountManager.java
│   └── config.properties  # Database configuration
├── Banking_System.iml
├── .gitignore
└── README.md
```

---

## ⚙️ Requirements
- Java 17+ (or compatible version)
- MySQL 8.0+
- MySQL Connector/J (JDBC driver)
- IntelliJ IDEA or any Java IDE

---

## 🗄 Database Setup

1. **Create Database**
```sql
CREATE DATABASE bank;
```

2. **Create Tables**
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    balance DECIMAL(15,2),
    security_pin  VARCHAR(100),
);
```

---

## 🔧 Configuration

Add a `config.properties` file in your project root **or** `src/` folder:

```
db.url=your_database_url
db.username=your_username
db.password=your_password
```

⚠ **Security Note**:  
Do **NOT** commit `config.properties` to GitHub. Add it to `.gitignore`:

```
# Ignore config file
config.properties
```

---

## ▶️ Running the Project

1. **Clone repository**
```bash
git clone https://github.com/PallabSamanta/Banking_System.git
cd Banking_System
```

2. **Compile and run**
```bash
javac -cp "mysql-connector-j-8.0.xx.jar" src/*.java
java -cp "mysql-connector-j-8.0.xx.jar:src" BankingApp
```

Or simply **Run** from your IDE if you already connected your database with IDE.

---





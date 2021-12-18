# Library-Management_System
Library Management project using JAVA and MYSQL Database.

**About Project :-**


+ The Library Management System allows the Librarian to login using a password to access the system.

+ The Librarian can perform many functions after logging into the system such as, adding a new book, adding a new student, issuing a book, returning (accepting) a book etc.

+ While issuing a book the Librarian must enter the ISBN number of the book, the ID of the student, and the issue date.After issuing,quantity of issued book decreases by one.

+ When the book is returned, student ID is entered by the Librarian to get details of the book issued, further the return date is mentioned in the records table.After returning ,quantity of returned book increases by one.

**Tables Used :-**
+ Student: This table contains fields like Student_ID, Name, Course, Branch and State.
+ Book : This table contains fields like ISBN number, Title, Author, Quantity and Price.
+ Record: This table contains fields like Return_Date, ISBN, Student_id and Issue_Date.

**CREATING DATABASE**
```
create database if not exists demo;

use demo;
```
**CREATE BOOK TABLE**
```
CREATE TABLE `BOOK` (
  `ISBN` VARCHAR(14) PRIMARY KEY,
  `Title` varchar(64) DEFAULT NULL,
  `Author` varchar(64) DEFAULT NULL,
  `Quantity` varchar(10) DEFAULT NULL,
  `Price` varchar(10) DEFAULT NULL
);
```
**CREATE STUDENT TABLE**
```
CREATE TABLE `STUDENT` (
  `Student_id` VARCHAR(14) PRIMARY KEY,
  `Name` varchar(64) DEFAULT NULL,
  `Course` varchar(10) DEFAULT NULL,
  `Branch` varchar(10) DEFAULT NULL,
  `State` varchar(10) DEFAULT NULL
);
```
**CREATE RECORD TABLE**
```
CREATE TABLE `RECORD` (
  `Student_id` VARCHAR(14) ,
  `ISBN` varchar(14) ,
  `Issue_date` varchar(10) DEFAULT NULL,
  `Return_date` varchar(10) DEFAULT NULL,
   foreign key fk1(Student_id) references demo.STUDENT(Student_id),
   foreign key fk2(ISBN) references demo.BOOK(ISBN)
);
```
**TOOLS**
+ [Eclipse IDE 2021-12](https://www.eclipse.org/downloads/)
+ [MySQL 8.0.27](https://dev.mysql.com/downloads/installer/)
+ [Connector/J 8.0.27](https://dev.mysql.com/downloads/connector/j/)

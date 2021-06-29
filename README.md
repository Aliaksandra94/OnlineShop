# OnlineShopf
Test task - Online Shop. A WEB application that represents the system of small part of order's placing.


USER Role: Available actions: 
http://localhost:8080/registration            Registration
http://localhost:8080/login                   Authorization
http://localhost:8080/main                    View the list of orders and link for the catalog and basket
http://localhost:8080/main/orders/order/{id}  View the order
http://localhost:8080/main/catalog            View the catalog, filter items by description or tags, add item to the basket
http://localhost:8080/main/basket             View the basket, placed the order, clear basket, add some items to baasket
http://localhost:8080/logout                  Logout
***
Login/pass: USER/USER (using CapsLock).
Login/pass: SASHA/SASHA (using CapsLock).
***

ADMIN Role: 
http://localhost:8080/login                    - Authorization
http://localhost:8080/main                     - View the list of orders and link for the catalog
http://localhost:8080/main/catalog             - View the catalog, filter items by description or tags, links for adding, editing, deleting items
http://localhost:8080/main/catalog/new         - Add new item
http://localhost:8080/main/catalog/edit/{id}   - Edit item with id
http://localhost:8080/main/catalog/delete/{id} - Delete item with id
http://localhost:8080/logout                   - Logout
***
Login/pass: ADMIN/ADMIN (using CapsLock).
***



Technology Stack:

Java 8+
Spring (Spring Boot, Spring MVC, Spring Data, Spring Security)
H2(inMemoryDB)
Swagger (links:   http://localhost:8080/swagger-ui/          
                  http://localhost:8080/v2/api-docs)
Gmail account for sending messages 
The account settings are activated to allow it to be used by Less Secure Apps (Less Secure Apps) (for being able send mail thought the App). 
LOGIN = chococom.company@gmail.com;
PASSWORD = Ch0C0C0m123. 

If it's not and account didn't send messages, please, following the bellow instructions: 
1. Log in to Gmail account (with login and password above. 
2. Then follow the link: https://www.google.com/settings/security/lesssecureapps
3. Chenge field "Allow less secute apps" to ON.

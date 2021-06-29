# OnlineShopf
Test task - Online Shop. A WEB application that represents the system of small part of order's placing.


USER Role: Available actions: 

http://localhost:8080/registration            Registration

http://localhost:8080/login                   Authorization

http://localhost:8080/main                    View the list of orders and link for the catalog and basket

http://localhost:8080/main/orders/{id}/order  View the order with id {id}

http://localhost:8080/main/catalog            View the catalog, filter items by description (http://localhost:8080/main/catalog/byDescription); 
                                              or tags (http://localhost:8080/main/catalog/byDescription);
                                              add item to the basket (http://localhost:8080/main/catalog/byDescription)

http://localhost:8080/main/basket             View the basket, placed the order, clear basket, add some items to baasket

http://localhost:8080/main/basket/{itemId}/delete   Delete item with {itemId} from the basket

http://localhost:8080/main/basket/{itemId}/placedOrder   Placed order to the items in the basket

http://localhost:8080/login/logout                  Logout


***

Login/pass: USER/USER (using CapsLock).

Login/pass: SASHA/SASHA (using CapsLock).

***


ADMIN Role: 

http://localhost:8080/login                    - Authorization

http://localhost:8080/main                     - View the list of orders and link for the catalog

http://localhost:8080/main/catalog             - View the catalog, filter items by description or tags, links for adding, editing, deleting items

http://localhost:8080/main/catalog/new         - Add new item

http://localhost:8080/main/catalog/{itemId}/edit   - Edit item with id {itemId} 

http://localhost:8080/main/catalog/{itemId}/forceEdit   - Force update item with id {itemId} (in case if item has already in the basket)

http://localhost:8080/main/catalog/{itemId}/deletet  - Delete item with id {itemId} from Catalog

http://localhost:8080/login/logout                   - Logout

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
 

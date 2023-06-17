
# supermart-backend

A web based application for an online supermart. This repository contains backend for this application built using Spring Boot.

### User Management
  - A user can be ```admin, manager or customer```
  - Admin is the super user with all permissions.
  - Users (customer, admin and manager) will have information such as user name, user id etc.
  - ```id (unique across the system)```, phone number, and email id, and address.
  - An existing user should log into the system with user id and password.
  - New customers are permitted to register.
  - Manager/admin can add or delete items, modify details about the items (price/qty/offers etc.)
  - A user can ```withdraw``` from the Application.
  - Password change (reset) facility is provided.

### Admin/Manager Publishing Item Details
  - Manager or admin users should be allowed to ```publish the details of the items``` made available for sale. Details needed are item_name, item_code, price, offer if any, qty available, delivery time etc.
  - A manager or admin can ```delete the published item``` whenever required.

### Customer Order Management
  - A customer should be allowed to ```view/search the items``` published/available for sale.
  - Select items to the cart.
  - Customers can register and order items.
  - Confirm order and make payment
  - User should get a ```confirmation message``` with expected delivery date.
  - User will have a Wallet from where payments will be made, and user can ```top-up the wallet``` if necessary. But at the time of registration must start with minimum of 1000/-.
  - User can add review about the website.

### Other Functionality
  - Admin user should be able to generate reports like – items sold on a date, customer order details(summary) for a month, item stock status etc.
  - Any user can generate his/her ```order history``` (summary) for a given month.


## API Reference

### Admin Endpoints

```java
  POST /admin/getAdmin
  POST /admin/add
  GET /admin/allCustomers
  DELETE /admin/deleteCustomer
  GET /admin/getCustomer
  GET /admin/customerHistory
  POST /admin/addManager
  GET /admin/allManagers
  GET /admin/getManager
  POST /admin/updateManager
  GET /admin/allProducts
  DELETE /admin/deleteManager
  DELETE /admin/deleteProduct
  GET /admin/getProduct
  POST /admin/updateProduct
  GET /admin/monthlyReport
  GET /admin/totalRevenue
  GET /admin/monthlyRevenue
  GET /admin/orders
```

### Manager Endpoints

```java
  POST /manager/addProduct
  POST /manager/updateProduct
  DELETE /manager/deleteProduct
  GET /manager/getAllProducts
  GET /manager/getProduct
  GET /manager/getbyPrice
```

### Customer Endpoints

```java
  POST /customer/add
  GET /customer/getAll
  DELETE /customer/deleteOne
  POST /customer/getCustomer
  POST /customer/update
  POST /customer/addCredit
  POST /customer/history
  POST /customer/placeOrder
  POST /customer/forgotPassword
  POST /customer/sendOTP
  POST /customer/reset
  POST /customer/verify
  POST /customer/getCart
  DELETE /customer/deleteCart
  POST /customer/setCart
  POST /customer/deleteCartOne
  POST /customer/sendOrder
  POST /customer/addReview
```


### Login Endpoints

```java
  POST /login/auth/customer
  GET /login/auth/manager
  GET /login/auth/admin
  POST /login/auth/customer/getID
  POST /login/auth/admin/getID
```

### Product Endpoints

```java
  POST /product/add
  GET /product/getAll
  GET /product/getName
  DELETE /product/deleteOne
  GET /product/getProduct
  POST /product/update
  POST /product/changePrice
  GET /product/getbyPrice
```

## Run Locally

1. clone this git repo
2. install ```java``` and ```mysql``` database into your system
3. ```cd``` to resources
4. create ```application.properties``` file in this dir
5. you can use ```MySQL Workbench``` gui for database or ```cli```
6. now fill in the following lines
   
- server configuration
  ```bash
  server.PORT=
  ```
  set the ```port``` to any port number which is currently not being used by any other application.

- mysql configuration

  ```bash
  spring.jpa.hibernate.ddl-auto=
  spring.datasource.url=
  spring.datasource.username=
  spring.datasource.password=
  spring.datasource.driver-class-name=
  ```
  set ```spring.jpa.hibernate.ddl-auto=update``` to update the jpa repo <br/>
  
  put database connection string in ```spring.datasource.url``` which will be of the format ```jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}``` <br/>
  
  set ```username``` and ```password``` for your database connection, which can be set while establishing the connection from mysql workbench <br/>
  
  set spring.datasource.driver-class-name=```com.mysql.jdbc.Driver``` <br/>

- email configuration

  ```bash
  spring.mail.host=
  spring.mail.port=
  spring.mail.username=
  spring.mail.password=
  spring.mail.properties.mail.smtp.auth=
  spring.mail.properties.mail.smtp.starttls.enable=
  ```
  
  set host=```smtp.gmail.com``` for setting gmail.com as default service
  
  set ```port=587```
  
  put ```username``` and ```password``` for the email account you want to use as admin account for this app, all emails will be sent from this email id
  
  configure these lines
  ```
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.starttls.enable=true
  ```
7. Run ```DemoApplication.java``` file to start the ```JVM```
8. Once backend server starts apis can be accessed through ```http://127.0.0.1:${port}/```

## Tech Stack

- Spring Boot
- MySQL
- JPA
- Spring Mail
- JWT

### Link to Frontend Repo
- <a href = "https://github.com/pavas23/supermart-frontend">Frontend Repo</a>

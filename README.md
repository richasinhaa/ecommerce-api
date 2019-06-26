Backend service for an e-commerce system which allows users to search, add items to their shopping cart, create orders, and checkout successfully.

## Technology Stack

* Java
* Spring Boot
* Maven
* Mysql

### Installation and running the service

* Install Java
* Install Maven
* Install Mysql
* Create spring boot stub using SpringInitializr (https://start.spring.io/)
* Import project in IDE

## Running the application

mvn spring-boot:run

![Screenshot](https://github.com/richasinhaa/Turing-Assignment/blob/master/src/main/resources/backend-architecture.png)
![Screenshot](https://github.com/richasinhaa/Turing-Assignment/blob/master/src/main/resources/registration-login-design.png)
![Screenshot](https://github.com/richasinhaa/Turing-Assignment/blob/master/src/main/resources/system-design.png)

### Restful Endpoints
Request/Response Format Reference :
https://backendapi.turing.com/docs/

```
Examples 
```
* Fetch All Departments
* [GET] /departments

```
[
{
name: "Regional",
description: "Proud of your country? Wear a T-shirt with a national symbol stamp!",
department_id: 1
},
{
name: "Nature",
description: "Find beautiful T-shirts with animals and flowers in our Nature department!",
department_id: 2
},
{
name: "Seasonal",
description: "Each time of the year has a special flavor. Our seasonal T-shirts express traditional symbols using unique postal stamp pictures.",
department_id: 3
}
]
```

*Customer Registration :
* [GET] /customer

```
Request :

{
	"name" : "Richa.S",
	"email" : "richaa@gmail.com",
	"password" : "abc"
}

Response : 
{
    "customer": {
        "name": "Richa.S",
        "email": "richaa@gmail.com",
        "password": "$2a$10$B6KBLysDY2nrN0eesvbrEOpxA8plc4GtygScjBeow6/GEqvPtPQs2",
        "city": null,
        "region": null,
        "customer_id": 13,
        "credit_card": null,
        "address_1": null,
        "address_2": null,
        "postal_code": null,
        "shipping_region_id": 0,
        "day_phone": null,
        "eve_phone": null,
        "mob_phone": null
    },
    "accessToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWNoYWFAZ21haWwuY29tIiwicm9sZXMiOiJ1c2VyIiwiaWF0IjoxNTU5MTEzNDc4fQ.QhPBBXHlzlgDERLHMu-uK_zMtNF1pO7S2nriNSRWaM4",
    "expires_in": "24h"
}

```
* Customer Login
* [GET] /customers/login

```
Request: {
	"email" : "richaa@gmail.com",
	"password" : "abc"
}
Response : 
{
    "customer": {
        "name": "Richa.S",
        "email": "richaa@gmail.com",
        "password": "$2a$10$B6KBLysDY2nrN0eesvbrEOpxA8plc4GtygScjBeow6/GEqvPtPQs2",
        "city": null,
        "region": null,
        "customer_id": 13,
        "credit_card": null,
        "address_1": null,
        "address_2": null,
        "postal_code": null,
        "shipping_region_id": 0,
        "day_phone": null,
        "eve_phone": null,
        "mob_phone": null
    },
    "accessToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWNoYWFAZ21haWwuY29tIiwicm9sZXMiOiJ1c2VyIiwiaWF0IjoxNTU5MTE0NzkyfQ.EyV8adaKiEEE-KXMqxANia7DYyFKLZ0TGefT7SizGBw",
    "expires_in": "24h"
}
```

```
Detailed Request Response
```
https://github.com/richasinhaa/Turing-Assignment/blob/master/src/main/resources/Endpoints


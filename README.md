# Demo Bank Microservices

## Overview 

This is back end application which consist of two microservices, customer and credit card. With these microservice, we can add new resources and get them all or by id. Containerised with kubernetes.

### Technologies

Programming Language : Java 1.17 \
Libraries Used : Spring Boot 3.0.7(Rest API, JPA, SQL connector), Spring cloud(Openfeign), Maven
DB Used : MySQL

### Build and Run with Kubernetes

Execute following commands in project directory -

kubectl create -f mysql-secrets.yaml \
kubectl get secrets \
kubectl create -f mysql-configMap.yaml \
kubectl get configMap \
kubectl create -f db-deployment.yaml

docker build -t credit-card-service:1.0 . \
docker build -t customer-service:1.0 . \
docker images

kubectl create -f credit-card-service-deployment.yaml \
kubectl get svc \
kubectl get deployments \
kubectl get pods \
kubectl describe pod <pod-name> \
kebectl logs <pod-name> 

kubectl create -f customer-service-deployment.yaml \
kubectl get svc \
kubectl get deployments \
kubectl get pods \
kubectl describe pod <pod-name> \
kebectl logs <pod-name>

### Rest API Payloads   

1) GET http://localhost:8082/api/creditCard/getByCreditCardNo/12345678 - Used to get credit card by given credit card no.

   Request : NA \
   Response : 

    ````json
    {
   "credit_card_no": "12345678",
   "balance": 0,
   "card_limit": 2000
   }
    ````

2) POST http://localhost:8082/api/creditCard/create - Used to create/add new credit card 
      
      Request :

      Content-Type : application/json
      ````json
       {
         "card_no": "79927398713",
         "balance": 0,
         "credit_limit": 1000
       }
    ````
      Response :

   ````json
   {
   "card_no": "79927398713",
   "balance": 0,
   "credit_limit": 1000
   }
    ````
   
3) GET http://localhost:8082/api/creditCard - Used to get all credit cards.

   Request : NA \
   Response :

    ````json
   [
     {
       "credit_card_no": "1234567891",
       "balance": 0,
       "card_limit": 5000
     },
     {
       "credit_card_no": "888888888",
       "balance": 0,
       "card_limit": 1000
     }
   ]
    ````

4) GET http://localhost:8080/api/customer/getById/6 - Used to get customer info with credit card details from credit card microservice.

   Request : NA \
   Response :

    ````json
    {
   "id": 6,
   "first_name": "John",
   "last_name": "Smith",
   "creditCardResponse": {
      "credit_card_no": "12345678",
      "balance": 0,
      "card_limit": 2000
       }
   }
    ````

5) POST http://localhost:8082/api/customer/create - Used to create/add new customer

   Request :

   Content-Type : application/json
      ````json
       {
         "first_name":"John",
         "last_name":"Smith",
         "credit_card_no":"12345678"
       }
       
    ````
   Response :

   ````json
   {
   "id": 6,
   "first_name": "John",
   "last_name": "Smith",
   "creditCardResponse": {
      "credit_card_no": "12345678",
      "balance": 0,
      "card_limit": 2000
    }
   }
    ````

6) GET http://localhost:8080/api/customer - Used to get all customer info with credit card details from credit card microservice.

   Request : NA \
   Response :

    ````json
    [
      {
        "id": 1,
        "first_name": "Amruta",
        "last_name": "Waychal",
        "creditCardResponse": {
          "credit_card_no": "888888888",
          "balance": 0,
          "card_limit": 1000
        }
      },
      {
        "id": 4,
        "first_name": "test",
        "last_name": "test1",
        "creditCardResponse": {
          "credit_card_no": "1234567891",
          "balance": 0,
          "card_limit": 5000
        }
      }
   ]
    ````

### Ongoing Improvements
 
1) Proper exception handling 
2) Add Ingress, to have one api gateway 
3) Add request validations


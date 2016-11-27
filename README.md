# Money Transfer Service

This is a RESTful API for money transfers between users/accounts.<br/>
Non-functional requirements (NFRs):
* Service is kept simple to cover just base operations<br/>
(no authentication is implemented, few and just trivial validations, etc.)
* In-memory database _hsqldb_ is used
* 2 jars are generated and the latest is described in **Installation** chapter
  * small-sized for 'fair' web-app server 
  * large-sized with embedded Grizzly server

## Installation

1) Pull these files from [/money-transfer-service/additional/](/additional/) :
+ start.bat
+ money-transfer-service-1.0-SNAPSHOT.jar

2) Run start.bat on your local machine<br/>
   Service will start at: http://localhost:8080/moneytransfer

## DB Schema
![In-memory database schema](/additional/er_model.png "In-memory database schema")

## API

| Operation        | Parameters           | Result        |
|:-----------------|:-------------:|:-------------:|
| POST `/users/create` | QueryParameters: firstName, lastName |created User, JSON representation|
| GET `/users/{id}` | PathParams: id | found User, JSON representation|
| GET `/users/all` | |all Users, JSON representation|
| DELETE `/users/delete/{id}` | PathParams: id | deletion result, TEXT_HTML representation|
| POST `/accounts/create` | QueryParameters: userId, balance |created Account, JSON representation|
| GET `/accounts/{id}` | PathParams: id | found Account, JSON representation|
| GET `/accounts/all` | | all Account, JSON representation|
| DELETE `/accounts/delete/{id}` | PathParams: id | deletion result, TEXT_HTML representation|
| POST `/transfers/makeTransaction` | QueryParameters: accountFromId, accountToId, transferSum |created Transfer, JSON representation|
| GET `/transfers/{id}` | PathParams: id | found Transfer, JSON representation|
| GET `/transfers/all` | | all Transfers, JSON representation|
| DELETE `/transfers/delete/{id}` | PathParams: id | deletion result, TEXT_HTML representation|
| POST `/createTestData` |  |Helper operation, creates test data, TEXT_HTML representation|
| GET `/isworking` |  |Helper operation, checks service connectivity, TEXT_HTML representation|



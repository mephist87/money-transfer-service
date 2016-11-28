# Test Cases

|| Scenario        | Operation         | Result        |
|:-:|:-----------------|:-------------:|:-------------:|
|1.1| Create User: create | **curl** `'http://localhost:8080/moneytransfer/users/create?firstName=John&lastName=Doe' -X POST -H 'Content-Type: application/json'` |![](/additional/test/1_createUser_create.png)|
|1.2| Create User: check | **curl** `'http://localhost:8080/moneytransfer/users/1'` |![](/additional/test/1_createUser_get.png)|
| |   |   |   |
|2.1| Delete User: delete | **curl** `'http://localhost:8080/moneytransfer/users/delete/1' -X DELETE -d '{}' -H 'Content-Type: application/json'` |![](/additional/test/2_deleteUser_delete.png)|
|2.2| Delete User: check | **curl** `'http://localhost:8080/moneytransfer/users/1'` |![](/additional/test/2_deleteUser_check.png)|
| |   |   |   |
|3.1| Get all Users: createTestData | **curl** `'http://localhost:8080/moneytransfer/createTestData' -X POST -H 'Content-Type: application/json'` |![](/additional/test/3_getAllUsers_createTestData.png)|
|3.2| Get all Users: check | **curl** `'http://localhost:8080/moneytransfer/users/all'` |![](/additional/test/3_getAllUsers_check.png)|
| |   |   |   |
|4.1| Delete User's linked accounts: createTestData | **curl** `'http://localhost:8080/moneytransfer/users/create?firstName=Mike&lastName=Tyson' -X POST -H 'Content-Type: application/json'` |![](/additional/test/4_deleteUserAccountsByConstraint_createUser.png)|
|4.2| Delete User's linked accounts: create account #1 | **curl** `'http://localhost:8080/moneytransfer/accounts/create?userId=5&balance=20' -X POST -d '{}' -H 'Content-Type: application/json'` |![](/additional/test/4_deleteUserAccountsByConstraint_createAccount1.png)|
|4.3| Delete User's linked accounts: create account #2 | **curl** `'http://localhost:8080/moneytransfer/accounts/create?userId=5&balance=55' -X POST -d '{}' -H 'Content-Type: application/json'` |![](/additional/test/4_deleteUserAccountsByConstraint_createAccount2.png)|
|4.4| Delete User's linked accounts: delete User | **curl** `'http://localhost:8080/moneytransfer/users/delete/5' -X DELETE -d '{}' -H 'Content-Type: application/json'` |![](/additional/test/4_deleteUserAccountsByConstraint_deleteUser.png)|
|4.5| Delete User's linked accounts: check related accounts deleted | **curl** `'http://localhost:8080/moneytransfer/accounts/all'` |![](/additional/test/4_deleteUserAccountsByConstraint_check.png)|
| |   |   |   |
|5.1| Make Transfer: createTestData | **curl** `'http://localhost:8080/moneytransfer/createTestData' -X POST -H 'Content-Type: application/json'` |![](/additional/test/5_makeTransfer_createTestData.png)|
|5.2| Make Transfer: makeTransfer and check new balances | **curl** `'http://localhost:8080/moneytransfer/transfers/makeTransaction?accountFromId=2&accountToId=1&transferSum=12' -X POST -d '{}' -H 'Content-Type: application/json'` |![](/additional/test/5_makeTransfer_makeTransfer.png)|
| |   |   |   |
|6.1| Rollback Transfer: createTestData | **curl** `'http://localhost:8080/moneytransfer/createTestData' -X POST -H 'Content-Type: application/json'` |![](/additional/test/6_rollbackTransfer_createTestData.png)|
|6.2| Rollback Transfer: makeTransfer | **curl** `'http://localhost:8080/moneytransfer/transfers/makeTransaction?accountFromId=2&accountToId=1&transferSum=12' -X POST -d '{}' -H 'Content-Type: application/json'` |![](/additional/test/6_rollbackTransfer_makeTransaction.png)|
|6.3| Rollback Transfer: rollbackTransfer | **curl** `'http://localhost:8080/moneytransfer/transfers/rollbackTransaction?transferId=6' -X POST -d '{}' -H 'Content-Type: application/json'` |![](/additional/test/6_rollbackTransfer_rollbackTransaction.png)|
|6.4| Rollback Transfer: checkBalances | **curl** `'http://localhost:8080/moneytransfer/accounts/all'` |![](/additional/test/6_rollbackTransfer_checkBalances.png)|

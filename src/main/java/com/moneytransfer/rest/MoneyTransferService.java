package com.moneytransfer.rest;

//import java.util.Optional;
import com.moneytransfer.rest.dao.AccountDAO;
import com.moneytransfer.rest.dao.UserDAO;
import com.moneytransfer.rest.model.AccountEntity;
import com.moneytransfer.rest.model.UserEntity;

import java.util.List;
//import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MoneyTransferService {

//    private final CopyOnWriteArrayList<Customer> cList = CustomerList.getInstance();

    @Inject
    private UserDAO userDAO;

    @Inject
    private AccountDAO accountDAO;

    @POST
    @Path("/users/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserEntity createUser(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName)
    {
        UserEntity user = new UserEntity(firstName, lastName);
        userDAO.createOrUpdate(user);
        return user;
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity getUser(@PathParam("id") int userId) {
        return userDAO.get(userId);
    }

    @GET
    @Path("/users/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getAllUsers() {
        return userDAO.list();
    }

    @DELETE
    @Path("/users/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteUser(@PathParam("id") int userId)
    {
        UserEntity user = userDAO.get(userId);
        return userDAO.delete(user) ? "User Deleted:\n" + user.toString() : "Unable to delete user with id = " + userId;
    }


    @POST
    @Path("/accounts/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AccountEntity createAccount(@QueryParam("userId") int userId, @QueryParam("balance") int balance) {
        AccountEntity account = new AccountEntity();
        UserEntity user = userDAO.get(userId);
        if (user != null && user.getId() != 0) {
            account = new AccountEntity(user, balance);
            accountDAO.createOrUpdate(account);
        }
        return account;
    }

    @GET
    @Path("/accounts/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountEntity getAccount(@PathParam("id") int accountId) {
        return accountDAO.get(accountId);
    }

    @GET
    @Path("/accounts/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountEntity> getAllAccounts() {
        return accountDAO.list();
    }

    @DELETE
    @Path("/accounts/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteAccount(@PathParam("id") int accountId)
    {
        AccountEntity account = accountDAO.get(accountId);
        return accountDAO.delete(account) ? "Account Deleted:\n" + account.toString() : "Unable to delete account with id = " + accountId;
    }

//    @GET
//    @Path("{id}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getCustomer(@PathParam("id") long id) {
//        Optional<Customer> match
//                = cList.stream()
//                .filter(c -> c.getId() == id)
//                .findFirst();
//        StringBuilder result = new StringBuilder("---Customer---\n");
//        for (Customer c : cList) {
//            if (c.getId() == id) {
//                result.append(c.toString()).append("\n");
//                return result.toString();
//            }
//        }
//        return "Customer not found";
//    }
}
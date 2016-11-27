package com.moneytransfer.rest;

import com.moneytransfer.rest.dao.AccountDAO;
import com.moneytransfer.rest.dao.TransferDAO;
import com.moneytransfer.rest.dao.UserDAO;
import com.moneytransfer.rest.model.AccountEntity;
import com.moneytransfer.rest.model.TransferEntity;
import com.moneytransfer.rest.model.UserEntity;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MoneyTransferService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private TransferDAO transferDAO;

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
            accountDAO.create(account);
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

    @POST
    @Path("/transfers/makeTransaction")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TransferEntity makeTransfer(@QueryParam("accountFromId") int accountFromId,
                                       @QueryParam("accountToId") int accountToId,
                                       @QueryParam("transferSum") int transferSum) {

        AccountEntity accountFrom = accountDAO.get(accountFromId);
        AccountEntity accountTo = accountDAO.get(accountToId);
        if (accountFrom.getId() != 0 && accountTo.getId() != 0) {
            transferDAO.makeTransfer(accountFrom, accountTo, transferSum);
        }
        return null;
    }

    @GET
    @Path("/transfers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TransferEntity getTransfer(@PathParam("id") int transferId) {
        return transferDAO.get(transferId);
    }

    @GET
    @Path("/transfers/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TransferEntity> getAllTransfers() {
        return transferDAO.list();
    }

    @DELETE
    @Path("/transfers/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteTransfer(@PathParam("id") int transferId)
    {
        TransferEntity transfer = transferDAO.get(transferId);
        return transferDAO.delete(transfer) ? "Transfer Deleted:\n" + transfer.toString() : "Unable to delete transfer with id = " + transferId;
    }

    @POST
    @Path("/transfers/rollbackTransaction")
    @Produces(MediaType.TEXT_HTML)
    public String makeTransfer(@QueryParam("transferId") int transferId) {
        TransferEntity transfer = transferDAO.get(transferId);
        if (transfer.getId() != 0) {
            return transferDAO.rollbackTransfer(transfer) ? "Transfer rolled back:\n" + transfer.toString() : "Unable to rollback transfer with id = " + transferId;
        }
        return "Transfer with id = " + transferId + " not found";
    }
}
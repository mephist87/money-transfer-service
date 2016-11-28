package com.moneytransfer.rest;

import com.moneytransfer.rest.dao.AccountDAO;
import com.moneytransfer.rest.dao.TransferDAO;
import com.moneytransfer.rest.dao.UserDAO;
import com.moneytransfer.rest.model.AccountEntity;
import com.moneytransfer.rest.model.TransferEntity;
import com.moneytransfer.rest.model.UserEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Path("/")
public class HelperService {

    private static final int MIN_BALANCE = 100;
    private static final int MAX_BALANCE = 1000;

    @Inject
    private UserDAO userDAO;

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private TransferDAO transferDAO;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/isworking")
    @Produces(MediaType.TEXT_PLAIN)
    public String checkServiceStatus() {
        return "Service is working!";
    }

    @POST
    @Path("/createTestData")
    @Produces(MediaType.TEXT_HTML)
    public String createTestData()
    {
        UserEntity user1 = new UserEntity("Sergey", "Krasnov");
        UserEntity user2 = new UserEntity("John", "Doe");
        UserEntity user3 = new UserEntity("Christopher", "Nolan");
        Arrays.asList(user1, user2, user3).forEach(user -> userDAO.create(user));

        AccountEntity account1 = new AccountEntity(user1, getRandomBalance());
        AccountEntity account2 = new AccountEntity(user2, getRandomBalance());
        AccountEntity account3 = new AccountEntity(user3, getRandomBalance());
        Arrays.asList(account1, account2, account3).forEach(account -> accountDAO.create(account));

        TransferEntity transfer1 = new TransferEntity(account1, account2, 20);
        TransferEntity transfer2 = new TransferEntity(account2, account3, 50);
        TransferEntity transfer3 = new TransferEntity(account1, account3, 70);
        TransferEntity transfer4 = new TransferEntity(account3, account1, 22);
        TransferEntity transfer5 = new TransferEntity(account2, account3, 60);
        Arrays.asList(transfer1, transfer2, transfer3, transfer4, transfer5).forEach(transfer -> transferDAO.create(transfer));

        StringBuilder result = new StringBuilder();
        result.append("---Users---\n");
        userDAO.list().forEach(user -> result.append(user.toString()).append("\n"));
        result.append("\n---Accounts---\n");
        accountDAO.list().forEach(account -> result.append(account.toString()).append("\n"));
        result.append("\n---Transfers---\n");
        transferDAO.list().forEach(transfer -> result.append(transfer.toString()).append("\n"));

        return result.toString();
    }

    private int getRandomBalance() {
        return ThreadLocalRandom.current().nextInt(MIN_BALANCE, MAX_BALANCE);
    }

}

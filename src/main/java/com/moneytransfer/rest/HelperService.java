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
        StringBuilder result = new StringBuilder();

        List<UserEntity> testUsers = new ArrayList<>();
        UserEntity user1 = new UserEntity("Sergey", "Krasnov");
        UserEntity user2 = new UserEntity("John", "Doe");
        UserEntity user3 = new UserEntity("Christopher", "Nolan");
        testUsers.addAll(Arrays.asList(user1, user2, user3));
        result.append(createUsers(testUsers));

        List<AccountEntity> testAccounts = new ArrayList<>();
        AccountEntity account1 = new AccountEntity(user1, getRandomBalance());
        AccountEntity account2 = new AccountEntity(user2, getRandomBalance());
        AccountEntity account3 = new AccountEntity(user3, getRandomBalance());
        testAccounts.addAll(Arrays.asList(account1, account2, account3));
        result.append(createAccounts(testAccounts));

        List<TransferEntity> testTransfers = new ArrayList<>();
        TransferEntity transfer1 = new TransferEntity(account1, account2, 20);
        TransferEntity transfer2 = new TransferEntity(account2, account3, 50);
        TransferEntity transfer3 = new TransferEntity(account1, account3, 70);
        TransferEntity transfer4 = new TransferEntity(account3, account1, 22);
        TransferEntity transfer5 = new TransferEntity(account2, account3, 60);
        testTransfers.addAll(Arrays.asList(transfer1, transfer2, transfer3, transfer4, transfer5));
        result.append(createMoneyTransfers(testTransfers));

        return result.toString();
    }

    private int getRandomBalance() {
        return ThreadLocalRandom.current().nextInt(MIN_BALANCE, MAX_BALANCE);
    }

    private StringBuilder createUsers(List<UserEntity> users) {
        StringBuilder result = new StringBuilder("---Users---\n");
        users.forEach(user -> {
                    userDAO.create(user);
                    result.append(user.toString()).append("\n");
                }
        );
        return result.append("\n");
    }

    private StringBuilder createAccounts(List<AccountEntity> accounts) {
        StringBuilder result = new StringBuilder("---Accounts---\n");
        accounts.forEach(account -> {
            accountDAO.create(account);
            result.append(account.toString()).append("\n");
        });
        return result.append("\n");
    }

    private StringBuilder createMoneyTransfers(List<TransferEntity> transfers) {
        StringBuilder result = new StringBuilder("---Transfers---\n");
        transfers.forEach(transfer -> {
                    transferDAO.create(transfer);
                    result.append(transfer.toString()).append("\n");
                }
        );
        return result.append("\n");
    }

}

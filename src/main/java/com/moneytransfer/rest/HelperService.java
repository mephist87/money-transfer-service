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
        testUsers.add(new UserEntity("Sergey", "Krasnov"));
        testUsers.add(new UserEntity("John", "Doe"));
        testUsers.add(new UserEntity("Christopher", "Nolan"));
        result.append(createUsers(testUsers));

        List<AccountEntity> testAccounts = new ArrayList<>();
        for (UserEntity user : userDAO.list()) {
            int randomBalance = ThreadLocalRandom.current().nextInt(MIN_BALANCE, MAX_BALANCE);
            testAccounts.add(new AccountEntity(user, randomBalance));
        }
        result.append(createAccounts(testAccounts));

        return result.toString();
    }

    private StringBuilder createUsers(List<UserEntity> users) {
        StringBuilder result = new StringBuilder("---Users---\n");
        users.forEach(user -> {
                    userDAO.createOrUpdate(user);
                    result.append(user.toString()).append("\n");
                }
        );
        return result.append("\n");
    }

    private StringBuilder createAccounts(List<AccountEntity> accounts) {
        StringBuilder result = new StringBuilder("---Accounts---\n");
        accounts.forEach(account -> {
            accountDAO.createOrUpdate(account);
            result.append(account.toString()).append("\n");
        });
        return result.append("\n");
    }

    private StringBuilder createMoneyTransfers(List<TransferEntity> transfers) {
        StringBuilder result = new StringBuilder("---Transfers---\n");
        transfers.forEach(transfer -> {
                    transferDAO.createOrUpdate(transfer);
                    result.append(transfer.toString()).append("\n");
                }
        );
        return result.append("\n");
    }

}

package com.moneytransfer.rest;

import com.moneytransfer.rest.dao.AccountDAO;
import com.moneytransfer.rest.dao.UserDAO;
import com.moneytransfer.rest.model.AccountEntity;
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

//    public static CopyOnWriteArrayList<Customer> getInstance(){
//        return cList;
//    }
//
    public StringBuilder createUsers(List<UserEntity> users){
//        users.stream()
//                .forEach(i -> System.out.println(i));
//        String cString =
//                users.stream()
//                        .map( c -> c.toString())
//                        .collect(Collectors.joining("\n"));
        StringBuilder result = new StringBuilder("---Users---\n");
        for (UserEntity user : users) {
            userDAO.createOrUpdate(user);
            result.append(user.toString()).append("\n");
        }
        return result.append("\n");
    }

    public StringBuilder createAccounts(List<AccountEntity> accounts){
//        users.stream()
//                .forEach(i -> System.out.println(i));
//        String cString =
//                users.stream()
//                        .map( c -> c.toString())
//                        .collect(Collectors.joining("\n"));
        StringBuilder result = new StringBuilder("---Accounts---\n");
        for (AccountEntity account : accounts) {
            accountDAO.createOrUpdate(account);
            result.append(account.toString()).append("\n");
        }
        return result.append("\n");
    }

}

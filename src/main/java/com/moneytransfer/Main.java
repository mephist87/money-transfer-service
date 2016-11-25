package com.moneytransfer;

import com.moneytransfer.rest.dao.AccountDAO;
import com.moneytransfer.rest.dao.AccountDAOImpl;
import com.moneytransfer.rest.dao.UserDAO;
import com.moneytransfer.rest.dao.UserDaoImpl;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.ContextResolver;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final URI BASE_URI = URI.create("http://localhost:8080/moneytransfer/");

    public static HttpServer startServer() {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createResourceConfiguration(), false);
        server.getServerConfiguration().setAllowPayloadForUndefinedHttpMethods(true);
        try {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdownNow();
            }
        }));
        server.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return server;
    }

    // create a resource config that scans for JAX-RS resources and providers
    // in com.inmemorydbws.rest package
    public static ResourceConfig createResourceConfiguration() {
        return new ResourceConfig()
                .packages("com.moneytransfer.rest")
                .register(createMoxyJsonResolver())
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(UserDaoImpl.class).to(UserDAO.class);
                        bind(AccountDAOImpl.class).to(AccountDAO.class);
                    }
                });
    }

    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        Map<String, String> namespacePrefixMapper = new HashMap<>(1);
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try {
            System.out.println("Starting In Memory Money Transfer Sample");
            startServer();
            System.out.println(String.format("Application started.%nStop the application using Ctrl+C"));
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


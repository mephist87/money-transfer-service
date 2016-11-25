package com.inmemorydbws.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.moneytransfer.Main;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConnectivityTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        // get WebTarget
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    /**
     * Test to see that the message "Service is working!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target.path("isworking").request().get(String.class);
        assertEquals("Service is working!", responseMsg);
    }
}

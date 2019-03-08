import java.io.*;
import java.net.*;
import java.util.Scanner;

import Tests.*;
import com.sun.net.httpserver.*;

public class Server
{
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber)
    {
        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/", new FileHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

    public static void main(String[] args) throws Exception
    {
        org.junit.runner.JUnitCore.runClasses(RegisterTest.class, LoginTest.class, ClearTest.class, FillTest.class, LoadTest.class, PersonTest.class,
                PersonAllTest.class, EventTest.class, EventAllTest.class, PersonDAOTest.class, UserDAOTest.class, EventDAOTest.class, AuthTokenDAOTest.class);
        org.junit.runner.JUnitCore.main("Tests.RegisterTest", "Tests.LoginTest", "Tests.ClearTest", "Tests.FillTest", "Tests.LoadTest",
                "Tests.PersonTest", "Tests.PersonAllTest", "Tests.EventTest", "Tests.EventAllTest", "Tests.PersonDAOTest", "Tests.UserDAOTest", "Tests.EventDAOTest", "Tests.AuthTokenDAOTest");

        String portNumber = args[0];
        new Server().run(portNumber);

        System.out.println();


    }
}

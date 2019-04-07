import java.io.*;
import java.net.*;
import Handlers.*;
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

        server.createContext("/", new BaseContext());
        server.createContext("/user/register", new RegisterContext());
        server.createContext("/user/login", new LoginContext());
        server.createContext("/clear", new ClearContext());
        server.createContext("/load", new LoadContext());
        server.createContext("/person", new PersonContext());
        server.createContext("/event", new EventContext());
        server.createContext("/fill", new FillContext());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

    public static void main(String[] args) throws Exception
    {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}

import java.io.*;
import java.net.*;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Requests.LoadRequest;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.*;
import Services.*;
import com.sun.net.httpserver.*;
import com.google.gson.*;
import java.nio.file.*;

class FileHandler implements HttpHandler
{
    static String absolutePath = System.getProperty("user.dir") + "/src/Web/";

    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            Gson gson = new Gson();

            String requestPath = exchange.getRequestURI().toString();
            System.out.println(requestPath);
            Headers requestHeader = exchange.getRequestHeaders();
            String requestBody = readString(exchange.getRequestBody());

            OutputStream responseBody = exchange.getResponseBody();
            String responseData = null;

            String filePathStr = null;

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            if (exchange.getRequestMethod().equals("GET"))
            {
                if (requestPath.equals("/"))
                {
                    Database db = new Database();
                    try
                    {
                        db.createTables();
                    }
                    catch (DataAccessException e)
                    {

                    }
                    filePathStr = absolutePath + "index.html";
                    Path filePath = FileSystems.getDefault().getPath(filePathStr);
                    Files.copy(filePath, responseBody);
                }
                if (requestPath.equals("/css/main.css"))
                {
                    filePathStr = absolutePath + "css/main.css";
                    Path filePath = FileSystems.getDefault().getPath(filePathStr);
                    Files.copy(filePath, responseBody);
                }
                if (requestPath.equals("HTML/404.html"))
                {
                    filePathStr = absolutePath + "HTML/404.html";
                    Path filePath = FileSystems.getDefault().getPath(filePathStr);
                    Files.copy(filePath, responseBody);
                }
                if (requestPath.equals("/favicon.ico"))
                {
                    filePathStr = absolutePath + "favicon.ico";
                    Path filePath = FileSystems.getDefault().getPath(filePathStr);
                    Files.copy(filePath, responseBody);
                }
                if (requestPath.matches("/person/([-a-zA-Z_0-9]+)"))
                {
                    PersonResult personResult = null;
                    if (requestHeader.containsKey("Authorization"))
                    {
                        System.out.println("Starting person service");
                        personResult = new PersonService().person(requestPath.substring(8), requestHeader.getFirst("Authorization"));
                    }
                    else
                    {
                        personResult = new PersonResult("No authentication token provided");
                    }
                    responseData = gson.toJson(personResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.equals("/person"))
                {
                    PersonAllResult personAllResult = null;
                    if (requestHeader.containsKey("Authorization"))
                    {
                        personAllResult = new PersonAllService().person(requestHeader.getFirst("Authorization"));
                    }
                    else
                    {
                        personAllResult = new PersonAllResult("No authentication token provided");
                    }
                    responseData = gson.toJson(personAllResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.matches("/event/([-a-zA-Z_0-9]+)"))
                {
                    EventResult eventResult = null;
                    if (requestHeader.containsKey("Authorization"))
                    {
                        System.out.println("Starting event service");
                        eventResult = new EventService().event(requestPath.substring(7), requestHeader.getFirst("Authorization"));
                    }
                    else
                    {
                        eventResult = new EventResult("No authentication token provided");
                    }
                    responseData = gson.toJson(eventResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.equals("/event"))
                {
                    EventAllResult eventAllResult = null;
                    if (requestHeader.containsKey("Authorization"))
                    {
                        System.out.println("Starting person all service");
                        eventAllResult = new EventAllService().event(requestHeader.getFirst("Authorization"));
                    }
                    else
                    {
                        eventAllResult = new EventAllResult("No authentication token provided");
                    }
                    responseData = gson.toJson(eventAllResult);
                    writeString(responseData, responseBody);
                }
            }
            else
            {
                if (requestPath.equals("/user/register"))
                {
                    RegisterResult registerResult = null;
                    try
                    {
                        registerResult = new RegisterService().register(gson.fromJson(requestBody, RegisterRequest.class));
                    }
                    catch (Exception e)
                    {
                        registerResult = new RegisterResult("Request property missing or has invalid value");
                    }
                    responseData = gson.toJson(registerResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.equals("/user/login"))
                {
                    LoginResult loginResult = null;
                    try
                    {
                        loginResult = new LoginService().login(gson.fromJson(requestBody, LoginRequest.class));
                    }
                    catch (Exception e)
                    {
                        loginResult = new LoginResult("Request property missing or has invalid value");
                    }
                    responseData = gson.toJson(loginResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.equals("/load"))
                {
                    LoadResult loadResult = null;
                    try
                    {
                        loadResult = new LoadService().load(gson.fromJson(requestBody, LoadRequest.class));
                    }
                    catch (Exception e)
                    {
                        loadResult = new LoadResult("Invalid request data");
                    }
                    responseData = gson.toJson(loadResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.equals("/clear"))
                {
                    ClearResult clearResult = new ClearService().clear();
                    responseData = gson.toJson(clearResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.matches("/fill/([a-zA-Z_0-9]+)"))
                {
                    FillResult fillResult = new FillService().fill(requestPath.substring(6), 4);
                    responseData = gson.toJson(fillResult);
                    writeString(responseData, responseBody);
                }
                if (requestPath.matches("/fill/([-a-zA-Z_0-9]+)/([0-9]+)"))
                {
                    int index = requestPath.lastIndexOf("/");
                    FillResult fillResult = new FillService().fill(requestPath.substring(6, index), Integer.parseInt(requestPath.substring(index + 1)));
                    responseData = gson.toJson(fillResult);
                    writeString(responseData, responseBody);
                }
            }
            responseBody.close();
        }
        catch (Exception e)
        {
            writeString("Internal Server Error", exchange.getResponseBody());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }

    private String readString(InputStream is) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException
    {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
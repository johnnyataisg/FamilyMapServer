import java.io.*;
import java.net.*;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Requests.RegisterRequest;
import Results.ClearResult;
import Results.FillResult;
import Results.PersonResult;
import Results.RegisterResult;
import Services.ClearService;
import Services.FillService;
import Services.PersonService;
import Services.RegisterService;
import com.sun.net.httpserver.*;
import com.google.gson.*;
import java.nio.file.*;

class FileHandler implements HttpHandler
{
    static String absolutePath = System.getProperty("user.dir") + "/src/Web/";

    public void handle(HttpExchange exchange) throws IOException
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
            if (requestPath.matches("/person/([-a-zA-Z_0-9]*)"))
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
            if (requestPath.equals("/create"))
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
        }
        else
        {
            if (requestPath.equals("/user/register"))
            {
                RegisterResult registerResult = new RegisterService().register(gson.fromJson(requestBody, RegisterRequest.class));
                responseData = gson.toJson(registerResult);
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
                System.out.println("Starting fill service for " + requestPath.substring(6));
                FillResult fillResult = new FillService().fill(requestPath.substring(6), 4);
                responseData = gson.toJson(fillResult);
                writeString(responseData, responseBody);
            }
            if (requestPath.matches("/fill/([-a-zA-Z_0-9]+)/([0-9]+)"))
            {
                int index = requestPath.lastIndexOf("/");
                System.out.println("Starting fill service for " + requestPath.substring(6, index) + ": " + Integer.parseInt(requestPath.substring(index + 1)) + " generations");
                FillResult fillResult = new FillService().fill(requestPath.substring(6, index), Integer.parseInt(requestPath.substring(index + 1)));
                responseData = gson.toJson(fillResult);
                writeString(responseData, responseBody);
            }
        }
        responseBody.close();
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
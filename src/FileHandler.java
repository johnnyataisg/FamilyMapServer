import java.io.*;
import java.net.*;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Requests.RegisterRequest;
import Results.ClearResult;
import Results.RegisterResult;
import Services.ClearService;
import Services.RegisterService;
import com.sun.net.httpserver.*;
import com.google.gson.*;
import java.nio.file.*;

class FileHandler implements HttpHandler
{
    static String absolutePath = "C:/Users/Johnny Pao/Desktop/School Stuff/Class Files/CS 240/FamilyMapServer/src/Web/";
    static Gson gson = new Gson();
    String responseData = "";

    public void handle(HttpExchange exchange) throws IOException
    {
        String requestPath = exchange.getRequestURI().toString();
        String requestBody = readString(exchange.getRequestBody());

        OutputStream responseBody = exchange.getResponseBody();
        String responseData = null;

        String filePathStr = null;

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        if (exchange.getRequestMethod().equals("GET"))
        {
            switch (requestPath)
            {
                case "/":
                    filePathStr = absolutePath + "index.html";
                    break;
                case "/css/main.css":
                    filePathStr = absolutePath + "css/main.css";
                    break;
                case "/HTML/404.html":
                    filePathStr = absolutePath + "HTML/404.html";
                    break;
                case "/favicon.ico":
                    filePathStr = absolutePath + "favicon.ico";
                    break;
                case "/create":
                    Database db = new Database();
                    try
                    {
                        db.createTables();
                    }
                    catch (DataAccessException e)
                    {

                    }
                    filePathStr = absolutePath + "index.html";
                    break;
            }
            Path filePath = FileSystems.getDefault().getPath(filePathStr);
            Files.copy(filePath, responseBody);
        }
        else
        {
            switch (requestPath)
            {
                case "/user/register":
                    RegisterResult registerResult = new RegisterService().register(gson.fromJson(requestBody, RegisterRequest.class));
                    responseData = gson.toJson(registerResult);
                    break;
                case "/clear":
                    ClearResult clearResult = new ClearService().clear();
                    responseData = gson.toJson(clearResult);
                    break;
            }
            writeString(responseData, responseBody);
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
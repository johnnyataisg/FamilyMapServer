package Handlers;

import java.io.*;
import java.net.*;
import DataAccess.DataAccessException;
import DataAccess.Database;
import com.sun.net.httpserver.*;
import com.google.gson.*;
import java.nio.file.*;

public class BaseContext implements HttpHandler
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
package Handlers;

import java.io.*;
import java.net.*;
import Results.*;
import Services.*;
import com.sun.net.httpserver.*;
import com.google.gson.*;

public class EventContext implements HttpHandler
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

            if (requestPath.equals("/event") && exchange.getRequestMethod().equals("GET"))
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
            else if (requestPath.matches("/event/([-a-zA-Z_0-9]+)") && exchange.getRequestMethod().equals("GET"))
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
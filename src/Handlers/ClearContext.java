package Handlers;

import java.io.*;
import java.net.*;
import Results.*;
import Services.*;
import com.sun.net.httpserver.*;
import com.google.gson.*;

public class ClearContext implements HttpHandler
{
    static String absolutePath = System.getProperty("user.dir") + "/src/Web/";

    public void handle(HttpExchange exchange) throws IOException
    {
        try {
            Gson gson = new Gson();

            String requestPath = exchange.getRequestURI().toString();
            System.out.println(requestPath);
            Headers requestHeader = exchange.getRequestHeaders();
            String requestBody = readString(exchange.getRequestBody());

            OutputStream responseBody = exchange.getResponseBody();
            String responseData = null;

            String filePathStr = null;

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            if (requestPath.equals("/clear"))
            {
                ClearResult clearResult = new ClearService().clear();
                responseData = gson.toJson(clearResult);
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

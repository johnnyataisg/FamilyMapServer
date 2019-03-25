package Handlers;

import java.io.*;
import java.net.*;
import Requests.LoginRequest;
import Results.*;
import Services.*;
import com.sun.net.httpserver.*;
import com.google.gson.*;

public class LoginContext implements HttpHandler
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

            if (requestPath.equals("/user/login") && exchange.getRequestMethod().equals("POST"))
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

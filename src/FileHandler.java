import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import com.sun.net.httpserver.*;
import com.google.gson.*;
import java.nio.file.*;

/*
	The ListGamesHandler is the HTTP handler that processes
	incoming HTTP requests that contain the "/games/list" URL path.
	
	Notice that ListGamesHandler implements the HttpHandler interface,
	which is define by Java.  This interface contains only one method
	named "handle".  When the HttpServer object (declared in the Server class)
	receives a request containing the "/games/list" URL path, it calls 
	ListGamesHandler.handle() which actually processes the request.
*/
class FileHandler implements HttpHandler
{
    static String absolutePath = "C:/Users/Johnny Pao/Desktop/School Stuff/Class Files/CS 240/FamilyMapServer/src/Web/";
    static Database db = new Database();

    public void handle(HttpExchange exchange) throws IOException
    {
        String requestPath = exchange.getRequestURI().toString();
        String filePathStr = null;

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
            case "/create":
                try
                {
                    db.createTables();
                }
                catch (DataAccessException e)
                {
                    e.printStackTrace();
                }
                filePathStr = absolutePath + "index.html";
        }
        Path filePath = FileSystems.getDefault().getPath(filePathStr);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream oStream = exchange.getResponseBody();
        Files.copy(filePath, oStream);
        oStream.close();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
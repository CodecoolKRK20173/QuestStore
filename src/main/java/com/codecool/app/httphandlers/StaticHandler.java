package com.codecool.app.httphandlers;

import com.codecool.app.helpers.MimeTypeResolver;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class StaticHandler implements HttpHandler {
    private final String PAGE_404_URL = "static/page404.html";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Get file path from URL
        URI uri = httpExchange.getRequestURI();
        System.out.println("Looking for: " + uri.getPath());
        String path = "." + uri.getPath();

        // Get file from resource folder
        ClassLoader classLoader = getClass().getClassLoader();
        URL fileURL = classLoader.getResource(path);

        if (fileURL == null){
            send404(httpExchange);
        } else {
            sendFile(httpExchange, fileURL);
        }
    }

    private void send404(HttpExchange httpExchange) throws IOException{
        StringBuilder result = new StringBuilder();
        String response = "";
        ClassLoader classLoader = getClass().getClassLoader();
        File loginPage = new File(classLoader.getResource(PAGE_404_URL).getFile());

        try (Scanner scanner = new Scanner(loginPage)){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            response = result.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException{
        // Get the file
        File file = new File(fileURL.getFile());

        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();

        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        // send the file
        FileInputStream fileInputStream = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int bytesRead = 0;
        while ((bytesRead = fileInputStream.read(buffer)) >= 0){
            os.write(buffer, 0, bytesRead);
        }
        os.close();
    }
}

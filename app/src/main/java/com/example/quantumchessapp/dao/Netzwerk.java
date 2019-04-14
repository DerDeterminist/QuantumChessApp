package com.example.quantumchessapp.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Netzwerk {

    public void sendeAnServer(JSONObject jd) {

        int port = 1337;
        Socket socket = null;

        System.out.println("Daten werden geschickt...");

        try {
            socket = new Socket("localhost", port);
            startSocketHandler(socket, jd);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert socket != null;
            closeSocket(socket);
        }
    }

    private static void startSocketHandler(Socket socket, JSONObject jd) {

        try {
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // String text = reader.readLine();
            // JSONObject jsonObject = new JSONObject();
            // jsonObject.put("message", "Hello World!");
            // writer.write(jsonObject.toString() + "\n");

            writer.write(jd.toString() + "\n");
            writer.flush();

            String s = reader.readLine();
            JSONObject jsonObject = new JSONObject(s);

            System.out.println("Received from server: \n" + jsonObject.toString(2));

        } catch (IOException | JSONException e) {
            System.err.println(e);
        } finally {
            assert socket != null;
            closeSocket(socket);
        }
    }

    private static void closeSocket(Socket socket) {
        try {

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

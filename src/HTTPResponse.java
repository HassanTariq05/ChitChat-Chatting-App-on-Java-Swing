import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HTTPResponse {
    public static void getHTTPChannelResponse() {
        String urlString = "http://127.0.0.1:8080/channels/Client-ID=" + Client.myId;
        System.out.println(urlString);

        try {
            HttpURLConnection conn = fetchResponse(urlString);

            if (conn.getResponseCode() != 200) {
                System.out.println("Error: " + conn.getResponseMessage());
                return;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scan = new Scanner(conn.getInputStream());
            while (scan.hasNext()) {
                resultJson.append(scan.nextLine());
            }

            scan.close();
            conn.disconnect();
            System.out.println("resultJson:");
            System.out.println(resultJson);

            Client.addChannel(resultJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getHTTPChatResponse(int channelId) {
        String urlString = "http://127.0.0.1:8080/chats/Channel-ID=" + channelId;
        System.out.println(urlString);

        try {
            HttpURLConnection conn = fetchResponse(urlString);

            if (conn.getResponseCode() != 200) {
                System.out.println("Error: " + conn.getResponseMessage());
                return;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scan = new Scanner(conn.getInputStream());
            while (scan.hasNext()) {
                resultJson.append(scan.nextLine());
            }

            scan.close();
            conn.disconnect();
            System.out.println("Chats from server:");
            System.out.println(resultJson);

            String jsonString = resultJson.toString();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray chatArray = jsonObject.getJSONArray("channelChat");
            ChatInterface.clearChatUI();

            for (int i = 0; i < chatArray.length(); i++) {
                JSONObject chatMessage = chatArray.getJSONObject(i);

                System.out.println("Chat message: " + chatMessage);

                int receiverId = chatMessage.getInt("receiverId");
                int senderId = chatMessage.getInt("senderId");
                String message = chatMessage.getString("message");

                if (receiverId == Client.myId) {
                    ChatInterface.updateChatUI(message, "incoming");
                }
                if (senderId == Client.myId) {
                    ChatInterface.updateChatUI(message, "outgoing");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HttpURLConnection fetchResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        return conn;
    }
}

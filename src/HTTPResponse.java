import App.env;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HTTPResponse {
    public static void getHTTPChannelResponse() {
        String urlString = env.HTTP_BASE_URL +  "channels/Client-ID=" + User.getInstance().myId;
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



    public static JSONObject getHTTPChatResponse(int channelId, Boolean channelSelected) {
        String urlString = env.HTTP_BASE_URL +  "chats/Channel-ID=" + channelId;
        System.out.println(urlString);

        try {
            HttpURLConnection conn = fetchResponse(urlString);

            if (conn.getResponseCode() != 200) {
                System.out.println("Error: " + conn.getResponseMessage());
                return null;
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
                String timestamp = chatMessage.getString("timestamp");

                if (channelSelected && receiverId == User.getInstance().myId) {
                    ChatInterface.updateChatUI(message, "incoming");

                }
                if (channelSelected && senderId == User.getInstance().myId) {
                    ChatInterface.updateChatUI(message, "outgoing");
                }
            }
            if(!channelSelected) {
                if(!chatArray.isNull(0)) {
                    return chatArray.getJSONObject(chatArray.length()-1);
                } else {
                    JSONObject emptyJson = new JSONObject();
                    emptyJson.put("message","No Message");
                    emptyJson.put("timestamp", "");
                    emptyJson.put("senderId", -1);
                    return emptyJson;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getHTTPAllUserListResponse(int clientId) {
        String urlString = env.HTTP_BASE_URL +  "users/Client-ID=" + clientId;
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
            System.out.println("AllUserList from server:");
            System.out.println(resultJson);

            String jsonString = resultJson.toString();
            JSONObject jsonObject = new JSONObject(jsonString);

            Client.addAllUsersToUsersPanel(jsonObject.toString());

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

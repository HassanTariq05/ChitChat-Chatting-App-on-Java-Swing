import model.Keys;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    static PrintWriter output;
    static String myFullname;

    static int myId;
    static String myUsername;

    static ChatInterface chatInterface;
    static SignupInterface signupInterface;
    static int channelId;

    static List<Channel> channelList = new ArrayList<>();
    static  int selectedChannel;

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 6001)) {
            signupInterface = new SignupInterface();
            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);

            Scanner scanner = new Scanner(System.in);
            String userInput;

            ClientRunnable clientRun = new ClientRunnable(socket);
            new Thread(clientRun).start();

            while (true) {
                userInput = scanner.nextLine();
                System.out.println("Received: " + userInput);
            }
        } catch (Exception e) {
            System.out.println("Exception occured in client main: ");
            e.printStackTrace();
        }
    }
    public static void signup(JSONObject userData) {

        userData.put("command", "signup");
        output.println(userData);

        System.out.println("Sent: "+userData);

    }

    public static void login(JSONObject userData) {
        userData.put("command", "login");
        output.println(userData);

        System.out.println("Sent: " + userData);
    }

    public static void gotoChats() {
        SignupInterface.signupFrame.dispose();
        new ChatInterface();
    }

    public static void sendChatMessage(JSONObject jsonObject) {

        jsonObject.put("command", "send_chat_message");
        output.println(jsonObject);
        System.out.println("Sent: "+jsonObject);
    }
    public static void addNewContact(String username) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "add_new_contact");
        jsonObject.put("username", username);
        jsonObject.put("id", myId);
        jsonObject.put("fullName", myFullname);
        output.println(jsonObject);
        System.out.println("Sent:" + jsonObject);
    }

    public static void addChannel(String resultJson) {
        JSONObject jsonObject = new JSONObject(resultJson);
        JSONArray channels = jsonObject.getJSONArray("clientChannels");

        for (int i = 0; i < channels.length(); i++) {
            JSONObject channelJson = channels.getJSONObject(i);
            JSONArray userIdsJson = channelJson.getJSONArray("userIds");
            JSONArray userFullNamesJson = channelJson.getJSONArray("userFullNames");

            int userId1 = userIdsJson.getInt(0);
            int userId2 = userIdsJson.getInt(1);

            if (userId1 == Client.myId || userId2 == Client.myId) {
                Channel newChannel = new Channel();
                newChannel.userIds.add(userId1);
                newChannel.userIds.add(userId2);
                newChannel.channelId = channelJson.getInt("channelId");
                newChannel.channelName = channelJson.getString("channelName");
                newChannel.userFullNames.add(userFullNamesJson.getString(0));
                newChannel.userFullNames.add(userFullNamesJson.getString(1));

                boolean isDuplicate = false;
                for (Channel channel : Client.channelList) {
                    if (channel.userIds.contains(userId1) && channel.userIds.contains(userId2)) {
                        isDuplicate = true;
                        break;
                    }
                }

                if (!isDuplicate) {
                    Client.channelList.add(newChannel);
                }
            }
        }

        System.out.println("Channels Subscribed: ");
        for (Channel ch : Client.channelList) {
            String fullName;
            int id;
            int channelId = ch.channelId;
            if (ch.userIds.get(0) == Client.myId) {
                fullName = ch.userFullNames.get(1);
                id = ch.userIds.get(1);
            } else {
                fullName = ch.userFullNames.get(0);
                id = ch.userIds.get(0);
            }
            ChatInterface.updateChannelPanel(id, fullName, channelId);
            System.out.println(ch);
        }
    }

    public static void getChannelChat(int channelId) {
        HTTPResponse.getHTTPChatResponse(channelId);
    }
}
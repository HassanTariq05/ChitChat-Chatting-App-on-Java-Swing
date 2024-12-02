import App.env;
import model.User;
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
    private static Client clientInstance = null;
    Client() {
    }

    public static Client getInstance() {
        if(clientInstance == null) {
            clientInstance = new Client();
        }
        return clientInstance;
    }

    PrintWriter output;

    SignupInterface signupInterface;
    public List<Channel> channelList = new ArrayList<>();

    public static void main(String[] args) {

        try (Socket socket = new Socket(env.SOCKET_HOST, env.SOCKET_PORT)) {
            getInstance().signupInterface = new SignupInterface();
            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            getInstance().output = new PrintWriter(socket.getOutputStream(),true);

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
        getInstance().output.println(userData);

        System.out.println("Sent: "+userData);

    }

    public static void login(JSONObject userData) {
        userData.put("command", "login");
        getInstance().output.println(userData);

        System.out.println("Sent: " + userData);
    }

    public static void gotoChats() {
        SignupInterface.signupFrame.dispose();
        new ChatInterface();
    }

    public static void sendChatMessage(JSONObject jsonObject) {

        jsonObject.put("command", "send_chat_message");
        getInstance().output.println(jsonObject);
        System.out.println("Sent: "+jsonObject);
    }
    public static void addNewContact(String username) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "add_new_contact");
        jsonObject.put("username", username);
        jsonObject.put("myUsername", User.getInstance().myUsername);
        jsonObject.put("id", User.getInstance().myId);
        jsonObject.put("fullName", User.getInstance().myFullname);
        getInstance().output.println(jsonObject);
        System.out.println("Sent:" + jsonObject);
    }

    public static void addChannel(String resultJson) {

        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            JSONArray channels = jsonObject.getJSONArray("clientChannels");

            for (int i = 0; i < channels.length(); i++) {
                JSONObject channelJson = channels.getJSONObject(i);
                JSONArray userIdsJson = channelJson.getJSONArray("userIds");
                JSONArray userFullNamesJson = channelJson.getJSONArray("userFullNames");
                JSONArray usernamesJson = channelJson.getJSONArray("usernames");

                int userId1 = userIdsJson.getInt(0);
                int userId2 = userIdsJson.getInt(1);

                if (userId1 == User.getInstance().myId || userId2 == User.getInstance().myId) {
                    Channel newChannel = new Channel();
                    newChannel.userIds.add(userId1);
                    newChannel.userIds.add(userId2);
                    newChannel.channelId = channelJson.getInt("channelId");
                    newChannel.channelName = channelJson.getString("channelName");
                    newChannel.userFullNames.add(userFullNamesJson.getString(0));
                    newChannel.userFullNames.add(userFullNamesJson.getString(1));

                    newChannel.usernames.add(usernamesJson.getString(0));
                    newChannel.usernames.add(usernamesJson.getString(1));


                    boolean isDuplicate = false;
                    for (Channel channel : Client.getInstance().channelList) {
                        if (channel.userIds.contains(userId1) && channel.userIds.contains(userId2)) {
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (!isDuplicate) {
                        Client.getInstance().channelList.add(newChannel);
                    }
                }
            }

            System.out.println("Channels Subscribed: ");
            for (Channel ch : Client.getInstance().channelList) {
                String fullName;
                int id;
                String username;
                int channelId = ch.channelId;
                if (ch.userIds.get(0) == User.getInstance().myId) {
                    fullName = ch.userFullNames.get(1);
                    id = ch.userIds.get(1);
                    username = ch.usernames.get(0);
                } else {
                    fullName = ch.userFullNames.get(0);
                    id = ch.userIds.get(0);
                    username = ch.usernames.get(1);
                }
                ChatInterface.updateChannelPanel(id, fullName, channelId, username);
                System.out.println(ch.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addAllUsersToUsersPanel(String resultJson) {
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            JSONArray userList = jsonObject.getJSONArray("allUserList");

            for (int i = 0; i < userList.length(); i++) {
                JSONObject userObj = userList.getJSONObject(i);
                String fullname = userObj.getString("fullname");
                String username = userObj.getString("username");
                int id = userObj.getInt("id");

                boolean alreadyAdded = false;
                for (Channel ch : ChatInterface.addedChannels) {
                    if (ch.usernames.size() >= 2) {
                        if (username.equalsIgnoreCase(ch.usernames.get(0)) || username.equalsIgnoreCase(ch.usernames.get(1))) {
                            alreadyAdded = true;
                            break;
                        }
                    }
                }
                if (!alreadyAdded) {

                    AddChannelInterface.updateAddAllUserPanel(id, fullname, username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getChannelChat(int channelId) {
        HTTPResponse.getHTTPChatResponse(channelId, true);
    }
}
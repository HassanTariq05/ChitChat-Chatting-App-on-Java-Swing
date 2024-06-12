import model.Chat;
import model.Keys;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.CancelablePrintJob;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ClientRunnable implements Runnable {

    private Socket socket;
    private BufferedReader input;
    public ClientRunnable(Socket s) throws IOException {
        this.socket = s;
        this.input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
    }
    @Override
    public void run() {

        try {
            while(true) {
                String response = input.readLine();
                System.out.println("Receive: "+response);
                receiveMessage(new JSONObject(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveMessage(JSONObject jsonObject) {
        String command = jsonObject.getString("command");
        switch (command) {
            case "registration_successful":
                handleRegistrationSuccessful(jsonObject);
                break;
            case "error":
                handleError(jsonObject);
                break;
            case "response_login_successful":
                handleLoginSuccessfulResponse(jsonObject);
                break;
            case "response_send_chat_message":
                handleResponseSentChatMessage(jsonObject);
                break;
            case "response_add_new_contact":
                handleResponseAddChannel();
                break;
            case "contact_added_to_server":
                handleContactAddedToServer(jsonObject);
                break;
        }
    }

    private static void handleRegistrationSuccessful(JSONObject jsonObject) {
        String username = jsonObject.getString(Keys.KEY_USERNAME);
        if(Client.myUsername.equalsIgnoreCase(username)) {
            Client.myFullname = jsonObject.getString(Keys.KEY_FULL_NAME);
            Client.myId = jsonObject.getInt(Keys.KEY_ID);
            Client.gotoChats();
            HTTPResponse. getHTTPChannelResponse();
        }
    }

    private void handleError(JSONObject jsonObject) {
        String message = jsonObject.getString("error_message");
        SignupInterface.errorLabel.setText(message);
        System.out.println("Error: "+message);
    }
    private void handleLoginSuccessfulResponse(JSONObject jsonObject) {
        String username = jsonObject.getString(Keys.KEY_USERNAME);
        if(Client.myUsername.equalsIgnoreCase(username)) {
            Client.myFullname = jsonObject.getString(Keys.KEY_FULL_NAME);
            Client.myId = jsonObject.getInt(Keys.KEY_ID);
            Client.gotoChats();
            HTTPResponse.getHTTPChannelResponse();

        }
    }

    private void handleResponseSentChatMessage(JSONObject jsonObject) {
        int receiverId = jsonObject.getInt("receiverId");
        int channelId = jsonObject.getInt("channelId");
        if(receiverId == Client.myId && Client.selectedChannel == channelId) {
            String message = jsonObject.getString("message");
            ChatInterface.updateChatUI(message, "incoming");
        }
    }

    private void handleResponseAddChannel() {
        AddChannelInterface.buttonPanel.removeAll();
        HTTPResponse.getHTTPChannelResponse();
    }
    private void handleContactAddedToServer(JSONObject jsonObject) {
        System.out.println("New contact from server!");
        String username = jsonObject.getString("username");
        if(Objects.equals(username, Client.myUsername)) {
            System.out.println("Username: " + username + "\nMy Username:" + Client.myUsername);
            HTTPResponse.getHTTPChannelResponse();
        }
    }
}

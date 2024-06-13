import model.Chat;
import model.User;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ChatInterface {
    JPanel mainContainer = new JPanel();
    JPanel addUserPanel = new JPanel();
    JPanel sidePanel = new JPanel();
    JPanel profilePanel = new JPanel();
    static JPanel profilePicPanel;
    JPanel chatBoxPanel = new JPanel();
    JPanel sendMessagePanel = new JPanel();
    static JScrollPane chatScrollPane = new JScrollPane();
    static JScrollPane channelScrollPane = new JScrollPane();

    static JTextField messageInput = new JTextField();
    JFrame frame = new JFrame();
    JLabel nameLabel;
    static JLabel profileName;
    static int recieverId = 0;
    static JButton sendBtn;
    static JPanel channelPanel;
    static List<Channel> addedChannels = new ArrayList<>();

    ChatInterface() {
        frame.setTitle("ChitChat");
        frame.setBounds(0, 0, 1440, 790);
        frame.setMinimumSize(new Dimension(950,350));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        mainContainer.setLayout(new BorderLayout());
        frame.add(mainContainer, BorderLayout.CENTER);

        sidePanel.setPreferredSize(new Dimension(375, 0));
        sidePanel.setBackground(new Color(40,40,41));
        sidePanel.setLayout(new BorderLayout());
        mainContainer.add(sidePanel, BorderLayout.WEST);

        addUserPanel.setPreferredSize(new Dimension(375,50));
        addUserPanel.setBackground(new Color(63, 63, 63));
        addUserPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        addUserPanel.setBorder(BorderFactory.createEmptyBorder(0,0,2,0));
        sidePanel.add(addUserPanel, BorderLayout.NORTH);

        channelScrollPane.setPreferredSize(new Dimension(365,560));
        channelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        channelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        channelScrollPane.setBorder(BorderFactory.createMatteBorder(10,1,0,1,new Color(40,40,41)));
        channelScrollPane.getViewport().setBackground(new Color(40,40,41));
        channelScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));

        channelPanel = new JPanel();
        channelPanel.setLayout(new BoxLayout(channelPanel, BoxLayout.Y_AXIS));
        channelPanel.setBackground(new Color(40,40,41));
        channelScrollPane.setViewportView(channelPanel);

        sidePanel.add(channelScrollPane, BorderLayout.CENTER);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(null);
        namePanel.setPreferredSize(new Dimension(160,40));
        addUserPanel.add(namePanel);

        nameLabel = new JLabel(User.getInstance().myFullname);
        nameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(4,0,0,0));
        namePanel.add(nameLabel);

        JButton showChannelsBtn = new JButton("Add");
        showChannelsBtn.setPreferredSize(new Dimension(70,35));
        showChannelsBtn.setBackground(new Color(42,123,246));
        showChannelsBtn.setForeground(new Color(255, 255, 255));
        showChannelsBtn.setOpaque(true);
        showChannelsBtn.setFocusPainted(false);
        showChannelsBtn.setFocusable(false);
        showChannelsBtn.setBorder(BorderFactory.createLineBorder(new Color(42,123,246),4,true));
        showChannelsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HTTPResponse.getHTTPAllUserListResponse(User.getInstance().myId);
                new AddChannelInterface();
            }
        });
        addUserPanel.add(showChannelsBtn);


        chatBoxPanel.setLayout(new BorderLayout());
        chatBoxPanel.setPreferredSize(new Dimension(775,545));
        chatBoxPanel.setBackground(new Color(24,24,25));
        mainContainer.add(chatBoxPanel, BorderLayout.CENTER);

        chatScrollPane.setPreferredSize(new Dimension(775,545));
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setBorder(BorderFactory.createMatteBorder(0,1,0,1,new Color(63,63,63)));
        chatScrollPane.getViewport().setBackground(new Color(24,24,25));
        chatScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));

        chatBoxPanel.add(chatScrollPane, BorderLayout.CENTER);

        profilePanel.setLayout(new BorderLayout());
        profilePanel.setPreferredSize(new Dimension(775, 50));
        profilePanel.setBackground(new Color(63, 63, 63));
        chatBoxPanel.add(profilePanel, BorderLayout.NORTH);
        profilePicPanel = new JPanel();
        profilePicPanel.setPreferredSize(new Dimension(70,40));
        profilePicPanel.setBackground(new Color(63, 63, 63));
        profilePicPanel.setBorder(BorderFactory.createMatteBorder(0,20,0,10, new Color(63, 63, 63)));
        profilePanel.add(profilePicPanel, BorderLayout.WEST);
        JLabel profilePicIcon = new JLabel(loadImage(User.getInstance().baseAssetPath + "profilePic.png", 40,40));
        profilePicPanel.add(profilePicIcon);
        profilePicPanel.setVisible(false);
        profileName = new JLabel("Name Here");
        profileName.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        profileName.setForeground(Color.WHITE);
        profilePanel.add(profileName);
        profileName.setVisible(false);

        JButton logoutBtn = new JButton();
        logoutBtn.setIcon(loadImage(User.getInstance().baseAssetPath + "logout.png",40,40));
        logoutBtn.setPreferredSize(new Dimension(60,30));
        logoutBtn.setOpaque(true);
        logoutBtn.setBackground(null);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setFocusable(false);
        logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(63,63,63),1,true));
        logoutBtn.setVisible(true);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SignupInterface();
                SignupInterface.signupBtn.doClick();
            }
        });
        profilePanel.add(logoutBtn, BorderLayout.EAST);

        sendMessagePanel.setPreferredSize(new Dimension(775, 50));
        sendMessagePanel.setBackground(new Color(63, 63, 63));
        chatBoxPanel.add(sendMessagePanel, BorderLayout.SOUTH);
        sendMessagePanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

        messageInput.setPreferredSize(new Dimension( 480, 30));
        messageInput.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        messageInput.setBackground(new Color(89, 89, 89));
        messageInput.setCaretColor(new Color(42,123,246));
        messageInput.setForeground(Color.WHITE);
        messageInput.setBorder(BorderFactory.createMatteBorder(0,10,0,3, new Color(89, 89, 89)));
        messageInput.setVisible(false);
        messageInput.setEditable(true);
        messageInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendBtn.doClick();
            }
        });
        sendMessagePanel.add(messageInput);

        sendBtn = new JButton("Send");
        sendBtn.setPreferredSize(new Dimension(70,30));
        sendBtn.setBackground(new Color(42,123,246));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setOpaque(true);
        sendBtn.setFocusPainted(false);
        sendBtn.setFocusable(false);
        sendBtn.setBorder(BorderFactory.createLineBorder(new Color(42,123,246),4,true));
        sendBtn.setVisible(false);
        sendMessagePanel.add(sendBtn);

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = messageInput.getText();

                if(!msg.trim().isEmpty()) {
                    Chat chat = new Chat();
                    chat.setSenderId(User.getInstance().myId);
                    chat.setReceiverId(recieverId);
                    chat.setChannelId(User.getInstance().channelId);
                    chat.setTimestamp(getCurrentTime());
                    chat.setMessage(msg);
                    System.out.println("Chat Instance:" + chat.toJSONObject());
                    Client.sendChatMessage(chat.toJSONObject());
                    updateChatUI(msg, "outgoing");
                    Client.getInstance().channelList.clear();
                    System.out.println("Channel List after:" + Client.getInstance().channelList);
                    addedChannels.clear();
                    channelPanel.removeAll();
                    HTTPResponse.getHTTPChannelResponse();
                }

                messageInput.setText("");
            }
        });
        chatBoxPanel.repaint();
        chatBoxPanel.revalidate();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void updateChatUI(String msg, String messageState) {
        if (msg.isEmpty()) {
            return;
        }

        JPanel messageContainer = new JPanel();
        messageContainer.setBackground(null);
        messageContainer.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(msg);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));

        if (Objects.equals(messageState, "outgoing")) {
            textArea.setBackground(new Color(31, 79, 70));
        } else if (Objects.equals(messageState, "incoming")) {
            textArea.setBackground(new Color(86, 86, 86));
        }
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(24, 24, 25));
        textPanel.add(textArea, BorderLayout.NORTH);

        JLabel timeLabel = new JLabel(getCurrentTime());
        timeLabel.setForeground(new Color(171, 171, 171));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 10));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 380, 10, 10));
        textPanel.add(timeLabel, BorderLayout.CENTER);

        if (Objects.equals(messageState, "incoming")) {
            messageContainer.add(textPanel, BorderLayout.WEST);
        } else {
            messageContainer.add(textPanel, BorderLayout.EAST);
        }

        JScrollPane chatScrollPane = ChatInterface.chatScrollPane;
        JPanel messageHolder = (JPanel) chatScrollPane.getViewport().getView();
        if (messageHolder == null) {
            messageHolder = new JPanel();
            messageHolder.setBackground(new Color(24, 24, 25));
            messageHolder.setLayout(new BoxLayout(messageHolder, BoxLayout.Y_AXIS));
            chatScrollPane.setViewportView(messageHolder);
        }

        Component[] components = messageHolder.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && "emptyPanel".equals(component.getName())) {
                messageHolder.remove(component);
                break;
            }
        }

        messageHolder.add(messageContainer);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(300, 600));
        emptyPanel.setBackground(new Color(24, 24, 25));
        emptyPanel.setName("emptyPanel");
        messageHolder.add(emptyPanel);

        messageHolder.revalidate();
        messageHolder.repaint();
    }



    public static void clearChatUI() {
        JPanel messageHolder = (JPanel) ChatInterface.chatScrollPane.getViewport().getView();
        if (messageHolder != null) {
            messageHolder.removeAll();
            messageHolder.revalidate();
            messageHolder.repaint();
        }
    }



    public static ImageIcon loadImage(String resourcePath, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            Image newing = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newing);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading image: " + resourcePath);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("Assets/error.png"));
            } catch (IOException ex) {
                System.out.println("Error loading default error image");
            }
            Image newing = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newing);
        }
    }

    private static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(cal.getTime()).toUpperCase(Locale.ROOT);
    }

    public static void updateChannelPanel(int receiverId, String fullName, int channelId, String username) {
        if (ChatInterface.channelPanel == null) {
            ChatInterface.channelPanel = new JPanel();
            ChatInterface.channelPanel.setLayout(new BoxLayout(ChatInterface.channelPanel, BoxLayout.Y_AXIS));
            ChatInterface.channelScrollPane.setViewportView(ChatInterface.channelPanel);
        }

        Channel channel = new Channel();
        channel.channelName = null;
        channel.userFullNames = new ArrayList<>();
        channel.userFullNames.add(User.getInstance().myFullname);
        channel.userFullNames.add(fullName);
        channel.channelId = channelId;
        channel.usernames.add(User.getInstance().myUsername);
        channel.usernames.add(username);

        boolean alreadyAdded = false;
        for (Channel ch : ChatInterface.addedChannels) {
            if (channel.channelId == ch.channelId) {
                alreadyAdded = true;
                break;
            }
        }

        if (!alreadyAdded) {
            JSONObject lastMessageJson = HTTPResponse.getHTTPChatResponse(channelId, false);
            String lastMessageStr = lastMessageJson.getString("message");
            String timestampStr = lastMessageJson.getString("timestamp");
            int senderId = lastMessageJson.getInt("senderId");
            System.out.println("Last Message:" + lastMessageJson);

            ChatInterface.addedChannels.add(channel);
            ChannelButton userPanelBtn = new ChannelButton("Assets/profilePic.png", fullName, lastMessageStr, timestampStr, senderId);
            userPanelBtn.addActionListener(getChatPanelReady(fullName, receiverId, channelId));
            ChatInterface.channelPanel.add(userPanelBtn);
            ChatInterface.channelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            ChatInterface.channelPanel.revalidate();
            ChatInterface.channelPanel.repaint();
        }
    }


    public static ActionListener getChatPanelReady(String fullname, int id, int channelId) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatInterface.profileName.setText(fullname);
                ChatInterface.recieverId = id;
                User.getInstance().channelId = channelId;
                User.getInstance().selectedChannel = channelId;
                ChatInterface.profilePicPanel.setVisible(true);
                ChatInterface.profileName.setVisible(true);
                ChatInterface.messageInput.setVisible(true);
                ChatInterface.sendBtn.setVisible(true);

                Client.getChannelChat(channelId);
            }
        };
    }
}

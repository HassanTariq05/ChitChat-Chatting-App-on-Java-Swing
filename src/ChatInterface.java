import model.Chat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ChatInterface {
    JPanel mainContainer = new JPanel();
    JPanel addUserPanel = new JPanel();
    static JPanel sidePanel = new JPanel();
    JPanel profilePanel = new JPanel();
    static JPanel profilePicPanel;
    JPanel chatBoxPanel = new JPanel();
    JPanel sendMessagePanel = new JPanel();
    static String baseAssetPath = "/Users/i2p/IdeaProjects/Chat/Assets/";
    static JScrollPane chatScrollPane = new JScrollPane();
    static JScrollPane channelScrollPane = new JScrollPane();
    static JTextField messageInput = new JTextField();
    static JPanel emptyPanel = new JPanel();
    JFrame frame = new JFrame();
    static JTextField addUserInput;
    JLabel nameLabel;
    static JLabel profileName;
    static int recieverId = 0;
    static JButton sendBtn;
    static JPanel channelPanel;
    static JButton addUserBtn;

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
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBackground(null);
        namePanel.setPreferredSize(new Dimension(160,40));
        addUserPanel.add(namePanel);

        nameLabel = new JLabel(Client.myFullname);
        nameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(4,0,0,0));
        namePanel.add(nameLabel);

        addUserInput = new JTextField();
        addUserInput.setPreferredSize(new Dimension( 150, 20));
        addUserInput.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        addUserInput.setBackground(new Color(89,89,89));
        addUserInput.setBorder(BorderFactory.createMatteBorder(3,10,3,3, new Color(89,89,89)));
        addUserInput.setCaretColor(new Color(42,123,246));
        addUserInput.setForeground(Color.WHITE);
        addUserInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserBtn.doClick();
            }
        });
        addUserPanel.add(addUserInput);

        addUserBtn = new JButton("Add");
        addUserBtn.setPreferredSize(new Dimension(40,20));
        addUserBtn.setBackground(new Color(42,123,246));
        addUserBtn.setForeground(new Color(255, 255, 255));
        addUserBtn.setOpaque(true);
        addUserBtn.setFocusPainted(false);
        addUserBtn.setFocusable(false);
        addUserBtn.setBorder(BorderFactory.createLineBorder(new Color(42,123,246),4,true));
        addUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = addUserInput.getText();
                Client.addNewContact(username);
            }
        });
        addUserPanel.add(addUserBtn);

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
        JLabel profilePicIcon = new JLabel(loadImage(baseAssetPath + "profilePic.png", 40,40));
        profilePicPanel.add(profilePicIcon);
        profilePicPanel.setVisible(false);
        profileName = new JLabel("Name Here");
        profileName.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        profileName.setForeground(Color.WHITE);
        profilePanel.add(profileName);
        profileName.setVisible(false);

        JButton logoutBtn = new JButton();
        logoutBtn.setIcon(loadImage(baseAssetPath + "logout.png",40,40));
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
                    chat.setSenderId(Client.myId);
                    chat.setReceiverId(recieverId);
                    chat.setChannelId(Client.channelId);
                    chat.setTimestamp(getCurrentTime());
                    chat.setMessage(msg);
                    System.out.println("Chat Instance:" + chat.toJSONObject());
                    Client.sendChatMessage(chat.toJSONObject());

                    updateChatUI(msg, "outgoing");
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

        // Remove the existing empty panel if any
        Component[] components = messageHolder.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && "emptyPanel".equals(component.getName())) {
                messageHolder.remove(component);
                break;
            }
        }

        messageHolder.add(messageContainer);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(300, 400));
        emptyPanel.setBackground(new Color(24, 24, 25));
        emptyPanel.setName("emptyPanel");
        messageHolder.add(emptyPanel);

        messageHolder.revalidate();
        messageHolder.repaint();
        int scrollToValue = chatScrollPane.getViewport().getHeight() - emptyPanel.getPreferredSize().height + 200;
        if (scrollToValue < 0) scrollToValue = 0;

        int finalScrollToValue = scrollToValue;
        SwingUtilities.invokeLater(() -> chatScrollPane.getVerticalScrollBar().setValue(finalScrollToValue));
    }



    public static void clearChatUI() {
        JPanel messageHolder = (JPanel) chatScrollPane.getViewport().getView();
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

    public static void updateChannelPanel(int receiverId, String fullName, int channelId) {
        if (channelPanel == null) {
            channelPanel = new JPanel();
            channelPanel.setLayout(new BoxLayout(channelPanel, BoxLayout.Y_AXIS));
            channelScrollPane.setViewportView(channelPanel);
        }

        JButton userPanelBtn = new JButton(fullName);
        Dimension buttonSize = new Dimension(365, 50);
        userPanelBtn.setPreferredSize(buttonSize);
        userPanelBtn.setMinimumSize(buttonSize);
        userPanelBtn.setMaximumSize(buttonSize);
        userPanelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        userPanelBtn.setBackground(new Color(63, 63, 63));
        userPanelBtn.setForeground(new Color(255, 255, 255));
        userPanelBtn.setOpaque(true);
        userPanelBtn.setFocusPainted(false);
        userPanelBtn.setFocusable(false);
        userPanelBtn.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 41), 4, true));
        userPanelBtn.addActionListener(getChatPanelReady(fullName, receiverId, channelId));
        channelPanel.add(userPanelBtn);
        channelPanel.add(Box.createRigidArea(new Dimension(0, 5)));  // Add spacing between buttons
        channelPanel.revalidate();
        channelPanel.repaint();
    }

    public static ActionListener getChatPanelReady(String fullname, int id, int channelId) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileName.setText(fullname);
                recieverId = id;
                Client.channelId = channelId;
                Client.selectedChannel = channelId;
                profilePicPanel.setVisible(true);
                profileName.setVisible(true);
                messageInput.setVisible(true);
                sendBtn.setVisible(true);

                Client.getChannelChat(channelId);
            }
        };
    }

    public static void displayUserNotFoundDialog() {
        JFrame f = new JFrame();
        JDialog d = new JDialog(f, "Error");
        d.setLayout(new FlowLayout(FlowLayout.CENTER));
        d.getContentPane().setBackground(new Color(40, 40, 41));
        JLabel text = new JLabel("User not Found!");
        text.setForeground(Color.white);
        d.add(text);
        JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(180, 30));
        button.setForeground(Color.white);
        button.setBackground(new Color(42, 123, 246));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setFocusable(false);
        d.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.setModal(false);
                d.dispose();
            }
        });
        d.setSize(200, 60);
        d.setLocation(600, 400);
        d.setUndecorated(true);
        d.setModal(true);
        d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        d.setVisible(true);
    }
}

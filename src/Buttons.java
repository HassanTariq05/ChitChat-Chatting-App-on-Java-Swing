import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Buttons extends JButton {
    private BufferedImage profilePic;
    private BufferedImage deliveredIcon;
    private String fullName;
    private String lastMessage;
    private String timestamp;
    private int senderId;
    private String buttonName;
    private String username;
    public Buttons(String profilePicPath, String fullName, String lastMessage, String timestamp, int senderId) {
        this.fullName = fullName;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.buttonName = "channelButton";

        try {
            profilePic = ImageIO.read(new File(profilePicPath));
            profilePic = scaleImage(profilePic, 50, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            deliveredIcon = ImageIO.read(new File("Assets/doubleTick.png"));
            deliveredIcon = scaleImage(deliveredIcon, 17, 17);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dimension buttonSize = new Dimension(373, 80);
        setPreferredSize(buttonSize);
        setMinimumSize(buttonSize);
        setMaximumSize(buttonSize);
        setContentAreaFilled(false);

        setBackground(new Color(40, 40, 41));
        setForeground(new Color(255, 255, 255));
        setOpaque(true);
        setFocusPainted(false);
        setFocusable(false);
        setBorder(BorderFactory.createLineBorder(new Color(40, 40, 41), 4, true));
    }

    public Buttons(String profilePicPath, String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
        this.buttonName = "userListButton";

        try {
            profilePic = ImageIO.read(new File(profilePicPath));
            profilePic = scaleImage(profilePic, 30, 30);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dimension buttonSize = new Dimension(297, 50);
        setPreferredSize(buttonSize);
        setMinimumSize(buttonSize);
        setMaximumSize(buttonSize);
        setContentAreaFilled(false);

        setBackground(new Color(63, 63, 63));
        setForeground(new Color(255, 255, 255));
        setOpaque(true);
        setFocusPainted(false);
        setFocusable(false);
        setBorder(BorderFactory.createLineBorder(new Color(40, 40, 41), 2, true));
    }

    private BufferedImage scaleImage(BufferedImage src, int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, w, h, null);
        g2.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Objects.equals(buttonName, "channelButton")) {
            paintChannelButton(g);
        } else if(Objects.equals(buttonName, "userListButton")) {
            paintUserListButton(g);
        }

    }

    private void paintChannelButton(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (profilePic != null) {
            g2.drawImage(profilePic, 20, 15, this);
        }

        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString(fullName, 80, 35);

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.setColor(Color.LIGHT_GRAY);
        if(senderId == User.getInstance().myId) {
            if (profilePic != null) {
                g2.drawImage(deliveredIcon, 80, 42, this);
            }
            g2.drawString(lastMessage, 100, 55);
        } else if (senderId == -1) {
            g2.drawString(lastMessage, 80, 55);
        } else {
            g2.drawString(lastMessage, 80, 55);
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString(timestamp, 300, 35);
    }
    private void paintUserListButton(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (profilePic != null) {
            g2.drawImage(profilePic, 20, 10, this);
        }

        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString(fullName, 65, 23);

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString(username, 65, 37);
    }
}
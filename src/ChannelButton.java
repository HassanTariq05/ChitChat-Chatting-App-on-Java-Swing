import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChannelButton extends JButton {
    private BufferedImage profilePic;
    private String fullName;
    private String lastMessage;
    private String timestamp;
    private int senderId;
    public ChannelButton(String profilePicPath, String fullName, String lastMessage, String timestamp, int senderId) {
        this.fullName = fullName;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.senderId = senderId;

        try {
            profilePic = ImageIO.read(new File(profilePicPath));
            profilePic = scaleImage(profilePic, 50, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dimension buttonSize = new Dimension(365, 80);
        setPreferredSize(buttonSize);
        setMinimumSize(buttonSize);
        setMaximumSize(buttonSize);
        setContentAreaFilled(false);

        setBackground(new Color(63, 63, 63));
        setForeground(new Color(255, 255, 255));
        setOpaque(true);
        setFocusPainted(false);
        setFocusable(false);
        setBorder(BorderFactory.createLineBorder(new Color(40, 40, 41), 4, true));
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
            g2.drawString("You: " + lastMessage, 80, 55);
        } else if (senderId == -1) {
            g2.drawString(lastMessage, 80, 55);
        } else {
            g2.drawString(fullName+": " + lastMessage, 80, 55);
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString(timestamp, 300, 35);
    }
}
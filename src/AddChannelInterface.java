import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddChannelInterface {
    static JScrollPane addChannelScrollPane = new JScrollPane();
    static JPanel buttonPanel;

    AddChannelInterface() {
        JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(300, 400);
        f.setLocation(378, 105);
        f.getContentPane().setBackground(new Color(40, 40, 41));

        JPanel addChannelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addChannelPanel.setPreferredSize(new Dimension(100,50));
        addChannelPanel.setBackground(new Color(63, 63, 63));
        addChannelPanel.setBorder(new EmptyBorder(5,0,0,0));
        f.add(addChannelPanel, BorderLayout.NORTH);

        JTextField addUserInput = new JTextField();
        addUserInput.setPreferredSize(new Dimension( 150, 30));
        addUserInput.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        addUserInput.setBackground(new Color(89,89,89));
        addUserInput.setBorder(BorderFactory.createMatteBorder(3,10,3,3, new Color(89,89,89)));
        addUserInput.setCaretColor(new Color(42,123,246));
        addUserInput.setForeground(Color.WHITE);
        addChannelPanel.add(addUserInput);

        JButton addUserBtn;
        addUserBtn = new JButton("Add");
        addUserBtn.setPreferredSize(new Dimension(40,30));
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
        addChannelPanel.add(addUserBtn);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(40, 40, 41));

        addChannelScrollPane.setViewportView(buttonPanel);
        addChannelScrollPane.setPreferredSize(new Dimension(365, 560));
        addChannelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addChannelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addChannelScrollPane.setBorder(BorderFactory.createMatteBorder(10, 1, 0, 1, new Color(40, 40, 41)));
        addChannelScrollPane.getViewport().setBackground(new Color(40, 40, 41));
        addChannelScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));

        f.add(addChannelScrollPane, BorderLayout.CENTER);

        JPanel closeBtnPanel = new JPanel();
        closeBtnPanel.setPreferredSize(new Dimension(100,30));
        closeBtnPanel.setBackground(new Color(63, 63, 63));
        f.add(closeBtnPanel, BorderLayout.SOUTH);
        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(new Color(42,123,246));
        closeBtn.setForeground(new Color(255, 255, 255));
        closeBtn.setPreferredSize(new Dimension(280, 20));
        closeBtn.setOpaque(true);
        closeBtn.setFocusPainted(false);
        closeBtn.setFocusable(false);
        closeBtn.setBorder(BorderFactory.createLineBorder(new Color(42,123,246),4,true));
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
            }
        });
        closeBtnPanel.add(closeBtn);

        addUserBtn.doClick();
        f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        f.setVisible(true);
    }

    public static void updateAddChannelPanel(int receiverId, String fullName, int channelId) {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            addChannelScrollPane.setViewportView(buttonPanel);
        }

        JButton userPanelBtn = new JButton(fullName);
        Dimension buttonSize = new Dimension(280, 50);
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
        userPanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatInterface.updateChannelPanel(receiverId, fullName, channelId);
            }
        });
        buttonPanel.add(userPanelBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
}

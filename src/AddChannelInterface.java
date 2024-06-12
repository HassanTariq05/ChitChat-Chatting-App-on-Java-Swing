import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddChannelInterface {
    static JScrollPane addChannelScrollPane = new JScrollPane();
    static JPanel buttonPanel;
    static JFrame f;

    AddChannelInterface() {
        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(300, 400);
        f.setLocation(378, 105);
        f.getContentPane().setBackground(new Color(40, 40, 41));

        JPanel addChannelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addChannelPanel.setPreferredSize(new Dimension(100,50));
        addChannelPanel.setBackground(new Color(63, 63, 63));
        addChannelPanel.setBorder(new EmptyBorder(5,0,0,0));
        f.add(addChannelPanel, BorderLayout.NORTH);

        JLabel selectUserLabel = new JLabel("Select User");
        selectUserLabel.setForeground(Color.WHITE);
        selectUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        selectUserLabel.setBorder(BorderFactory.createEmptyBorder(7,0,0,0));
        addChannelPanel.add(selectUserLabel);

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

        f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        f.setVisible(true);
    }

    public static void updateAddAllUserPanel(int receiverId, String fullName, String username) {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            addChannelScrollPane.setViewportView(buttonPanel);
        }

        String buttonText = "<html><div style='text-align: center;'><span style='font-size: 12px;'>" + fullName +
                "</span><br><span style='font-size: 8px;'>" + username + "</span></div></html>";

        JButton userPanelBtn = new JButton(buttonText);
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
                Client.addNewContact(username);
                f.setVisible(false);
            }
        });
        SwingUtilities.invokeLater(() -> {
            buttonPanel.add(userPanelBtn);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }
}

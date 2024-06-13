import model.Keys;
import model.User;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginInterface extends SignupInterface{
    static JSONObject userData = new JSONObject();
    public static void loginInterface() {
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        submitBtn.setVisible(false);

        usernameLabel.setBounds(25,70,100,25);
        usernameField.setBounds(25,100,250,40);

        passwordLabel.setText("Password");
        passwordLabel.setBounds(25,150,100,25);
        passwordField.setBounds(25,180,250,40);
        errorLabel.setText("Login to your account");

        JButton submitBtn;
        submitBtn = new JButton("Log In");
        submitBtn.setBounds(25,340,300,40);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(42,123,246));
        submitBtn.setOpaque(true);
        submitBtn.setFocusPainted(false);
        submitBtn.setFocusable(false);
        submitBtn.setBorder(BorderFactory.createLineBorder(new Color(42,123,246),4,true));
        signupFormPanel.add(submitBtn);

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userData.put(Keys.KEY_USERNAME, usernameField.getText());
                    userData.put(Keys.KEY_PASSWORD, passwordField.getText());

                    User.getInstance().myUsername = usernameField.getText();
                    Client.login(userData);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


}

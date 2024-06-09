import model.Keys;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupInterface {
    static JPanel mainContainer = new JPanel();
    static JPanel signupFormPanel = new JPanel();
    static JTextField nameField;
    static JTextField usernameField;
    static JPasswordField passwordField;
    static JButton submitBtn;
    static JLabel errorLabel = new JLabel("Register your account");
    JSONObject userData = new JSONObject();
    public static JFrame signupFrame = new JFrame();
    static JLabel nameLabel;
    static JLabel usernameLabel;
    static JLabel passwordLabel;
    static JButton signupBtn;

    SignupInterface() {
        signupFrame.setTitle("ChitChat-Signup or Login");
        signupFrame.setBounds(150, 100, 1150, 650);
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setLayout(new BorderLayout());

        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }

        mainContainer.setLayout(null);
        mainContainer.setBackground(new Color(52, 52, 52));
        signupFrame.add(mainContainer);

        JLabel appLogo = new JLabel(ChatInterface.loadImage(ChatInterface.baseAssetPath + "appLogo.png", 200,200));
        appLogo.setBounds(200,150,200,200);
        mainContainer.add(appLogo);

        JLabel appLogoText = new JLabel(ChatInterface.loadImage(ChatInterface.baseAssetPath + "appLogoText.png", 250,100));
        appLogoText.setBounds(170,340,250,100);
        mainContainer.add(appLogoText);


        signupFormPanel.setBounds(700,110,350,400);
        signupFormPanel.setBackground(new Color(86, 86, 86));
        signupFormPanel.setBorder(BorderFactory.createLineBorder(new Color(80,142,247),8,true));
        signupFormPanel.setLayout(null);
        mainContainer.add(signupFormPanel);

        signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(70,20,90,40);
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setBackground(new Color(80,142,247));
        signupBtn.setOpaque(true);
        signupBtn.setFocusPainted(false);
        signupBtn.setFocusable(false);
        signupBtn.setBorder(BorderFactory.createLineBorder(new Color(80,142,247),4,true));
        signupBtn.addActionListener(signupBtnAction());
        signupFormPanel.add(signupBtn);

        JButton loginBtn = new JButton("Log In");
        loginBtn.setBounds(170,20,90,40);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(80,142,247));
        loginBtn.setOpaque(true);
        loginBtn.setFocusPainted(false);
        loginBtn.setFocusable(false);
        loginBtn.setBorder(BorderFactory.createLineBorder(new Color(80,142,247),4,true));
        loginBtn.addActionListener(loginBtnAction());
        signupFormPanel.add(loginBtn);

        nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("SanSerif",Font.BOLD,15));
        nameLabel.setBounds(25,70,100,25);
        nameLabel.setForeground(Color.WHITE);
        signupFormPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(25,100,250,40);
        nameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        nameField.setBackground(new Color(52, 52, 52));
        nameField.setForeground(Color.white);
        nameField.setCaretColor(new Color(250, 250, 250));
        nameField.setBorder(BorderFactory.createMatteBorder(3,10,3,3, new Color(49, 49, 49, 174)));
        signupFormPanel.add(nameField);


        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SanSerif",Font.BOLD,15));
        usernameLabel.setBounds(25,150,100,25);
        usernameLabel.setForeground(Color.WHITE);
        signupFormPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(25,180,250,40);
        usernameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        usernameField.setBackground(new Color(52, 52, 52));
        usernameField.setForeground(Color.white);
        usernameField.setCaretColor(new Color(250, 250, 250));
        usernameField.setBorder(BorderFactory.createMatteBorder(3,10,3,3, new Color(49, 49, 49, 174)));
        signupFormPanel.add(usernameField);

        passwordLabel = new JLabel("Create Password");
        passwordLabel.setFont(new Font("SanSerif",Font.BOLD,15));
        passwordLabel.setBounds(25,230,150,25);
        passwordLabel.setForeground(Color.WHITE);
        signupFormPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(25,260,250,40);
        passwordField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        passwordField.setBackground(new Color(52, 52, 52));
        passwordField.setForeground(Color.white);
        passwordField.setCaretColor(new Color(255, 255, 255));
        passwordField.setBorder(BorderFactory.createMatteBorder(3,10,3,3, new Color(49, 49, 49, 174)));
        signupFormPanel.add(passwordField);

        JPanel errorPanel = new JPanel();
        errorPanel.setBounds(700,510,350,25);
        errorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        errorPanel.setBackground(null);
        mainContainer.add(errorPanel);

        errorLabel.setForeground(Color.WHITE);
        errorPanel.add(errorLabel);

        submitBtn = new JButton("Sign Up");
        submitBtn.setBounds(25,340,300,40);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(80,142,247));
        submitBtn.setOpaque(true);
        submitBtn.setFocusPainted(false);
        submitBtn.setFocusable(false);
        submitBtn.setBorder(BorderFactory.createLineBorder(new Color(80,142,247),4,true));
        signupFormPanel.add(submitBtn);

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    userData.put(Keys.KEY_FULL_NAME, nameField.getText());
                    userData.put(Keys.KEY_USERNAME, usernameField.getText());
                    userData.put(Keys.KEY_PASSWORD, passwordField.getText());

                    Client.myUsername = usernameField.getText();
                    Client.signup(userData);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        signupFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        signupFrame.setResizable(false);
        signupFrame.setVisible(true);
    }
    public ActionListener loginBtnAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                LoginInterface.loginInterface();
            }
        };
    }
    private ActionListener signupBtnAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupFormPanel.removeAll();
                errorLabel.setText("Register your Account");
                new SignupInterface();
            }
        };
    }
}

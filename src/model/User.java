package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class User {
    int id;
    String username;
    String fullName;
    String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User convert(String data) {
        JSONObject jsonObject = new JSONObject(data);
        return User.convertFromJSON(jsonObject);
    }

    public static User convertFromJSON(JSONObject jsonObject) {
        User user = new User();
        user.id = jsonObject.getInt(Keys.KEY_ID);
        user.fullName = jsonObject.getString(Keys.KEY_FULL_NAME);
        user.username = jsonObject.getString(Keys.KEY_USERNAME);
        user.password = jsonObject.getString(Keys.KEY_PASSWORD);
        return user;
    }
}

import java.util.ArrayList;

public class Channel {
    ArrayList<Integer> userIds = new ArrayList<>();
    int channelId;

    String channelName;

    int myId = Client.myId;

    ArrayList<String> userFullNames = new ArrayList<>();


    @Override
    public String toString() {
        return "{" +
                "\"userIds\":" + userIds +
                ", \"channelId\":" + channelId +
                ", \"channelName\":" + "\"" +channelName + "\"" +
                ", \"userFullNames\":" + userFullNames +
                ", \"myId\":" + myId +
                '}';
    }
}

package jolyjdia.bot.objects;

import jolyjdia.bot.utils.MessageChannel;

public class User {
    private final int userId;
    private final int peerId;

    public User(int userId, int peerId) {
        this.userId = userId;
        this.peerId = peerId;
    }

    /*public void sendMessage(String msg) {
        MessageChannel.sendMessage(msg, peerId);
    }*/
    public void sendMessage(Object msg) {
        MessageChannel.sendMessage(msg.toString(), peerId);
    }
    public boolean hasPermission(String perm) {
        return true;
    }

    public int getUserId() {
        return userId;
    }

    public int getPeerId() {
        return peerId;
    }
}

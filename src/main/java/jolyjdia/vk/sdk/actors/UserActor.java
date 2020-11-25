package jolyjdia.vk.sdk.actors;

public class UserActor implements Actor {

    private final int userId;
    private final String accessToken;
    private String phone;

    public UserActor(int userId, String accessToken) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public int getId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserActor userActor = (UserActor) o;
        return userId == userActor.userId;
    }

    @Override
    public int hashCode() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserActor{" +
                "userId=" + userId +
                ", accessToken='" + accessToken + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

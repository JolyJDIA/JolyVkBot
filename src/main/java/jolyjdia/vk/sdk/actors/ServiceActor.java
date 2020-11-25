package jolyjdia.vk.sdk.actors;

public class ServiceActor implements Actor {
    private final int appId;
    private final String accessToken;
    private String clientSecret;

    public ServiceActor(int appId, String accessToken) {
        this.accessToken = accessToken;
        this.appId = appId;
    }

    public ServiceActor(int appId, String clientSecret, String accessToken) {
        this.accessToken = accessToken;
        this.appId = appId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public int getId() {
        return appId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceActor that = (ServiceActor) o;
        return appId == that.appId;
    }

    @Override
    public int hashCode() {
        return appId;
    }

    @Override
    public String toString() {
        return "ServiceActor{" +
                "appId=" + appId +
                ", accessToken='" + accessToken + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}

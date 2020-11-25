package jolyjdia.vk.sdk.actors;

public class GroupActor implements Actor {
    private final int groupId;
    private final String accessToken;

    public GroupActor(Integer groupId, String accessToken) {
        this.accessToken = accessToken;
        this.groupId = groupId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public int getId() {
        return -groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupActor that = (GroupActor) o;
        return groupId == that.groupId;
    }

    @Override
    public int hashCode() {
        return groupId;
    }

    @Override
    public String toString() {
        return "GroupActor{" +
                "groupId=" + groupId +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}

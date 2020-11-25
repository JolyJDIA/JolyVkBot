package jolyjdia.vk.sdk.objects.messages;

import org.jetbrains.annotations.NonNls;

public class Message {
    private Integer id;
    private Integer peer_id;
    private String text;
    private Integer from_id;
    private Integer admin_author_id;
    private Integer conversation_message_id;
    private Integer date;
    private Integer members_count;
    private String payload;
    private Integer random_id;
    private String ref;
    private String ref_source;
    private Integer update_time;

    public Integer getFromId() {
        return from_id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPeerId() {
        return peer_id;
    }

    public String getText() {
        return text;
    }

    @Override
    public @NonNls String toString() {
        return "Message{" +
                "id=" + id +
                ", peer_id=" + peer_id +
                ", text='" + text + '\'' +
                ", from_id=" + from_id +
                ", admin_author_id=" + admin_author_id +
                ", conversation_message_id=" + conversation_message_id +
                ", date=" + date +
                ", members_count=" + members_count +
                ", payload='" + payload + '\'' +
                ", random_id=" + random_id +
                ", ref='" + ref + '\'' +
                ", ref_source='" + ref_source + '\'' +
                ", update_time=" + update_time +
                '}';
    }
}

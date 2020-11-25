package jolyjdia.bot.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jolyjdia.bot.objects.Chat;
import jolyjdia.bot.objects.User;

import java.util.concurrent.TimeUnit;

public class UserManager {
    private final LoadingCache<Integer, Chat> chats = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public Chat load(Integer integer) {
                    return new Chat(integer);
                }
            });

    public Chat getChatByUser(int peerId) {
        return chats.getUnchecked(peerId);
    }

    //Тут добавим права пользователя
    public User getChatByUser(int userId, int peerId) {
        Chat chat = chats.getUnchecked(peerId);
        return chat.getUsers().getUnchecked(userId);
    }

    public void deleteUser(int userId, int peerId) {
        Chat chat = chats.getUnchecked(peerId);
        chat.getUsers().invalidate(userId);
    }
}
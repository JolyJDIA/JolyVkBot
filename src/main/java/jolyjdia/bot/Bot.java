package jolyjdia.bot;

import com.google.common.collect.Iterators;
import com.google.gson.JsonObject;
import jolyjdia.bot.commands.defaults.CitiesCommands;
import jolyjdia.bot.commands.defaults.UtilsCommands;
import jolyjdia.bot.manager.UserManager;
import jolyjdia.bot.scheduler.BotScheduler;
import jolyjdia.bot.utils.MyHttpClient;
import jolyjdia.vk.sdk.actors.GroupActor;
import jolyjdia.vk.sdk.actors.UserActor;
import jolyjdia.vk.sdk.client.TransportClient;
import jolyjdia.vk.sdk.client.VkApiClient;
import jolyjdia.vk.sdk.objects.callback.longpoll.GetLongPollEventsResponse;
import jolyjdia.vk.sdk.objects.groups.LongPollServer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public final class Bot {
    public static final ExecutorService ASYNC_POOL = new ThreadPoolExecutor(
            Math.max(1, Runtime.getRuntime().availableProcessors() / 4), Integer.MAX_VALUE,
            15, MILLISECONDS,
            new SynchronousQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger count = new AtomicInteger();
                @Override
                public Thread newThread(@NotNull Runnable r) {
                    @NonNls Thread thread = new Thread(r);
                    thread.setName("Bot-Worker-" + count.getAndIncrement());
                    return thread;
                }
            });
    private static final BotScheduler scheduler = new BotScheduler();
    private static final TransportClient transportClient = new MyHttpClient();
    private static final VkApiClient vkApiClient = new VkApiClient(transportClient);
    private static final BotManager manager = new BotManager();
    private static final UserManager userManager = new UserManager();
    private static final Properties properties = new Properties();
    private static final int groupId;
    private static final String accessToken;
    private static final GroupActor groupActor;
    private static final UserActor userActor;
    private static final Thread leader;

    static {
        leader = Thread.currentThread();
        leader.setName("Main Thread");
        try (InputStream inputStream = Bot.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        groupId = Integer.parseInt(properties.getProperty("groupId"));
        accessToken = properties.getProperty("groupAccessToken");
        groupActor = new GroupActor(groupId, accessToken);
        userActor = new UserActor(
                Integer.parseInt(properties.getProperty("userId")),
                properties.getProperty("userAccessToken")
        );
        CitiesCommands.register();
    }

    private static LongPollServer longPollServer;//volatile

    static {
        try {
            longPollServer = getLongPollServer().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private static final AtomicInteger lastTimeStamp = new AtomicInteger();
    private static final AtomicReference<CompletableFuture<GetLongPollEventsResponse>> cf = new AtomicReference<>();
    private static final Iterator<Supplier<String>> status = Iterators.cycle(
            () -> "Roses are red Niggers are dead",
            () -> "IQ = 0;",
            UtilsCommands::getNewYear
    );

    private Bot() {}

    public static void main(String[] args) {
        CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler();
        scheduler.runRepeatingSyncTaskAfter(() -> {
            String text = status.next().get();
            vkApiClient.status()
                    .set(userActor)
                    .text(text)
                    .execute();
        }, 0, 1, TimeUnit.MINUTES);
        lastTimeStamp.set(Integer.parseInt(longPollServer.getTs()));
        System.out.println("start");
        for (;;) {
            scheduler.mainThreadHeartbeat();
            CompletableFuture<GetLongPollEventsResponse> cfE;
            //Дабы не нацеплять на один ивент 10000 одинаковых действиq
            if ((cfE = cf.get()) != null) {
                if (cfE.isDone()) {
                    try {
                        GetLongPollEventsResponse response = cfE.get();//не блокируюсь, т к CF is Done
                        for (JsonObject jsonObject : response.getUpdates()) {
                            handler.parse(jsonObject);
                        }
                        lastTimeStamp.set(response.getTs());
                    } catch (InterruptedException | ExecutionException e) {
                        try {
                            longPollServer = getLongPollServer().get();
                        } catch (InterruptedException | ExecutionException ex) { break; }
                    }
                    setNewEvents();
                }
            } else {
                setNewEvents();
            }
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) { break; }
        }
    }
    public static void setNewEvents() {
        cf.set(vkApiClient.longPoll().getEvents(
                longPollServer.getServer(),
                longPollServer.getKey(),
                lastTimeStamp.get()
        ).waitTime(25).execute());
    }
    private static CompletableFuture<LongPollServer> getLongPollServer() {
        return groupActor != null
                ? vkApiClient.groupsLongPoll().getLongPollServer(groupActor, groupActor.getGroupId()).execute()
                : vkApiClient.groupsLongPoll().getLongPollServer(userActor, groupId).execute();

    }

    public static GroupActor getGroupActor() {
        return groupActor;
    }

    public static int getGroupId() {
        return groupId;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static Properties getConfig() {
        return properties;
    }

    public static BotManager getBotManager() {
        return manager;
    }

    public static BotScheduler getScheduler() {
        return scheduler;
    }

    public static VkApiClient getVkApiClient() {
        return vkApiClient;
    }

    public static Thread getLeader() {
        return leader;
    }

    public static UserManager getUserManager() {
        return userManager;
    }
}

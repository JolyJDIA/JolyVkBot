package jolyjdia.bot;

import com.google.common.collect.Iterators;
import com.google.gson.JsonObject;
import jolyjdia.bot.commands.HelpCommands;
import jolyjdia.bot.commands.defaults.CitiesCommands;
import jolyjdia.bot.commands.defaults.UtilsCommands;
import jolyjdia.bot.manager.UserManager;
import jolyjdia.bot.scheduler.BotScheduler;
import jolyjdia.bot.utils.MyHttpClient;
import jolyjdia.bot.utils.timeformat.TemporalDuration;
import jolyjdia.bot.utils.timeformat.TimeFormatter;
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
import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
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
                    thread.setName("Nigga-Worker-" + count.getAndIncrement());
                    return thread;
                }
            });
    private static final TransportClient httpClient = new MyHttpClient();
    private static final BotScheduler scheduler = new BotScheduler();
    private static final VkApiClient vkApiClient = new VkApiClient(httpClient);
    private static final Properties properties = new Properties();
    private static final BotManager manager;
    private static final UserManager userManager = new UserManager();
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
        manager = new BotManager();
        CitiesCommands.register();
        manager.registerCommand(new HelpCommands());
    }

    private static final Iterator<Supplier<String>> status = Iterators.cycle(
            () -> "Время: "+ new TemporalDuration(Duration.ofSeconds(LocalTime.now().toSecondOfDay()))
                    .toFormat(TimeFormatter.HOURS, TimeFormatter.MINUTES),
            () -> "Roses are red Niggers are dead",
            () -> "Go, go, go mulignane",
            () -> "IQ = 0;",
            () -> "Манки дай",
            UtilsCommands::getNewYear
    );

    private Bot() {}

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler();
        scheduler.runRepeatingSyncTaskAfter(() -> {
            //String text = status.next().get();
            String text = UtilsCommands.getNewYear();
            vkApiClient.status()
                    .set(userActor)
                    .text(text)
                    .execute();
        }, 0, 1, TimeUnit.MINUTES);
        CompletableFuture<GetLongPollEventsResponse> cf = null;
        LongPollServer server = getLongPollServer().get();
        int lts = Integer.parseInt(server.getTs());
        System.out.println("start");
        for (;;) {
            scheduler.mainThreadHeartbeat();
            if (cf != null) {//Дабы не нацеплять на один ивент 10000 одинаковых действиq
                if (cf.isDone()) {
                    try {
                        GetLongPollEventsResponse response = cf.get();//не блокируюсь, т к CF is Done
                        List<JsonObject> updates; Integer ts;
                        if ((updates = response.getUpdates()) != null && (ts = response.getTs()) != null) {
                            for (JsonObject jsonObject : updates) {
                                handler.parse(jsonObject);
                            }
                            lts = ts;
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        try {
                            server = getLongPollServer().get();
                        } catch (InterruptedException | ExecutionException ex) { break; }
                    }
                    cf = vkApiClient.longPoll().getEvents(server.getServer(), server.getKey(), lts).waitTime(25).execute();
                }
            } else {
                cf = vkApiClient.longPoll().getEvents(server.getServer(), server.getKey(), lts).waitTime(25).execute();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) { break; }
        }
    }
    private static CompletableFuture<LongPollServer> getLongPollServer() {
        return groupActor != null
                ? vkApiClient.groupsLongPoll().getLongPollServer(groupActor).execute()
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

    public static TransportClient getHttpClient() {
        return httpClient;
    }
}

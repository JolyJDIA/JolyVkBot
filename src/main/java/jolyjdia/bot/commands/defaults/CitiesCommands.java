package jolyjdia.bot.commands.defaults;

import com.google.common.reflect.TypeToken;
import jolyjdia.bot.Bot;
import jolyjdia.bot.commands.CommandLabel;
import jolyjdia.bot.commands.ConsumerCommand;
import jolyjdia.bot.events.EventLabel;
import jolyjdia.bot.events.Listener;
import jolyjdia.bot.events.messages.BotNewMessageEvent;
import jolyjdia.bot.module.Module;
import jolyjdia.bot.utils.MathUtils;
import jolyjdia.bot.utils.StringBind;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CitiesCommands extends ConsumerCommand implements Listener, Module {
    private static final Map<Character, List<String>> map;
    static {
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                Objects.requireNonNull(Bot.class.getClassLoader().getResourceAsStream("cities.json")),
                StandardCharsets.UTF_8)) {
            map = Collections.unmodifiableMap(StringBind.fromJson(inputStreamReader, new MapTypeToken().getType()));
        } catch (IOException e) {
            throw new InternalError();
        }
    }
    //private char lastLetter;
    private static final int SKIP = 1 << 'ь' | 1 << 'ъ' | 1 << 'ы' ;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public void onLoad() {
        CitiesCommands citiesCommands = new CitiesCommands();
        Bot.getBotManager().registerCommand(citiesCommands);
        Bot.getBotManager().registerEvent(citiesCommands);
    }

    public static CitiesCommands register() {
        CitiesCommands citiesCommands = new CitiesCommands();
        Bot.getBotManager().registerCommand(citiesCommands);
        Bot.getBotManager().registerEvent(citiesCommands);
        return citiesCommands;
    }

    @EventLabel
    public void onCities(BotNewMessageEvent e) {
        String word = e.getMessage().getText();
        if(word.length() < 1) {
            return;
        }
        User user = users.computeIfAbsent(e.getUser().getPeerId(), integer -> new User());
        if(user.check(word)) {
            e.getUser().sendMessage(user.randCity());
        }
    }
    @CommandLabel(alias = "города", desc = "играть в города")
    public void addGameToChat() {
        sender.sendMessage(users.computeIfAbsent(sender.getPeerId(), integer -> new User()).randCity());
    }
    @CommandLabel(alias = "стоп-города", desc = "остановить игру")
    public void stop() {
        users.remove(sender.getPeerId());
    }
    @SuppressWarnings("uncheked")
    private static class MapTypeToken extends TypeToken<Map<Character, List<String>>> {}

    private static final class User {
        private char lastLetterU;
        private final Map<Character, List<String>> mapPrivate = new HashMap<>(map);

        public User() {
            Set<Character> characters = map.keySet();
            lastLetterU = (char) characters.toArray()[MathUtils.randomInt(characters.size())];
        }

        private String randCity() {
            List<String> cities = mapPrivate.get(lastLetterU);
            String city = cities.get(MathUtils.randomInt(cities.size()));
            for(int i = city.length()-1; i > 0; --i) {
                char c = city.charAt(i);
                if(!Character.isLetter(c) || (SKIP >> (int) c & 1) != 0) {
                    continue;
                }
                lastLetterU = Character.toUpperCase(c);
                cities.remove(city);
                return city;
            }
            return randCity();
        }
        public boolean check(String word) {
            char firstChar = Character.toUpperCase(word.charAt(0));

            if(!Character.isLetter(firstChar) || firstChar != lastLetterU) {
                return false;
            }
            List<String> city;
            if((city = map.get(firstChar)) != null && city.contains(word)) {
                for(int i = word.length()-1; i > 0; --i) {
                    char c = word.charAt(i);
                    if (Character.isLetter(c) && (SKIP >> (int) c & 1) == 0) {
                        lastLetterU = Character.toUpperCase(c);
                        break;
                    }
                }
                mapPrivate.get(firstChar).remove(word);
                return true;
            }
            return false;
        }
    }
}
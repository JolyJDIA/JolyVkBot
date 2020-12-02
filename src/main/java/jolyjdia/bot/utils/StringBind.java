package jolyjdia.bot.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import jolyjdia.vk.sdk.objects.enums.EnumParam;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

public final class StringBind {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private StringBind() {}

    public static String toArray(Collection<?> elements) {
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(",");
        for (Object o : elements) {
            joiner.add(o.toString());
        }
        return joiner.toString();
    }

    @SafeVarargs
    public static <U> String toArray(U... elements) {
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(",");
        for (U u : elements) {
            joiner.add(u.toString());
        }
        return joiner.toString();
    }

    public static String toArrayEnum(EnumParam... elements) {
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(",");
        for (EnumParam ep : elements) {
            joiner.add(ep.getValue());
        }
        return joiner.toString();
    }

    public static String toArrayEnum(Collection<EnumParam> elements) {
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(",");
        for (EnumParam ep : elements) {
            joiner.add(ep.getValue());
        }
        return joiner.toString();
    }
    public static void log(String msg) {
        try {
            System.out.println('[' +Thread.currentThread().getName()+"]: "+msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String bind(int start, String[] elements) {
        Objects.requireNonNull(elements);
        StringJoiner joiner = new StringJoiner(" ");
        for (int i = start; i < elements.length-1; ++i) {
            joiner.add(elements[i]);
        }
        return joiner.toString();
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
    public static <T> T fromJson(Reader json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(JsonElement json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }
}

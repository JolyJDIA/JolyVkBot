package jolyjdia.bot.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import jolyjdia.vk.sdk.objects.enums.EnumParam;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

public final class StringBind {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private StringBind() {}

    public static String toArray(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            sb.append(it.next());
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(',');
        }
    }

    @SafeVarargs
    public static <U> String toArray(U... element) {
        int iMax = element.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; ; ++i) {
            builder.append(element[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(',');
        }
    }

    public static String toArrayEnum(EnumParam... fields) {
        int iMax = fields.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; ; ++i) {
            builder.append(fields[i].getValue());
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(',');
        }
    }

    public static String toArrayEnum(Collection<EnumParam> collection) {
        Iterator<EnumParam> it = collection.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (;;) {
            sb.append(it.next().getValue());
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(',');
        }
    }
    public static void log(String msg) {
        try {
            System.out.println('[' +Thread.currentThread().getName()+"]: "+msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String bind(int start, String[] a) {
        int iMax = a.length;
        if (iMax == 0) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(" ");
        for (int i = start; i < iMax; ++i) {
            joiner.add(a[i]);
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

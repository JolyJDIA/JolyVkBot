package jolyjdia.bot.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class MathUtils {
    private MathUtils() {}

    public static int randomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
    public static int randomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static double randomDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }
    public static double randomDouble(double bound) {
        return ThreadLocalRandom.current().nextDouble(bound);
    }
    public static double randomDouble(double origin, double bound) {
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    public static float randomFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    public static float randomFloat(float bound) {
        if (bound <= 0.0F) {
            throw new IllegalArgumentException("граница должна быть положительной");
        }
        float result = ThreadLocalRandom.current().nextFloat() * bound;
        return (result < bound) ? result : // correct for rounding
                Float.intBitsToFloat(Float.floatToIntBits(bound) - 1);
    }

    public static float randomFloat(float origin, float bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("граница должна быть больше начала");
        }
        float r = ThreadLocalRandom.current().nextFloat();
        if (origin < bound) {
            r = r * (bound - origin) + origin;
            if (r >= bound) {
                r = Float.intBitsToFloat(Float.floatToIntBits(bound) - 1);
            }
        }
        return r;
    }

    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
    /*public static class Concurrent {
        public static int randomInt(int i, int i1) {
            return ThreadLocalRandom.current().nextInt((i1 - i) + 1) + i;
        }
        public static int randomInt(int i) {
            return ThreadLocalRandom.current().nextInt(i);
        }
        public static double randomDouble(double i, double i1) {
            return ThreadLocalRandom.current().nextDouble(i1 - i + 1) + i;
        }
        public static double randomDouble(double i) {
            return ThreadLocalRandom.current().nextDouble(i);
        }
        public static float randomFloat() {
            return ThreadLocalRandom.current().nextFloat();
        }
        public static boolean randomBoolean() {
            return ThreadLocalRandom.current().nextBoolean();
        }
    }*/
}
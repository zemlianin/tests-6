package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Crash_457585311770eb129e0bb40d957103666bef3dd8 {
    static final String base64Bytes = String.join("", "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABdAANwIA3CjY2CjYKJQoKIXg=");

    public static void main(String[] args) throws Throwable {
        Crash_457585311770eb129e0bb40d957103666bef3dd8.class.getClassLoader().setDefaultAssertionStatus(true);
        try {
            Method fuzzerInitialize = org.example.JazzerTarget.class.getMethod("fuzzerInitialize");
            fuzzerInitialize.invoke(null);
        } catch (NoSuchMethodException ignored) {
            try {
                Method fuzzerInitialize = org.example.JazzerTarget.class.getMethod("fuzzerInitialize", String[].class);
                fuzzerInitialize.invoke(null, (Object) args);
            } catch (NoSuchMethodException ignored1) {
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
        com.code_intelligence.jazzer.api.CannedFuzzedDataProvider input = new com.code_intelligence.jazzer.api.CannedFuzzedDataProvider(base64Bytes);
        org.example.JazzerTarget.fuzzerTestOneInput(input);
    }
}
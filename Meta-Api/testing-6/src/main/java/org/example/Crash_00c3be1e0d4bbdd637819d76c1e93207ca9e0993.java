package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Crash_00c3be1e0d4bbdd637819d76c1e93207ca9e0993 {
    static final String base64Bytes = String.join("", "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABdAACCiV4");

    public static void main(String[] args) throws Throwable {
        Crash_00c3be1e0d4bbdd637819d76c1e93207ca9e0993.class.getClassLoader().setDefaultAssertionStatus(true);
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
package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Crash_e5b86a4e8fe2a13271c58d90b0ad5c60221cc445 {
    static final String base64Bytes = String.join("", "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABdAACCj14");

    public static void main(String[] args) throws Throwable {
        Crash_e5b86a4e8fe2a13271c58d90b0ad5c60221cc445.class.getClassLoader().setDefaultAssertionStatus(true);
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
package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;

        reader = new BufferedReader(new InputStreamReader(System.in));
        var firstTest = reader.readLine();
        var line = reader.readLine();

        var cursor = -1;
        StringBuilder result = new StringBuilder();
        var action = '0';
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '<') {

                action = line.charAt(i + 1);
                switch (action) {
                    case 'd':
                        if (cursor != result.length() -1) {
                            result.deleteCharAt(cursor + 1);
                        }
                        break;
                    case 'b':
                        if (cursor != -1) {
                            result.deleteCharAt(cursor);
                            cursor--;
                        }
                        break;
                    case 'l':
                        if (cursor != -1) {
                            cursor--;
                        }
                        break;
                    case 'r':
                        if (cursor != result.length() - 1) {
                            cursor++;
                        }
                        break;
                }
                while (line.charAt(i) != '>') {
                    i++;
                }
                continue;
            }

            result.insert(cursor + 1, line.charAt(i));
            cursor++;
        }
        if(result.toString().equals(firstTest)){
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

    }
}
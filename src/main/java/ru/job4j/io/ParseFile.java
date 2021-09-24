package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        synchronized (this) {
            this.file = file;
        }
    }

    public String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int bytesRead;
            while ((bytesRead = input.read()) != -1) {
                if (filter.test((char) bytesRead)) {
                    output.append((char) bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}

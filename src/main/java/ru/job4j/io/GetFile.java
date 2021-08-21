package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class GetFile {
    private final File file;

    public GetFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return content(integer -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(integer -> integer < 0x80);
    }

    private String content(Predicate<Integer> filter) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = in.read()) > 0) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocExtractor {
    public List<Map<String, String>> extractLines (String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            List<Map<String, String>> flashCardsList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {

                flashCardsList.add(convertLine(line));
                System.out.println("line: " + line);
            }

            return flashCardsList;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> convertLine (String operation) {
        Pattern reg = Pattern.compile("^(.*)\s-\s(.*)$");
        Matcher matcher = reg.matcher(operation);

        if (matcher.find()) {
            return conventerResult(matcher);
        } else {
            System.out.println("there is no operation in this line");
            return null;
        }
    }

    public static Map<String, String> conventerResult(Matcher matcher) {
        Map<String, String> result = new HashMap<>();
        result.put("eng", matcher.group(1));
        result.put("pl", matcher.group(2));

        return result;
    }
}

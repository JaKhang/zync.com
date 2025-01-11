package com.zync.network.post.infrastructure.utils;

import com.zync.network.core.domain.ZID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    /**
     * Extracts all values enclosed in parentheses from the given input string.
     *
     * @param text The input string to search.
     * @return A list of values found inside parentheses.
     */
    public static Set<ZID> extractMentionIds(String text) {
        // Regular expression to match values inside parentheses
        String regex = "\\(([^)]+)\\)";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // List to store extracted values
        Set<ZID> values = new HashSet<>();

        // Find all matches
        while (matcher.find()) {
            values.add(ZID.from(matcher.group(1))); // Get the content inside parentheses
        }

        return values;
    }

    public static void main(String[] args) {
        String text = "Hello @[john_doe](01H7V9XJ5FHDPNC9B1XG7XV8RV) @[jane_doe](01H7V9XJ5JYZT1DJY0VGMPQ5FR)!";
        System.out.println(extractMentionIds(text));
    }
}

package me.shib.test.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class PatternAnalyzer implements Analyzer {

    private static final Pattern emailAddressRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern phoneNumberPattern = Pattern.compile("\\s+[789]\\d{9}\\s+");
    private static final Pattern[] secretPatterns = {Pattern.compile("\\s+[789]\\d{9}\\s+")};

    @Override
    public String analyze(File file) throws IOException {
        StringBuilder matchResult = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int lineNumber = 1;
        while ((line = br.readLine()) != null) {
            StringBuilder lineBuilder = new StringBuilder();
            int matchNumber = 1;
            if (emailAddressRegex.matcher(line).find()) {
                lineBuilder.append("\n").append(matchNumber).append(". Email Address Found");
                matchNumber++;
            }
            if (phoneNumberPattern.matcher(line).find()) {
                lineBuilder.append("\n").append(matchNumber).append(". Phone Number Found");
                matchNumber++;
            }
            for (Pattern pattern : secretPatterns) {
                if (pattern.matcher(line).find()) {
                    lineBuilder.append("\n").append(matchNumber).append(". Hardcoded Secret found.");
                    break;
                }
            }
            if (!lineBuilder.toString().isEmpty()) {
                matchResult.append("Issues found in line ").append(lineNumber).append(":").append(lineBuilder);
            }
            lineNumber++;
        }
        br.close();
        String result = matchResult.toString();
        if (!result.isEmpty()) {
            return result;
        }
        return null;
    }
}

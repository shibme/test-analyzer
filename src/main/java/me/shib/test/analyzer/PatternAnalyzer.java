package me.shib.test.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PatternAnalyzer implements Analyzer {

    private final List<Signature> signatures;

    PatternAnalyzer() throws IOException {
        this.signatures = Signature.getSignatures();
    }

    @Override
    public String analyze(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int lineNumber = 1;
        while ((line = br.readLine()) != null) {
            List<String> findings = new ArrayList<>();
            for (Signature signature : signatures) {
                Pattern pattern = Pattern.compile(signature.getRegex(), Pattern.CASE_INSENSITIVE);
                if (pattern.matcher(line).find()) {
                    findings.add(signature.getName());
                }
            }
            if (findings.size() > 0) {
                result.append("\nIssues found in line ").append(lineNumber)
                        .append(":\t").append(String.join(", ", findings));
            }
            lineNumber++;
        }
        br.close();
        String resultStr = result.toString();
        if (!resultStr.isEmpty()) {
            return resultStr;
        }
        return null;
    }
}

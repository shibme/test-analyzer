package me.shib.test.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzerEngine {

    private final List<Analyzer> analyzers;

    public AnalyzerEngine() throws IOException {
        this.analyzers = new ArrayList<>();
        this.analyzers.add(new PermissionAnalyzer());
        this.analyzers.add(new PatternAnalyzer());
    }

    public boolean analyze(File file) {
        boolean issuesFound = false;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                issuesFound |= analyze(f);
            }
        } else {
            StringBuilder fileResult = new StringBuilder();
            for (Analyzer analyzer : analyzers) {
                try {
                    String result = analyzer.analyze(file);
                    if (result != null) {
                        fileResult.append(result).append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!fileResult.toString().isEmpty()) {
                issuesFound = true;
                System.out.println("\n" + file.getPath() + ":\n" + fileResult);
            }
        }
        return issuesFound;
    }
}

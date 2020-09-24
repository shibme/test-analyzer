package me.shib.test.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnalyzerEngine {

    private final List<Analyzer> analyzers;

    public AnalyzerEngine() {
        this.analyzers = new ArrayList<>();
        this.analyzers.add(new PermissionAnalyzer());
        this.analyzers.add(new PatternAnalyzer());
    }

    public void analyze(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                analyze(f);
            }
        } else {
            System.out.println(file.getPath() + ":");
            for (Analyzer analyzer : analyzers) {
                try {
                    String result = analyzer.analyze(file);
                    if (result != null) {
                        System.out.println(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

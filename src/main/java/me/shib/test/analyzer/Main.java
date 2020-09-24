package me.shib.test.analyzer;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            File file = new File(args[0]);
            if (!file.exists()) {
                System.out.println("File/Directory doesn't exist");
                System.exit(1);
            }
            AnalyzerEngine analyzerEngine = new AnalyzerEngine();
            analyzerEngine.analyze(file);
        }
    }

}

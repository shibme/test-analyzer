package me.shib.test.analyzer;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            File file = new File(args[0]);
            if (!file.exists()) {
                System.out.println("File/Directory doesn't exist");
                System.exit(1);
            }
            AnalyzerEngine analyzerEngine = new AnalyzerEngine();
            if (analyzerEngine.analyze(file)) {
                System.exit(1);
            }
        } else {
            System.out.println("Please input the target directory/file as argument");
            System.exit(1);
        }
    }

}

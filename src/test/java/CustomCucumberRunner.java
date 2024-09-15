
import io.cucumber.core.cli.Main;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CustomCucumberRunner {

    public static void main(String[] args) {
        Properties props = new Properties();

        // Attempt to load cucumber.properties file if it exists
        try {
            FileInputStream propFile = new FileInputStream("src/test/resources/cucumber.properties");
            props.load(propFile);
            System.out.println("Loaded cucumber.properties file.");
        } catch (IOException e) {
            System.out.println("No cucumber.properties file found. Proceeding with VM args or default values.");
        }

        // Set all possible Cucumber options
        String gluePath = System.getProperty("cucumber.glue", props.getProperty("cucumber.glue", "com.aisa.cucumber7.stepdefinitions"));
        String featurePath = System.getProperty("cucumber.features", props.getProperty("cucumber.features", "src/test/resources/features"));
        String reportPath = System.getProperty("cucumber.report", props.getProperty("cucumber.report", "target/cucumber-reports/cucumber.html"));
        String jsonReport = System.getProperty("cucumber.json", props.getProperty("cucumber.json", "target/cucumber-reports/Cucumber.json"));
        String rerunPath = System.getProperty("cucumber.rerun", props.getProperty("cucumber.rerun", "target/rerun.txt"));
        String junitReport = System.getProperty("cucumber.junit", props.getProperty("cucumber.junit", "target/cucumber-reports/Cucumber.xml"));
        String tags = System.getProperty("cucumber.tags", props.getProperty("cucumber.tags", "@regression"));
        String snippets = System.getProperty("cucumber.snippets", props.getProperty("cucumber.snippets", "camelcase"));
        String threads = System.getProperty("cucumber.threads", props.getProperty("cucumber.threads", "4"));
        String nameFilter = System.getProperty("cucumber.name", props.getProperty("cucumber.name", ""));

        // Get boolean options (defaulting to "true" or "false" as needed)
        boolean monochrome = Boolean.parseBoolean(System.getProperty("cucumber.monochrome", props.getProperty("cucumber.monochrome", "false")));
        boolean dryRun = Boolean.parseBoolean(System.getProperty("cucumber.dryRun", props.getProperty("cucumber.dryRun", "false")));
        boolean publish = Boolean.parseBoolean(System.getProperty("cucumber.publish", props.getProperty("cucumber.publish", "false")));

        // Inform the user which values are being used
        System.out.println("Using the following configuration:");
        System.out.println("Glue: " + gluePath);
        System.out.println("Feature Path: " + featurePath);
        System.out.println("Report Path: " + reportPath);
        System.out.println("JSON Report Path: " + jsonReport);
        System.out.println("Rerun Path: " + rerunPath);
        System.out.println("JUnit Report Path: " + junitReport);
        System.out.println("Tags: " + tags);
        System.out.println("Snippets Style: " + snippets);
        System.out.println("Publish: " + publish);
        System.out.println("Monochrome: " + monochrome);
        System.out.println("Dry Run: " + dryRun);
        System.out.println("Threads: " + threads);
        System.out.println("Name Filter: " + nameFilter);

        // Create the list of Cucumber arguments dynamically
        List<String> cucumberArgsList = new ArrayList<>();
        cucumberArgsList.add("--glue");
        cucumberArgsList.add(gluePath);
        cucumberArgsList.add("--plugin");
        cucumberArgsList.add("pretty");
        cucumberArgsList.add("--plugin");
        cucumberArgsList.add("html:" + reportPath);
        cucumberArgsList.add("--plugin");
        cucumberArgsList.add("json:" + jsonReport);
        cucumberArgsList.add("--plugin");
        cucumberArgsList.add("junit:" + junitReport);
        cucumberArgsList.add("--plugin");
        cucumberArgsList.add("rerun:" + rerunPath);
        cucumberArgsList.add("--threads");
        cucumberArgsList.add(threads);
        cucumberArgsList.add("--tags");
        cucumberArgsList.add(tags);
        cucumberArgsList.add("--snippets");
        cucumberArgsList.add(snippets);
//        cucumberArgsList.add("--publish");
//        cucumberArgsList.add(publish);

        // Add boolean flags based on their value
        if (monochrome) {
            cucumberArgsList.add("--monochrome");
        }

        if (dryRun) {
            cucumberArgsList.add("--dry-run");
        }

        if (publish) {
            cucumberArgsList.add("--publish");
        }
        // Optionally apply name filter if provided
        if (!nameFilter.isEmpty()) {
            cucumberArgsList.add("--name");
        }

        // Add feature path at the end
        cucumberArgsList.add(featurePath);

        // Convert List to Array and pass it to Cucumber
        String[] cucumberArgs = cucumberArgsList.toArray(new String[0]);

        // Run the Cucumber tests
        Main.run(cucumberArgs, Thread.currentThread().getContextClassLoader());
        generateReport();
    }

    private static void generateReport() {
        try {
            File reportOutputDirectory = new File("target/cucumber-reports");
            List<String> jsonFiles = Arrays.asList("target/cucumber-reports/Cucumber.json");

            String buildNumber = "1";  // Can be dynamic, useful in CI pipelines
            String projectName = "Cucumber Project";

            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            configuration.setBuildNumber(buildNumber);

            // Optionally set additional metadata like OS, Browser, etc.
            configuration.addClassifications("Platform", System.getProperty("os.name"));
            configuration.addClassifications("Java Version", System.getProperty("java.version"));

            // Generate the report
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();

            System.out.println("Cucumber Report generated at: " + reportOutputDirectory.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error generating Cucumber report: " + e.getMessage());
        }
    }
}
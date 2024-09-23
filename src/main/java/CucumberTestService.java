import io.cucumber.core.cli.Main;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;


//import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CucumberTestService {

    public void runTests() {
        TestReportService testReportService = new TestReportService();
        Properties props = loadCucumberPropertiesFile();
        String[] cucumberArgs = buildCucumberArguments(props);
        runCucumberTests(cucumberArgs);
        testReportService.generateCucumberReport("Cucumber Test Report for Data Lake");
    }

    // Load cucumber.properties file
    private Properties loadCucumberPropertiesFile() {
        Properties props = new Properties();
        try {
            FileInputStream propFile = new FileInputStream("src/test/resources/cucumber.properties");
            props.load(propFile);
            System.out.println("Loaded cucumber.properties file.");
        } catch (IOException e) {
            System.out.println("No cucumber.properties file found. Proceeding with VM args or default values.");
        }
        return props;
    }

    // Build the list of Cucumber arguments
    private String[] buildCucumberArguments(Properties props) {
        // Setting all possible Cucumber options, may change in future if requried
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

        // boolean options (defaulting to "true" or "false" as needed)
        boolean monochrome = Boolean.parseBoolean(System.getProperty("cucumber.monochrome", props.getProperty("cucumber.monochrome", "false")));
        boolean dryRun = Boolean.parseBoolean(System.getProperty("cucumber.dryRun", props.getProperty("cucumber.dryRun", "false")));
        boolean publish = Boolean.parseBoolean(System.getProperty("cucumber.publish", props.getProperty("cucumber.publish", "false")));

        System.out.println("Using the following configuration:");
        printCucumberConfiguration(gluePath, featurePath, reportPath, jsonReport, rerunPath, junitReport, tags, snippets, publish, monochrome, dryRun, threads, nameFilter);

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
        cucumberArgsList.add("--plugin");
        cucumberArgsList.add("io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm");  // Allure plugin
        cucumberArgsList.add("--threads");
        cucumberArgsList.add(threads);
        cucumberArgsList.add("--tags");
        cucumberArgsList.add(tags);
        cucumberArgsList.add("--snippets");
        cucumberArgsList.add(snippets);

        // Adding boolean flags based on their value
        if (monochrome) cucumberArgsList.add("--monochrome");
        if (dryRun) cucumberArgsList.add("--dry-run");
        if (publish) cucumberArgsList.add("--publish");

        //  Applying name filter
        if (!nameFilter.isEmpty()) cucumberArgsList.add("--name");

        // Adding feature path at the end ()
        cucumberArgsList.add(featurePath);

        return cucumberArgsList.toArray(new String[0]);
    }

    // printing the configuration, to check what values are set
    private void printCucumberConfiguration(String gluePath, String featurePath, String reportPath, String jsonReport, String rerunPath, String junitReport, String tags, String snippets, boolean publish, boolean monochrome, boolean dryRun, String threads, String nameFilter) {
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
    }

    // Run Cucumber tests
    private void runCucumberTests(String[] cucumberArgs) {
        Main.run(cucumberArgs, Thread.currentThread().getContextClassLoader());
    }
}
//package com.aisa.cucumber7;

import io.cucumber.core.cli.Main;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        // Define the feature paths, glue code, plugins & all
        String[] cucumberArgs = new String[]{
                "--glue", "com.aisa.cucumber7.stepdefinitions",
                "--plugin", "pretty",
                "--plugin", "html:target/cucumber-reports/cucumber.html",
                "--plugin", "json:target/cucumber-reports/Cucumber.json", // JSON output needed for Cucumber Reporting
                "--threads", "4", // Set the number of threads for parallel execution
                "src/test/resources/features"
        };

        // Set parallel execution properties
        System.setProperty("cucumber.parallel.threads", "4"); // Number of threads for parallel execution

        // Set up a custom listener to log scenario execution
        System.setProperty("cucumber.logging.level", "INFO");

        System.out.println("Starting Cucumber tests with parallel execution...");

        // Run Cucumber tests
        Main.run(cucumberArgs, Thread.currentThread().getContextClassLoader());

        System.out.println("Cucumber tests execution finished.");
        System.out.println("Generating Master Thought Cucumber Report");
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
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TestReportService {
    // Master thought report
    public void generateCucumberReport() {
        try {
            File reportOutputDirectory = new File("target/cucumber-reports");
            List<String> jsonFiles = Arrays.asList("target/cucumber-reports/Cucumber.json");

            String projectName = "Cucumber Automation Report";

            Configuration configuration = new Configuration(reportOutputDirectory, projectName);

            //  Setting additional metadata like OS, Java Version, etc.
            configuration.addClassifications("Platform", System.getProperty("os.name"));
            configuration.addClassifications("Java Version", System.getProperty("java.version"));

            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();

            System.out.println("Cucumber Report generated at: " + reportOutputDirectory.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error generating Cucumber report: " + e.getMessage());
        }
    }

    // Master thought report
    public void generateCucumberReport(String projectName) {
        try {
            File reportOutputDirectory = new File("target/cucumber-reports");
            List<String> jsonFiles = Arrays.asList("target/cucumber-reports/Cucumber.json");

            Configuration configuration = new Configuration(reportOutputDirectory, projectName);

            //  Setting additional metadata like OS, Java Version, etc.
            configuration.addClassifications("Platform", System.getProperty("os.name"));
            configuration.addClassifications("Java Version", System.getProperty("java.version"));

            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();

            System.out.println("Cucumber Report generated at: " + reportOutputDirectory.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error generating Cucumber report: " + e.getMessage());
        }
    }
}

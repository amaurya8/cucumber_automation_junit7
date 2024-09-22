import org.junit.jupiter.api.Test;

public class DataCloudRunner {
    public static void main(String[] args) {
        CucumberTestService cucumberTestService = new CucumberTestService();
        cucumberTestService.runTests();
    }
}

package harness;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonExportTestListener implements TestExecutionListener {
    private static JsonExportTestListener INSTANCE;
    public JsonExportTestListener() {
        INSTANCE = this;
    }

    private record Value(
            String fen,
            String from,
            String to,
            String description,
            String testCase,
            boolean valid
    ) {}
    private static final ThreadLocal<String> testName = new ThreadLocal<>();

    private final List<Value> values = new ArrayList<>();

    public void registerTestVal(
            String fen,
            String from,
            String to,
            String description,
            boolean valid
    ) {
       values.add(new Value(fen, from, to, description, testName.get(), valid));
    }

    public static void registerTest(
            String fen,
            String from,
            String to,
            String description,
            boolean valid
    ) {
        INSTANCE.registerTestVal(fen, from, to, description, valid);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        TestExecutionListener.super.executionStarted(testIdentifier);
        testName.set(testIdentifier.getDisplayName());
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionStarted(testPlan);
        System.err.println("Test plan started!");
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        TestExecutionListener.super.testPlanExecutionFinished(testPlan);

        try {
            new ObjectMapper().writeValue(new File("./tests-output.json"), values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

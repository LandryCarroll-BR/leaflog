package com.landrycarroll.leaflog.infrastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A mock implementation of UserIO for testing purposes.
 */
public class MockUserIO implements UserIO {
    private final Queue<String> inputs = new LinkedList<>();
    private final List<String> outputs = new ArrayList<>();

    public void addInput(String input) {
        inputs.add(input);
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public String getLastOutput() {
        return outputs.isEmpty() ? null : outputs.get(outputs.size() - 1);
    }

    @Override
    public String readInput(String prompt) {
        // Optionally log the prompt if you want to verify it in tests
        outputs.add(prompt);

        // Simulate user input
        if (inputs.isEmpty()) {
            throw new IllegalStateException("No more inputs available in MockUserIO");
        }

        return inputs.poll();
    }

    @Override
    public void writeOutput(String message) {
        outputs.add(message);
    }
}

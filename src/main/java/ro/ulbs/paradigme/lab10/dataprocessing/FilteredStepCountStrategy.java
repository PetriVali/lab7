package ro.ulbs.paradigme.lab10.dataprocessing;

import ro.ulbs.paradigme.lab10.storage.DataRepository;
import ro.ulbs.paradigme.lab10.storage.SensorData;

import java.util.ArrayList;
import java.util.List;

public class FilteredStepCountStrategy implements StepCountStrategy {
    private final DataRepository repository;

    public FilteredStepCountStrategy(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void consumeMessage(SensorData sample) {

    }

    @Override
    public int getTotalSteps() {
        List<SensorData> data = repository.getRecords();
        if (data.isEmpty()) {
            return 0;
        }
        List<SensorData> accepted = new ArrayList<>();
        int headIndex = 0;
        int windowSum = 0;
        int total = 0;

        for (SensorData current : data) {
            int steps = current.getStepsCount();
            long currentTs = current.getTimestamp();

            if (steps <= 0) {
                continue;
            }
            long cutoff = currentTs - 60000;
            while (headIndex < accepted.size() && accepted.get(headIndex).getTimestamp() < cutoff) {
                windowSum -= accepted.get(headIndex).getStepsCount();
                headIndex++;
            }
            if (windowSum <= 1000) {
                accepted.add(current);
                windowSum += steps;
                total += steps;
            }
        }

        return total;
    }

    @Override
    public String getStrategyDescription() {
        return "Filtered (performant, O(N))";
    }
}

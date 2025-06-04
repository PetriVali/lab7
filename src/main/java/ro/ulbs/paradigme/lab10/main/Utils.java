package ro.ulbs.paradigme.lab10.main;

import ro.ulbs.paradigme.lab10.dataprocessing.BasicStepCountStrategy;
import ro.ulbs.paradigme.lab10.dataprocessing.FilteredStepCountStrategy;
import ro.ulbs.paradigme.lab10.dataprocessing.StepCountStrategy;
import ro.ulbs.paradigme.lab10.storage.DataRepository;

public class Utils {
    public static final String BASIC_STRATEGY = "basic";
    public static final String FILTERED_STRATEGY = "filtered";

    public static int getClientId() {
        return 42;
    }

    public static StepCountStrategy getStrategy(String type) {
        if (type.equals(BASIC_STRATEGY)) {
            return new BasicStepCountStrategy();
        } else if (type.equals(FILTERED_STRATEGY)) {
            DataRepository dataRepository = DataRepository.getInstance();
            return new FilteredStepCountStrategy(dataRepository);
        } else {
            throw new IllegalArgumentException("Unknown strategy type: " + type);
        }
    }
}
package ro.ulbs.paradigme.lab10.storage;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private final List<SensorData> records;
    private DataRepository() {
        this.records = new ArrayList<>();
    }
    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public void addData(SensorData dataRecord) {
        records.add(dataRecord);
    }

    public List<SensorData> getRecords() {
        return new ArrayList<>(records);
    }
}
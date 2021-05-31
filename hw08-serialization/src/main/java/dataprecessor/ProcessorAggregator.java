package dataprecessor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Measurement;

public class ProcessorAggregator implements Processor {

    //группирует выходящий список по name, при этом суммирует поля value
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        var map = new TreeMap<String, Double>();
        data.forEach(measurement ->
            map.compute(measurement.getName(), (k, v) -> (v == null ? 0 : v) + measurement.getValue())
        );
        return map;
    }

}

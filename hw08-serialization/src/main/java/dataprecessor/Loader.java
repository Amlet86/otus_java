package dataprecessor;

import java.io.IOException;
import java.util.List;

import model.Measurement;

public interface Loader {

    List<Measurement> load() throws IOException;
}

package dataprecessor;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Measurement;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    //читает файл, парсит и возвращает результат
    @Override
    public List<Measurement> load() throws IOException {
        try (JsonReader json = new JsonReader(new FileReader(fileName))) {
            Gson gson = new Gson();
            return Arrays.asList(gson.fromJson(json, Measurement[].class));
        }
    }
}

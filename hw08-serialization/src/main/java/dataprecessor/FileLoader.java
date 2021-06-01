package dataprecessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Measurement;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    //читает файл, парсит и возвращает результат
    @Override
    public List<Measurement> load() {
        List<Measurement> measurements;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
            Type type = new TypeToken<List<Measurement>>() {
            }.getType();
            measurements = new Gson().fromJson(streamReader, type);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
        return measurements;
    }

}

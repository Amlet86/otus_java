package dataprecessor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.google.gson.Gson;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    //формирует результирующий json и сохраняет его в файл
    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new Gson();
            gson.toJson(data, writer);
        }
    }
}

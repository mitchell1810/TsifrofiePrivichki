package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.TestObject;

import java.io.File;
import java.io.IOException;

public class JsonFileLoader {

    public static Object[][] getTestObjectDataFromFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        TestObject obj = mapper.readValue(file, new TypeReference<>() {
        });
        Object[][] posData = new Object[][]{{obj}};
        return posData;
    }
}

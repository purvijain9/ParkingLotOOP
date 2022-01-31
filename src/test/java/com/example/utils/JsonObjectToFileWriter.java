package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonObjectToFileWriter {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static JsonObjectToFileWriter instance;

    public static JsonObjectToFileWriter getInstance() {
        if (instance == null) {
            instance = new JsonObjectToFileWriter();
        }
        return instance;
    }

    public void writeJsonToFile(JSONObject jsonObject, String jsonFileName) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonFileName = jsonFileName.replaceAll(".xlsx","");
            Files.createDirectories(Paths.get(new File("target").getAbsolutePath() + "/json-response"));
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().
                    getFile().replace("test-classes", "json-response") + jsonFileName + ".json";
            log.info("Json File Path : " + path);
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(jsonObject.toString(4));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

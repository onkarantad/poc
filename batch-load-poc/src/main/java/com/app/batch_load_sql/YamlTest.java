package com.app.batch_load_sql;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

public class YamlTest {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        Reader yamlFile = null;
        try {
            yamlFile = new FileReader("config.yaml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> configMap = yaml.load(yamlFile);
        String sheetName = (String) configMap.get("sheetName");
        System.out.println("sheetName: "+sheetName);


    }
}

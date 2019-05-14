package com.movie.util;

import java.io.*;
import java.util.Properties;

public class PropertiesReader {
    private static PropertiesReader instance = new PropertiesReader();
    private static Properties properties;
    private static String path = "/Users/won/workspace/study-serving/movie-lucene/src/main/resources/application.properties";

    static {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(new BufferedInputStream(fileInputStream));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}

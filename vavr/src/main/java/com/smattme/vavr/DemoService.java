package com.smattme.vavr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DemoService {

    public static int computeDivision(int numerator, int denominator) {
        return numerator / denominator;
    }

    public static String readFile(String filePath) throws IOException, URISyntaxException {
        var uri  = DemoService.class.getClassLoader().getResource(filePath)
                .toURI();
        return Files.readString(Paths.get(uri));
    }



}

package com.david.parser;

import com.david.parser.bean.Info;
import org.junit.Test;

import java.io.File;


public class TestYamlParser {

    @Test
    public void test() throws Exception {
        YamlParser parser = new YamlParser();
        NodeRoot nodeRoot = parser.readFile(new File("src/test/resources/file_simple.yaml"));
    }

    @Test
    public void parseYamlFile() throws Exception {
        Parser parser = new YamlParser();
        Info info = parser.read(new File("src/test/resources/file_simple.yaml"), Info.class);
    }

}

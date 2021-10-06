package com.david.parser;

import com.david.parser.bean.Dependency;
import com.david.parser.bean.DevDependency;
import com.david.parser.bean.Info;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;


public class TestYamlParser {

    @Test
    public void test() throws Exception {
        YamlParser parser = new YamlParser();
        EntityRoot entityRoot = parser.readFile(new File("src/test/resources/file2.yaml"));
    }

    @Test
    public void parseYamlFileShouldSuccess() throws Exception {
        Parser parser = new YamlParser();
        Info info = parser.read(new File("src/test/resources/file2.yaml"), Info.class);
        Info infoExpected = buildInfo();
        assertEquals(infoExpected,info);
    }

    private Info buildInfo() {
        Info info = new Info();
        info.setName("dakar");
        info.setVersion("0.0.1");
        info.setDescription("Une bibliothèque qui permet de créer des éléments à collectionner.");
        Dependency dependency = new Dependency();
        dependency.setArgs("any");
        dependency.setBrowser("any");
        dependency.setGeo("any");
        dependency.setShelf("any");
        dependency.setGoogle_maps("^3.1.0");
        dependency.setDart_to_js_script_rewriter("^0.1.0");
        dependency.setShelf_static("any");
        dependency.setShelf_web_socket("any");
        dependency.setXml_rpc("any");
        DevDependency devDependency = new DevDependency();
        devDependency.setTest("any");
        info.setDependencies(dependency);
        info.setDev_dependencies(devDependency);
        info.setTransformers(Arrays.asList("dart_to_js_script_rewriter","js_anonymizer","js_compresser"));
        return info;
    }
}

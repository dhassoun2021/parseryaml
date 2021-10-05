package com.david.parser;

import com.david.parser.bean.Info;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


public class TestYamlParser {

    @Test
    public void test() throws Exception {
        YamlParser parser = new YamlParser();
        NodeRoot nodeRoot = parser.readFile(new File("src/test/resources/file2.yaml"));
    }

    @Test
    public void parseYamlFileShouldSuccess() throws Exception {
        Parser parser = new YamlParser();
        Info info = parser.read(new File("src/test/resources/file2.yaml"), Info.class);
        assertNotNull(info);
        assertEquals("dakar",info.getName());
        assertEquals("Une bibliothèque qui permet de créer des éléments à collectionner.",info.getDescription());
        assertEquals("0.0.1",info.getVersion());
        assertEquals("any",info.getDependencies().getArgs());
        assertEquals("any",info.getDependencies().getBrowser());
        assertEquals("any",info.getDependencies().getGeo());
        assertEquals("any",info.getDependencies().getShelf());
        assertEquals("any",info.getDependencies().getShelf_web_socket());
        assertEquals("any",info.getDependencies().getShelf_static());
        assertEquals("any",info.getDependencies().getXml_rpc());
        assertEquals("^0.1.0",info.getDependencies().getDart_to_js_script_rewriter());
        assertEquals("^3.1.0",info.getDependencies().getGoogle_maps());
        assertEquals("any",info.getDev_dependencies().getTest());
        List<String> transformers = info.getTransformers();
        assertNotNull(transformers);
        List<String> transformersWaited = Arrays.asList("dart_to_js_script_rewriter","js_anonymizer","js_compresser");
        assertEquals(transformersWaited,transformers);
    }


}

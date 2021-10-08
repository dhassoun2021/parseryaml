package com.david.yamlparser.parser;

import com.david.yamlparser.introspector.IntrospectorEngine;
import com.david.yamlparser.structure.EntityRoot;

import java.io.File;

/**
 * This class has responsability to parse YAML file and store data in instance
 */
public abstract class AbstractParser implements Parser {


    /**
     * Read a  file and store data in the instance corresponding to the class theClass
     */
    @Override
    public <T> T read(File file, Class<T> theClass)  {
        final EntityRoot entityRoot = readFile(file);
        final IntrospectorEngine introspector = IntrospectorEngine.getInstance();
        return introspector.toInstance(theClass,entityRoot);
    }

    /**
     * Read a yaml file and store data in recursive structure
     * @param file
     * @return
     */
    public abstract EntityRoot readFile (File file) ;
}

package com.david.parser;

import com.david.introspector.IntrospectorEngine;

import java.io.File;


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

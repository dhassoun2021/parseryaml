package com.david.parser;

import com.david.exceptions.IntrospectionException;
import com.david.introspector.Introspector;

import java.io.File;
import java.io.IOException;

public abstract class AbstractParser implements Parser {

    @Override
    /**
     * Read a  file and store data in the instance corresponding to the class theClass
     */
    public <T> T read(File file, Class<T> theClass) throws IOException, IntrospectionException {
        NodeRoot nodeRoot = readFile(file);
        Introspector introspector = new Introspector(nodeRoot);
        return introspector.toInstance(theClass);
    }

    /**
     * Read a yaml file and store data in recursive structure
     * @param file
     * @return
     * @throws IOException
     */
    public abstract NodeRoot readFile (File file) throws IOException;
}

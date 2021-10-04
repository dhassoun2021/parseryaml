package com.david.parser;

import com.david.exceptions.IntrospectionException;
import com.david.exceptions.ParsingException;
import com.david.introspector.Introspector;

import java.io.File;
import java.io.IOException;

public abstract class AbstractParser implements Parser {

    @Override
    /**
     * Read a  file and store data in the instance corresponding to the class theClass
     */
    public <T> T read(File file, Class<T> theClass) throws IOException, IntrospectionException, ParsingException {
        final NodeRoot nodeRoot = readFile(file);
        final Introspector introspector = new Introspector(nodeRoot);
        return introspector.toInstance(theClass);
    }

    /**
     * Read a yaml file and store data in recursive structure
     * @param file
     * @return
     * @throws IOException
     */
    public abstract NodeRoot readFile (File file) throws IOException, ParsingException;
}

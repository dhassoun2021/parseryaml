package com.david.parser;

import java.io.File;
import java.io.IOException;

public abstract class AbstractParser implements Parser {

    @Override
    /**
     * Read a  file and store data in the instance corresponding to the class theClass
     */
    public <T> T read(File file, Class<T> theClass) throws IOException {
        NodeRoot nodeRoot = readFile(file);
        return null;
    }

    /**
     * Read a yaml file and store data in recursive structure
     * @param file
     * @return
     * @throws IOException
     */
    public abstract NodeRoot readFile (File file) throws IOException;
}

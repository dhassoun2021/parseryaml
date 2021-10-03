package com.david.parser;

import com.david.exceptions.IntrospectionException;

import java.io.File;
import java.io.IOException;

public interface Parser {

 <T> T  read (File file,Class <T> theClass) throws IOException, IntrospectionException;
}

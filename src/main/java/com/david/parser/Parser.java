package com.david.parser;


import java.io.File;
import java.io.IOException;

public interface Parser {

 <T> T  read (File file,Class <T> theClass) ;
}

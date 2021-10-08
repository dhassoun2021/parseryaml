package com.david.parser;

import java.io.File;


public interface Parser {

 <T> T  read (File file,Class <T> theClass) ;
}

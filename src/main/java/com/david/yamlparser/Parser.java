package com.david.yamlparser;


import java.io.File;

public interface Parser {

 /**
  * Read a  file and store data in the instance corresponding to the class theClass
  * @param file file to parse
  * @param theClass class corresponding to instance we want populate
  * @param <T> Type
  * @return Instance corresponding to the class theClass populated with data parsed in the file
  */
 <T> T  read (File file,Class <T> theClass) ;
}

# Yaml parser

##Purpose
This project is a yaml parser which could read a yaml file and generate an instance with data read.

##Prerequisite
To use this project you need to install jdk 11.

##Usage 
Entry point class of this tool is YamlFactory class.

You could use this as for example:

Parser parser = YamlFactory.getParser();
Info info = parser.read(new File("src/test/resources/file_nominal.yaml"), Info.class);


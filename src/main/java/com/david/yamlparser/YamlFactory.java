package com.david.yamlparser;

public final class YamlFactory {

    public static Parser getParser () {
        return new YamlParser();
    }
}

package com.david.yamlparser;

import com.david.yamlparser.parser.Parser;
import com.david.yamlparser.parser.YamlParser;

public final class YamlFactory {

    public static Parser getParser () {
        return new YamlParser();
    }
}

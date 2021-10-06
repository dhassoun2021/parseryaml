package com.david.introspector.util;

/**
 * This class contains utility method for introspection
 */
public final class InstrospectorUtil {

    public static String convertPropertyToMethod (String property,String prefix){
        char firstChar = property.charAt(0);
        String capitalLetter = Character.toString ( firstChar-32);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(capitalLetter);
        sb.append(property.substring(1));
        return sb.toString();
    }
}

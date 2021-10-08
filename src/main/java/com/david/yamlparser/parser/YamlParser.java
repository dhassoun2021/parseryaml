package com.david.yamlparser.parser;

import com.david.yamlparser.exceptions.ParsingException;
import com.david.yamlparser.structure.EntityRoot;
import com.david.yamlparser.structure.Node;
import com.david.yamlparser.structure.NodeElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class has responsability to parse YAML file and store data in recursive structure
 */
 public class YamlParser extends AbstractParser {

    private static final String COLON = ":";
    private static final String DASH = "-";
    private static final String BLANK = " ";
    private static final char BLANK_CHAR = ' ';
    private static final String KEY_VALUE_PATTERN = "\\w+:\\s\\S.*";

    /**
     * Store deep level of data parsed and associate it to parent NodeElement structure.
     */
    private Map <Integer, NodeElement> widthSpaceNodes = new HashMap<>();


    /**
     * Read a yaml file and store data in recursive structure
     * @param file
     * @return
     * @throws IOException
     */
    public EntityRoot readFile (File file)  {
        EntityRoot entityRoot = new EntityRoot();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int nbLine = 1;
            int widthSpace;
            int nbLineKeyValue = 0;

            NodeElement lastNodeProcessed = null;
            boolean lineWithListPatternProcessed = false;
            List <String> elements = null;
            while ((line = br.readLine()) != null) {
                widthSpace = computePrefixeWidthSpace(line);
                line = line.trim();

                //store node root at first line read
                if (isFirstLineRead(nbLine)) {
                    widthSpaceNodes.put(widthSpace, entityRoot);
                }

                // key/value data ... pattern as "key:value"
                if (hasLinePatternKeyValue(line)) {
                    nbLineKeyValue++;
                    if (isFirstLineInKeyValuePattern(nbLineKeyValue) && hasPreviousNodeWithSameWidthSpaceVisited(widthSpace) && !(widthSpaceNodes.get(widthSpace).isRoot())) {
                        widthSpaceNodes.remove(widthSpace);
                    }
                    Node node = getNodeFromLineKeyValuePattern(line);
                    processNode(node,widthSpace,lastNodeProcessed);

                        //Tree structure ... pattern as ... "tree:"
                    } else if (hasLinePatternNode(line)) {
                        nbLineKeyValue = 0;
                        Node node = getNodeFromLineNodePattern(line);
                        lastNodeProcessed = node;
                        processNode(node,widthSpace,lastNodeProcessed);

                      //list pattern ... pattern as "- listElement"
                    } else if (hasLinePatternList(line)) {
                        nbLineKeyValue = 0;
                       String elementOfList = getElementList(line);

                       //when first line is processing we create empty list
                       if (!lineWithListPatternProcessed) {
                           elements = new ArrayList<>();

                           //add list as value to parent node
                           if (lastNodeProcessed instanceof Node) {
                               Node nodeList = (Node) lastNodeProcessed;
                               nodeList.setValue(elements);
                           } else {
                               throw new ParsingException("Error parsing at line " + nbLine);
                           }
                       }
                       elements.add(elementOfList);
                       lineWithListPatternProcessed = true;
                    }

                nbLine++;
            }
        } catch (IOException ex) {
            throw new ParsingException(ex.getMessage());
        }
        return entityRoot;
    }

    /**
     * Compute length of white space begining a line
     * @param line
     * @return
     */
    private int computePrefixeWidthSpace (String line){
        int lng = 0;
        for (int i = 0; i < line.length(); i++){
            if (line.charAt(i) != BLANK_CHAR){
                return lng;
            } else {
                lng ++;
            }
        }
        return lng;
    }

    private void processNode (Node node, int widthSpace, NodeElement lastNode) {
        NodeElement parentNode = getParentNode(widthSpace,lastNode);
        parentNode.addNode(node);
        node.setParentNode(parentNode);
    }

    private boolean hasPreviousNodeWithSameWidthSpaceVisited (int widthSpace) {
        return widthSpaceNodes.get(widthSpace) != null;
    }

    private boolean isFirstLineInKeyValuePattern(int nbLineKeyValue) {
        return nbLineKeyValue == 1;
    }

    private boolean isFirstLineRead (int nbLine) {
        return (nbLine == 1);
    }

    private boolean hasLinePatternList (String line) {
        if (line == null) {
            return false;
        }
        return line.startsWith(DASH);
    }

    private boolean hasLinePatternKeyValue (String line) {
        if (line == null) {
            return false;
        }
        return line.matches(KEY_VALUE_PATTERN);
    }

    private boolean hasLinePatternNode (String line) {
        if (line == null) {
            return false;
        }
        return line.endsWith(COLON);
    }

    private Node getNodeFromLineKeyValuePattern (String line) {
        int index = line.indexOf(COLON);
        String key = line.substring(0,index);
        String value = line.substring(index+2);
        Node node = new Node(key);
        node.setValue(value);
        return node;
    }

    private Node getNodeFromLineNodePattern (String line) {
        String name = line.substring(0,line.length()-1);
        Node node = new Node(name);
        return node;
    }

    private String getElementList (String line) {
        if (line == null) {
            return null;
        }
        int index = line.indexOf(BLANK);
        if (index == -1) {
            return null;
        }
        return line.substring(index+1);

    }

    private NodeElement getParentNode (int widthSpace, NodeElement lastNode) {
        NodeElement parentNode = widthSpaceNodes.get(widthSpace);
        if (parentNode == null) {
            widthSpaceNodes.put(widthSpace, lastNode);
            parentNode = lastNode;
        }
        return parentNode;
    }


}

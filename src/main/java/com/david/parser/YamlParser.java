package com.david.parser;

import com.david.exceptions.ParsingException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class has responsability to parse YAML file and store data in instance
 */
public class YamlParser extends  AbstractParser {

    private Map <Integer,NodeElement> widthSpaceNodes = new HashMap<>();
   // private Map <Integer,Integer> widthSpaceDeepLevels = new HashMap<>();

    private static final String COLON = ":";
    private static final String DASH = "-";



    /**
     * Compute length of white space begining a line
     * @param line
     * @return
     */
    private int computePrefixeWidthSpace (String line){
        int lng = 0;
        for (int i = 0; i<line.length();i++){
            if (line.charAt(i) != ' '){
                return lng;
            } else {
                lng ++;
            }
        }
        return lng;
    }

    /**
     * Read a yaml file and store data in recursive structure
     * @param file
     * @return
     * @throws IOException
     */
    public NodeRoot readFile (File file) throws IOException,ParsingException {
        NodeRoot nodeRoot = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int nbLine = 1;
            int widthSpace;
            int lastId = 0;
            int nbLineKeyValue = 0;
            nodeRoot = new NodeRoot();
            NodeElement lastNode = null;
            boolean lineWithListPattern = false;
            List <String> elements = null;
            while ((line = br.readLine()) != null) {
                widthSpace = computePrefixeWidthSpace(line);
                line = line.trim();
                if (nbLine == 1) {
                    widthSpaceNodes.put(widthSpace,nodeRoot);
                }
                String[] datas = line.split(COLON);
                if (datas != null) {

                    // key/value data
                    if (datas.length == 2) {
                        nbLineKeyValue++;
                        if (nbLineKeyValue == 1 && widthSpaceNodes.get(widthSpace) != null && !(widthSpaceNodes.get(widthSpace) instanceof NodeRoot)) {
                            widthSpaceNodes.remove(widthSpace);
                        }
                        String name = datas[0].trim();
                        String value = datas[1].trim();
                        Node node = new Node(name);
                        node.setValue(value);
                        NodeElement nodeElement = widthSpaceNodes.get(widthSpace);
                        if (nodeElement == null) {
                            widthSpaceNodes.put(widthSpace, lastNode);
                            nodeElement = lastNode;
                        }
                        nodeElement.addNode(node);
                        node.setParentNode(nodeElement);

                        //Tree structure
                    } else if (line.endsWith(COLON)) {
                        nbLineKeyValue = 0;
                        String name = datas[0].trim();
                        Node node = new Node(name);
                        lastNode = node;
                        //add to parent node
                        NodeElement nodeElement = widthSpaceNodes.get(widthSpace);
                        if (nodeElement == null) {
                            widthSpaceNodes.put(widthSpace, lastNode);
                            nodeElement = lastNode;
                        }
                        nodeElement.addNode(node);
                        node.setParentNode(nodeElement);

                        //list
                    } else if (line.startsWith(DASH)) {
                        nbLineKeyValue = 0;
                        int index = line.indexOf(" ");
                       String elementOfList = line.substring(index+1,line.length());
                       if (!lineWithListPattern) {
                           elements = new ArrayList<>();
                           if (lastNode instanceof  Node) {
                               Node nodeList = (Node) lastNode;
                               nodeList.setValue(elements);
                           } else {
                               throw new ParsingException("");
                           }
                       }
                       elements.add(elementOfList);
                       lineWithListPattern = true;
                    }
                }

                nbLine++;
                // process the line.
            }
        }
        return  nodeRoot;
    }


}

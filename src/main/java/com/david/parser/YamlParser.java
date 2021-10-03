package com.david.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class has responsability to parse YAML file and store data in instance
 */
public class YamlParser extends  AbstractParser {

    private Map <Integer,NodeElement> deepLevelNodes = new HashMap<>();
    private Map <Integer,Integer> widthSpaceDeepLevels = new HashMap<>();

    private static final String COLON = ":";



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
    public NodeRoot readFile (File file) throws IOException {
        NodeRoot nodeRoot = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int nbLine = 1;
            int widthSpace;
            int lastId = 0;
            nodeRoot = new NodeRoot();
            deepLevelNodes.put(lastId, nodeRoot);
            while ((line = br.readLine()) != null) {
                widthSpace = computePrefixeWidthSpace(line);
                String[] datas = line.split(COLON);
                if (datas != null) {

                    // key/value data
                    if (datas.length == 2) {
                        String name = datas[0].trim();
                        String value = datas[1].trim();
                        Node node = new Node(name);
                        node.setValue(value);
                        if (widthSpaceDeepLevels.get(widthSpace) == null) {
                            widthSpaceDeepLevels.put(widthSpace, ++lastId);
                        }
                        int nodeId = widthSpaceDeepLevels.get(widthSpace);
                        NodeElement nodeElement = deepLevelNodes.get(nodeId-1);
                        nodeElement.addNode(node);
                        node.setParentNode(nodeElement);

                        //Tree structure
                    } else {
                        String name = datas[0].trim();
                        Node node = new Node(name);

                        //add to parent node
                        if (widthSpaceDeepLevels.get(widthSpace) == null) {
                            widthSpaceDeepLevels.put(widthSpace, ++lastId);

                        }
                        int nodeId = widthSpaceDeepLevels.get(widthSpace);
                        deepLevelNodes.put(nodeId,node);
                        NodeElement nodeElement = deepLevelNodes.get(nodeId-1);
                        nodeElement.addNode(node);
                        node.setParentNode(nodeElement);
                    }
                }
                nbLine++;
                // process the line.
            }
        }
        return  nodeRoot;
    }
}

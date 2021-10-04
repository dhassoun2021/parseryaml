package com.david.parser;

import com.david.introspector.Introspector;
import com.david.parser.bean.Info;
import org.junit.Assert;
import org.junit.Test;

public class TestIntrospector {

    @Test
    public void applyIntrospection()  throws Exception{
        NodeRoot nodeRoot = new NodeRoot();
        Node nodeName = new Node("name");
        nodeName.setValue("value");
        nodeRoot.addNode(nodeName);
        Node nodeDependency = new Node("dependencies");
        Node nodeGeo = new Node("geo");
        nodeDependency.addNode(nodeGeo);
        nodeRoot.addNode(nodeDependency);
        nodeName.setParentNode(nodeRoot);
        nodeDependency.setParentNode(nodeRoot);
        nodeDependency.setParentNode(nodeDependency);
        nodeGeo.setParentNode(nodeDependency);
        nodeGeo.setValue("any");
        Introspector introspector = new Introspector(nodeRoot);
        Info info = introspector.toInstance(Info.class);
        Assert.assertNotNull(info);
        Assert.assertEquals("value",info.getName());
        Assert.assertNotNull(info.getDependencies());
        Assert.assertNotNull(info.getDependencies().getArgs());
        Assert.assertEquals("any",info.getDependencies().getGeo());
    }
}

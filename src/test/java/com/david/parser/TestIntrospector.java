package com.david.parser;

import com.david.introspector.Introspector;
import com.david.parser.bean.Info;
import org.junit.Assert;
import org.junit.Test;

public class TestIntrospector {

    @Test
    public void applyIntrospection()  throws Exception{
        EntityRoot entityRoot = new EntityRoot();
        Node nodeName = new Node("name");
        nodeName.setValue("value");
        entityRoot.addNode(nodeName);
        Node nodeDependency = new Node("dependencies");
        Node nodeGeo = new Node("geo");
        nodeDependency.addNode(nodeGeo);
        entityRoot.addNode(nodeDependency);
        nodeName.setParentNode(entityRoot);
        nodeDependency.setParentNode(entityRoot);
        nodeDependency.setParentNode(nodeDependency);
        nodeGeo.setParentNode(nodeDependency);
        nodeGeo.setValue("any");
        Introspector introspector = new Introspector(entityRoot);
        Info info = introspector.toInstance(Info.class);
        Assert.assertNotNull(info);
        Assert.assertEquals("value",info.getName());
        Assert.assertNotNull(info.getDependencies());
        Assert.assertNotNull(info.getDependencies().getArgs());
        Assert.assertEquals("any",info.getDependencies().getGeo());
    }
}

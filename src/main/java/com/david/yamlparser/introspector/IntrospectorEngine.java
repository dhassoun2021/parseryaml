package com.david.yamlparser.introspector;

import com.david.yamlparser.exceptions.ParsingException;
import com.david.yamlparser.introspector.util.InstrospectorUtil;
import com.david.yamlparser.structure.EntityRoot;
import com.david.yamlparser.structure.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class has responsability to create an instance dynamically and populate it from data store in EntityRoot structure.
 */
public class IntrospectorEngine {

    private final static String GET_PREFIX = "get";
    private final static String SET_PREFIX = "set";

    private static IntrospectorEngine _instance = new IntrospectorEngine();

    private IntrospectorEngine() {

    }

    public static IntrospectorEngine getInstance() {
        return _instance;
    }

    /**
     * Create an instance from class aClass and populate it from nodeRoot structure
     *
     * @param aClass Class which will be instancied
     * @param <T>    generic type of instance
     * @return
     * @throws ParsingException
     */
    public <T> T toInstance(Class<T> aClass, EntityRoot entityRoot)  {
        try {
            final T t = aClass.getDeclaredConstructor().newInstance();
            for (Node node : entityRoot.getNodes()) {
                applyIntrospection(node, t);
            }
            return t;
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex) {
            throw new ParsingException((ex.getMessage()));
        }
    }

    private <T> void applyIntrospection(Node node, T t) {
        if (!node.hasChildren()) {
            doIntrospection(node, t);
            return;
        }
        for (Node child : node.getChildren()) {
            applyIntrospection(child, t);
        }
    }

    private Object invokeGetMethod(String getMethod, String setMethod, Object instance)  {
        Object newInstance = null;
        try {
            //invoke get method
            final Method method = instance.getClass().getMethod(getMethod);
            newInstance = method.invoke(instance);

            //if instance returned is null, we create instance form return type and set it to current instance
            if (newInstance == null) {
                newInstance = method.getReturnType().getDeclaredConstructor().newInstance();
                //Set new instance to current instance
                final Optional<Method> optionalSetMethod = Arrays.stream(instance.getClass().getMethods()).filter(m -> m.getName().equals(setMethod)).findFirst();
                optionalSetMethod.get().invoke(instance, newInstance);
                if (optionalSetMethod.isEmpty()) {
                    throw new ParsingException("Method " + setMethod + " not found");
                }
            }
        } catch
        (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                        ex) {
            throw new ParsingException(ex.getMessage());

        }

        return newInstance;
    }

    private void invokeSetMethod(String setMethod, Object currentInstance, Object value)  {
        Optional<Method> optionalMethod = Arrays.stream(currentInstance.getClass().getMethods()).filter(m -> m.getName().equals(setMethod)).findFirst();
        if (optionalMethod.isEmpty()) {
            throw new ParsingException ("Method " + setMethod + " not found");
        }
        try {
            optionalMethod.get().invoke(currentInstance, value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ParsingException(ex.getMessage());
        }
    }

    private <T> void doIntrospection(Node node, T t){
        //properties corresponding to methods to invoke
        //for exemple if properties are : x,y,z, methods to invoke will be getX().getY().setZ(...)
        final List<String> properties = node.getPath();
        Object currentInstance = t;
        for (int i = 0; i < properties.size(); i++) {
            String prefix = (i + 1 == properties.size()) ? SET_PREFIX : GET_PREFIX;
            String property = properties.get(i);
            String methodName = InstrospectorUtil.convertPropertyToMethod(properties.get(i), prefix);

            //apply get method
            if (methodName.startsWith(GET_PREFIX)) {
                currentInstance = invokeGetMethod(methodName, InstrospectorUtil.convertPropertyToMethod(property, SET_PREFIX), currentInstance);
            } else {
                //apply set method
                invokeSetMethod(methodName, currentInstance, node.getValue());
            }
        }
    }


}

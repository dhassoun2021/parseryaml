package com.david.introspector;

import com.david.exceptions.IntrospectionException;
import com.david.parser.Node;
import com.david.parser.NodeRoot;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class has responsability to create an instance dynamically and populate it from data store in EntityRoot structure.
 */
public class Introspector {

    private final static String GET_PREFIX = "get";
    private final static String SET_PREFIX = "set";

    private final NodeRoot nodeRoot;
    public Introspector (NodeRoot nodeRoot){
       this.nodeRoot = nodeRoot;
    }

    /**
     * Create an instance from class aClass and populate it from nodeRoot structure
     * @param aClass Class which will be instancied
     * @param <T> generic type of instance
     * @return
     * @throws IntrospectionException
     */
      public  <T> T toInstance (Class <T> aClass) throws IntrospectionException{
        try {
         final T t =  aClass.getDeclaredConstructor().newInstance();
          for (Node node : nodeRoot.getNodes()) {
              applyIntrospection (node,t);
          }
          return t;
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException| InvocationTargetException|InstantiationException ex) {
            throw new IntrospectionException( (ex.getMessage()));
        }
    }

    private <T> void applyIntrospection (Node node, T t) throws IntrospectionException {
       if (!node.hasChildren()){
           doIntrospection(node,t);
           return;
       }
      for (Node child : node.getChildren()){
          applyIntrospection(child,t);
      }
    }

    private Object invokeGetMethod (String getMethod,String setMethod, Object instance) throws IntrospectionException {
        Object newInstance = null;
        try {
            //invoke get method
           final Method method = instance.getClass().getMethod(getMethod);
            newInstance = method.invoke(instance);
            if (newInstance == null) {
                newInstance = method.getReturnType().getDeclaredConstructor().newInstance();
            }
            //Set new instance to current instance
            final Optional <Method> optionalSetMethod = Arrays.stream(instance.getClass().getMethods()).filter(m->m.getName().equals(setMethod)).findFirst();
            optionalSetMethod.get().invoke(instance,newInstance);
            if (optionalSetMethod.isEmpty()){
                throw new IntrospectionException("Method " + setMethod + " not found");
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IntrospectionException(ex.getMessage());

        }
        return newInstance;
    }

    private void invokeSetMethod (String setMethod,Object currentInstance, Object value) throws IntrospectionException{
        Optional <Method> optionalMethod = Arrays.stream(currentInstance.getClass().getMethods()).filter(m->m.getName().equals(setMethod)).findFirst();
        if (optionalMethod.isEmpty()){
            throw new IntrospectionException("Method " + setMethod + " not found");
        }
        try {
            optionalMethod.get().invoke(currentInstance, value);
        } catch ( IllegalAccessException | IllegalArgumentException| InvocationTargetException ex) {
            throw new IntrospectionException(ex.getMessage());
        }
    }

    private <T> void doIntrospection (Node node, T t) throws IntrospectionException{
       final List<String> properties = node.getPath();
       Object currentInstance = t;
       for (int i = 0 ; i < properties.size(); i++){
           String prefix = (i+1 == properties.size()) ? SET_PREFIX:GET_PREFIX;
           String property = properties.get(i);
           String methodName = convertPropertyToMethod(property,prefix);
           if (methodName.startsWith(GET_PREFIX)) {
               currentInstance = invokeGetMethod(methodName,convertPropertyToMethod(property,SET_PREFIX),currentInstance);
           } else {
               //apply set method
               invokeSetMethod(methodName,currentInstance,node.getValue());
           }
       }
    }


    private static String convertPropertyToMethod (String property,String prefix){
        char firstChar = property.charAt(0);
        String capitalLetter = Character.toString ( firstChar-32);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(capitalLetter);
        sb.append(property.substring(1));
        return sb.toString();
    }
}

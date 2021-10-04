package com.david.introspector;

import com.david.exceptions.IntrospectionException;
import com.david.parser.Node;
import com.david.parser.NodeRoot;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Introspector {

    private final static String GET_PREFIX = "get";
    private final static String SET_PREFIX = "set";

    private final NodeRoot nodeRoot;
    public Introspector (NodeRoot nodeRoot){
       this.nodeRoot = nodeRoot;
    }

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

    private <T> void doIntrospection (Node node, T t) throws IntrospectionException{
       final List<String> paths = node.getPath();
       Object currentInstance = t;
       for (int i = 0 ; i < paths.size(); i++){
           String prefix = (i+1 == paths.size()) ? SET_PREFIX:GET_PREFIX;
           String property = paths.get(i);
           String methodName = convertPathToMethod(property,prefix);
           try {
                if (methodName.startsWith(GET_PREFIX)) {
                   currentInstance = invokeGetMethod(methodName,convertPathToMethod(property,SET_PREFIX),currentInstance);
                } else {
                    //apply set method
                    Optional <Method> optionalMethod = Arrays.stream(currentInstance.getClass().getMethods()).filter(m->m.getName().equals(methodName)).findFirst();
                    if (optionalMethod.isEmpty()){
                        throw new IntrospectionException("Method " + methodName + " not found");
                    }
                    optionalMethod.get().invoke(currentInstance,node.getValue());
                }
           } catch ( IllegalAccessException | IllegalArgumentException| InvocationTargetException ex) {
               throw new IntrospectionException(ex.getMessage());
           }
       }
    }


    private static String convertPathToMethod (String path,String prefix){
        String capitalLetter = path.substring(0,1).toUpperCase();
        String methodName = capitalLetter + path.substring(1);
        methodName = prefix+methodName;
        return methodName;
    }
}

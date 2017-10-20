package com.zzbcome.domain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MainHandler implements InvocationHandler {  
    private Object target;  
  
    MainHandler() {  
        super();  
    }  
  
    MainHandler(Object target) {  
        super();  
        this.target = target;  
    }  
  
    @Override  
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {  
        Object result = method.invoke(target, args);  
        return result;  
  
    }  
}  

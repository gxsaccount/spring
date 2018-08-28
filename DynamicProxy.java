
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyAnnotation implements InvocationHandler {

    private Object target;

    public DynamicProxyAnnotation(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProxyAnnotation annotation = (ProxyAnnotation) method.getDeclaredAnnotation(ProxyAnnotation.class);
        Object result = null;
        if(annotation==null){
            result = method.invoke(target, args);
        }
        else if (annotation.value()) {
            System.out.println("true的业务之前做的事情");
            result = method.invoke(target, args);
            System.out.println("true的业务之后做的事情");
        } else if(!annotation.value()) {
            System.out.println("false的业务之前做的事情");
            result = method.invoke(target, args);
            System.out.println("false的业务之后做的事情");
        }
        
        return result;
    }

    public static void main(String[] args) {
        DoSomething doSomething = new RealDoSomething();
        DynamicProxyAnnotation hander = new DynamicProxyAnnotation(doSomething);
        DoSomething instance =(DoSomething)Proxy.newProxyInstance(
                doSomething.getClass().getClassLoader(), 
                doSomething.getClass().getInterfaces(), 
                hander);
        instance.doSomething("doSomething");
        System.out.println("");
        instance.doSomething2("doSomething2");
        System.out.println("");
        instance.doSomething3("doSomething3");
    }

}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface ProxyAnnotation {

    public boolean value() default true;
}

interface DoSomething {

    @ProxyAnnotation(true)
    public default void doSomething(String... things) {
        for (String thing : things) {
            System.out.println(thing);
        }
    }

    @ProxyAnnotation(false)
    public default void doSomething2(String... things) {
        for (String thing : things) {
            System.out.println(thing);
        }
    }
    public default void doSomething3(String... things) {
        for (String thing : things) {
            System.out.println(thing);
        }
    }

}

class RealDoSomething implements DoSomething {

}

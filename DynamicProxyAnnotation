
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
        if (annotation.value()) {
            System.out.println("业务之前做的事情");
            result = method.invoke(target, args);
            System.out.println("业务之后做的事情");
        } else {
            result = method.invoke(target, args);
        }
        return result;
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

}

class RealDoSomething implements DoSomething {

}

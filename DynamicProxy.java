
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class DynamicProxy implements InvocationHandler {

    private Object object;

    public DynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("实现业务之前需要做的事情");
        method.invoke(object, args);
        System.out.println("实现业务之后需要做的事情");
        return null;
    }

    public static void main(String[] args) {
        DoSomething doSomething = new RealDoSomething();
        InvocationHandler handler = new DynamicProxy(doSomething);
        DoSomething proxy = (DoSomething) Proxy.newProxyInstance(
                handler.getClass().getClassLoader(), 
                doSomething.getClass().getInterfaces(), 
                handler);
        proxy.doSomething("do homework");
    }
}
interface DoSomething{
    public default void  doSomething(String... things){
        for (String thing : things) {
            System.out.println(thing);
        }
    }; 
}
class RealDoSomething implements DoSomething{
    
}

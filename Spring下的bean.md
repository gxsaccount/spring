bean：可以复用的类。
bean规范：
必须是个公有(public)类
有无参构造函数
用公共方法暴露内部成员属性(getter,setter)


实现这样规范的类，被称为Java Bean。即是一种可重用的组件。
容器：一切Spring bean都存储在Spring容器内，并由其通过IoC技术管理。Spring容器也就是一个bean工厂（BeanFactory）。应用中bean的实例化，获取，销毁等都是
由这个bean工厂管理的。

Spring的应用上下文：Spring容器抽象的一种实现

Spring容器下的bean生命周期：
实例化-> 填充属性 -> 调用BeanNameAware的setBeanName() -> 调用BeanFactoryAware的setBeanFactory()方法 ->
调用ApplicationContextAware的setApplicationContext()方法 -> 调用BeanPostProcessor的预初始化方法 -> 
调用InitlizingBean的afterPropertiesSet()方法 -> 调用自定义的初始化方法 -> 调用BeanPostProcessor初始化方法
 -> 使用 -> 容器关闭 -> 调用DisposableBean的destroy()方法 -> 调用自定义的销毁方法
 
常用bean的装配：
XML显示装配
Java中显示装配（JavaConfig）（次建议）
隐式的bean发现机制和自动装配（建议）

自动化装配实现：
组件扫描：spring自动发现应用上下文中创建的bean（@Component）
自动装配：Spring自动满足bean之间依赖（@Autowired）

组件：
@Component注解将某个类注解为组件类。（如是类A）

配置类：配置上下文如何装配bean
@Configuration
@ComponentScan扫面组件，查找所有带@Component的类，自动为其创建bean，相当于上下文
<!-- Spring 的注解开发:组件扫描(类上注解: 可以直接使用属性注入的注解) -->
<context:component-scan base-package="com.apress.prospringmvc.bookstore.web"/>
@ComponentScan(basePackages = { "com.apress.prospringmvc.bookstore.web" })  

被注入的类：
@ContextConfiguration(上下文类.class)
@Autowired使用在构造器，setter等各种方法上，自动注入。（将对应的bean注入）

Bean的属性：
scope 属性：Bean 的作用范围.
* singleton :默认值，单例的.
* prototype :多例的
* request :WEB 项目中,Spring 创建一个 Bean 的对象,将对象存入到 request 域中.
* session :WEB 项目中,Spring 创建一个 Bean 的对象,将对象存入到 session 域中.
* globalSession :WEB 项目中,应用在 Porlet 环境.如果没有 Porlet 环境那么 globalSession 相当于 session.

  Spring 生成 Bean 的时候三种方式(了解)
【无参数的构造方法的方式:】
<!-- 方式一：无参数的构造方法的实例化 -->
<bean id="bean1" class="cn.itcast.spring.demo3.Bean1"></bean>


【静态工厂实例化的方式】
提供一个工厂类：
public class Bean2Factory {
   public **static** Bean2 getBean2(){
      return new Bean2();
   }
}
<!-- 方式二：静态工厂实例化 Bean -->
<bean id="bean2" class="cn.itcast.spring.demo3.Bean2Factory"factory-method="getBean2"/>


【实例工厂实例化的方式】
提供 Bean3 的实例工厂:
public class Bean3Factory {
   public Bean3 getBean3(){
       return new Bean3();
   }
}
<!-- 方式三：实例工厂实例化 Bean -->
<bean id="bean3Factory" class="cn.itcast.spring.demo3.Bean3Factory"></bean>
<bean id="bean3" factory-bean="bean3Factory" factory-method="getBean3"></bean>

Spring 的 Bean 的属性注入:
【构造方法的方式注入属性】
<!-- 第一种：构造方法的方式 -->
<bean id="car" class="cn.itcast.spring.demo4.Car">
    <constructor-arg name="name" value="保时捷"/>
    <constructor-arg name="price" value="1000000"/>
</bean>
【set 方法的方式注入属性】
<!-- 第二种：set 方法的方式 -->
<bean id="car2" class="cn.itcast.spring.demo4.Car2">
    <property name="name" value="奇瑞 QQ"/>
    <property name="price" value="40000"/>
</bean>
1.2.6.3 Spring 的属性注入：对象类型的注入:
<!-- 注入对象类型的属性 -->
<bean id="person" class="cn.itcast.spring.demo4.Person">
    <property name="name" value="会希"/>
    <!-- ref 属性：引用另一个 bean 的 id 或 name -->
    <property name="car2" ref="car2"/>
</bean>
SpEL 的方式的属性注入:Spring3.x 版本后提供的方式.
SpEL：Spring Expression Language.
语法:#{ SpEL }
<!-- SpEL 的注入的方式 -->
<bean id="car2" class="cn.itcast.spring.demo4.Car2">
<property name="name" value="#{'奔驰'}"/>
<property name="price" value="#{800000}"/>
</bean>
 <bean id="person" class="cn.itcast.spring.demo4.Person">
 <property name="name" value="#{'冠希'}"/>
 <property name="car2" value="#{car2}"/>
 </bean>
<bean id="carInfo" class="cn.itcast.spring.demo4.CarInfo"></bean>
引用了另一个类的属性
<bean id="car2" class="cn.itcast.spring.demo4.Car2">
<!-- <property name="name" value="#{'奔驰'}"/> -->
<property name="name" value="#{carInfo.carName}"/>
<property name="price" value="#{carInfo.calculatePrice()}"/>
</bean>
1.2.6.6 注入复杂类型:
<!-- Spring 的复杂类型的注入===================== -->
<bean id="collectionBean" class="cn.itcast.spring.demo5.CollectionBean">
<!-- 数组类型的属性 -->
<property name="arrs">
<list>
<value>会希</value>
<value>冠希</value>
<value>天一</value>
</list>
传智播客——专注于 Java、.Net 和 Php、网页平面设计工程师的培训
</property>
<!-- 注入 List 集合的数据 -->
<property name="list">
<list>
<value>芙蓉</value>
<value>如花</value>
<value>凤姐</value>
</list>
</property>
<!-- 注入 Map 集合 -->
<property name="map">
<map>
<entry key="aaa" value="111"/>
<entry key="bbb" value="222"/>
<entry key="ccc" value="333"/>
</map>
</property>
<!-- Properties 的注入 -->
<property name="properties">
<props>
<prop key="username">root</prop>
<prop key="password">123</prop>
</props>
</property>
</bean>


Bean 的生命周期的配置:
@PostConstruct :相当于 init-method
@PreDestroy :相当于 destroy-method

<table>
 <tr>
 <th></th>
  <th>XML</th>
  <th>注解</th>
 </tr>
 <tr>
 <th>bean定义</th>
  <td>&lt;bean id="..." class="..."/&gt;</td>
  <td>@Component衍生类 @Repository @Service @Controller</td>
 </tr>
  <tr>
  <th>bean名称</th>
  <td>通过id或name指定</td>
  <td>@Component("person")</td>
 </tr>
   <tr>
   <th>bean注入</th>
  <td>&lt;property&gt;</td>
  <td>@Autowired按类型注入</td>
 </tr>
 <tr>
   <th>生命过程</th>
  <td>init-method/destroy-method</td>
  <td>@PostConstruct/@preDestroy</td>
 </tr>
 <tr>
   <th>bean作用范围</th>
  <td>scope属性</td>
  <td>@Scope设置</td>
 </tr>
 <tr>
   <th>适合场景</th>
  <td>Bean来自第三方</td>
  <td>Bean的实现类用户自己开发</td>
 </tr>
</table>

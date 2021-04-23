# myspringboot
MySpringBoot是以Maven为基础，以Netty作为Http服务器的，仿Springboot框架。实现了Springboot部分的功能，用于学习Spring和Spring Boot的编程思想以及深入理解Spring的实现原理。

默认启动端口：8080

服务器启动地址：127.0.0.1:8080

## 特点

- 使用Netty编写Http服务器，代替原生内嵌的Tomcat
- 实现SpringMVC部分的注解并进行路由控制
- 实现SpringIOC容器及自动装配功能
- 实现SpringAOP部分功能
- 与SpringBoot相同的简单的启动方式



## 服务器端

使用netty配置服务器，使用MVC模型处理请求。



## 控制器（Controller）

**已实现注解：**

- @RestController：标注一个Controller类；参数作为该Controller的父路径。
- @GetMapping：处理GET请求；参数为子路径。
- @PostMapping：处理POST请求；参数为子路径。
- @RequestParam：处理GET请求的参数；控制参数是否为必要参数，参数value的值作为该参数的key值。
- @RequestBody：处理POST请求中的JSON；使用JSON传入相关参数。
- @PathVariable：截取路径中{...}的内容作为参数；参数为路径中的变量。



## IOC容器

**已实现注解：**

- @Component：标注一个类为Component组件，IOC容器自动为该组件进行注册；参数为对该组件的重命名。
- @Autowired：自动依赖注入。
- @Qualifier：注入接口时，使用指定实现类进行依赖注入；参数为实现类在IOC注册的名称，即实现类的@Component参数。



## AOP

根据目标对象是否存在接口，能动态选择实现 JDK 和 Cglib 代理方法。

**已实现注解：**

- @Aspect：指定当前Component为一个AOP连接点。
- @PointCut：定义切点，参数value为切点路径。
- @Before：切点的前置拦截器。
- @After：切点的后置拦截器。



## 使用

启动demo包中的MyBootApplicationDemo，通过**@ComponentScan("com.msb.demo")**注解，自动引入包的根路径。



参考项目：

https://spring.io/projects/spring-boot

https://github.com/xiaogou446/jsonboot

https://github.com/Snailclimb/jsoncat
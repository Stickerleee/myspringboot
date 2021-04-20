# myspringboot
练习：仿springboot制作的只能处理json的服务器

参考项目地址：https://github.com/xiaogou446/jsonboot

默认启动端口：8080

本地地址：127.0.0.1:8080



## 服务器端

使用netty配置服务器，使用MVC模型处理请求。



## 控制器（Controller）

**已实现注解：**

- @RestController：标注一个Controller类；参数作为该Controller的父路径。
- @GetMapping：处理GET请求；参数为子路径。
- @PostMapping：处理POST请求；参数为子路径。
- @RequestParam：处理GET请求的参数；控制参数是否为必要参数，参数value的值作为该参数的key值。
- @RequestBody：处理POST请求中的JSON。
- @PathVariable：截取路径中{...}的内容作为参数；参数为路径中的变量。



## IOC容器

**已实现注解：**

- @Component：标注一个类为Component组件，IOC容器自动为该组件进行注册；参数为对该组件的重命名。
- @Autowired：自动依赖注入。
- @Qualifier：注入接口时，使用指定实现类进行依赖注入；参数为实现类在IOC注册的名称，即实现类的@Component参数。
# SpringBoot学习笔记（二）：Web简单开发

- **SpringBoot Web开发非常简单，包括json输出、自定义filter、property操作等**
## 一、项目结构
![项目结构图](结构.png)
## 二、Json 接口开发
* 需要类添加 **@RestController** 或（**@Controller+@ResponseBody**），默认类中的方法都会以 **json** 的格式返回
## 三、自定义 Filter
### 两个步骤
1. 实现 Filter 接口，实现 Filter 方法

```java
public class MyFilter implements Filter {
    @Override
	public void destroy() {}
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		System.out.println("This is MyFilter,url :"+request.getRequestURI());
		filterChain.doFilter(servletRequest, servletResponse);
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}
```
2. 增加配置类，类上添加**@Configuration** 注解，将自定义Filter加入过滤链

```java
@Configuration
public class WebConfiguration {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }    
    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter()); //添加过滤器
        registration.addUrlPatterns("/*");      //设置过滤路径
        registration.addInitParameter("name", "Filter");//添加默认参数
        registration.setName("MyFilter");
        registration.setOrder(1);   //执行的顺序
        return registration;
    }
}
```
## 四、自定义 Property
1. 配置在 **application.properties** 中

```properties
config.title=CSDN
config.description=分享技术的网站
```
2. 自定义配置类

- **@Value**：单个注入值
- **@ConfigurationProperties**：批量注入配置文件中的属性

```java 
@Component
public class NeoProperties {
	@Value("${config.title}")
    private String title;
    @Value("${config.description}")
	private String description;
	//省略getter settet方法
}
```

```java
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigProperties {
    private String title;
    private String description;
    //省略getter settet方法
}
```

## 五、控制类

```java
@RestController
public class MyController {
    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    FilterRegistrationBean registration;
    /**
    * 返回信息配置类*/
    @RequestMapping("/config")
    public String getConfig(){
        StringBuffer result=new StringBuffer();
        String title = configProperties.getTitle();
        String description = configProperties.getDescription();
        result.append(title).append(",").append(description);
        return result.toString();
    }
    /**
     * 返回过滤器初始化参数
     */
    @RequestMapping("/parameters")
    public Map<String, String> getName() {
        Map<String, String> initParameters = registration.getInitParameters();
        return initParameters;
    }
}
```


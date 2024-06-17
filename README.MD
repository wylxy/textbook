
项目采用前后端分离的方式进行开发，前端使用Vue2进行开发。  
前端代码放在另一个压缩包里，在前端目录里使用`npm run serve`命令即可启动前端项目，需要安装`npm 14`才能启动。  

项目使用Maven进行依赖管理，使用主要技术为Spring + SpringMVC + Mybatis，  
关键技术版本：
- Java 8
- Tomcat 9.0
- MySQL 8
- Spring 5.3.27
- Mybatis 3.5.6

数据库表结构的文件在附带的.sql文件中，使用mysql8直接运行以后可以恢复数据库，里面包含少量测试数据

后端项目可以直接使用maven编译成war以后由tomcat启动，或者在idea里添加tomcat9的运行配置。


## 备注

### 登录过滤器
登录过滤器的白名单使用application.properties实现

实际上，@WebFilter是Servlet的注解，属于Servlet容器的内容，Spring容器不参与管理

代码中同时加上@WebFilter和@Component注解，
会让过滤器同时添加到Servlet容器和Spring容器中，但实际上两个容器中是不同的对象

为了让Servlet容器中的过滤器能够通过Spring的@Value读取到配置文件里的白名单，
需要在过滤器中添加一个静态的白名单字段，然后在非静态的setter方法上加上@Value注解。

这样，spring容器中的过滤器就能够通过@Value注解读取到配置文件里的白名单，同时，Servlet容器中的过滤器也能够通过静态字段读取到白名单。

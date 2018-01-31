#### 使用SpringCloud 搭建分布式服务

> Spring Cloud为开发人员提供了快速构建分布式系统中一些常见模式的工具（例如配置管理，服务发现，断路器，智能路由，微代理，控制总线，一次性令牌，全局锁，领导选举，分布式会话，集群状态）。分布式系统的协调导致了样板模式, 使用Spring Cloud开发人员可以快速地支持实现这些模式的服务和应用程序。他们将在任何分布式环境中运行良好，包括开发人员自己的笔记本电脑，裸机数据中心，以及Cloud Foundry等托管平台。

SpringCloud 本身不是实现分布式的技术而是与SpringBoot 整合实现更简单的 编码

### Spirng Cloud Eureka 

云端服务发现，一个基于 REST 的服务，用于定位服务，以实现云端中间层服务发现和故障转移。

服务注册与发现对于微服务系统来说非常重要。有了服务发现与注册，你就不需要整天改服务调用的配置文件了，你只需要使用服务的标识符，就可以访问到服务。

1. ### Eureka服务端

   Eureka服务端，即服务注册中心。它同其他服务注册中心一样，支持高可用配置。依托于强一致性提供良好的服务实例可用性，可以应对多种不同的故障场景。

      Eureka服务端支持集群模式部署，当集群中有分片发生故障的时候，Eureka会自动转入自我保护模式。它允许在分片发生故障的时候继续提供服务的发现和注册，当故障分配恢复时，集群中的其他分片会把他们的状态再次同步回来。集群中的的不同服务注册中心通过异步模式互相复制各自的状态，这也意味着在给定的时间点每个实例关于所有服务的状态可能存在不一致的现象。

   搭建Eureka 服务端

   Pom文件中加入

   ```XML
   <dependencies>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-eureka-server</artifactId>
       </dependency>
   </dependencies>
   ```

   SpringBoot 启动类加入@EnableEurekaServer

   ```java
   @SpringBootApplication
   @EnableEurekaServer
   public class discovery {
       public static void main(String[] args) {
           SpringApplication.run(discovery.class,args);
       }
   }
   ```


   ```
   server:
     port: 8080
   eureka:
     instance:
       hostname: localhost
     client:
       register-with-eureka: false
       fetch-registry: false
       serviceUrl:
         defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
   ```

   以上配置文件：

   port：8080我们设置服务端端口为8080 

   hostname：服务注册中心实例的主机名

   register-with-eureka：是否向服务注册中心注册自己

   fetch-registry：是否检索服务

   defaultZone：服务注册中心的配置内容，指定服务注册中心的位置

   defaultZone 其实等于http://locahost:8080/eureka/

   配置完成后 我们启动程序访问 localhost:8080

   ![https://coding.net/u/y535744264h/p/myImage/git/raw/master/localhost8080.png](https://coding.net/u/y535744264h/p/myImage/git/raw/master/localhost8080.png)

2. ### 创建并注册服务提供者

   新建一个Project 

   Pro文件中加入

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>

   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-eureka-server</artifactId>
   </dependency>
   ```

   启动类

   ```java
   @SpringBootApplication
   @EnableEurekaServer
   public class server1 {
       public static void main(String[] args) {
           SpringApplication.run(server1.class,args);
       }
   }
   ```

   配置文件

   ```
   spring:
     application:
       name: server1
   server:
     port: 8081
   eureka:
     client:
       serviceUrl:
         defaultZone: http://localhost:8080/eureka/
   ```

   以上配置文件：

   port：8081我们设置服务端端口为8081

   name：项目名

   defaultZone：服务注册中心的配置内容，指定服务注册中心的位置

   配置完成后 启动8080 和8081

   ![8081](https://coding.net/u/y535744264h/p/myImage/git/raw/master/8081.png)

   同样的方法 再创建一个Server2 这样几个分布的服务就可以互相发现

### Spring Cloud Zuul

Eureka用于服务的注册于发现，外部的应用如何来访问内部各种各样的微服务呢？在微服务架构中，后端服务往往不直接开放给调用端，而是通过一个API网关根据请求的url，路由到相应的服务。当添加API网关后，在第三方调用端和服务提供方之间就创建了一面墙，这面墙直接与调用方通信进行权限控制，后将请求均衡分发给后台服务端。

Spring Cloud Zuul路由是微服务架构的不可或缺的一部分，提供动态路由，监控，弹性，安全等的边缘服务。Zuul是Netflix出品的一个基于JVM路由和服务端的负载均衡器。

新建一个Project
Pom文件

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-security</artifactId>
</dependency>
```

启动类加入@EnableZuulProxy@EnableEurekaClient

```java
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class gateway {
    public static void main(String[] args) {
        SpringApplication.run(gateway.class,args);
    }
}
```

配置文件

```yaml
spring:
  application:
    name: gateway
server:
  port: 8083
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8080/eureka/
zuul:
  routes:
    server1:  /server1/**
    server2:  /server2/**
```

以上配置文件：（解释过的不再解释）

routes-server1: server1是第一个组件的Name  /server1/**访问路径

配置完成后 访问localhost:8083/server1/就能访问到 Server1的资源

配置完成后 访问localhost:8083/server2/就能访问到 Server2的资源

end

Session 共享 待
```java
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class WorkerWholicApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkerWholicApplication.class, args);
    }
}
```
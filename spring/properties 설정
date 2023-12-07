작성 기준 - spring-boot '3.1.6'

# application.properties

기본적으로 생성되는 파일.

```properties
 server.port=8081
```

스프링에서 제공하는 속성 - [application-properties.html 공식 문서](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)

<br>

# application.yml 

Spring Boot Documentation - [21.5 Using YAML instead of Properties](https://docs.spring.io/spring-boot/docs/1.2.0.M1/reference/html/boot-features-external-config.html#boot-features-external-config-yaml)  
Spring Boot를 사용할 경우, `SnakeYAML`라이브러리가 포함되어 있기 때문에 자동으로 인식. (spring-boot-starter에 포함)  


```yml
server:  
    port: 8081
```

* 파일 우선 순위: yml, properties 상관없이 정의된 순서 (2.4에 변경)
* 단점: `@PropertySource` 를 통해 호출 불가 (확인 필요)

<br>
<br>

# Profile 기능을 통한 환경 분리

Spring Boot Documentation - [Core Features - 3. Profiles](https://docs.spring.io/spring-boot/docs/3.1.6/reference/html/features.html#features.profiles)

### 설명

* Spring에서는 `@Profile`을 통해 @Component, @Configuration 또는 @ConfigurationProperties 클래스의 호출을 특정 프로필로 제한할 수 있음.
    ```java
    @Configuration(proxyBeanMethods = false)
    @Profile("production")
    public class ProductionConfiguration {

        // ...

    }
    ```
  
    _참고사항_
    > 만약 @ConfigurationProperties 이 자동 스캔이 아닌 @EnableConfigurationProperties을 통해 빈으로 주입되었다면, @Profile은 @EnableConfigurationProperties가 있는 @Configuration 클래스 안에 지정되어있어야 함.

* 테스트에서는 `@ActiveProfiles`를 통해 특정 프로필 호출 가능.
    ```java
    @SpringBootTest
    @ActiveProfiles("test")
    class CafeServiceTest {

        //...
        
    }
    ```

### 설정 파일

> * 2.4 버전 주요 변경사항 참고 - [Config file processing in Spring Boot 2.4](https://spring.io/blog/2020/08/14/config-file-processing-in-spring-boot-2-4)
>   * @deprecated "spring.profiles" -> __spring.config.activate.on-profile__ 
>   * 특정 프로필 내에서 activated 설정 불가
>   * 파일 로딩 순서는 정의된 순서로

```yml
server:
    port: 9000
---
spring:
  config:
    activate:
      on-cloud-platform: "kubernetes"
      on-profile: "prod | staging"
server:
    port: 9001
---
spring:
  config:
    activate:
      on-profile: production
server:
    port: 0
```

* `---` 를 통해 문서를 분류하여 매핑됨.
* 발견된 순서로 입력되어, 마지막 값이 병합됨.
    * 위 예제에서, 기본 포트는 9000 이고, "development, production"이 활성화 시 포트는 0.
* `on-cloud-platform` : 해당 플랫폼일 경우만 profile 활성화


### 별도 파일로 profile 분리
Spring Boot Documentation - [2.3.3. Profile Specific Files](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.files.profile-specific)

* 위 작업을 별도 파일로 생성하려면, `application-${profile}.properties` 에 profile값을 입력하여 사용하면 됨 (yml 동일).
* profile 설정은 기본 설정보다 항상 우선되고, active가 두 개 이상일 경우 뒤에 나온 profile이 덮어쓰기됨 (`last-win 전략` 적용)
* `last-win 전략` 에 파일 경로에 따른 그룹 레벨이 있음으로 주의! - 공식문서 참고



<br>
<br>


> 참조 링크  
https://www.baeldung.com/spring-profiles  


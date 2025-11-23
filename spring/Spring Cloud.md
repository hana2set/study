# [Spring Cloud](https://spring.io/projects/spring-cloud)란?

분산 시스템에서 사용되는 [12-요소](https://12factor.net/) 패턴을 빠르게 구축하도록 돕는 도구들. 클라우드 프로젝트에서 발생할 수 있는 이슈를 다룰 수 있는 각종 도구를 제공한다.
- 버저닝
- 서비스 레지스트레이션, 디스커버리
- 라우팅
- 로드밸런싱
- MSA


![cloud-3](https://github.com/user-attachments/assets/07e75e94-38a2-4dd2-bfc7-cb3e7ba16a11?raw=true)

> [!note]
> Nexflix에서 Spring Cloud에 구성요소들을 다양하게 지원해왔으나 현재 대부분이 중단된 상태.(Kubernetes 등의 영향으로 생각됨)  
> 2025년 6월 기준 Eureka 서버 외엔 존재하지 않음으로, 다른 서비스로 마이그레이션하는 것 추천. 
 

## [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)

Spring WebFlux 기반의 API 게이트웨이.

### 사용법
1. Gateway 어플리케이션 생성
    ```gradle
    // spring boot 3.5.0 기준

    ext {
        set('springCloudVersion', "2025.0.0")
    }

    ...

    dependencies {
        ...
        implementation 'org.springframework.cloud:spring-cloud-starter-gateway-server-webflux'
    }

    ...

    dependencyManagement {
        imports {
            mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
        }
    }

    ```
2. application.yml에 정보 등록
    ```yml
    spring.application.name: gateway
    server:
    port: 8000

    spring:
    cloud:
        consul:
        host: localhost
        port: 8500
        gateway:
        routes:
            - id: multiplications
            uri: lb://social-multiplication  #load_balancer://<spring.application.name>
            predicates:
                - Path=/api/multiplications/**
            - id: results
            uri: lb://social-multiplication/results
            predicates:
                - Path=/api/results/**
    ```

## [Spring Cloud Consul](https://spring.io/projects/spring-cloud-consul)

Spring Boot 앱에 대한 대규모 분산 처리를 도와주는 도구. 주로 `Service Discovery`, `Service Registry`, `LoadBalancer` 역할을 담당할 수 있음.  
내부적으로 Discovery, Config(Key-Value 저장소)로 나뉘어져있음.

### consul-discovery 설치 
Eureka와 다르게, consul 서버는 별도 Java App이 필요없음(Third Party)   
종속성을 client 서버에 추가하고 실행하면 자동으로 인식함.

1. registry에 등록할 서버(client)에 의존성 설치
    ``` java
	implementation 'org.springframework.cloud:spring-cloud-starter-consul-discovery'
    ```
3. Consul 설치 (docker로 진행)
    ```docker
    docker run -d --name=consul -p 8500:8500 consul:latest
    ```


## [Spring Cloud Circuit Breaker](https://spring.io/projects/spring-cloud-circuitbreaker#overview)
"서킷 브레이커"가 구현되어 있는 라이브러리.(실패 요청을 최소화하기 위한 패턴) `Resilience4J`와 `Retry`로 구성되어있다. (`Netflix Hystrix`의 대체로 적합)
- 주요 컴포넌트[^circuit1]
  - Circuit Breaker: 서비스 호출 실패를 감지하고 자동으로 보호.
  - Retry: 서비스가 실패하면 자동으로 재시도.
  - Rate Limiter: 특정 시간 동안 호출할 수 있는 횟수를 제한.
  - Bulkhead: 병렬로 실행되는 작업의 최대 수를 제한하여 시스템 보호.
  - Time Limiter: 서비스 호출이 일정 시간 안에 완료되지 않으면 타임아웃 처리.




[^circuit1]: https://digitalbourgeois.tistory.com/395  
https://resilience4j.readme.io/docs/getting-started-3

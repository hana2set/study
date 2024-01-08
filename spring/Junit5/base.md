## 테스트 전략

| 어노테이션 | Target Layer | 설명 | 부모 클래스 | Bean |
|---|---|---|---|---|
| @SpringBootTest | ALL | 통합 테스트, 전체 | IntegrationTest | Bean 전체 |
| @WebMvcTest | Controller | 단위 테스트, Mvc 테스트 | MockApiTest | MVC 관련된 Bean |
| @DataJpaTest | Repository | 단위 테스트, Jpa 테스트 | RepositoryTest | JPA 관련 Bean |
| None | Service | 단위 테스트, Service 테스트 | MockTest | None |
| None | DTO,Entity,Util... | POJO, 도메인 테스트 | None | None |


### FIRST원칙

* Fast - 빨리
* Independent - 단위 기능
* Repeatable - 같은결과
* Self-Validating - 단언
    * 내용 확인시 비용 -> 값이 명확해야함. (assert 사용)
* Timely - 즉시 작성

## Spring 통합 테스트 vs JPA 슬라이스 테스트 

통합테스트는 아래와 같은 단점때문에 슬라이스 테스트를 권장
- 실제 구동되는 애플리케이션의 설정, 모든 `Bean`을 로드하기 때문에 시간이 오래걸리고 무겁다.
- 테스트 단위가 크기 때문에 디버깅이 어려운 편이다.
- 결과적으로 웹을 실행시키지 않고 테스트 코드를 통해 빠른 피드백을 받을 수 있다는 장점이 희석된다.
1. Spring 전체 빈 을 사용해야하는 Spring 통합 테스트
    ```java
    @SpringBootTest   
    // SpringApplication 띄울때의 빈들을 모두 생성해줍니다.
    @Transactional    
    // 테스트 메소드들이 모두 트랜잭션에 포함되어 최적화 되도록 합니다. 
    // 테스트 대상 함수의 실행환경에서는 Transaction이 안걸려 있을 수 있으니 실무에 사용시 주의
    @Rollback(value = false) // 테스트 데이터가 롤백되지 않고 실제 DB에 반영되도록 합니다.
    ```
2. JPA 관련 빈 만 사용하는 JPA 슬라이스 테스트
    ```java
    @DataJpaTest      
    // SpringDataJpa 테스트에 필요한 빈들만 생성해줍니다.
    @Transactional    
    // 테스트 메소드들이 모두 트랜잭션에 포함되어 최적화 되도록 합니다. 
    // 테스트 대상 함수의 실행환경에서는 Transaction이 안걸려 있을 수 있으니 실무에 사용시 주의
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  
    // 테스트 용 DB를 따로 설정하지 않고 main 환경 DB를 그대로 사용하도록 합니다.
    @Import(JPAConfiguration.class) 
    // JPAQueryFactory와 같이 테스트시 필요한 빈들을 정의해놓은 Configuration 설정합니다.
    @Rollback(value = false)
    // 테스트 데이터가 롤백되지 않고 실제 DB에 반영되도록 합니다.
    ```


## Mock(Mocito)
* 환경 구축이 어렵거나, 객체가 의존적이거나, 시간이 오래걸리는 경우 가짜 객체를 사용함.  

용어
* 테스트 더블
* 더미 객체
* 테스트 스텁
* 페이크 객체
* 테스트 스파이



### 
@WebMvcTest

```
        mockmvc.perform(put("/api/comments/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TEST_TODO_TITLE))
                .andDo(print());
```

### storming
TransactionalTestExecutionListener
ReflectionTestUtils.setField  

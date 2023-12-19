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

### TransactionalTestExecutionListener

### Mock(Mocito)
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
ReflectionTestUtils.setField  

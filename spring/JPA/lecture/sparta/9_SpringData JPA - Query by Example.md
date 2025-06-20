# 개요

- 받고싶은 예시객체를 만들어서 조건절로 사용하는 기술로 예제 객체를 가지고 쿼리를 만드는 개념
- 잘 안씀

# 구성요소

- Example
    - **Example**은 **Probe 과 ExampleMatcher** 을 하나로 합친 것
- Probe
    - **Probe**는 필드에 어떤 값들을 가지고 있는 도메인 객체
- ExampleMatcher
    - **ExampleMatcher**는 **Prove**에 들어있는 그 필드의 값들을 어떻게 쿼리할 데이터와 비교할지 정의한 것

# 기능

- 별다른 코드 생성기(QClass 같은)나 애노테이션 처리기(@Qeury같은) 필요 없이 그냥 쓰면된다.
- 도메인 객체가 수정되면 같이 반영됨 (필드나 함수를 그대로 쓰기때문에)
- 독립적인 인터페이스를 가져서 영향도가 적다.

# 제한사항

- 여러필드 조합해서 조건만드는 **nested** 또는 자식 **Collection** 제약 조건을 못 만든다.
- 문자열은 **starts/contains/ends/regex** 가 가능하고 그밖에 필드는 값이 정확히 일치해야 한다

# QueryByExampleExecutor

- Repository Interface 에 `QueryByExampleExecutor` 의존성 추가

```java
public interface UserRepository extends JpaRepository<User, Long>, QueryByExampleExecutor<User> {
    ...
}
```

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example

- 테스트코드
```java
@Test
public void queryByExampleTest() {
    // given
    var newUser = User.builder().username("user").profileImageUrl("http://").password("pass")
        .build();
    userRepository.save(newUser);

    // when
    User prove = new User();
    prove.updatePassword("pass");
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnorePaths("up", "down");
    Example<User> example = Example.of(prove, exampleMatcher);
    var users = userRepository.findAll(example);

    // then
    assert !users.isEmpty();
}
```



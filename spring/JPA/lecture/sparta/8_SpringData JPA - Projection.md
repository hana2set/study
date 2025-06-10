
# Projection의 기능

1. 원하는 필드만 지정해서 조회 가능
2. 여러필드 합쳐서 재정의 필드(Alias) 조회 가능 (Nested 프로젝션)
3. 조회 필드 그룹을 인터페이스 또는 클래스로 만들어놓고 재사용 가능

## Projection 필드 사용방법
1. get필드() 메소드로 정의
    ```java
    public interface UserProfile {
        String getUsername();

        String getProfileImageUrl();
    }
    // select username, profileImageUrl from user; // closed projection
    ```
    - 정의한 필드만 조회하기 때문에 Closed 프로젝션 이라고 한다.
    - 쿼리를 줄이므로 최적화 할 수 있다.
    - 메소드 형태이기 때문에 Java 8의 메소드를 사용해서 연산을 할 수 있다.
 
2. @Value 로 정의
    ```java
    public interface UserProfile {

        @Value('#{target.profileImageUrl != null}')
        Boolean hasProfileImage;
    }
    ```
    - 전체 필드를 조회할 수 밖에 없어서 Open 프로젝션 이라고 한다.
    - @Value(SpEL)을 사용해서 연산을 할 수 있다. 
    - 스프링 빈의 메소드도 호출 가능하다.
        ```java
        // workersHolder 는 bean 으로 등록한 contextHolder

        @Value("#{workersHolder.salaryByWorkers['George']}")
        private Integer georgeSalary;
        ```
    - 쿼리 최적화를 할 수 없다. SpEL을 엔티티 대상으로 사용하기 때문.

- [SpEL 관련 글]("https://www.baeldung.com/spring-expression-language") 참조
```text
// 출처 : Spring Expression Language Guide
// https://www.baeldung.com/spring-expression-language

Type	       Operators
Arithmetic	 +, -, *, /, %, ^, div, mod
Relational	 <, >, ==, !=, <=, >=, lt, gt, eq, ne, le, ge
Logical	     and, or, not, &&, ||, !
Conditional	 ?:
Regex	       matches
```



# Projection 구현체 정의방법

### 1. **인터페이스 기반** Projection

- Projection 을 Interface 처럼 사용하는 방법
```java
public interface UserProfile {

  String getUsername();

  String getProfileImageUrl();

  @Value("#{target.profileImageUrl != null}")
  boolean hasProfileImage();

  default String getUserInfo() {
    return getUsername() + " " + (hasProfileImage() ? getProfileImageUrl() : "");
  }
}
```
- 테스트 코드
    ```java
    // UserRepository.java

    List<UserProfile> findByUsername(String username);
    ```

    ```java
    @Test
    void projectionTest() {
        // given
        var newUser = User.builder().username("user").profileImageUrl("http://").password("pass")
            .build();

        // when
        var savedUser = userRepository.save(newUser);

        // then interface projection
        var userProfiles = userRepository.findByUsername("user");
        System.out.println("interface projection : ");
        userProfiles.forEach(userProfile -> System.out.println(userProfile.hasProfileImage()));
        assert !userProfiles.isEmpty();
    }
    ```
### 2. 클래스 기반 Projection

- Projection 을 DTO 클래스 처럼 사용하는 방법
```java
@Getter
@AllArgsConstructor
public class UserInfo {

  private String username;

  private String password;

  public String getUserInfo() {
    return username + " " + password;
  }
}
```

- 테스트 코드
    ```java
    // UserRepository.java

    List<UserInfo> findByPassword(String password);
    ```

    ```java
    @Test
    void projectionTest() {
        // given
        var newUser = User.builder().username("user").profileImageUrl("http://").password("pass")
            .build();

        // when
        var savedUser = userRepository.save(newUser);

        // then class projection
        var userInfos = userRepository.findByPassword("pass");
        System.out.println("class projection : ");
        userInfos.forEach(userInfo -> System.out.println(userInfo.getUserInfo()));
        assert !userInfos.isEmpty()
    }
    ```


### 3. 다이나믹 Projection

- Projection 클래스를 동적으로 지정해서 사용하는 방법
```java
// UserRepository.java

<T> List<T> findByProfileImageUrlStartingWith(String profileImageUrlStartWith, Class<T> type);
```
- 테스트 코드
    ```java
    @Test
    void projectionTest() {
        // given
        var newUser = User.builder().username("user").profileImageUrl("http://").password("pass")
            .build();

        // when
        var savedUser = userRepository.save(newUser);

        // then dynamic projection
        var userProfiles2 = userRepository.findByProfileImageUrlStartingWith("http", UserProfile.class);
        System.out.println("dynamic projection1 : ");
        userProfiles2.forEach(userProfile -> System.out.println(userProfile.getProfileImageUrl()));
        assert !userProfiles2.isEmpty();

        // then dynamic projection
        var userInfos2 = userRepository.findByProfileImageUrlStartingWith("http", UserInfo.class);
        System.out.println("dynamic projection2 : ");
        userInfos2.forEach(userInfo -> System.out.println(userInfo.getProfileImageUrl()));
        assert !userProfiles2.isEmpty();
    }
    ```


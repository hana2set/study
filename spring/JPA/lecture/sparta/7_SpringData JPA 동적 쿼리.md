# @DynamicInsert
Insert 쿼리를 날릴 때 null 인 값은 제외하고 쿼리문 생성.

## 적용 방법
- Entity에 `@DynamicInsert` 추가
```java
@DynamicInsert
public class User {
    ...
}
```

- 테스트 코드
    ```java
    @Test
    void dynamicInsertTest() {
        // given
        var newUser = User.builder().username("user").build();

        // when
        userRepository.save(newUser);

        // then
        // 부분 생성 쿼리
    }
    ```
    - 적용 전
    ```sql
    Hibernate: 
        insert 
        into
            users
            (password, username, id) 
        values
            (?, ?, ?)  // 141ms 소요
    ```
    - 적용 후
    ```sql
    Hibernate: 
        insert 
        into
            users
            (username, id) 
        values
            (?, ?)  // 133ms 소요
    ```

# @DynamicUpdate
Update 쿼리를 날릴 때 null인 값은 제외하고 쿼리문 생성.
## 적용 방법
- Entity에 `@DynamicUpdate` 추가
```java
@DynamicUpdate
public class User {
    ...
}
```
​
- 테스트 코드
    ```java
    @Test
    void dynamicUpdateTest() {
        // given
        var newUser = User.builder().username("user").password("password").build();
        userRepository.save(newUser);

        // when
        newUser.updatePassword("new password");
        userRepository.save(newUser);

        // then
        // 부분 수정 쿼리
    }
    ```
    - 적용 전
    ```sql
    Hibernate: 
        update
            users 
        set
            password=?,
            username=? 
        where
            id=?  // 149ms
    ```
    - 적용 후
    ```sql
    Hibernate: 
        update
            users 
        set
            password=? 
        where
            id=?  // 134ms
    ```









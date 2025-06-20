# 페이징 레파지토리
- JpaRepository 의존성
    - ListPagingAndSortingRepository
        - PagingAndSortingRepository <- Paging & Sort 기능

# 페이징 처리 프로세스

1. PageRequest 를 사용하여 Pageable에 페이징 정보를 담아 객체화 한다.
2. Pageable을 JpaRepository가 상속된 인터페이스의 메서드에 `T`(Entity)와 함꼐 파라미터로 전달한다.
3. 2번의 메서드의 return 으로 Page<`T`>가 응답 된다.
4. 응답된 Page<`T`>에 담겨진 Page 정보를 바탕으로 로직을 처리하면 된다.

# 페이징 요청/응답 클래스

## Pageable

- 요청 : `org.springframework.data.domain.Pageable`
    ```java
    PageRequest.of(int page, int size) : 0부터 시작하는 페이지 번호와 개수. 정렬이 지정되지 않음
    PageRequest.of(int page, int size, Sort sort) : 페이지 번호와 개수, 정렬 관련 정보
    PageRequest.of(int page int size, Sort sort, Direction direction, String ... props) : 0부터 시작하는 페이지 번호와 개수, 정렬의 방향과 정렬 기준 필드들
    ```
    
    - 메서드    
    ```java
    pageable.getTotalPages() : 총 페이지 수
    pageable.getTotalElements() : 전체 개수
    pageable.getNumber() : 현재 페이지 번호
    pageable.getSize() : 페이지 당 데이터 개수
    pageable.hasnext() : 다음 페이지 존재 여부
    pageable.isFirst() : 시작페이지 여부
    pageable.getContent(), PageRequest.get() : 실제 컨텐츠를 가지고 오는 메서드. getContext는 List<Entity> 반환, get()은 Stream<Entity> 반환
    ```
        
- 응답 : `org.springframework.data.domain.Page`
    - 페이징 findAll() 의 기본적인 반환 타입
    ```java
    {
        "content": [
            {"id": 1, "username": "User 0", "address": "Korea", "age": 0},
            ...
            {"id": 5, "username": "User 4", "address": "Korea", "age": 4}
        ],
        "pageable": {
            "sort": {
                "sorted": false, // 정렬 상태
                "unsorted": true,
                "empty": true
            },
            "pageSize": 5, // 페이지 크기
            "pageNumber": 0, // 페이지 번호 (0번 부터 시작)
            "offset": 0, // 해당 페이지의 첫번째 요소의 전체 순번 (다음 페이지에서는 5)
            "paged": true,
            "unpaged": false
        },
        "totalPages": 20, // 페이지로 제공되는 총 페이지 수
        "totalElements": 100, // 모든 페이지에 존재하는 총 원소 수
        "last": false,  // 마지막 페이지 여부
        "number": 0,
        "sort": {
            "sorted": false,    // 정렬 사용 여부
            "unsorted": true,
            "empty": true
        },
        "size": 5,       // Contents 사이즈
        "numberOfElements": 5,  // Contents 의 원소 수
        "first": true,   // 첫페이지 여부
        "empty": false   // 공백 여부
    }
    ```

# 반환 타입

## Page<T> 타입
- **게시판 형태의 페이징**에서 사용된다.
- 전체 요소 갯수도 함께 조회한다. (`totalElements`)
- 형식 위 참조

## **Slice<T> 타입**
- 더보기 형태의 페이징에서 사용된다.
- 전체 요소 갯수 대신 `offset` 필드로 조회할 수 있다.
    - 따라서 count 쿼리가 발생되지 않고 limit+1 조회를 한다. (offset 은 성능 안좋음)
```java
{
    "content": [
        { "id": 13, "username": "User 12", "address": "Korea", "age": 12 },
        ...
        { "id": 16, "username": "User 15", "address": "Korea", "age": 15 }
    ],
    "pageable": {
        "sort": { "sorted": false, "unsorted": true, "empty": true },
        "pageNumber": 3,
        "pageSize": 4,
        "offset": 12,
        "paged": true,
        "unpaged": false
    },
    "number": 3,
    "numberOfElements": 4,
    "first": false,
    "last": false,
    "size": 4,
    "sort": { "sorted": false, "unsorted": true, "empty": true },
    "empty": false
}
```

## **List<T> 타입**

- 전체 목록보기 형태의 페이징에서 사용된다.
- 기본 타입으로 **count 조회가 발생하지 않는다** (성능 향상)

# 정렬

## 컬럼 값으로 정렬
- Sort 클래스 사용
    ```java
    Sort sort1 = Sort.by("name").descending();     // 내림차순
    Sort sort2 = Sort.by("password").ascending();  // 오름차순
    Sort sortAll = sort1.and(sort2);      // 2개이상 다중정렬
    Pageable pageable = PageRequest.of(0, 10, sortAll);  // pageable 생성시 추가
    ```    

## 컬럼이 아닌값으로 정렬

- @Query 사용시 Alias(쿼리에서 as 로 지정한 문구) 를 기준으로 정렬할 수 있다.
    ```java
    // 아래와 같이 AS user_password 로 Alias(AS) 를 걸어주면
    @Query("SELECT u, u.password AS user_password FROM user u WHERE u.username = ?1")
    List<User> findByUsername(String username, Sort sort);
    ```
    
    ```java
    // 이렇게 해당 user_password 를 기준으로 정렬 가능
    List<User> users = findByUsername("user", Sort.by("user_password"));
    ```
        

## SQL 함수를 사용해서 정렬하기

- JpaSort 를 사용해서 쿼리 함수를 기준으로 정렬할 수 있다.
    ```java
    // 아래와 같이 일반적인 쿼리에서
    @Query("SELECT u FROM user u WHERE u.username = ?1") // 이건 없어도됨
    List<User> findByUsername(String username, Sort sort);
    ```
    
    ```java
    // 이렇게 쿼리함수 LENGTH() 조건을 걸어서 password 문자길이 기준으로 정렬 가능
    List<User> users = findByUsername("user", JpaSort.unsafe("LENGTH(password)"));
    ```
<br><br><br>

# 페이징 & 정렬 팁

## 1. List<T>가 필요하면 응답을 Page<T>로 받지말고 List<T> 로 받기
- Page<T>는 전체 count 쿼리가 추가로 발생함

## 2. Pageable 과 실제 페이지사이의 -1 문제 해결하기

- -1 처리를 중복으로 해줘야하는 이슈
- PageDTO 를 만들어서 toPageable() 메소드를 사용해보자
    
    ```java
    public class PageDTO {
      @Positive // 0보다 큰수
      private Integer currentPage;
      private Integer size;
      private String sortBy;
    
      public Pageable toPageable() {
        return PageRequest.of(currentPage-1, size, Sort.by(sortBy).descending());
      }
    }
    ```
    
    ```java
    // UserService 일부
    public List<User> findAll(PageDTO pageDTO){
    	return userRepository.findUsers(pageDTO.toPageable());
    }
    ```

## 3. Pageable 을 GET API의 요청필드로 받아오기

```java
@GetMapping("/users")
public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
}
```

- `http://localhost:8080/users?page=0`
    - 0번 페이지 부터 20개(default) 조회한다.
- `http://localhost:8080/users?page=0&size=5`
    - 0번 페이지부터 5개 조회한다.
- `http://localhost:8080/users?page=0&size=5&sort=id.desc`
    - 0번 페이지부터 5개 조회 하는데, id 의 역순으로 조회한다.


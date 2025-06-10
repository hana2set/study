
# Raw JPA 테이블 매핑 기능
## @Entity
- 객체 관점에서의 이름
- 디폴트로 클래스명으로 설정됨
- 엔티티의 이름은 JQL에서 쓰임
    - JQL : Entity 명으로 쿼리짤때 쓰이는 언어 (ex. JPQL, QueryDsl)

## @Table
- RDB 의 테이블 이름
- @Entity의 이름이 테이블의 기본값.
```java
@Entity
@Table(name = "users")
public class User () {...}
```

## @Id
- 엔티티의 주키를 맵핑할 때 사용.
- 자바의 모든 primitive 타입과 그 랩퍼 타입을 사용할 수 있음
    - Date랑 BigDecimal, BigInteger도 사용 가능.

## @GeneratedValue
- 주키의 생성 방법을 맵핑하는 애노테이션
- 생성 전략과 생성기를 설정할 수 있다.
    - 기본 전략은 AUTO: 사용하는 DB에 따라 적절한 전략 선택
    - TABLE, SEQUENCE, IDENTITY 중 하나.

## @Column
- 기본적으로 @Entity 변수에는 다 적용되어있음.
- unique
- nullable
- length
- columnDefinition

## @Temporal
- 날짜. JPA 2.1버전에선 LocalDateTime 지원 X

## @Transient
- 변수를 컬럼으로 맵핑하지 않음

<br><br>

# Raw JPA 필드 타입 매핑 기능

## Value 타입 종류
- 기본 타입
    - **`@Column`**
        - String, Date, Boolean, 과 같은 타입들에 공통으로 사이즈를 제한할 용도로 쓰인다.
        - Class 에 `@Entity` 가 붙어있으면 자동으로 필드들에 `@Column` 이 붙음
    - **`@Enumerated`**
        - Enum 매핑용도로 쓰이며 실무에서는 `@Enumerated(EnumType.*STRING*)` 으로 사용권장
        > Default 타입인 `ORDINAL` 은 0,1,2.. 값으로 들어가기 때문에 추후 순서가 바뀔 가능성있다.

-  복합 변수 타입
    - **`@Embeddable`**
    - **`@Embedded`**
    - **`@AttributeOverrides`**
    - **`@AttributeOverride`**
    ```java
    @Entity
    public class Account {
        @Id @GeneratedValue
        private Long id;

        @Embedded
        @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "home_street"))
        })
        private Address address;
    }
    ```
    ```java
    @Embeddable
    public class Address {

        private String street;

        private String city;

        private String state;

        private String zipCode;

    }
    ```

<br><br><br>

# Raw JPA 연관관계 매핑 기능

## **`@OneToOne`**
- 일대일 관계를 나타내는 매핑 정보
```java
// 일대일 양방향
@Entity
public class Member {
  @Id @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;

  private String username;

  @OneToOne
  @JoinColumn(name = "LOCKER_ID")
  private Locker locker;
}

@Entity
public class Locker {
  @Id @GeneratedValue
  @Column(name = "LOCKER_ID")
  private Long id;

  private String name;

  @OneToOne(mappedBy = "locker")
  private Member member;
```

## **`@OneToMany`**

- 일대다 관계를 나타내는 매핑 정보
- _단방향_ 으로 쓰이면 문제가 발생할 수 있다. (양방향 추천)
    - 단방향으로 매핑하면 자식 엔티티가 관리하는 외래 키가 다른 테이블에 있음 => 작업한 Entity가 아닌 다른 Entity에서 쿼리문이 나가는 경우가 있어 헷갈림
        - 양방향으로 하면 외래 키가 같은 테이블에 생김
    - 단방향으로 매핑하면 불필요한 쿼리문이 발생(update 등)
        - 양방향으로 하면 연관관계 정보를 통해 한번에 쿼리 수행
- 속도를 위해 기본적으로 **FetchType** 설정이 **LAZY** 로 설정되어 있습니다.
- 속성
    - mappedBy : 연관관계의 주인 필드를 선택한다.
    - fetch : 글로벌 페치 전략 설정
    - cascade : 영속성 전이 기능을 사용한다.
    - targetEntity : 연관된 엔티티의 타입 정보를 설정한다.

```java
// 일대다 양방향 JPA 표준 스펙이 제공하지 않음. (구현은 되나 비추)
// 대신, 다대일 양방향 관계 추천
@Entity(name = "parent")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy="parent")
    private List<Child> childList;
}

@Entity(name = "child")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
```

## **`@ManyToOne`**

- 다대일 관계를 나타내는 매핑 정보
- 속성
    - optional (default true) : false로 설정하면 연관된 엔티티가 반드시 있어야 함.
    - fetch : 글로벌 패치 전략 설정
        - 기본이 EGEAR 로 설정되어있음. 기본 LAZY로 설정하는것 추천
    - cascade : 영속성 전이 기능 사용
    - targetEntity : 연관된 엔티티의 타입 정보 설정 (targetEntity = Member.class 식으로 사용)

```java
@Entity(name = "parent")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

@Entity(name = "child")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
```

## **`@JoinColumn`**
- 외래 키 매핑 시 사용 (Join 을 요청하기 위한 매핑정보로 쓰인다.)
- 어노테이션을 생략해도 외래 키가 생성됨.
- 속성
    - name : 매핑할 외래 키의 이름
    - referencedColumnName : 외래 키가 참조하는 대상 테이블의 컬럼명
    - foreignKey : 외래 키 제약조건 지정 (테이블 생성 시에만 적용됨)
    - unique/nullable/insertable/updateable/columnDefinition/table : `@Column`의 속성과 같음


## **`@ManyToMany`**

- 다대다 관계를 나타내는 매핑 정보 (N:M)
- 다대다 설정을 하게되면 중간 매핑테이블(JoinTable)이 자동으로 생성된다.
- 중간 매핑 테이블은 JPA상에서 숨겨져서(Entity 정의 없이) 관리된다.
- 매핑 테이블 관리가 불가능해서 실무에서는 잘 사용하지 않는 기능.
    - 직접 매핑 테이블 지정 권장

```java
@Entity
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany(mappedBy = "parents")
    private List<Child> childs;
}

@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    @JoinTable(
        name = "parent_child",
        joinColumns = @JoinColumn(name = "parent_id"),
        inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    private List<Parent> parents;
}
```

## 복합키 사용하기


1. **`@IdClass`를 활용하는 복합키는 복합키를 사용할 엔티티 위에 @IdClass(식별자 클래스) 사용**
    
    ```java
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class UserChannelId implements Serializable {
        private Long user;   // UserChannel 의 user 필드명과 동일해야함
        private Long channel; // UserChannel 의 channel 필드명과 동일해야함
    
        @Override
        public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChannelId userChannelId = (UserChannelId) o;
        return Objects.equals(getUser(), userChannelId.getUser()) && Objects.equals(getChannel(), userChannelId.getChannel());
        }
    
        @Override
        public int hashCode() {
        return Objects.hash(getUser(), getChannel());
        }
    }
    ```
    
    ```java
    @Entity
    @IdClass(UserChannelId.class)
    public class UserChannel {
        ....
    
        @Id
        @ManyToOne
        @JoinColumn(name = "user_id")
        User user;
    
        @Id
        @ManyToOne
        @JoinColumn(name = "channel_id")
        Channel channel;
        ...
    }
    ```
        
2. **`@EmbeddedId`를 활용하는 복합키는 복합키 위에 @EmbeddedId 사용**
        
    ```java
    @Entity
    public class UserChannel {
    
        @EmbeddedId
        private UserChannelId userChannelId;
    
        ...
    
        @ManyToOne
        @MapsId("user_id")
        User user;
    
        @ManyToOne
        @MapsId("channel_id")
        Channel channel;
    
        ...
    
    }
    ```
    
    ```java
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Embeddable
    public class UserChannelId implements Serializable {
    
        @Column(name = "user_id")
        private Long userId;
    
        @Column(name = "channel_id")
        private Long channelId;
    
        @Override
        public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChannelId userChannelId = (UserChannelId) o;
        return Objects.equals(getUser(), userChannelId.getUser()) && Objects.equals(getChannel(), userChannelId.getChannel());
        }
    
        @Override
        public int hashCode() {
        return Objects.hash(getUser(), getChannel());
        }
    }
    ```

<br><br><br>

# Raw JPA 기타 기능

## Cascade (영속성 전이)
- 사용 위치
    - 연관관계의 주인 반대편 - 부모 엔티티
- 옵션 종류
    - ALL : 전체 상태 전이
    - PERSIST : 저장 상태 전이
    - REMOVE : 삭제 상태 전이
    - MERGE : 업데이트 상태 전이
    - REFERESH : 갱신 상태 전이
    - DETACH : 비영속성 상태 전이

## ****orphanRemoval (고아 객체 제거)****

- 사용 위치
    - `@OneToMany` 또는 `@OneToOne` 에서 사용 - 부모 엔티티
- 사용법
    - Cascade.REMOVE 와 비슷한 용도로 삭제를 전파하는데 쓰인다.
- 옵션
    - true
    - false

> Cascade.REMOVE 와 orphanRemoval 차이점
>
> Cascade.REMOVE: 부모 엔티티를 삭제할 경우 자식 엔티티 삭제  
> orphanRemoval=true: Cascade.REMOVE + 리스트 요소의 영속성 전이 포함(부모 엔티티에서 자식 엔티티를 제거할 경우 자식 엔티티 delete 쿼리)

```
영속성 전이 최강 조합 : orphanRemoval=true + Cascade.ALL

위 2개를 함께 설정하면 **자식 엔티티의 라이프 사이클이 부모 엔티티와 동일해지며, 직접 자식 엔티티의 생명주기를 관리할 수 있게 되므로 자식 엔티티의 Repository 조차 없어도 된다.** (따라서, 매핑 테이블에서 많이 쓰임) 
```


## Fetch (조회시점)

- 사용 위치
    - Entity 에 FetchType 으로 설정할 수 있다.
        - `@ElementCollection`, `@ManyToMany`, `@OneToMany`, `@ManyToOne`, `@OneToOne`
        - Query 수행시 fetch Join 을 통해서 LAZY 인 경우도 즉시 불러올 수 있다.
- 사용법
    - 기본 LAZY를 설정한 뒤에 필요할때만 fetch Join 을 수행한다.
    - 항상 같이 쓰이는 연관관계 일 경우만 EAGER 를 설정한다.
- 옵션(FetchType)
    - EAGER : 즉시 로딩 (부모 조회 시 자식도 같이 조회)
    - LAZY : 지연 로딩 (자식은 필요할때 따로 조회)
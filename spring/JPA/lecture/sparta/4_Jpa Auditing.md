# JPA Auditing

## Auditing 적용 방법
1. 메인 애플리케이션 위에 @EnableJpaAuditing 추가
    ```java
    @EnableJpaAuditing
    @SpringBootApplication
    public class Application {
    ```​
2. 엔티티 클래스 위에 @EntityListeners(AuditingEntityListener.class) 추가
    ```java
    @Getter
    @MappedSuperclass
    @EntityListeners(AuditingEntityListener.class)
    public class TimeStamp {
        @CreatedDate
        private LocalDateTime createdAt;

        @CreatedBy
        @ManyToOne
        private User createdBy;

        @LastModifiedDate
        private LocalDateTime modifiedAt;

        @LastModifiedBy
        @ManyToOne
        private User modifiedBy;
    }
    ```


<details>
  <summary>직접 구현해보기</summary>
  
## Auditing 
- 생성일시, 생성자, 수정일시, 수정자는 결국 엔티티의 영속성이 변경될때 저장한다.
- 엔티티의 영속성이 변경되는 `생성 > 수정 > 삭제`  이 흐름을 엔티티 라이프 사이클 이벤트라고 한다.
- Auditing 도 이러한 엔티티의 라이프 사이클 이벤트를 통해 구현하고있다.
- `@PrePersist`, `@PreUpdate`, `@PerRemove` 등으로 구현

```java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamp {

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  public void updateCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

  public void updateModifiedAt() {
    this.modifiedAt = LocalDateTime.now();
  }
}
​```
```java
// Entity 내 정의

  @PrePersist
  public void prePersist() {
    super.updateModifiedAt();
    super.updateCreatedAt();
  }

  @PreUpdate
  public void PreUpdate() {
    super.updateModifiedAt();
  }
```

</details>

# Introduce
```java
public record recordDto(Long id, String name, String email) {}
```

- java 14에 추가됨. 
- 필드의 유형과 이름만 필요한 **불변 데이터 클래스**
- 장점
  1. 간단함
  2. 데이터 불변
- 단점
  - **로직이 들어가면 불가능함**
    - setter가 필요하거나, 데이터 검증 로직이 필요하면 사용 불가
  - 상속, abstract 안됨
  - 멤버 변수 선언 불가

> 코드 통일성 때문에 당분간 사용 안할듯

---

https://www.baeldung.com/java-record-keyword
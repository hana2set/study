## 테이블 매핑 

```java
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Table(name = "member") // 매핑할 테이블의 이름을 지정
public class Member {

    @Id //필수, PK
    @GeneratedValue //자동생성
    @Column(name = "MEMBER_ID")
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;
    
    // length: 컬럼 길이 지정
    @Column(name = "CITY", nullable = false, length = 500)
    private String city;
    private String street;
    private String zipcode;

}
```

## @Entity
* JPA에서 테이블과 매핑하는 클래스
* 기본 생성자 필수
* final, enum, interface, inner 클래스에 사용 X
* 필드에 fianl 사용 X


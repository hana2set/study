## 기본 형태
```java
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Base {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // JPA 트랜젝션 필수
        tx.begin();

        try {

            ///////////////////////////////////
            ///////// 아래 코드 공간 ///////////
            ///////////////////////////////////

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}

```
### 입력
```java
    Member member = new Member();
    member.setId(3L);
    member.setName("Hello");

    em.persist(member);
```
<br>

### 조회
1차 캐시 - 없으면 DB에서 조회
```java
    Member findMember = em.find(Member.class, 3L);
    System.out.println(findMember.getId());
    System.out.println(findMember.getName());
```
<br>

### 쿼리
```java
List<Member> result = em.createQuery("select m from Member as m", Member.class)
                   .setFirstResult(5)
                   .setMaxResults(8)
                   .getResultList();
```
<br>

### 엔터티 분리 (준영속 상태로 변경)
```java
    //하나만 초기화
    Member findMember = em.find(Member.class, 3L);
    member.setName("aaaaaa");
    em.detach(findMember);

    //전체 초기화
    em.clear();

    //켄텍스트 제거
    em.close();
```
<br>

### 엔터티 병합 (영속 상태로 변경)
```java
    Member member = new Member();
    member.setId(3L);
    member.setName("Hello");

    // 있으면 update, 없으면 insert
    // 해당 객체 입력 X, 새롭게 생성한 객체를 입력
    Member mergedmember = em.merge(member);

    System.out.println("em.contains(member) = " + em.contains(member)); //false
    System.out.println("em.contains(mergedmember) = " + em.contains(mergedmember)); //true
```
<br>

### 플러시 모드
```java
    em.setFlushMode(FlushModeType.COMMIT)

    // FlushModeType.AUTO : 커밋이나 쿼리를 실행할 때 플러시 (기본값) 
    // FlushModeType.COMMIT : 커밋할 때만 플러시
```
<br>

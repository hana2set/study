
# 1. JPQL

(Java Persistence Query Language)

Table 이 아닌 Entity(객체) 기준으로 작성하는 쿼리를 JPQL 이라고 하며

이를 사용할 수 있도록 `EntityManger` 또는 `@Query` 구현체를 통해 JPQL 쿼리를 사용할 수 있다.

## 1-1. EntityMananger.createQuery()

- 쿼리 문자열과 Entity 를 직접 넣어서 쿼리를 작성한다.
- setParameter 와 같이 key, value 문자열을 통해서 쿼리 파라미터를 매핑 
    ```java
    @Test
    	public void testEmCreateQuery() {
    		String qlString = "select u from User u " +
    							"where u.username = :username";
    
    		Member findUser = em.createQuery(qlString, User.class)
    				.setParameter("username", "teasun")
    				.getSingleResult();
    
    		assertThat(findUser.getUsername()).isEqualTo("teasun");
    	}
    ```

## 1-2. `@Qeury` **(repository interface)**

- 테이블명이 아니라 **Entity 명**
- 변수 바인딩
    1. `?변수순번`
        
        ```java
        public interface UserRepository extends JpaRepository<User, Long> {
        
          @Query("SELECT u, u.password AS customField FROM User u WHERE u.username = ?1")
          List<User> findByUsernameWithCustomField(String username, Sort sort);
        
          @Query("SELECT u FROM User u WHERE u.username = ?1")
          List<User> findByUsername(String username, Sort sort);
        }
        ```
        
    2. `:변수명` (추천)
        
        ```java
        public interface UserRepository extends JpaRepository<User, Long> {
        
          @Query("SELECT u, u.password AS customField FROM User u WHERE u.username = :username")
          List<User> findByUsernameWithCustomField(String username, Sort sort);
        
          @Query("SELECT u FROM User u WHERE u.username = :username")
          List<User> findByUsername(String username, Sort sort);
        }
        ```
        

# 2. QueryDSL (`JPAQueryFactory`)

- Entity 의 매핑정보를 활용하여 쿼리에 적합하도록 **쿼리 전용 클래스(Q클래스)** 로 재구성해주는 기술
- 여기에 JPAQueryFactory 을 통한 **Q클래스** 를 활용할 수 있는 기능들을 제공한다.
    > - JPAQueryFactory  
    > 재구성한 **Q클래스**를 통해 문자열이 아닌 객체 또는 함수로 쿼리를 작성하고 실행하게 해주는 기술

    ```java
    @PersistenceContext
    EntityManager em;
    
    public List<User> selectUserByUsernameAndPassword(String username, String password){
        JPAQueryFactory jqf = new JPAQueryFactory(em);
        QUser user = QUser.user;
    
        List<Person> userList = jpf
                    .selectFrom(user)
                    .where(person.username.eq(username)
                        .and(person.password.eq(password))
                    .fetch();
                                    
        return userList;
    }
    ```
-  JPAQueryFactory는 빈 등록 후 사용 권장
    ```java
    // configuration 패키지안에 추가
    @Configuration
    public class JPAConfiguration {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }
    ```



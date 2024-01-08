# SpringData 쿼리

https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

## 기능

- SprintData Common 의 `CRUDRepository` + `PagingAndSortingRepository` 에서 쿼리기능을 제공

## 사용 방법

- 프로그래밍되어 제공되는 쿼리명 규칙
    - `리턴타입` {`접두어`}{`도입부`}By{`프로퍼티 표현식`}(`조건식`)[(And|Or){`프로퍼티 표현식`}(`조건식`)](OrderBy{`프로퍼티`}Asc|Desc) (`매개변수`...)
    
|||
| --- | --- |
| 접두어 | Find, Get, Query, Count, ... |
| 도입부 | Distinct, First(N), Top(N) |
| 프로퍼티 표현식 | Person.Address.ZipCode => find(Person)ByAddress_ZipCode(...) |
| 조건식 | IgnoreCase, Between, LessThan, GreaterThan, Like, Contains, ... |
| 정렬 조건 | OrderBy{프로퍼티}Asc|Desc |
| 리턴 타입 | E, Optional<E>, List<E>, Page<E>, Slice<E>, Stream<E> |
| 매개변수 | Pageable, Sort |

- 쿼리 예제

```java
// 기본
List<User> findByNameAndPassword(String name, String password);

// distinct (중복제거)
List<User> findDistinctUserByNameOrPassword(String name, String password);
List<User> findUserDistinctByNameOrPassword(String name, String password);

// ignoring case (대소문자 무시)
List<User> findByNameIgnoreCase(String name);
List<User> findByNameAndPasswordAllIgnoreCase(String name, String password);

// 정렬
List<Person> findByNameOrderByNameAsc(String name);
List<Person> findByNameOrderByNameDesc(String name);

// 페이징
Page<User> findByName(String name, Pageable pageable);  // Page 는 카운트쿼리 수행됨
Slice<User> findByName(String name, Pageable pageable); // Slice 는 카운트쿼리 수행안됨
List<User> findByName(String name, Sort sort);
List<User> findByName(String name, Pageable pageable);

// 스트림 (stream 다쓴후 자원 해제 해줘야하므로 try with resource 사용추천)
Stream<User> readAllByNameNotNull();
```
<br>

# QueryDSL

## 기능

- QueryDSL의 **`Predicate`** 인터페이스로 조건문을 여러개를 구성하여 따로 관리할 수 있다.
    - `findOne(Predicate)`, `findAll(Predicate)` 주로 이 2개 메소드가 사용된다.
        - `findOne` = Optional<T> 리턴
        - `findAll` = List<T> | Page<T> | **Iterable<T>** | Slice<T> 리턴
- **Type Safe 기능**
    - 조건문 구성시에 사용되는 객체, 필드 조건이 실제 타입과 일치한지 체크해준다.

## 장점

1. 문자가 아닌 코드로 쿼리를 작성함으로써, 컴파일 시점에 문법 오류를 쉽게 확인할 수 있다.
2. 자동 완성 등 IDE의 도움을 받을 수 있다.
3. 동적인 쿼리 작성이 편리하다.
4. 쿼리 작성 시 제약 조건 등을 메서드 추출을 통해 재사용할 수 있다.

## 원리

- QueryDSL 의존성을 추가하면 SpringData에 의해 **`QueryDslPredicateExecutor`** 인터페이스가 추가된다.
    - **`QueryDslPredicateExecutor`** 는 Repository가 QueryDsl 을 실행할 수 있는 인터페이스를 제공하는 역할을 합니다.

## 사용 방법
- Spring 2.X 버전 연동설정 (복잡함)
    ```java
    // build.gradle
    
    plugins {
        ...
        id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
    }
    
    compileQuerydsl{
        options.annotationProcessorPath = configurations.querydsl
    }
    
    configurations {
        ...
        querydsl.extendsFrom compileClasspath
    }
    
    def querydslSrcDir = 'src/querydsl/generated'
    
    querydsl {
        library = "com.querydsl:querydsl-apt"
        jpa = true
        querydslSourcesDir = querydslSrcDir
    }
    
    sourceSets {
        main {
            java {
                srcDirs = ['src/main/java', querydslSrcDir]
            }
        }
    }
    
    project.afterEvaluate {
        project.tasks.compileQuerydsl.options.compilerArgs = [
                "-proc:only",
                "-processor", project.querydsl.processors() +
                        ',lombok.launch.AnnotationProcessorHider$AnnotationProcessor'
        ]
    }
    
    dependencies {
        implementation("com.querydsl:querydsl-jpa") // querydsl
        implementation("com.querydsl:querydsl-apt") // querydsl
        ...
    }
    ```

    - QueryDSL gradle 빌드 스크립트 원리
        - Gradle 빌드시에 QueryDSL은 프로젝트 내의 **`@Entity`** 어노테이션을 선언한 클래스를 탐색하고,  **`JPAAnnotationProcessor`** 를 사용해 Q클래스를 생성
        - **`querydsl-apt`** 가 **`@Entity`** 및 **`@Id`** 등의 매핑정보 Annotation을 알 수 있도록, **`javax.persistence`** 과 **`javax.annotation`** 을 annotationProcessor에 함께 추가
            - **`annotationProcessor`** 는 Java 컴파일러 플러그인으로서, 컴파일 단계에서 어노테이션을 분석 및 처리함으로써 추가적인 파일을 생성합니다.
        - 개발 환경에서 생성된 Q클래스를 인지할 수 있도록 **/build/generated** 디렉토리를 프로젝트의 **sourceSet**에 추가합니다.


- Spring 3.X 버전 연동설정 (의존성으로 내장됨)
    ```java
    // build.gradle

    dependencies {
        ....

        // 9. QueryDSL 적용을 위한 의존성 (SpringBoot3.0 부터는 jakarta 사용해야함)
        implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
        annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
        annotationProcessor "jakarta.annotation:jakarta.annotation-api"
        annotationProcessor "jakarta.persistence:jakarta.persistence-api"
    }
    ```

## 실습
- QuerydslPredicateExecutor<Channel> 의존성 추가
```java
public interface ChannelRepository extends JpaRepository<Channel, Long>, QuerydslPredicateExecutor<Channel> {

}
```

```java
@SpringBootTest
@Transactional
@Rollback(value = false)
class ChannelJpaRepositoryTest {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    ChannelJpaRepository channelJpaRepository;

    @Test
    void queryDslTest() {
        // given
        var newChannel = Channel.builder().name("teasun").build();
        channelRepository.insertChannel(newChannel);

        Predicate predicate = QChannel
                                .channel
                                .name
                                .equalsIgnoreCase("TEASUN");

        // when
        Optional<Channel> optional = channelJpaRepository.findOne(predicate);

        // then
        assert optional.get().getName().equals(newChannel.getName());
    }
}
```

## 주의사항

기준 데이터
1. Mention 엔티티 생성
2. User - Mention - Thread 다대다 연관관계 설정
3. User, Mention 조건으로 Thread 목록 조회 쿼리수행

### Join 이 필요한 쿼리 일 경우 (불가능)

**`QuerydslPredicateExecutor`** 로는 Join 연산이 불가능하여 구현 불가능!

- 멤버 컬렉션까지만 조회 가능하며 이것을 **묵시적 조인(1 Depth 자동 조인)** 이라고 한다.
    - 즉, channel 의 threads 까지만 접근 가능한것이 묵시적 조인
- 반면에, Join 연산이 수행되는건 명시적 조인 이라고 한다. (2 Depth 이상 조인)

- 구현 테스트 코드
    ```java
    @Test
      void getMentionedThreadList() {
        // given
        var newUser = User.builder().username("new").password("1").build();
        var savedUser = userRepository.save(newUser);
        var newThread = Thread.builder().message("message").build();
        newThread.addMention(savedUser);
        threadService.insert(newThread);
    
        var newThread2 = Thread.builder().message("message2").build();
        newThread2.addMention(savedUser);
        threadService.insert(newThread2);
    
        // when
        // 모든 채널에서 내가 멘션된 쓰레드 목록 조회 기능
        var mentionedThreads = savedUser.getMentions().stream().map(Mention::getThread).toList();
    
        // then
        assert mentionedThreads.containsAll(List.of(newThread, newThread2));
      }
    ```
    
### Join 이 없는 대신 조건이 많은 쿼리 (가능)

Join 없이 조건이 많이 추가될 경우  **`QuerydslPredicateExecutor`** 활용 가능
    
- 다만 현업에서는 **JPAQueryFactory** 를 주로 씀
- 구현 테스트 코드 
    ```java
    @Service
    public class ThreadServiceImpl implements ThreadService {
    
        @Autowired
        ThreadRepository threadRepository;
    
        @Override
        public List<Thread> selectNotEmptyThreadList(Channel channel) {
            var thread = QThread.thread;
        
            // 메세지가 비어있지 않은 해당 채널의 쓰레드 목록
            var predicate = thread
                        .channel
                        .eq(channel)
                        .and(thread.message.isNotEmpty());
            var threads = threadRepository.findAll(predicate);
        
            return IteratorAdapter.asList(threads.iterator());
        }
    
        @Override
        public Thread insert(Thread thread) {
            return threadRepository.save(thread);
        }
    }
    ```
    
    ```java
    @Test
        void getNotEmptyThreadList() {
        // given
        var newChannel = Channel.builder().name("c1").type(Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var newThread = Thread.builder().message("message").build();
        newThread.setChannel(savedChannel);
        threadService.insert(newThread);
    
        var newThread2 = Thread.builder().message("").build();
        newThread2.setChannel(savedChannel);
        threadService.insert(newThread2);
    
        // when
        var notEmptyThreads = threadService.selectNotEmptyThreadList(savedChannel);
    
        // then 메세지가 비어있는 newThread2 는 조회되지 않는다.
        assert !notEmptyThreads.contains(newThread2);
        }
    ```

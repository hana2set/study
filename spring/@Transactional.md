## Propagation (전파전략)

- <details>
    <summary> Propagation 테스트 코드</summary>
    
    ```java
    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class TransTest {

        @Autowired
        Parent parent;

        @Test
        @Transactional(propagation = Propagation.NOT_SUPPORTED) // 트랜잭션 생성 방지
        public void transactionalPropagationTest() {
            parent.parentMethod(); // 부모 메서드 실행
        }
    }

    @Component
    public class Parent {

        @Autowired
        Child child;

        @Transactional
        public void parentMethod() {
            System.out.println("Parent");
            child.calledMethod();
        }
    }

    @Component
    public class Child {

        @Transactional(propagation = ?) // <- 여기에 하나씩 넣어보세요.
        public void calledMethod() {
            System.out.println("Child");
        }
    }
    ```
    </details>

<br>

1. `PROPAGATION_REQUIRED` ( JpaTransactionManager Default )
    - 부모 트랜잭션이 존재할 경우
        - 부모 트랜잭션에 참여
    - 부모 트랜잭션이 없을 경우
        - 새 트랜잭션을 시작
2. `PROPAGATION_SUPPORTS`
    - 부모 트랜잭션이 존재할 경우
        - 부모 트랜잭션에 참여
    - 부모 트랜잭션이 없을 경우
        - non-transactional 하게 동작

3. `PROPAGATION_MANDATORY`
    - 부모 트랜잭션이 존재할 경우
        - 부모 트랜잭션에 참여
    - 부모 트랜잭션이 없을 경우
        - Exception

4. `PROPAGATION_REQUIRES_NEW`
    - 부모 트랜잭션이 존재할 경우
        - 부모 트랜잭션을 중지
    - 부모 트랜잭션 유무에 상관없이 새 트랜잭션

5. `PROPAGATION_NOT_SUPPORTED`
    - 부모 트랜잭션이 존재할 경우
        - 부모 트랜잭션을 중지 시킨다.
    - 부모 트랜잭션 유무에 상관없이 트랜잭션 없음

6. `PROPAGATION_NEVER`
    - 부모 트랜잭션이 존재할 경우
        - Exception

## Isolation (격리 수준)
![1_hEpucQJzGE6K7D9M_0bEVw](https://github.com/hana2set/study/assets/97689567/eedd23fe-8bcf-4c59-8103-a6b5a14610d8)  
출처: https://velog.io/@guswns3371/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EA%B2%A9%EB%A6%AC%EC%88%98%EC%A4%80

1. `READ UNCOMMITTED` (level 0): 다른 트랜잭션에서 커밋되지 않은 내용도 참조할 수 있다
2. `READ COMMITTED` (level 1): 다른 트랜잭션에서 커밋되 내용만 참조할 수 있다.
3. `REPETABLE READ` (level 2): 트랜잭션에 진입하기 이전에 커밋된 내용만 참조할 수 있다.
4. `SERIALIZABLE` (level 3):  트랜잭션에 진입하면 락을 걸어 다른 트랜잭션이 접근하지 못하게 한다.

> `READ COMMITTED`, `REPETABLE READ` 두개가 주로 사용됨

## rollbackFor (롤백)
```java
@Transactional(rollbackFor = Exception.class)
```

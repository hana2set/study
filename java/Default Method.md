# 디폴트 메서드(Default Methods)
- JDK8에서 추가된 인터페이스 메서드.
- 인터페이스가 확장(함수 추가, 수정)될 경우, 이미 구현된 함수들이 동작하지 않는 문제를 해결하기 위해 이전 버전과 호환되는 방식으로 인터페이스를 확장하는 메커니즘으로 도입. 람다를 활용하기 위해 고려된 방법으로 추측됨.
  - 즉, 새로운 핵심 컬렉션들의 **하위 호환성을 유지하기 위해 추가**됨
- 예시> Collection 인터페이스에 정의된 removeIf 함수
    ```java
    default boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            final Iterator<E> each = iterator();
            while (each.hasNext()) {
                if (filter.test(each.next())) {
                    each.remove();
                    removed = true;
                }
            }
            return removed;
        }
    ```

#### 주의
- 구현체에 갈 영향을 고려해 설계해야함.
  - 컴파일 되더라도 런타임 에러 발생가능성 있음.
- Object에서 제공하는 메서드(equals/hashCode/toString)들은 default method로 정의해서는 안됨.
  - [제공하지 않는 이유](https://mail.openjdk.org/pipermail/lambda-dev/2013-March/008435.html) 
    - 중요한 범주가 아니라고 판단
    - 복잡함
      - 상속 모델을 복잡하게 하는 비용이 크다고 함.
      - 복수 상속 가능성 (다중상속을 하면 깨짐)
        - > 자바 상속 규칙
            > 1. 클래스 상속이 우선 (구체 클래스 우선)
            > 2. 특정 인터페이스("subtyping")가 우위를 점함
            > 3. 그 외에는 수동으로 지정해야 함
            ```java
            public class Clazz implements A, B {
                public void foo(){
                    A.super.foo();
                }
            }
            ```

---
https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html
https://www.baeldung.com/java-static-default-methods
https://dzone.com/articles/interface-default-methods-java
https://www.techempower.com/blog/2013/03/27/everything-about-java-8/
# 함수형 인터페이스
- 추상 메서드 1개만 정의된 인터페이스.
- Java 8에서 **람다식의 활용**을 위해 적극적으로 활용되기 시작
  - 이 인터페이스들의 인스턴스를 람다식으로 만듬으로서, 코드를 명확하고 간결하게 만들 수 있게됨
```java
@FunctionalInterface
interface Square {
    int calculate(int x);
}

class Geeks {
    public static void main(String args[]) {
        int a = 5;

        // lambda expression to 
        // define the calculate method
        Square s = (int x) -> x * x;

        // parameter passed and return type must be
        // same as defined in the prototype
        int ans = s.calculate(a);
        System.out.println(ans);
    }
}
```


## 표준 함수형 인터페이스 (Standard Functional Interfaces)
- 자주 사용하는 함수형 인터페이스를 정의함.
- java.util.function 패키지에 정의된 함수.
- 필요 때마다 직접 정의할 경우, 작업이 많기 때문에 직관적이고 이미 정의되어있는 해당 함수 사용 권장.
- 총 43개의 인터페이스가 존재
- <details>
    <summary>예시: BiFunction.java</summary>

    ```java
    package java.util.function;

    import java.util.Objects;

    /**
     * Represents a function that accepts two arguments and produces a result.
     * This is the two-arity specialization of {@link Function}.
     *
     * <p>This is a <a href="package-summary.html">functional interface</a>
     * whose functional method is {@link #apply(Object, Object)}.
     *
     * @param <T> the type of the first argument to the function
     * @param <U> the type of the second argument to the function
     * @param <R> the type of the result of the function
     *
     * @see Function
     * @since 1.8
     */
    @FunctionalInterface
    public interface BiFunction<T, U, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t the first function argument
         * @param u the second function argument
         * @return the function result
         */
        R apply(T t, U u);

        /**
         * Returns a composed function that first applies this function to
         * its input, and then applies the {@code after} function to the result.
         * If evaluation of either function throws an exception, it is relayed to
         * the caller of the composed function.
         *
         * @param <V> the type of output of the {@code after} function, and of the
         *           composed function
         * @param after the function to apply after this function is applied
         * @return a composed function that first applies this function and then
         * applies the {@code after} function
         * @throws NullPointerException if after is null
         */
        default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (T t, U u) -> after.apply(apply(t, u));
        }
    }

    ```

</details>

| 인터페이스 | 함수 시그니처 | 용도 | 예 |
|-|-|-|-|
| `UnaryOperator<T>` | `T apply(T t)` | 반환 값과 인수의 타입이 같은 함수, 인수는 1개 | `String::toLowerCase` |
|`BinaryOperator<T>` | `T apply(T t1, T t2)` | 반환 값과 인수의 타입이 같은 함수, 인수는 2개 | `BigInteger::add` |
| `Predicate<T>` | `boolean test(T t)` | 인수 하나를 받아 boolean을 반환하는 함수 | `Collection::isEmpty` |
| `Function<T, R>` | `R apply(T t)` | 인수와 반환 타입이 다른 함수 | `Arrays::asList` |
| `Supplier<T>` | `T get()` | 인수를 받지 않고 값을 반환(혹은 제공)하는 함수 | `Instant::now` |
| `Consumer<T>` | `void accept(T t)` | 인수 하나 받고 반환 값은 없는 함수 | `System.out::println` |

- 이름에 따라 분류, 조합 되어있음
  - 기본 이름
    1. `Operator`: 반환 값과 인수의 타입이 같은 함수
    2. `Predicate`: 인수를 받아 boolean을 반환하는 함수
    3. `Function`: 인수와 반환 타입이 다른 함수
    4. `Supplier`: 인수를 받지 않고 값을 반환하는 함수
    5. `Consumer`: 인수를 받고 반환 값은 없는 함수
  - 접두어
    1. `SrcToResult`: `Src`을 받아 `Result`를 반환 (객체일 경우 Src 생략)
    2. `Bi`: 인수를 2개씩 받음
  - 그 외
    - `BooleanSupplier`: boolean을 반환
- 예시
  - `LongToIntFunction`: long을 받아 int로 반환
  - `ToLongFunction<int[]>`: int[] 타입을 받아 long으로 반환


## 직접 만든 함수형 인터페이스
- **항상 @FunctionalInterface 애너테이션을 사용할 것**
  - 람다용으로 설계된 것임을 명시 (문서화, 오용 방지)
  - 해당 인터페이스가 추상 메서드를 오직 하나만 가지고 있어야 컴파일되도록 함 -> 실수 방지



<br>
<br>

> 참조: https://www.geeksforgeeks.org/functional-interfaces-java/
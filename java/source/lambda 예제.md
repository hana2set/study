### 익명 클래스 간략화
- before
``` java
public class Item42 {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
    }
}
```

- after 1
``` java
public class Item42 {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
    }
}
```
- after 2
```java
Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
Collections.sort(words, ComparingInt(String::length));
word.sort(ComparingInt(String::length))
```

### 인스턴스 필드에 람다로 저장 (enum 예제)
- 추상 함수를 익명 클래스로 저장하는 방식을 개선 (effective java 3e)
- before
```java
public enum Operation {
    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);
}
```


- after
```java
public enum Operation {
    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    private final String symbol;
    private final DoubleBinaryOperator operator;

    Operation(String symbol, DoubleBinaryOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return operator.applyAsDouble(x, y);
    }
}
```
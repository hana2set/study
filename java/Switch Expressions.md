JDK 13부터 (도입:JDK12) switch문을 Arrow Operator로 표현 가능.
<br>
반환값도 변수로 받을 수 있다. <br>
반환값 받으면 switch expression로 변경. switch statement와 다르게 return을 내부에서 사용할 수 없음.
<br>
<br>

* case에 복수값 입력가능 <br>
* yield 키워드 사용으로 반환값 직접 지시가 가능.
```java
String message = switch (number) {
  case ADD -> "Got a 1";
  case CANCEL-> {
          yield "Got a 2";
      }
  case DETAIL, ORDER -> {
      yield "More than 2";
  }
};
```

출처:

https://www.baeldung.com/java-yield-switch
<br>
https://www.baeldung.com/java-switch#switch-expressions

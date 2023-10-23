* 스트림 배열 생성
```java
IntStream oddNumbers = 
    IntStream.rangeClosed(10, 30)
             .filter(n -> n % 2 == 1);
```
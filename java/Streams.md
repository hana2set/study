# Streams
스트림은 컬렉션 처리를 대체하기 위해 나왔습니다.
간단한 코드로 컬렉션 처리를 간소화, 효율화할 수 있습니다.

예를 들어 컬렉션을 효율적으로 처리하려면 어떻게 해야할까요? 처리속도를 높히기 위해 멀티코어 아키텍처를 사용하는 것이 좋습니다.<br>
Stream에서는 다음과 같이 간단하게 병렬처리할 수 있습니다! _parallelStream()_
```java
List<Integer> transactionsIds = 
    transactions.parallelStream()
                .filter(t -> t.getType() == Transaction.GROCERY)
                .sorted(comparing(Transaction::getValue).reversed())
                .map(Transaction::getId)
                .collect(toList());
```

## Streams의 정의
간단하게 정의하면, "집합 작업을 지원하는 소스에 대한 구성 요소들의 나열" (a sequence of elements from a source that supports aggregate operations)이라 할 수 있습니다.

* Sequence of elements: 스트림은 특정한 구성 요소 타입로 이루어진 집합열에 인터페이스를 제공합니다. 그러나 데이터를 저장하지 않고 요청에 대한 계산만 합니다.
* Source: 스트림은 데이터가 제공되는 소스에서 사용됩니다(collections, arrays, 또는 I/O resources).
* Aggregate operations: 스트림은 함수형 프로그래밍 언어에서 제공되는 SQL-like 작업이나 공통 작업을 제공합니다(filter, map, reduce, find, match, sorted 등등).

<br>

## Streams vs Collection
Streams에는 Collection과 다음 두가지 큰 차이가 있습니다.
* Pipelining: 스트림 작업은 스트림을 반환함으로서 연결 작업을 통한 파이프라인을 구성할 수 있게합니다.  
* Internal iteration: Stream의 반복자는 백그라운드에서 작업합니다.


<details>
<summary>pipeline?</summary>

In computing, pipeline refers to the logical queue that is filled with all the instructions for the computer processor to process in parallel. It is the process of storing and queuing tasks and instructions that are executed simultaneously by the processor in an organized way.
</details>

<br>

## Streams 작업
Stream 작업에서 제공되는 인터페이스는 두가지 큰 분류로 묶입니다.
* 파이프라인을 연결할 수 있는 작업 (filter, sorted, map)
    * 중간 연산이라 합니다. 스트림을 반환합니다.
* 결과를 반환하는 작업 (collect)
    * 터미널 연산이라 합니다. 파이프라인을 닫고 결과를 생성합니다.

위 처럼 나눈 이유는 무엇일까요? 이는 스트림이 "Lazy" 작업을 통해 중간 연산을 "merged"하여 진행하기 때문입니다. __중간 연산은 터미널 작업이 호출될 때까지 수행되지 않습니다.__

```java
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> twoEvenSquares =
                numbers.stream()
                        .filter(n -> {
                            System.out.println("filtering " + n);
                            return n % 2 == 0;
                        })
                        .map(n -> {
                            System.out.println("mapping " + n);
                            return n * n;
                        })
                        .limit(3)
                        .collect(Collectors.toList());


        for (Integer num : twoEvenSquares) {
            System.out.println("num : " + num);
        }
```

위 코드에 대한 결과입니다.

```
filtering 1
filtering 2
mapping 2
filtering 3
filtering 4
mapping 4
filtering 5
filtering 6
mapping 6
num : 4
num : 16
num : 36
```

7,8에 대한 결과는 출력되지 않았고, 2가 filter 되자마자 mapping이 이루어지는 것을 볼 수 있습니다. 이는 스트림이 단락 평가(Short-circuit evaluation)로 데이터를 처리하고 있기 때문입니다.

<br>
<br>

<br>

<br><br>
출처: https://www.oracle.com/technical-resources/articles/java/ma14-java-se-8-streams.html
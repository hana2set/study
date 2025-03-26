- 스트림에서 사용되는 수집기
- 주요 수집기 팩터리: toList, toSet, toMap, groupingBy, joining
- 다운스트림 수집기: map, list, set, count, sum...


#### toMap(keyMapper, valueMapper, operator)
```java
// 각 원소가 고유한 키에 매핑되있을 경우
Map<String, Operation> stringToEnum = 
    Stream.of(values()).collect(
        toMap(Object::toString, e-> e));

// 키가 같은 경우 최대값(maxBy)을 매핑
Map<Artist, Album> topHits = 
    albums.collect(
        toMap(Album:artist, a->a, maxBy(comparing(Album::sales))));

// 마지막 값을 매핑
toMap(keyMapper, valueMapper, (oldVal, newVal) -> newVal)
```

#### groupingBy

```java
// counting 다운스트림 수집기 사용
Map<String, Long> freq = 
    words.collect(groupingBy(String::toLowerCase, counting()))
```

#### joining
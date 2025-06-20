## Optional

> [!note]
> 요약: 
> - Optional은 "return 값"에 대한 null 가능성을 표현해야함.
> - 값(매개변수)이 null임을 체크하고 싶다 -> Ojbects.requireNonNull();

<br>

optional은 악명높은 NFE와 같이 예기치 못한 오류가 발생할 수 있는 시나리오에서 __"값의 부재를 나타내기 위한 return type"__ 을 제공하는 것을 목적으로 설계되었다.

> Optional is primarily intended for use as a method return type where there is a clear need to represent "no result," and where using is likely to cause errors. A variable whose type is should never itself be ; it should always point to an instance.nullOptionalnullOptional
> 
> [Optional (Java SE 17 & JDK 17)](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Optional.html)

이 API는 명확한 설계 의도로 "좁게" 작성되어 있기에 __목적에 반하는 방법으로 사용하는 것을 지양__ 해야 한다. <br>

- [declarative 하지만 Monad 패턴은 아님](https://www.baeldung.com/java-monads) <br>
- [Optional return type을 사용하지 않는 것이 더 나은 경우가 많다](https://www.baeldung.com/java-optional-return) <br>
- [오라클 공식 문서에서 Optional을 필드로 사용한다](https://www.oracle.com/technical-resources/articles/java/java8-optional.html) <br>
- [첫 문서에는 목적에 대한 설명이 없었음](https://docs.oracle.com/javase/8/docs/api/)

이 외에도 Optional을 잘못 사용하여 발생하는 수 많은 문제들이 보이는데(성능저하, NoSuchElementException) 이런 것들을 보면 애초에 설계 시 Optional 자체를 "인스턴스인 반환 타입"으로 쓰는 것 외에는 염두하지 않았거나 의도적으로 막은 것으로 보인다. 

<br>
<br>


<details open>
<summary>참고: Optional 사용에 대한 어려움이 드러나는 글</summary>
  
  <br>
  
  __vJUG24이 제시한 Optional 스타일 규칙__<br>
  
  1. 절대 Optional 변수 혹은 반환값에 null을 사용하지 마세요.<br>  
  1. 절대 값이 있음을 증명할 수 없으면 Optional.get()를 사용하지 마세요.<br>  
  3. Optional.isPresent()이나 Optional.get() 보다 다른 대체 API들을 선호하세요.<br>  
  4. 값을 얻기 위해 Optional을 사용해 chaining methods를 만드는 것은 정말 좋지 않은 생각입니다.<br>  
  5. 중첩된 Optional 체인이 있거나 중간 결과가 Optional인 경우 너무 복잡해질 수 있습니다.<br>  
  6. 필드, 매개변수, Collections에서 Optional 사용을 피하세요. <br>  
  7. Optional을 사용하여 Collection Type(List, Set, Map)을 래핑하지 마세요. 대신, 빈 Collection을 사용하여 값이 없음을 나타내세요.<br>

  출처: https://stuartmarks.wordpress.com/2016/09/27/vjug24-session-on-optional/
</details>

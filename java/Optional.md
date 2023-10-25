## Optional

optional은 악명높은 NFE와 같이 예기치 못한 오류가 발생할 수 있는 시나리오에서 __"값의 부재를 나타내기 위한 return type"__ 을 제공하는 것을 목적으로 설계되었다.

> Optional is primarily intended for use as a method return type where there is a clear need to represent "no result," and where using is likely to cause errors. A variable whose type is should never itself be ; it should always point to an instance.nullOptionalnullOptional
> <br>
> [Optional (Java SE 17 & JDK 17)](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Optional.html)

이 API는 명확한 설계 의도로 "좁게" 작성되어 있기에 __목적에 반하는 방법으로 사용하는 것을 지양__ 해야 한다. <br>

> [declarative 하지만 Monad 패턴은 아님](https://www.baeldung.com/java-monads) <br>
> [Optional return type을 사용하지 않는 것이 더 나은 경우가 많다](https://www.baeldung.com/java-optional-return) <br>
> [오라클 공식 문서에서 Optional을 필드로 사용한다](https://www.oracle.com/technical-resources/articles/java/java8-optional.html) <br>
> [첫 문서에는 목적에 대한 설명이 없었음](https://docs.oracle.com/javase/8/docs/api/)

이 외에도 Optional을 잘못 사용하여 발생하는 수 많은 문제들이 보이는데(성능저하, NoSuchElementException) 이런 것들을 보면 애초에 설계 시 Optional 자체를 "인스턴스인 반환 타입"으로 쓰는 것 외에는 염두하지 않았거나 의도적으로 막은 것으로 보인다. 


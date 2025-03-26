# 메소드 참조 (method reference)
Java 8에서 도입된 람다 표현식은 코드를 크게 줄이고 가독성을 향상시킬 수 있으나, 경우에 따라 기존 함수를 호출하는 것 외에 아무 작업도 수행하지 않습니다.<br> 이럴 경우 __함수의 이름으로 참조__ 하는 것이 더욱 명확한데, 메소드 참조는 이를 위해 고안된 람다 표현식의 특별한 형태입니다. 

## 참조 표현식의 종류
4가지 종류가 있습니다.

|Kind|	Syntax|	Examples|
|---|---|---|
|정적 함수|	ContainingClass::staticMethodName	|Person::compareByAge <br> MethodReferencesExamples::appendStrings|
|"특정 객체"에 대한 인스턴스 함수	|containingObject::instanceMethodName	|myComparisonProvider::compareByName <br> myApp::appendStrings2|
|"특정 유형의 임의 객체"에 대한 인스턴스 함수	|ContainingType::methodName	|String::compareToIgnoreCase <br> String::concat|
|생성자	|ClassName::new	|HashSet::new|

<br>
<details>
<summary>특정 유형의 임의 객체에 대한 인스턴스 함수</summary>

 원본: an Instance Method of an Arbitrary Object of a Particular Type<br>
 
```java
String[] stringArray = { "Barbara", "James", "Mary", "John",
    "Patricia", "Robert", "Michael", "Linda" };
Arrays.sort(stringArray, String::compareToIgnoreCase);
```

위 예제에서 통해, 특정 유형(String type)에 대한 임의 객체(stringArray)의 인스턴스 함수(compareToIgnoreCase)를 표현함을 알 수 있습니다. 

</details>

<br>
<br>

- vs 람다

|Kind|	Syntax|	ㅣLambda|
|---|---|---|
|정적|	Integer::parseInt	|str -> Integer.parseInt(str)|
|한정적 (인스턴스)	|Instant.now()::isAfter	|Instant then = Instant.now(); <br> t -> then.isAfter(t) |
|비한정적 (인스턴스)	|String::toLowerCase	|String::compareToIgnoreCase <br> str -> str.toLowerCase()|
|클래스 생성자	|TreeMap::new	|() -> new TreeMap()|
|배열 생성자	|int[]::new	|len -> new int[len]|


<br>
출처: <br>
https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html <br>
https://www.baeldung.com/java-method-references<br>
https://stackoverflow.com/questions/23533345/what-does-an-arbitrary-object-of-a-particular-type-mean-in-java-8
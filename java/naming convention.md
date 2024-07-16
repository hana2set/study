https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html


https://google.github.io/styleguide/javaguide.html

- 인뎁테이션
https://www.oracle.com/java/technologies/javase/codeconventions-indentation.html


```java
someMethod(longExpression1, longExpression2, longExpression3,

        longExpression4, longExpression5);
 
var = someMethod1(longExpression1,
                someMethod2(longExpression2,
                        longExpression3));
```

```java
longName1 = longName2 * (longName3 + longName4 - longName5)+ 4 * longname6; // PREFER
longName1 = longName2 * (longName3 + longName4
- longName5) + 4 * longname6; // AVOID
```
```java
//CONVENTIONAL INDENTATION
someMethod(int anArg, Object anotherArg, String yetAnotherArg,
Object andStillAnother) {
...
}
//INDENT 8 SPACES TO AVOID VERY DEEP INDENTS
private static synchronized horkingLongMethodName(int anArg,
        Object anotherArg, String yetAnotherArg,
        Object andStillAnother) {
...
}
```java
alpha = (aLongBooleanExpression) ? beta : gamma;  alpha = (aLongBooleanExpression) ? beta
: gamma;
alpha = (aLongBooleanExpression)
? beta
: gamma;
```
큰 숫자를 변환할 경우 데이터 손실 발생


```java
long a = 9223372036854775807L;
double b = a;
System.out.println(b);
```
![image](https://github.com/hana2set/study/assets/97689567/c7885872-328e-4fbe-95d8-a2d8e76c126c)

<br>

## 원인
[자바 문서](https://docs.oracle.com/javase/specs/jls/se17/html/jls-4.html#jls-4.2.4.)에 따르면
```
The floating-point types are , whose values exactly correspond to the 32-bit IEEE 754 binary32 floating-point numbers, and , whose values exactly correspond to the 64-bit IEEE 754 binary64 floating-point numbers
```
<br>

fraction(정수부)의 길이 ( [wiki 참조](https://en.wikipedia.org/wiki/Single-precision_floating-point_format) )<br> 
float은 23bits (32-bit IEEE 754 binary32)<br>
double은 52bits (64-bit IEEE 754 binary64)<br>

int는 4byte - 31bits + sign 1bits<br>
long은 8byte - 63bits + sign 1bits<br>
<br>
__따라서 int에서 float으로 변환 시 8bits의 데이터 손실 발생__

<br>

## 주의
primary 타입의 경우, 데이터 길이로 타입을 인식하는 경우가 있음.

```java
public class Main {
    public static void main(String[] args) {
        long a = 9223372036854775807L;
        Typetester tt = new Typetester();
        tt.printType(a);
    }
}

class Typetester {
    void printType(byte x) {
        System.out.println(x + " is an byte");
    }
    void printType(int x) {
        System.out.println(x + " is an int");
    }
    void printType(float x) {
        System.out.println(x + " is an float");
    }
    void printType(double x) {
        System.out.println(x + " is an double");
    }
    void printType(char x) {
        System.out.println(x + " is an char");
    }
}
```

![image](https://github.com/hana2set/study/assets/97689567/1bdc76ac-268a-4f37-93d4-87da0f708f54)



<br>
출처
<br>
https://docs.oracle.com/javase/specs/jls/se17/html/jls-4.html#jls-4.2.4.
<br>
https://en.wikipedia.org/wiki/Single-precision_floating-point_format

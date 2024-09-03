# Static Factory Method (정적 팩토리 메소드)

해당 클래스의 인스턴스를 반환하는 방법. 일반적으로 생성자 대신 제공함으로 이점을 살림
```java
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE L Boolean.FALSE;
}
```

## 생성법

* lombok으로 쉽게 생성 가능
    ```java
        @AllArgsConstructor(staticName = "of")
        public class Book {

            private Long id;
            private String isbn;
            private String name;
            private String author;
        }
    ```

* singleton 패턴
    ```java
        class Singleton {
            private static Singleton instance;

            private Singleton() {}

            // 정적 팩토리 메서드
            public static synchronized Singleton getInstance() {
                if (instance == null) {
                    instance = new Singleton();
                }
                return instance;
            }
        }
    ```



## naming convention
* from : 하나의 매개 변수를 받아서 객체를 생성
* of : 여러개의 매개 변수를 받아서 객체를 생성
* valueOf: from과 of의 더 자세한 버전
* getInstance | instance : 인스턴스를 생성. 이전에 반환했던 것과 같을 수 있음.
* newInstance | create : 새로운 인스턴스를 생성
* get[OtherType] : 다른 타입의 인스턴스를 생성. 이전에 반환했던 것과 같을 수 있음.
* new[OtherType] : 다른 타입의 새로운 인스턴스를 생성.
* [OtherType] : getType과 newType의 간략화 버전. (예시: Collections.list(arr);)


## 장점 (vs 생성자)
1. 메서드 이름에 객체의 생성 목적을 담을 수 있음
2. 호출할 때마다 새로운 객체를 생성할 필요가 없음
3. 하위 자료형 객체를 반환 가능
4. 캡슐화 <details><summary> entity-dto간 변환에서 entity 구현을 숨길 수 있음.</summary>

    ```java
    Car carDto = CarDto.from(car); // 정적 팩토리 메서드를 쓴 경우
    CarDto carDto = new CarDto(car.getName(), car.getPosition); // 생성자를 쓴 경우
    ```
    </details>

## 단점
  1. 하위 클래스를 만들 수 없음.(상속하려면 public이나 protected 생성자를 써야하나, 정적 팩터리 메서드만 제공 시 상속 못함) 
  2. 정적 팩터리 매서드를 프러그래머가 찾기 어려움 (인스턴스 방법을 알 수 없음)
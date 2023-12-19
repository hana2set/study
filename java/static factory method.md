##

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
* getInstance | instance : 인스턴스를 생성. 이전에 반환했던 것과 같을 수 있음.
* newInstance | create : 새로운 인스턴스를 생성
* get[OtherType] : 다른 타입의 인스턴스를 생성. 이전에 반환했던 것과 같을 수 있음.
* new[OtherType] : 다른 타입의 새로운 인스턴스를 생성.
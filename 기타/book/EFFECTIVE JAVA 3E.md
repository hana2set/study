## 객체 생성과 파괴
### 1. 생성자 대신 정적(Static) 팩터리 매서드를 고려하라
- 장점
  1. 이름을 가질 수 있다.
  2. 호출때마다 인스턴스를 생성할 필요 없음.
  3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있음. (유연성)
  4. 입력 매개변수에 따라 다른 클래스의 객체 반환 가능
  5. 반환 시점에 반환할 객체의 클래스가 존재하지 않아도 된다. (ex: JDBC)
- 단점
  1. 상속하려면 public이나 protected 생성자를 써야하니, 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없음.
  2. 정적 팩터리 매서드를 프러그래머가 찾기 어려움 (인스턴스 방법을 알 수 없음으로).
     - 따라서 api 문서가 있더라도, 명명법 등으로 문제를 완화해줘야함
     - [정적 팩터리 매서드 명명방식](https://github.com/hana2set/study/blob/main/java/static%20factory%20method.md#naming-convention)

### 2. 생성자에 매개변수가 많다면 빌더를 고려하라

> 요약: **매개변수가 많거나, 많아질 것으로 예상된다면 빌더 패턴을 사용하는 것이 유리.**
> 생성자 보다 코드를 읽고 쓰기가 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.

- 생성자, 정적 팩터리 둘 다 선택적 매개변수가 많을 경우 적절하게 대응하기 어려움
- 대안
  1. 점층적 생성자 패턴: 여러 생성자로 구성 
     - 그러나 동일하게 매개변수가 많아지면 **클라이언트 코드가 읽기 어렵거나 작성하기 어려움**
  2. 자바빈즈 패턴(JavaBeans): 일반 생성자로 만든 후 setter로 매개변수 입력
     - 매서드를 여러개 호출해야함.
     - 완전히 생성되기 전까지 일관성이 무너짐
     - 디버깅이 어려움 (매개변수의 유효성을 검사하려면 직접 확인해야함)
     - **클래스를 불변으로 만들 수 없음** -> 스레드 안정성을 얻으려면 프로그래머의 추가작업 필요
     - freezing 방식으로 단점 완화 가능 -> 하지만 다루기가 너무 어려움
  3. **빌더 패턴: 자신을 반환해, 연쇄적으로 매개변수를 입력하도록 구성**
     1. **점층적 생성자 패턴의 안정성 + 자바빈즈 패턴의 가독성**
     2. ≒ 파이썬의 명명된 선택적 매개변수 
     3. 계층적 설계된 클래스에 유리 (추상 클래스에 추상 빌더, 구체 클래스에 구체 빌더)
     4. 단점은 성능에 민감한 경우 문제가 될 수 있음. (코드가 장황해서, 4개 이상의 변수일 경우 효과를 봄)
  
```java
public abstract class Pizza {
    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        // 하위 클래스는 이 메서드를 재정의(overriding)하여
        // "this"를 반환하도록 해야 한다.
        protected abstract T self();
    }
}
```
```java
public class Calzone extends Pizza{

	private final boolean sauceInside;

	public static class Builder extends Pizza.Builder<Builder> {

		private boolean sauceInside = false;

		public Builder sauceInside() {
			sauceInside = true;
			return this;
		}

    // 연쇄적인 메서드 입력을 위한 자기자신 반환
		@Override
		Calzone build() {
			return new Calzone(this);
		}

		// 연쇄적인 메서드 입력을 위한 자기자신 반환
		@Override
		protected Builder self() {
			return this;
		}

	}

  // build시 개인변 수 입력 후 생성자로 생성
	private Calzone(Builder builder) {
		super(builder);
		sauceInside = builder.sauceInside;		
	}

}
```
```java
NyPizza pizza = new NyPizza.Builder(SMALL)
        .addTopping(SAUSAGE).addTopping(ONION).build();
Calzone calzone = new Calzone.Builder()
        .addTopping(HAM).sauceInside().build();
```

### 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라
- 방식 2가지
1. public static final 필드 방식
    ```java
    public clas Elvis {
      // public
      public static final Elvis INSTANCE = new Elvis();
      private Elvis() {...}

    }
    ```
    - 장점
      - 싱글턴임이 api에 명백히 드러남
      - 간결함
2. 정적 팩터리 방식
   ```java
   public clas Elvis {
     // private
     private static final Elvis INSTANCE = new Elvis();
     private Elvis() {...}

     public static Evlis getInstance() { return INSTANCE; }
   }
   ```
  - 장점
    - api를 바꾸지 않고 싱글턴이 아니게 변경 가능
    - 제네릭 싱글턴 팩터리로 변경 가능
    - 정적 팩터리의 메서드 참조를 공급자(Sipplier<Elvis>와 같이)로 사용 가능
3. 열거 타입 방식
  ```java
  public enum Elvis {
    INSTANCE;

  }
  ```
  - 1,2 방식과 다르게 **직렬화 상황, 리플렉션 공격에서도 제 2의 인스턴스가 생기는 일을 막아줌** (대부분 상황에서 해당 방식이 가장 좋은 방법)
  - 다만, 싱글턴이 클래스를 상속해야 한다면, 불가능함.

### 4. 인스턴스화를 막으려거든 private 생성자를 사용하라
- 추상 클래스로는 못막음
- 기본 생성자가 자동으로 생성됨으로 **private 생성자를 추가해야만 에측 불가능한 인스턴스를 막을 수 있음**
- 상속을 불가능하게 하는 효과도 있음
- 코드가 비직관적일 수 있음으로 주석을 추가해주면 좋음
```java
public class UtilityClass {
  // 기본 생성자가 만들어지는 것을 막는다(인스턴스화 방지용).
  private UtilityClass() {
    throw new AssertionError(); // 개발자의 실수 방지용 (내부함수에서 호출)
  }

  ...
}
```

### 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라
의존성 객체 주입 -> **클래스의 유연성, 재사용성, 테스트 용이성**을 향상시킴
- 싱글턴, 정적 유틸리티 -> 테스트 어려움, 멀티스레드에서 쓰기 힘듬

### 6. 불필요한 객체 생성을 피하라
- 비싼 객체를 미리 생성해두기
  ```java
  public class RomanNumerals {
      // 인스턴스 생성비용이 높은 Pattern 인스턴스를 미리 생성해두고, 해당 인스턴스를 재사용
      private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})"
              + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

      static boolean isRomanNumeral(String s) {
          return ROMAN.matcher(s).matches();
      }
  }
  ```

- 오토 박싱에 주의할 것. 성능적으로 매우 안좋음
  > 오토 박싱(auto boxing)이란?
  > 기본타입과 그에 대응하는 타입을 섞어 쓸 때 자동으로 변환해주는 기술
  - 예시
    ```java
      Long a = 1L;
      long b = 2L;
      long sum = a+b; // b가 자동으로 Long 타입을 생성함
    ```
- 그러나 **item50 → 방어적 복사가 필요한 상황에서**
  - **객체를 재사용했을 때의 피해 >>> 필요없는 객체를 반복 생성했을 때의 피해**

### 7. 다 쓴 객체 참조를 해제하라
- 가비지 컬렉션 → 다 쓴 객체를 유효 범위 밖으로 밀어내면 됨
- 주의 사항
  1. 자기 메모리를 직접 관리하는 클래스
     - 프로그래머가 항시 메모리 누수를 신경써야함. (다 쓴 객체 null 처리)
  2. 캐시
     - 캐시 엔트리의 유효 기간을 정확히 정의하기 어려움 → 엔트리 가치를 떨어트리는 방식 사용
       - `Scheduler`
       - `LinkedHashMap`으로 `removeEldestEntry`메서드 활용
     - 직접 지운다면 `WaekHachMap`으로 캐시 생성 → 엔트리 삭제 시 캐시 자동으로 제거됨
  3. 리스너, 콜백
     - 재사용 안한다면 콜백을 약한 참조 사용

### 8. finalizer와 cleaner 사용을 피하라
> 요약: **안정망 역할이나 중요하지 않은 네이티브 자원 회수용으로만 사용할 것.**
> 사용할 때 불확실성과 성능 저하에 주의해야 함.
- 예측할 수 없고, 상황에 따라 위험할 수 있음.
- 실행이 GC 알고리즘에 달렸으며, GC 구현에 따라 다름.
- **제때 실행되어야 하는 작업에 절때 사용해서는 안됨**
- 대안: `AutoCloseable`
- (필요하게 되면 C쪽 진영에서 쓰일듯?)

### 9. try-finally보다는 try-with-resources를 사용하라
- 꼭 회수해야 하는 자원이면 try-with-resources 사용할 것.
- **코드가 간결해지고 예외를 다루기 쉬워짐**
  - try-finally의 미묘한 결점 > 물리적 결함으로 catch문을 탈 경우, 스택에서 에러내용이 삭제됨 (디버깅 어려움)
```java
  static void copy(String src, String dst) throws IOException {
      try (InputStream   in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst)) {
          byte[] buf = new byte[BUFFER_SIZE];
          int n;
          while ((n = in.read(buf)) >= 0)
              out.write(buf, 0, n);
      }
  }
```


## 모든 객체의 공통 메서드
- **Ojbect는 equals, hashCode, toString, clone, finalize의 재정의(overriding)를 염두하고 설계**됨

### 10. equals는 일반 규약을 지켜 재정의하라
> 요약 : 꼭 필요한 경우가 아니면 equals를 재정의하지 말자!
> 재정의해야 할 때는 그 클래스의 핵심필드를 모두 정의하고, 다섯 가지 규약을 확인할 것

- 재정의가 필요한 경우 - **논리적 동치성이 필요한 경우**
- 그 외에는 안하는 것을 추천 (잘못 정의할 경우 문제가 생김)
  - 핵심 규약 (Ojbect 명세에 적힘)
    > equals 메서드는 동치관계를 구현하며 다음을 만족해야 한다
    > - `반사성`: x.equals(x) == true
    > - `대칭성`: x.equals(y) == true 이면 y.equals(x) == true
    > - `추이성`: x.equals(y) == true, y.equals(z) == true 이면 x.equals(z) == true
    > - `일관성`: 항상 true or false
    > - `null 아님`: x.equals(null) == false
- 재정의하지 않음 - **인스턴스 자기 자신과만 같게 됨**
   - 추천 Case
     1. 각 인스턴스가 본질적으로 고유함 - Thread 등 일반적으로 동작하는 개체로 표현되는 클래스
     2. 논리적 동치성을 검사할 필요가 없음 - Pattern
     3. 상위 클래스에서 정의함 - AbstractSet, AbstractMap
     4. private 이거나 equals 메서드를 호출할 필요가 없음
- 팁! float, double 필드 비교 -> Double.compare(double, double)... (Equals 사용시 오토박싱 -> 성능상 좋지 않음)
#### 양질의 equals 구현 순서
1. == 연산자를 통해 입력이 자기 자신의 참조인지 확인
2. instanceof 연산자로 입력이 올바른 타입인지 확인
3. 입력을 올바른 타입으로 형변환
4. '핵심'필드가 일치하는 지 하나씩 검사
   
#### 주의사항
- 재정의 할 땐 hashCode도 반드시 재정의 (item 11)
- 너무 복잡하게 해결하려 하지말 것 (별칭 비교 등 쓸데없는 것 비교 X)
- 매개변수는 Object만 선언


- 반복작업임으로 ->> AutoValue 프레임워크 도입 고려!


### 11. equals를 재정의하려거든 hashCode도 재정의하라
> equals 재정의 시 반드시 hashCode도 재정의해야함. (프로그램 동작이 멈출 수 있음)
> Object API 문서에 기술된 일반 규약을 따라야 함 (equals와 동일)
> 서로 다른 인스턴스라면 되도록 다른 해시코드를 반환하도록 할 것 (성능 이슈)
> 따분한 일임으로 프레임워크(AutoValue 등) 도입 검토

- Object 명세에서 발췌한 규약
  > - equals 비교에 사용되는 정보가 변경되지 않았다면 애플리케이션이 실행되는 동안 그 객체의 hashCode 메서드는 일관되게 항상 같은 값을 반환해야함
  > - **equals(Object)가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야한다.**
  > - equals(Object)가 두 객체를 다르다고 판단했더라도, 같은 값일 필요는 없음. 다만 **다른 값을 반환해야 해시테이블 성능이 좋아짐**.
- 예를 들어 HashMap에서 키 값을 분류하는데 hashCode 값이 다르면, 같은 값을 넣어도 엉뚱한 값을 get할 수 있음.
- 항상 같은 값을 반환하면, HashMap의 성능이 O(n)에 수렴함 (해시테이블 성능이 선형)
- **hash 메서드**는 해시코드를 자동으로 계산해주지만 **성능적으로 아쉽다**.

#### 좋은 hashCode 작성 요령
1. 해당 객체의 나머지 핵심 필드 f 각각에 대해 다음 작업을 수행
   1. 기본 타입 필드라면, Type.hashCode(f)를 수행 (Type은 박싱 클래스)
   2. 참조 타입 필드면서 이 클래스의 equals 메서드가 이 필드의 equals를 재귀적으로 호출해 비교한다면, 이 필드의 hashCode를 재귀적으로 호출.
     - 계산이 복잡하면 표준형을 만들 것
     - 값이 null이면 0을 반환 (전통적인 방식) 
   3. 필드가 배열이라면, 핵심 원소 각각을 별도 필드로 다룸. (Arrays.hashCode 사용) 
     - 핵심 원소가 없으면 0을 사용
2. **위 값들을 다음 수직으로 재귀적 반환 (result = 31 * result + c;)**
   - result는 첫번째 핵심 필드의 결과, c는 다음 필드의 결과
   - 31은 전통적으로 사용함. 31 * i 는 (i << 5) -i 로 최적화 할 수 있으나 요즘은 VM에서 자동으로 해줌
   - 예시 (해시 계산비용을 줄이기 위해 캐시를 사용하고, 스레드 안정성도 고려한 방식)
      ```java
      private int hashCode;

      @Override public int hashCode() {
          int result = hashCode;
          if(result == 0) {
            int result = Integer.hashCode(areaCode);
            result = 31 * result + Integer.hashCode(prifix);
            result = 31 * result + Integer.hashCode(lineNum);
            hashCode = result;
          }
          return result;
      }
      ```
   
### 12. toString을 항상 재정의하라
- 기본값 : `클래스_이름@16진수로_표현한_해시코드`
- **해당 객체에 관한 명확하고 유용한 정보를 읽기 좋은 형태로 반환해야함**
- 사용하기 편하고 디버깅이 쉬워짐

### 13. clone 재정의는 주의해서 진행하라
- **배열의 clone 메서드는 예외, 그 외는 생성자와 팩터리를 이용하는 것이 최고!**
- 일반적으로 봤을때 Cloneable/clone 패턴의 클래스 확장 방식이 위험이 크다.
  - Clonable 클래스의 문제점
    - 복제해도 되는 클래스임을 명시하지만 clone 메서드를 제공한다는 보장이 없음
    - clone 메소드는 Object 클래스에 protect로 선언되어 있음
  - 구현 난이도, 최적화 문제
    - 상속의 경우, super.clone 사용해야함
    - 내부 필드 전체를 deepCopy 해줘야함 (기본 필드는 상관없음)
    - clone보다 복사 생성자, 복사 팩터리 패턴이 더 효용일 가능성이 높음
- final 클래스가 아니라면 성능 최적화 관점에서 검토 후 별 문제가 없을 때만 드물게 사용하는 것 권장

### 14. Comparable을 구현할지 고민하라
- 순서를 고려해야 하는 값 클래스 작성시 꼭 Comparable 인터페이스로 구현할 것
  - 정렬, 검색, 비교 기능을 제공하는 컬렉션들 사용 가능 (Arrays.sort(), TreeSet, TreeMap 등)
- **CompareTo 메서드에서 <,> 연산자 사용하지 말것**
  - 거추장스럽고, 오류 유발함
  - 대신 기본 타입 클래스가 제공하는 **정적 compare 메서드**나 **Comparator 인터페이스에서 제공하는 비교자 생성 메서드** 사용
    - `정적 compare 메서드` (String 제공 비교자 사용)
      ```java
        public final class CaseInsensitiveString 
              implements Comparable<CaseInsensitiveString> {
          public int compareTo(CaseInsensitiveString cis) {
              return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
          }
        ... // 생략
        }
      ```
    - `비교자 생성 메서드` (성능 하락 10% 정도 감수할 수 있다면)
      ```java
      public static final Comparator<PhoneNumber> COMPARATOR =
          comparingInt( (PhoneNumber pn) -> pn.areaCode)
              .thenComparingInt(pn -> pn.prefix)
              .thenComparingInt(pn -> pn.lineNum);

      public int compareTo(PhoneNumber pn) {
          return COMPARATOR.compare(this, pn);
      }
      ```

## 클래스와 인터페이스
### 15. 클래스와 멤버의 접근 권한을 최소화하라
> 요약:
> 프로그램 요소의 접근성은 가능한 한 최소로 해 public API를 설계하자.
> 그 외에는 공개하는 일이 없도록 해야한다.
> public 클래스의 인스턴스 필드는 상수용 public static final 필드 외에는 어떠한 public 필드도 가져서는 안된다.
- 소프트웨어의 근간 - 정보 은닉, 캡슐화
  > **정보은닉의 장점**
  > - 개발 속도 상승 (병렬로 개발)
  > - 시스템 관리 비용 하락 (컴포넌트 별 디버깅, 교체)
  > - 성능 최적화에 도움 (컴포넌트별 최적화 가능)
  > - 소프트웨어 재사용성을 높임
  > - 큰 시스템의 제작 난이도 낮춤
- public 클래스의 인스턴스 필드는 되도록 public 필드도 가져서는 안된다 (item.16)
  - 의도치 않게 Serializable로 공개 API가 될 수 있음
  - 일반적으로 스레드 안전하지 않음 (수정될 때 다른작업 불가)
  - **클래스에서 public static final '배열' 필드를 두거나 이 필드를 반환하는 접근자 메서드를 제공해서는 안 된다**
    - 배열 내용은 수정가능 -> 보안적 허점

### 16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라.
- public 클래스는 절때 가변 필드를 직접 노출해서는 안 된다.
  - 불변 필드는 덜 위험하지만 완전히 안심할 수 없음,
  - package-private 클래스나 private 중첩 클래스에서는 노출하는게 나을 수도 있다.
- 접근자 메서드 <- getter, setter
- **접근자를 제공함으로서** 내부 표현 방식을 언제든 바꿀 수 있는 **유연성**이 생김
  - 예를들어 외부, 클라이언트에서 public field를 사용하고 있다면 바꾸는 데 제약이 생김


### 17. 변경 가능성을 최소화하라
- 불변 클래스 예시 : String, BigInteger, 기본 타입 박싱 클래스
> 클래스 불변 조건 
> - 객체 상태 변경 메서드 X
> - 클래스 확장 불가
> - 모든 필드는 private, final
> - 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없다.
- 정리 
1. **클래스는 꼭 필요한 경우가 아니면 불변**이어야 한다.
   - setter를 만들때는 신중히
   - 성능때문에 어쩔 수 없다면 불변 클래스와 쌍을 이루는 가변 동반 클래스를 public으로 제공하도록 할 것 (ex. String과 StringBuilder)
2. 불변으로 만들 수 없다면 **가변 부분을 최소화 할 것**
   - 합당한 이유가 없다면 모든 필드는 **private final**
3. **생성자는 불변식 설정이 모두 완료된, 초기화가 완벽히 끝난 상태의 객체를 생성**해야 한다.
   - 생성자, 정적 팩터리 외에는 초기화 메서드를 public 제공 X
   - 객체를 재사용해도 성능 이점은 거의 없고 복잡성만 커짐

### 18. 상속보다는 컴포지션을 사용하라
- 상속은 강력하지만 캡슐화를 해침
- 상속은 상위 클래스의 확장이나 변경에 취약함
  - 예를 들어, 상속받은 하위 클래스에서 메서드를 재정의했지만, 상위 클래스의 내부 구현이 변했을 경우 시스템 전체가 영향을 받음
  - 상위 클래스에 추가될 새로운 메서드와 이름이 겹칠 경우 컴파일 조차 되지않음
- 따라서 해당 취약점을 극복하기 위해 **컴포지션 방식**을 권장 
  - **private 필드에 클래스의 인스턴스를 가지고 있는 새 클래스 생성** 하는 방식
  - 컴포지션 방식 예시
    - 래퍼 클래스
      ```java
      public class InstrumentedHashSetUseComposition<E> extends ForwardingSet<E> {
        private int addCount = 0;

        public InstrumentedHashSetUseComposition(Set<E> s) {
            super(s);
        }

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
      }
      ```
    - 전달 클래스
      ```java
      public class ForwardingSet<E> implements Set<E> {
        private final Set<E> s;

        public ForwardingSet(Set<E> s) { this.s = s; }
        public int size() {return 0;}
        public boolean isEmpty() {return s.isEmpty();}
        public boolean contains(Object o) {return s.contains(o);}
        public Iterator<E> iterator() {return s.iterator();}
        public Object[] toArray() {return s.toArray();}
        public <T> T[] toArray(T[] a) {return s.toArray(a);}
        public boolean add(E e) {return s.add(e);}
        public boolean remove(Object o) {return s.remove(o);}
        public boolean containsAll(Collection<?> c) {return s.containsAll(c);}
        public boolean addAll(Collection<? extends E> c) {return s.addAll(c);}
        public boolean retainAll(Collection<?> c) {return s.retainAll(c);}
        public boolean removeAll(Collection<?> c) {return s.removeAll(c);}
        public void clear() {s.clear();}

        @Override public boolean equals(Object o) {return s.equals(o);}
        @Override public int hashCode() {return s.hashCode();}
        @Override public String toString() {return s.toString();}
      }
      ```
    - 사용
      ```java
      Set<Instance> times = new InstrumentedSet<>(new TreeSet<>(cmp));
      ```

### 19. 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라
- 상속용 클래스는 설계하기가 쉽지 않다. 따라서 설계 내용을 철저히 문서화 해야한다.
  - **재정의할 수 있는 메서드를 내부적으로 어떻게 이용하는지**를 남겨야함
  - 남기지 않으면 클래스 확장, 구체 클래스 생성에서 문제를 겪을 수 있음
  - **따라서 상속을 사용하지 않는 것이 좋은 방법일 수 있음. (item.18)**
- 효율 좋은 하위 클래스를 만들 수 있도록 일부 메서드를 protected로 제공해야 할 수도 있다.
  - 그래야 하위 메서드에서 수정 가능.
  - 최소화 하는것이 좋음
- **클래스를 확장해야 할 이유가 없다면 상속을 금지시키는 것도 좋은 방법**.
  - 클래스의 변화가 생길 때마다 하위 클래스를 오작동하게 만듬.
  - 구체 클래스에 상속을 꼭 허용해야 겠다면 클래스 내부에서 재정의 가능 메서드를 사용하지 않게 만들고 문서로 남겨야함.
    - private 도우미 메서드를 만들어 해당 메서드를 호출하게 함으로 제거 가능
  - final로 선언하거나, 생성자를 접근할 수 없도록 하고 public 정적 팩터리를 사용(`item.17`)

### 20. 추상 클래스보다는 인터페이스를 우선하라
- **일반적으로 다중 구현용 타입으로는 인터페이스가 적합**
  - 복잡한 인터페이스라면 구현하는 수고를 들어주는 골격 구현을 함께 제공하는 것을 고려 (AbstractSet, AbstractMap 등 참조, 추상 클래스로 구현)
    - 골격 구현은 '가능한 한' 인터페이스의 default method 활용 (인터페이스 제약 때문에 골격 구현을 추상클래스로 많이하기 때문)
- 추상 클래스 - 다루기 까다로움
  - 기존 클래스를 덧씌울 수 없고, 클래스는 두 부모를 섬길 수 없기 때문에 어려움
  - 두 클래스 사이에 끼워 넣으려다가 계층구조의 혼란을 초래
  - java8 이후 default method가 생긴 이후 굳이?

### 21. 인터페이스는 구현하는 쪽을 생각해 설계하라
- default 메서드를 추가해 구현체에 치명적인 영향을 주는 경우가 생각보다 많음
- **default 메서드는 컴파일에 성공하더라도 구현체에서 런타임 오류가 발생할 수 있음**
- 릴리스 한 후 수정할 수 있겠지만, 가능성에 기대면 안된다. 세심한 주의가 필요
 
### 22. 인터페이스는 타입을 정의하는 용도로만 사용하라
- 인터페이스 안티패턴: 상수 인터페이스 (static final로 상수를 정의)
  - 상수는 내부 구현인데 인터페이스는 API로 노출됨
    - 오염되고 다음 릴리즈에서도 유지됨, 인터페이스에 종속될 가능성 높음
    - 열거 타입, 혹은 인스턴스화 할수 없는 유틸리티 클래스 (박싱 클래스)에 상수로 표기 (Integer.MAX_VALUE ...)

### 23. 태그 달린 클래스보다는 클래스 계층 구조를 활용하라
> *용어 설명*:
> **태그 달린 클래스**: 태그 값에 따라 동작이 달라지는 클래스
> `서브타이핑(SubTyping)`: 타입 계층을 구성하기 위해 상속을 사용하는 경우를 가르킴. **인터페이스 상속**이라고 부르기도 한다.
- **태그 달린 클래스는 계층 구조로 리펙터링을 고민할 것!**
  - 태그 달린 클래스는 클래스 계층 구조를 어설프게 흉내는 아류
  - 비효율적, 오류 발생이 쉬움, 장황함
- 변경하는 방법
  1. 태그 값에 따라 **동작이 변하는 메서드**는 루트 클래스의 **추상 메서드**로 선언
  2. **동작이 일정한 메서드**는 루트 클래스의 **일반 메서드**로 선언
  3. 공통 변수를 루트 클래스로
- <details><summary>코드 예시</summary>

  - 태그달린 클래스
    ```java
    class Figure {
      enum Shape { RECTANGLE, CIRCLE };

      // Tag field - the shape of this figure
      final Shape shape;

      // These fields are used only if shape is RECTANGLE
      double length;
      double width;
      // This field is used only if shape is CIRCLE
      double radius;

      // Constructor for circle
      Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
      }

      // Constructor for rectangle
      Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
      }

      double area() {
        switch(shape) {
          case RECTANGLE:
          return length * width;

          case CIRCLE:
          return Math.PI * (radius * radius);

          default:
          throw new AssertionError(shape);
        }
      }
    }
    ```
  - 변경안
    ```java
    // Class hierarchy replacement for a tagged class
    abstract class Figure {
      abstract double area();
    }

    class Circle extends Figure {
      final double radius;

      Circle(double radius) { this.radius = radius; }

      @Override double area() { return Math.PI * (radius * radius); }
    }

    class Rectangle extends Figure {
      final double length;
      final double width;

      Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
      }

      @Override double area() { return length * width; }
    }
    ```
    
</details>


### 24. 멤버 클래스는 되도록 static으로 만들라 (nested class 관련)
> 중첩 클래스(nested class)의 종류
> - 정적 멤버 클래스
> - (비정적) 멤버 클래스
> - 익명 클래스
> - 지역 클래스
- 멤버 클래스
  - **메서드 밖에서도 사용해야 하거나, 메서드 안에서 정의하기에 너무 길다면 사용**
  - **바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙일 것!**
    - 생략 시 바깥 인스턴스에 대한 숨은 외부 참조를 갖게됨.
      - 이 참조를 저장하기 위한 시간과 공간이 낭비
      - 바깥 클래스의 인스턴스를 GC가 수거하지 못해 메모리 누수 발생 가능성
      - 참조가 눈에 보이지않아 원인 찾기가 어려움
    - 차후 릴리스에서 static을 붙이면 하위 호환성이 깨짐.
  - 비정적 멤버 클래스 예시
    - 어뎁터[Gamma95]를 정의할 때 주로 사용
    - Map,Set,List 인터페이스의 구현체들이 뷰를 구현할 때 (Iterator)
- 익명 클래스 
  - 인스턴스를 생성하는 지점이 단 한 곳인 경우
  - 현재 `람다`가 대체함
- 지역 클래스
  - 그 외

### 25. 톱레벨 클래스는 한 파일에 하나만 담으라
- 파일 하나에 클래스 하나
- 여러 개 담고 싶다면, 정적 멤버 클래스 고려 (`item.24`)

## 제네릭 (Generic)
- JDK 5 이상
- 이전에는 꺼낼 때마다 형변환 했어야함
- **런타임에 발생하는 ClassCastException을 막아 컴파일 레벨에서 다룰 수 있게 해줌**

### 26. 로 타입은 사용하지 말라
> **제네릭 클래스**: 선언에 매개변수가 쓰인 클래스 (`List<E>`)
> **제네릭 타입**: 제네릭 클래스와 제네렉 인터페이스를 통틀어 이르는 말
> **로 타입(raw type)**: 매개변수를 전혀 사용하지 않은 제네릭 타입 (`private final List li = ...`)
- 로 타입을 쓰면 제네릭의 안정성과 표현력을 모두 잃는다.
  - 로 타입은 런타임 예외가 일어날 수 있음(내부에서 Casting 에러)
  - 제네릭이 도입되기 전 코드 호환성을 위해 제공될 뿐임.
- 불분명한 타입이 필요하다면 `Set<Object>` 나 `Set<?>` 같은 방식을 사용할 것 권장
- 예외
  1. 클래스 리터럴 타입 (`List.class`)
  2. instanceof 연산자 (`o instanceof Set`)

### 27. 비검사 경고를 제거하라
- 무시하지 말고 수정할 것. (런타임 에러의 잠재적 가능성을 뜻함)
- 방법이 없다면? `@SuppressWarnings(unchecked)`
  - 다만, **범위를 최소화하고 왜 안전한지 주석을 남겨야함**


### 28. 배열보다는 리스트를 사용하라
- 배열은 공변, 실체화
- 제네릭(리스트)는 불공변, 타입 정보 소멸 
- 상극인 관계임으로 같이 사용할 수 없다. (허용X, 허용되더라도 이점이 없음)
- 성능을 조금 손해보더라도, **런타임 안정성을 위해 제네릭 사용 추천**
> 공변: 하위 타입 관계를 유지하면서 타입이 변할 수 있음을 허용
> 
> ```java
> Object[] objectArray = new Long[1]; //공변임으로 컴파일 되나
> objectArray = "str" // 타입이 달라서 런타임 에러
> ```
- tip
  ```java
  choiceArray = (T[]) choice.toArray(); // 경고 뜸. (비추천)
  choiceList = new ArrayList<>(choice); // 타입 안정성 확보! (추천)
  ```

### 29. 이왕이면 제네릭 타입으로 만들라
- 직접 형변환보다 제네릭이 안전하고 쓰기 편함
- 즉, **형변환 없이 사용할 수 있게 만드는 것이 베스트. -> 가장 쉬운 방법이 제네릭 타입**
- 배열을 사용하는 코드를 제네릭(`E[]`)로 바꾸는 방법
  1. `Object[]`로 선언 후 강제 형변환 - (E[]) new Object[]
  2. 필드 타입을 `Object[]`로 변환
  - 각각 장단이 있음. 1.이 더 인기있으나 힙오염 가능성 있음

### 30. 이왕이면 제네릭 메서드로 만들라
형변환이 필요없는 제네릭 메서드가 안정적이고 다루기 쉬움

### 31. 한정적 와일드카드를 사용해 API 유연성을 높여라
- 와일드카드를 사용하면 복잡해지지만 훨씬 유연해짐.
- **리턴값에는 와일드카드 사용 X**
- `팩스(PECS)`: product-extends, customer-super
  - 생산자(Producer): 데이터를 읽을 때 사용하는 제네릭 타입  (list.foreach)
  - 소비자(Customer): 데이터를 쓸 때 사용하는 제네릭 타입 (list.add)
  ```java
  public static <E extends Comparable<? super E>> E max(List<? extends E> list)
  ```
  - Comparable, Comparator 모두 소비자
- **메서드 선언에 타입 매개변수가 한 번만 나오면 와일드 카드로 대체**
  - public API 인 경우 **유연성 극대화를 위해** 아래 방식이 더 좋음
    - 변경 전
      ```java
      public static <E> void swap(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
      }
      ```
    - 변경 후 (헬퍼 없으면 컴파일 에러)
      ```java
      public static void swap(List<?> list, int i, int j) {
        swalHelper(list, i, j);
      }

      // 와일드카드 타입을 실제 타입으로 바꿔주는 private 도우미 함수
      private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
      }
      ```
> 매개변수(parameter): 메서드 선언에 정의한 변수
> 인수(argument): 실제 값


### 32. 제네릭과 가변인수를 함꼐 쓸 때는 신중하라
- **가변인수 (T... a)와 제네릭은 서로 맞지 않음**. 
  - 왜냐하면 가변인수는 암시적으로 배열을 생성하는데, 배열은 타입 안정성이 떨어짐
  - 힙오염 가능성 높음
- 하지만 허용은 됨으로, **제네릭이나 매개변수화 타입의 varargs 매개변수를 받는 모든 변수에 `@SafeVarargs` 달 것** (메서드가 타입 안전함을 보장하는 애너테이션)
  - `@SafeVarargs`와 함께 다음 수칙을 지킬 것
    1. varargs 매개변수 배열에 아무것도 저장하지 말것.
    2. 그 배열을 신뢰할 수 없는 코드에 노출하지 않을 것.
        ```java
        static <T> T[] toArray(T... args) {
          return args;
        }
        ```
    3. 재정의 할 수 없는 메서드여야 함 (안전할지 보장할 수 없기 때문)

### 33. 타입 안전 이종 컨테이너를 고려하라
- 제네릭은 매개변수가 고정되어 있음. (`Set<E>`, `Map<K, V>`)
- 유연하게 해당 타입을 확장하고 싶을 때, **타입 안전 이종 컨테이너 고려**. (잘 안쓰는 듯?)
> 타입 안전 이종 컨테이너(type safe heterogeneous container) 패턴: 위 매개변수(E, K, V) 등을 키로 바꾸어 (여기서는 `Class<T>`- class의 리터럴 타입) 타입 안전하게 컨테이너를 설계하는 방식.
> 여기서 쓰이는 Class 객체를 타입 토큰이라고 함. 
> 또 직접 구현한 키를 사용할 수 있음. 예를 들어서 데이터베이스의 행(컨테이너)를 표현한다면, 제네릭 타입인 `Column<T>`를 키로 사용할 수 있음
- 예시
  ```java
  public class Favorites {
      private Map<Class<?>, Object> favorites = new HashMap<>();

      public <T> void putFavorite(Class<T> type, T instance) {
          favorites.put(Objects.requireNonNull(type), instance);
      }

      public <T> T getFavorite(Class<T> type) {
          return type.cast(favorites.get(type));
      }
  }
  ...

  <!-- 사용법 -->
  instance.putFavorite(Integer.class, 123);
  ```

## 열거 타입과 애너테이션
- 자바에 존재하는 특수한 목적의 참조 타입 2가지
  - 열거 타입(Enum, 클래스의 일종)
  - 애너테이션(Annotation, 인터페이스의 일종)
### 34. int 상수 대신 열거 타입(Enum)을 사용하라
- 기존에는 정수 상수를 클래스 안 변수로 묶어서 표현했으나, 단점이 많았음
  - 깨지기 쉽고, 문자열로 출력하기 어렵고, 컴파일 후 값 변경이 어렵고..
- 이를 극복하기 위해 **enum type 등장**
  - 사실상 final (외부에서 생성자 접근 불가)
  - 인스턴스가 하나임을 보장 (싱글턴)
  - 타입 안전성 제공 (값 비교할 때 유리함)
  - 각자의 이름공간이 있어 여러 상수가 공존 가능
  - 컴파일에 각인되지 않아 수정에 자유로움
  - 메서드 추가 가능
  - 추상 메서드를 추가하여 **상수별 메서드 구현**이 가능
- **enum을 변수로 받아 처리하는 switch 분기는 안티패턴!**
  - 신규 상수 처리가 어려워짐.
  - enum안에 상수별 메서드 추가할 것
  - 기존 열거 타입으로 다른 동작을 추가할때는(ex. inverse 함수) 유효
- 예시
  ```java
  public enum Planet {
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6);

    private final double mass;          // 질량
    private final double raduis;        // 반지름
    private final double surfaceGravity; // 표면중력

    private static final double G = 6.67300E-11; // 중력 상수

    Planet(double mass, double raduis) {
        this.mass = mass;
        this.raduis = raduis;
        this.surfaceGravity = G * mass / (raduis * raduis);
    }

    public double mass() { return mass; }
    public double radius() { return raduis; }
    public double surfaceGravity() { return surfaceGravity; }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity; // F = ma
    }
  }
  ```
- 상수별 메서드 구현 (차후 람다로 개선가능)
  ```java
  public enum Operation {
    PLUS("+")   {public double apply(double x, double y) {return x + y;}},
    MINUS("-")  {public double apply(double x, double y) {return x - y;}},
    TIMES("*")  {public double apply(double x, double y) {return x * y;}},
    DIVIDE("/") {public double apply(double x, double y) {return x / y;}};

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override public String toString() {
        return symbol;
    }
    
    public abstract double apply(double x, double y);
  }
  ```


### 35. ordinal 메서드 대신 인스턴스 필드를 사용하라
- 변경에 취약함 (추가시 변경됨)

### 36. 비트 필드 대신 EnumSet을 사용하라
- 기존 상수 값들이 집합으로 사용될 경우, **비트 필드**를 이용해 비트 연산으로 집합을 표현하곤 함.
  - ex) 1 << 0은 Bold, 1 << 1은 Italic..
- **EnumSet이 비트 필드 수준의 명료함과 성능을 제공함으로, 비트 필드를 사용할 이유가 없음**
  - 내부가 비트 벡터로 구현됨 (원소 64 이하에서 최적화)
  - 단점은 불변클래스 불가
```java
public class Text {
  public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
  public void applyStyles(Set<Style> style) { ... }
}
```
```java
text.applyStyles(EnumSet.of(Style.BOLD, Style.UNDERLINE))
```

### 37. ordinal 인덱싱 대신 EnumMap을 사용하라
- ordinal은 변경에 취약 ([#35](#35-ordinal-메서드-대신-인스턴스-필드를-사용하라))
- 성능과 가독성 같이 잡기 가능
  - 내부에 이중배열로 구성되어있음.
- ex
  ```java
  // 해당 클래스의 인덱싱이 필요하다면 (종류별로 묶는다면)
  class Plant {
    enum LifeCycle { ANNUAL, PERENNIAL, BIENNIAL }  // 생애주기

    final String name;
    final LifeCycle lifeCycle;

    Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override public String toString() {
        return name;
    }
  }
  ```
  ```java
  // 다음과 같이 EnumMap 사용할 것 (배열 대신)
  Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle =
                new EnumMap<>(Plant.LifeCycle.class);  // EnumMap
                  
  for (Plant.LifeCycle lc : Plant.LifeCycle.values())
        plantsByLifeCycle.put(lc, new HashSet<>());

  for (Plant p : garden)
        plantsByLifeCycle.get(p.lifeCycle).add(p);
        
  System.out.println(plantsByLifeCycle);
  ```
  ```java
  // 스트림으로 한다면
  System.out.println(Arrays.stream(garden)
                .collect(groupingBy(p -> p.lifeCycle,
                        () -> new EnumMap<>(LifeCycle.class), toSet())));
  }
  ```

  ```java
  // 다차원 관계 라면
  public enum Phase {
    SOLID, LIQUID, GAS;
    
    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);
        
        private final Phase from;
        private final Phase to;
        
        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        // 상전이 맵을 초기화
        private static final Map<Phase, Map<Phase, Transition>>
                m = Stream.of(values()).collect(groupingBy(t -> t.from,
                () -> new EnumMap<>(Phase.class),
                toMap(t -> t.to, t -> t,
                        (x, y) -> y, () -> new EnumMap<>(Phase.class))));
        
        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
  }
  ```

### 38. 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라
- Enum은 확장 불가
  - 열거 패턴과 다르게 Enum은 서로 상속 불가능 -> 값을 가져와서 그대로 추가 X
- 인터페이스와 enum을 같이 사용 -> 확장하는 효과 가능
```java
public interface Operation {
    double apply(double x, double y);
}
```

```java
public enum BasicOperation implements Operation {
    PLUS("+") {
        public double apply(double x, double y) { return x + y; }
    },
    MINUS("-") {
        public double apply(double x, double y) { return x - y; }
    },
    TIMES("*") {
        public double apply(double x, double y) { return x * y; }
    },
    DIVIDE("/") {
        public double apply(double x, double y) { return x / y; }
    };

    private final String symbol;

    BasicOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override public String toString() {
        return symbol;
    }
}
```

```java
public enum ExtendedOperation implements Operation {
    EXP("^") {
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        public double apply(double x, double y) {
            return x % y;
        }
    };
    private final String symbol;
    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }
    @Override public String toString() {
        return symbol;
    }
}
```


### 39. 명명 패턴보다 애너테이션을 사용하라
- 명명 패턴의 단점
  - 오타가 나면 안됨
  - 올바른 프로그램에서만 이용되란 보장이 없음
  - 프로그램 요소를 매개변수로 전달할 좋은 방도가 없음
- 애너테이션으로 해결 가능
  - 명명 패턴을 사용해야 하는 경우, **애너테이션으로 대체 불가능한 경우는 없다**
> 메타애너테이션: 애너테이션 선언에 다는 애너테이션 (e.g. @Test > @Retention, @Target)

- @Test 예제 (m.inAnnotationPresent(Test.class))

### 40. @Override 애너테이션을 일관되게 사용하라
- 안 달 이유도 없고, 달면 실수할 때 컴파일러가 바로잡아줌. 의식적으로, 일관적으로 다는 것을 추천

### 41. 정의하려는 것이 타입이라면 마커 인터페이스를 사용하라
- 타입 정의가 목적 -> 마커 인터페이스
- 마커 애너테이션도 고려할 것
  - Spring 시스템 지원을 받을 수 있다는 장점
  - 포용성은 인터페이스가 더 큼


## 람다
### 42. 익명 클래스보다는 람다를 사용하라
-  익명 클래스 -> 낡은 기법
   -  코드가 너무 길어 함수형 프로그래밍(java)에 적합하지 않음
- **람다의 모든 매개변수 타입은 생략할 것**
  - 타입을 명시해야 코드가 더 명확할 때 제외
  - 타입 추론이 너무 복잡하기 때문(제네렉 쓰는 이유랑 비슷)
- **코드 자체로 동작이 명확히 설명이 안될 경우 사용 X**
  - 문서화 불가, 이름이 없음
- **람다 직렬화 금지**
  - 인스턴스이기 때문 
    - 직렬화 형태가 구현별로(가상머신 별로) 다를 수 있음
  - private 정적 중첩 클래스로 대체 (Item 24)
- lambda 예제.md

### 43. 람다보다는 메서드 참조를 사용하라
- 메소드 참조가 더 명확하다면 메소드 참조 사용
```java
// 메소드 참조가 명확
map.merge(key, 1, Integer::sum); 
// map.merge(key, 1, (a, b) -> a+b); 

// 람다가 명확
service.execute(() -> action()); 
// service.execute(GoshThisClassNameIsHumongous::action)
```
- Method References (메소드 참조).md

### 44. 표준 함수형 인터페이스를 사용하라
- 필요할 때마다 함수형 인터페이스를 만들면 양이 많고 복잡.
  - 그래서 표준 함수형 인터페이스가 등장 (java.util.function)
- 그 외 함수형 인터페이스가 필요하다면, 정의할 것.
  - @FunctionalInterface 꼭 붙이기

> TMI: Map의 removeEldestEntry를 재정의하면 캐시로 사용할 수 있다고 함 (최근원소 100개 유지)

### 45. 스트림은 주의해서 사용하라
- 반복문 스트림 둘다 장단점이 있음으로 더 알맞은 방법으로 사용할 것
- 스트림이 알맞은 경우
  - 원소들의 시퀸스를 일관되게 변환함
  - 원소들의 시퀸스를 필터링
  - 원소들의 시퀸스를 하나의 연산으로 결합
  - 원소들의 시퀸스를 컬렉션
  - 원소들의 시퀸스에서 특정 조건의 원소를 찾음
- 부적합한 경우
  - 가독성이 무너짐
  - char 값 처리
  - 지역 변수를 사용하거나 수정해야함
  - return이나 break 등 예외 처리

### 46. 스트림에서는 부작용 없는 함수를 사용하라
- 외부 상태를 변환하는 함수를 사용하지 말 것! (**순수함수만 사용할 것**)
- **forEach 함수는 계산할 때 사용하지 말자**
  - 대놓고 반복적이라 병렬화 불가
  - 가장 "덜" 스트림다움
  - 대신 collector 클래스 활용할 것
- 수집기 사용을 권장
  - toList, toSet, toMap, groupingBy, joining

### 47. 반환 타입으로는 스트림보다 컬렉션이 낫다
- 컬렉션을 반환할 수 있으면 컬렉션이 나음
  - 코드 가독성
  - 스트림 구현보다 더 빠름
  - 사용자가 사용하기 편함
- **불가능하다면, Stream과 Iterable 중 자연스러운 것 반환할 것**
  - 메모리에 올리기에(컬렉션) 너무 큼

### 48. 스트림 병렬화는 주의해서 적용하라
- **무작정 병렬화 시도 금지** ! (더 느려질 가능성이 높음)
- 병렬화에 적합한 구조
  - 처리 규모: **10만건 이상**
  - 데이터: **ArrayList, HashMap, HashSet, ConcurrentHashMap의 인스턴스, 배열, int~long 범위**
    - 설명: 시스템 적으로 병렬화 방법이 쉬움 (참조 지역성 높음, 크기가 정해져있음)
  - 종단연산: **reduce 메서드들** (min, max, count, sum)
    - 설명: 조건에 맞으면 바로 반환되는 메서드
- 주의사항
  - Stream.iterate나 limit를 쓰면 병렬화 불가(느려짐)
  - 병렬화의 이점을 제대로 누리고자 한다면 **spliterator 메서드 반드시 재정의** (난이도 높음)

> TIP) Random은 모든 연산 동기화 -> 병렬처리 X
> -> SplittableRandom 사용 (단일 쓰레드: ThreadLocalRandom)

## 메서드
### 49. 매개변수가 유효한지 검사하라
- 문서화하고 코드 시작 부분에서 명시적으로 검사할 것. (javadoc 권장)
- Objects.requireNonNull 등 활용

### 50. 적시에 방어적 복사본을 만들라
- 클래스가 클라이언트로 받거나, 반환할 **구성요소가 가변이라면 방어적 복사본 만들기 권장**
  - 예를들어, Date는 가변임으로 클래스 외부에서 변경 가능. -> 받거나 반환할 때 새로운 인스턴스 만드는 것 추천
  - 방어적 복사에 **clone 함수 쓰지 말 것** (매개변수가 확장가능하다면 문제 발생 확률 높음)
- 다만 복사 비용이 커질 수 있음으로 내부적 합의로 그냥 사용할 수 있음

### 51. 메서드 시그니처를 신중히 설계하라
- 메서드 이름은 신중하게 (Item 68)
- 편의 메서드를 너무 많이 만들지 말 것 
  - 메서드가 많으면 복잡 및 문서화 , 유지보수 어렵
  - 아주 자주 쓰일 경우만 별도 약칭 메서드 만들기.
  - 확신이 없으면 만들지 말기.
- 매개변수 목록은 짧게 유지 (4개 이하)
  - `목록을 줄이기 위한 기술`
    1. 메서드 쪼개기 (직교성을 넓일 것 = 서로 영향이 없게 만들기 = 마이크로서비스 아미텍처)
    2. 도우미 클래스 생성
    3. 빌더 패턴
- 매개 변수 타입은 클래스보다 인터페이스 권장 
- boolean 보다는 원소 2개짜리 열거 타입이 낫다 (가독성, 확장성)
```java
Thermometer.newInstance(true) // 불명확함, 확장 어려움
Thermometer.newInstance(TemperatureScale.CELSUIS) //명확함, 추가도 쉬움
```

### 52. 다중정의(overloading, 오버로딩)는 신중히 사용하라
- 오버라이딩은 최하위 클래스에서 재정의한 메서드가 실행되지만,
- 오버로딩은 **실행 가능한** 가장 마지막 메서드가 실행됨.
- 이유는 오버로딩이 런타임에서 정적으로 호출되기 때문임
- 이는 인지에서 벗어날 수 있음. (아래 코드에서 "그 외"가 3번 출력됨)
<details>
  - <summary>코드</summary>

```java
public class CollectionClassifier {
    public static String classify(Set<?> s) {
        return "집합";
    }

    public static String classify(List<?> lst) {
        return "리스트";
    }

    public static String classify(Collection<?> c) {
        return "그 외";
    }

    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<String>(),
                new ArrayList<BigInteger>(),
                new HashMap<String, String>().values()
        };

        for (Collection<?> c : collections)
            System.out.println(classify(c)); // "그 외", "그 외", "그 외"
    }
}
```
</details>

------

- 따라서, **다중 정의가 혼동을 일으키는 상황을 피해야 함**
  - 최대한 **매개변수가 같은 다중정의는 피할 것** 
    - 이름을 다르게 하는것으로 회피 가능(writeBoolean, writeInt, ...)
  - 근본적으로 다르도록, 매개변수가 **서로 형변환 불가능한 타입으로만 구성할 것**
  - 서로 다른 함수형 인터페이스라도, 같은 위치에서 받아서는 안됨
- 이를 지키지 못해 발생한 예시
```java
// Set 클래스
set.remove(1); //remove(Object)로 처리됨으로, 원소 "1"가 삭제

// List 클래스
list.remove(1); // remove(int index)로 처리됨으로, 인덱스 1이 삭제
list.remove((Integer) 1); //remove(Object)로 처리됨으로, 원소 "1"가 삭제
```

### 53. 가변인수는 신중히 사용하라
- 인수 개수가 정해지지 않았을 때 유효하나, 성능적인 문제가 있음 (호출 시 배열 생성, 인수 없을 때 에러  -> 비용증가)
- 따라서, 필수 인수 갯수가 정해져 있거나, 대부분 적은 인수를 사용한다면, 나누는 것을 추천
```java
// 인수 3개 이상인 경우가 적다면, 이렇게 만들어서 배열 생성을 최소화.
// 0개일 때 따로 처리 가능
public void foo() { }
public void foo(int a1) { }
public void foo(int a1, int a2) { }
public void foo(int a1, int a2, int... rest) { }
```

### 54. null이 아닌, 빈 컬렉션이나 배열을 반환하라
- null이더라도 성능이 크게 좋은 것도 아님.
  - Collections.emptyList 활용할 것
  - 길이가 0인 배열을 반환하게 된다면, 미리 변수로 할당해서 반환해서 성능 최적화
  ```java
  private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];

  public Cheese[] getCheese() {
    return cheeseInStock.toArray(EMPTY_CHEESE_ARRAY);
  }

  ```

### 55. 옵셔널 반환은 신중히 하라
> 옵셔널은 값 반환 시 null일 염려가 있을 경우 사용하기에 좋음.
> 성능 저하가 뒤따르니 주의해야함.
> 반환값 이외의 용도는 거의 드뭄
- 기존에 메소드에서 값을 반환할 수 없을때, 예외 혹은 null을 반환했음.
  - 예외 반환 시 스택 추적 전체를 캡처 -> 비용이 큼
  - null 반환 시 어디선가 예외 발생 확률이 높아짐.
- 따라서 등장한 Optional.
  - 반환 타입이 Optional이라면 **null을 반환해서는 안됨** (취지에 어긋남)
  - isPresent 메서드는 대부분의 경우 대체가 가능
  - **컬렉션, 스트림, 배열, 옵셔널 같은 컨테이너 타입은 옵셔널로 감싸면 안됨**
    - `Optional<List<T>>` -> 빈 `List<T>`
    - 기본 타입은 전용 옵셔널 클래스 사용 (`OptionalInt`, `OptionalLong`)
    - **옵셔널을 컬렉션의 키, 값, 원소나 배열의 원소로 사용하는게 적절한 상황은 거의 없음**


### 56. 공개된 API 요소에는 항상 문서화 주석을 작성하라
- 올바로 문서화하려면 공개된 모든 클래스, 인터페이스, 메서드, 필드 선언에 문서화 주석을 달아야함.
- **스레드 안전 수준 반드시 포함해야함**
- 메서드용 문서화 주석
  - what을 기술할 것
    - 전제조건 (@throws, @param)
    - 사후조건 (부작용)
  - 동사구로 작성할 것
  - 제네릭 매서드는 모든 타입 매개변수에 주석 달아야함. (K,V -> 둘다 @param <K>, @param <V>)
- 그 외
  - 명사절로 작성
  - 열거 타입은 각 상수에 주석 추천
  - 에너타이션 타입은 모든 멤버에도 주석
  
> 태그 정리
> `@param`
> `@return`
> `@throws`
> `@implSpec` 해당 메서드와 하위 클래스 사이의 계약을 설명 (상속시 동작 방식)
> `<pre>{@code ... 코드 ...}</pre>` 실제 코드 적을때 유용
> `@literal` HTML 마크업이나 자바독 태그를 무시
> `@summary` (java10) 요약 태그 
> `@index` (java9) API에서 중요한 용어 색인화 가능
> `@inheritDoc` 상위 타입에서 주석의 일부 상속


## 일반적인 프로그래밍 원칙
### 57. 지역변수의 범위를 최소화하라
- 지역변수 범위를 최소화 하는 방법
  - **가장 처음 쓰일 때 선언하기**
    - 지역 변수의 범위를 줄이는 가장 강력한 기법임
    - 기존 첫머리에 선언하는 것은 C의 잔재
  - **선언과 동시에 초기화 할 것**
  - **메서드를 작게 유지하고 한 가지 기능에 집중할 것**
- 반복문에서의 지역변수
  - while보다 **for문 권장** 
    - 괄호 안에 지역변수가 갇혀 외부에서 접근 불가
    - 같은 변수를 반복해서 사용 가능

### 58. 전통적인 for 문보다는 for-each 문을 사용하라
- 명료하고, 유연하고, 버그를 예방함. 그에 따른 성능 저하는 일절 없음
- for-each가 사용 불가능한 상황
  - **파괴적인 필터링**: remove같은 메서드 호출할 경우(java8에서 Collection의 removeIf 같은 메소드로 회피 가능)
  - **변형**: 전체나 원소를 교체할 경우 인덱스가 필요
  - **병렬 반복**: 여러 컬렉션을 병렬로 순회할 경우
- `Iterable`을 구현한 객체라면 모두 사용 가능함으로, 묶음으로 표현하는 객체는 구현하는 것도 고려


### 59. 라이브러리를 익히고 사용하라
- 표준 라이브러리를 사용하면 
  - 그 코드를 작성한 전문가의 지식과 앞서 사용한 다른 프로그래머들의 경험을 활용 가능.
    - 예를 들어, java7부터 Random 함수는 ThreadLocalRandom으로 대체해야함. 꾸준히 수정되고 개선된 함수이기 때문.(고품질에 속도도 더 빠르다.)
  - 핵심적인 일과 크게 관련 없는 문제 해결에 시간을 허비하지 않아도 됨
  - 노력하지 않아도 성능이 지속해서 개선됨
  - 기능이 점점 많아짐
  - 여러 사람에게 낯익은 코드가 됨
- 따라서, **자바 표준 라이브러리 사용 -> 고품질 서드파티 라이브러리 -> 직접 구현** 순으로 개발

### 60. 정확한 답이 필요하다면 float와 double은 피하라
- 빠르고 정밀한 근사치로 설계됨
- 금융에는 `BigDecimal`, `int`, `long` 사용
  - `BigDecimal`의 단점은 느리고 쓰기 불편함
  - 따라서 소숫점을 직접 추적하거나 숫자가 너무 크지않으면 long, int 사용할 것. (최대 18자리임을 염두)

### 61. 박싱된 기본 타입보다는 기본 타입을 사용하라
||기본 타입|박싱 타입|
|-|-|-|
|식별성|X|O|
|유효한 값(null)|X|허용|
|비용(시간,메모리)|작음|큼|
- 식별성 때문에, Integer 끼리는 `==`연산자를 사용하면 안됨 (에러 발생, compare 사용할 것)
  ```java
  Comprator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);
  // 여기서 i==j는 Integer 객체 비교이기 때문에, 인지에 벗어난 결과가 나타난다.
  // naturalOrder.compare(new Integer(42), new Integer(42)) 의 결과를 우리는 0으로 생각하지만,
  // 실제로는 식별성 검사로 1로 나타난다.
  ```

> 박싱된 기본 타입은 번거로움은 줄여주지만 그 위험까지 없애주지는 않는다. (null일 경우 사용시 에러 발생..)
> 따라서, 적절하게 골라 쓰자.


### 62. 다른 타입이 적절하다면 문자열 사용을 피하라
- 문자열은 쓰기는 쉽지만 잘못사용할 경우, 번거롭고, 덜 유연하고, 느리고, 오류 가능성도 크다.
- **문자열**은, **다른 값 타입**, **열거 타입**, **혼합 타입**, **권한**을 표현하기에 적합하지 않다.
  - **혼합 타입**
    - className + # + index 같은 것
    - 파싱 과정이 포함되어, 느리고 복잡하고 번거로움.
    - 차라리 **전용 클래스**를 만들 것!

### 63. 문자열 연결은 느리니 주의하라
- `+`연산 대신 `StringBuilder` 쓰기

### 64. 객체는 인터페이스를 사용해 참조하라
- 적합한 인터페이스만 있다면, **매개변수, 반환값, 변수, 필드 전부 인터페이스 타입으로 선언**하라
  - 유연해짐
    - 예) HashMap -> EnumMap으로 바꿔서 성능 개선
- 적합한 인터페이스가 없는 경우
  - 값 클래스: `String`, `BigInteger`
  - 클래스 기반으로 작성된 프레임워크가 제공하는 객체들: `OutputStream`, `java.io`
  - 특별한 메서드를 제공하는 클래스: `PriorityQueue`

### 65. 리플렉션보다는 인터페이스를 사용하라


### 66. 네이티브 메서드는 신중히 사용하라
### 67. 최적화는 신중히 하라
### 68. 일반적으로 통용되는 명명 규칙을 따르라

## 예외
### 69. 예외는 진짜 예외 상황에만 사용하라
### 70. 복구할 수 있는 상황에는 검사 예외를, 프로그래밍 오류에는 런타임 예외를 사용하라
### 71. 필요 없는 검사 예외 사용은 피하라
### 72. 표준 예외를 사용하라
### 73. 추상화 수준에 맞는 예외를 던지라
### 74. 메서드가 던지는 모든 예외를 문서화하라
### 75. 예외의 상세 메시지에 실패 관련 정보를 담으라
### 76. 가능한 한 실패 원자적으로 만들라
### 77. 예외를 무시하지 말라

## 동시성
### 78. 공유 중인 가변 데이터는 동기화해 사용하라
### 79. 과도한 동기화는 피하라
### 80. 스레드보다는 실행자, 태스크, 스트림을 애용하라
### 81. wait와 notify보다는 동시성 유틸리티를 애용하라
### 82. 스레드 안전성 수준을 문서화하라
### 83. 지연 초기화는 신중히 사용하라
### 84. 프로그램의 동작을 스레드 스케줄러에 기대지 말라

## 직렬화
### 85. 자바 직렬화의 대안을 찾으라
### 86. Serializable을 구현할지는 신중히 결정하라
### 87. 커스텀 직렬화 형태를 고려해보라
### 88. readObject 메서드는 방어적으로 작성하라
### 89. 인스턴스 수를 통제해야 한다면 readResolve보다는 열거 타입을 사용하라
### 90. 직렬화된 인스턴스 대신 직렬화 프록시 사용을 검토하라
---

[Gamma95] GoF의 디자인 패턴
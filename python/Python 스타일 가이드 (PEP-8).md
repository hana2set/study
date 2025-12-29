출처
> https://wikidocs.net/7929  
> https://peps.python.org/pep-0008/#naming-conventions

## Naming Conventions

파이썬은 네이밍 관련 협약이 엉망이라고 함. 이를 위해 [PEP 8 – Style Guide for Python Code](https://peps.python.org/pep-0008)라는 Python 네이밍 표준 규칙을 만들고 대부분의 라이브러리는 이 표준을 따르고 있음.

### 규정 (Naming Conventions)

1. 피해야할 단일 문자 변수명 (구분 안됨됨)
   - `l`(소문자 엘) 
   - `O`(대문자 오) 
   - `I`(대문자 아이)
2. 패키지와 모듈
   - snake_case 사용. (소문자와 밑줄만 사용)
   - 고수준 C/C++ 확장 모듈은 선행 밑줄(e.g. `_socket`)을 가져야 함
3. **클래스**
   - PascalCase 사용할 것(CapWords 라고 표현함)
   - 내장 클래스는 별도 협약 있음
4. 자료형 변수(Type Variable)
   - PascalCase 사용
5. 예외 (Exception)
   - 클래스 협약(PascalCase)를 따르되, 접미사 `Error`를 붙인다.
6. 함수 
   - snake_case 사용
7. 함수, 메서드 인자
   - 인스턴스 메서드의 첫 인자는 항상 `self`
   - 클래스 메서드의 첫 인자는 항상 `cls`
   - 만약 인자명에 예약어와 충돌한다면 단일 후행 밑줄(e.g. `class_`)이 가장 낫다.
8. 메서드, 변수
   - snake_case 사용
   - 비공개 메서드와 인스턴스 변수 -> 선행 밑줄
   - 하위 클래스와 이름 충돌 -> 두개의 선행 밑줄
    > [!tip]
    >**이중 선행 밑줄은 하위 클래스로 설계된 클래스 안에 속성과 이름 충돌을 피할 목적** 으로만 사용해야함
9.  상수
    - 대문자 + 밑줄 사용
### 상속을 위한 설계
- 항상 우선적으로 `non-public`을 선택하라. 비공개를 공개로 만드는 것이 더 쉽다. 
  - python에서 'private'라는 것은 없다.
  - python에서 'protected'는 `subclass API`로 구현됨.

### 요약 
   |분류|규정|
   |-|-|
   |클래스, 자료형 변수|PascalCase|
   |예외|PascalCase**Error**|
   |함수, 변수, 인자, 패키지, 모듈|snake_case|
   |상수|UPPERCASE_WITH_UNDERSCOPE|
   |비공개 메서드, 변수|_snake_case|
   |하위 클래스(충돌시)|__PascalCase|

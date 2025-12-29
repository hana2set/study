## @classmethod
- 클래스 내에서 cls가 필요한 경우 사용.
- 주로 **생성자 패턴 (Factor)** 를 사용할 때 활용함: 대안 생성자(Alternative Constructor)
- 적극적으로 활용됨
    ```py
    d1 = datetime.date(2023, 12, 25)           # 기본 __init__
    d2 = datetime.date.today()                 # @classmethod (대안 생성자 1)
    d3 = datetime.date.fromtimestamp(123456)   # @classmethod (대안 생성자 2)
    ```
- 장점
  - 상속 시 `cls`인자를 통한 호출 클래스를 명확히 알 수 있음
  - 클래스 상태 수정을 격리

## @staticmethod
- 클래스 내에서 `self`인자가 필요 없는 경우 사용. 즉 전역 함수랑 똑같음
- 따라서 **Namespace Grouping** 외에 어떠한 의미도 없음. (전역 공간과 분리)
- pythonic하지 않기 때문에 잘 사용되지 않음(혹은 신중하게).
  - 외부에선 사용하지 않고, 논리적으로 **이 클래스의 맥락에서만 의미가 있을 때 사용**
    ```py
    class Geometry:
        @staticmethod
        def calculate_area(r):
            return 3.14 * r * r
    ```
- 장점
  - 함수 격리로 인한 전역 공간 오염 방지
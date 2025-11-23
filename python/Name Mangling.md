# 네임 맹글링(Name Mangling)
클래스 외부에서 접근하기 어렵도록 변수/함수 이름을 내부적으로 변환하는 것. 
Python은 `__name` → `_ClassName__name` 으로 변경

## 목적
- 네임스페이스 충돌 방지 (실수 방지)

## 예제
```py
class Mailer:
    def __init__(self, address):
        self.__address = address
    
    def __send_mail(self):
        print(f"Sending mail to {self.__address}")
    
    def send(self):
        self.__send_mail()
```
- 내부 접근 (O)
  ```py
  m = Mailer("test@example.com")
  m.send()
  # 출력: Sending mail to test@example.com
  ```
- 외부 접근 (X)
  ```py
  m.__send_mail()  
  # AttributeError: 'Mailer' object has no attribute '__send_mail'
  ```
  - 강제 호출
    ```py
    m._Mailer__send_mail()
    # 출력: Sending mail to test@example.com
    ```
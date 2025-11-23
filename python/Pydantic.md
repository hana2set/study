# [Pydantic](https://docs.pydantic.dev/latest/)
Python용 데이터 검증 라이브러리. 유효성 검사에 사용됨.

## 설치
```cmd
pip install pydantic
```
- 옵션: `email`, `timezone`
    ```cmd
    # with the `email` extra:
    pip install 'pydantic[email]'

    # or with `email` and `timezone` extras:
    pip install 'pydantic[email,timezone]'
    ```
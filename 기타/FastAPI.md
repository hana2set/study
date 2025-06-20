# [FastAPI](https://fastapi.tiangolo.com/ko/)란?
Python의 API 웹 프레임워크.

### FastAPI 장점
- 빠른 성능
- DI 위주 설계로 의존성 관리가 편함
- [Pydantic](https://docs.pydantic.dev/latest/) 기반 Validation이 잘 됨
- OpenAPI 기반의 문서 자동화가 효율적 -> swagger


## 설치
```cmd
pip install fastapi[standard]
```
> [!tip]
> [standard]옵션은 optional 종속성까지 추가됨. 하지만 실제로 추가 안하면 fastapi 명령어 실행 불가


## 실행

```cmd
fastapi dev main.py
```
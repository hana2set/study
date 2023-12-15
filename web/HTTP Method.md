Specification:   
[RFC9110](https://www.rfc-editor.org/rfc/rfc9110#GET)  
[RFC5789](https://www.rfc-editor.org/rfc/rfc5789) - patch

## GET
주로 정보 검색용
* response code: 200 (OK)

## POST
* response code: 사양 정해지지않음. 다 상관없음.
    * 200 (OK)
    * 206 (Partial Content)
* error recommend code
    * 304 (Not Modified)
    * 416 (Range Not Satifful)

## PUT
PUT 메소드는 대상 자원의 상태를 표현된대로 변경, 생성하기 위한 요청 (완전한 교체). 처리 정보를 __반드시(MUST)__ 알려야 하고, 제약 조건과 일치하는지 __확인해야 한다(SHOULD verify)__.

* response code: __필수(MUST)__, 멱등원
    * 200 (OK) 
    * 201 (Created)
    * 204 (No Content)
* error recommend code
    * 409 (Conflict) - 제안. 필수아님
    * 415 (Unsupported Media Type) - Content-Type 틀리면

## PATCH
PUT이 전체 자원을 대체함으로 부분 수정을 위해 PATCH 등장.  
서버에서 허용되는지 확인할 것
* response code:
    * 200 (OK)
* error recommend code
    * 400 (Bad Request)
    * 404 (Not Found)
    * 409 (Conflict)
    * 415 (Unsupported Media Type)
    * 422 (Unprocessable Entity) 

## DELETE
DELETE 메소드는 "대상 리소스와의 연결을 제거한다"는 의미가 큼. 일반적으로 규정된 삭제 메커니짐을 가진 리소스에 대해서만 DELETE 허용. 
> 리소스가 두 개면 하나만 지우거나 삭제하지 않을 수 있음.  
연결을 해제하고 비활성화만 할 수 있음.
* response code: __필수(SHOULD)__, 멱등원
    * 200 (OK) 성공적으로 수행했고 메세지에 그 이후 상태를 설명
    * 202 (Accepted) - 성공적으로 수행할 것으로 예상되나 실행하지는 않음
    * 204 (No Content) - 성공적으로 수행해 제공할 정보가 없음

### CONNECT, OPTIONS, TRACE, HEAD
<!-- 잘 안써서 별도분리 -->
* HEAD
    > GET과 동일하나 response body가 없음
* CONNECT
    > 양방향 통신을 위해 터널을 여는데에 사용
* TRACE
    > 메세지 루프백 테스트
* OPTIONS
    > 통신 옵션을 설명하는 용도



> 참고 : [return code는 멱등성에 포함되지 않음](https://stackoverflow.com/questions/24713945/does-idempotency-include-response-codes/24713946#24713946)

출처:  
https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
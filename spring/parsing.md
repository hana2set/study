## json -> object

* jackson - ObjectMapper 
    <details>
    	<summary>데이터 예시 형태</summary>
        
        {
            "id": 1632335751,
            "properties": {
                "nickname": "르탄이",
            },
            "kakao_account": {
                "profile": {
                    "nickname": "르탄이"
                },
                "has_email": true,
                "email_needs_agreement": false,
                "email": "letan@sparta.com"
            }
        }
    </details>

    * node
    ```java
            JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
            Long id = jsonNode.get("id").asLong();
            String nickname = jsonNode.get("properties")
                    .get("nickname").asText();
            String email = jsonNode.get("kakao_account")
                    .get("email").asText();
    ```
     

    * value
    ```java
        LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
    ```
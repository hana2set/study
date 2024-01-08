### spec
* JDK 17
* spring boot:3.2.1
* springdoc-openapi-starter-webmvc-ui:2.3.0'  
<br>

# 개요
* RESTful Api를 개발하고 설명하기 위한 규칙, 사양 등을 자동으로 작성해주는 오픈 소스 툴.  
* `Swagger`는 Api를 간단히 문서화하는 도구로 시작하였고 이 사양이 그룹에 채택되어 OpenAPI Specification(OAS, Swagger specification)로 리브랜딩 되었다. 그리하여 한때 Swagger와 OpenAPI가 같은 의미로 쓰였으나, 현재는 OpenAPI는 스펙 그 자체, Swagger는 OpenApi를 구현하는 도구를 의미하는 것으로 변경되었다.  
* `Springfox`는 업데이트가 중지되어 미해결된 버그가 많기때문에 `Springdoc`을 사용

<br>

# 사용법
* 의존성
    ```java
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'
    ```
* 설정 파일
    ```java
    import io.swagger.v3.oas.annotations.info.Contact;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import io.swagger.v3.oas.annotations.OpenAPIDefinition;
    import io.swagger.v3.oas.annotations.info.Info;
    import io.swagger.v3.oas.models.Components;
    import io.swagger.v3.oas.models.OpenAPI;
    import io.swagger.v3.oas.models.security.SecurityRequirement;
    import io.swagger.v3.oas.models.security.SecurityScheme;
    import lombok.RequiredArgsConstructor;

    @OpenAPIDefinition(
        info = @Info(
                title = "Trello Project Api Document",
                description = "트렐로 프로젝트 API 문서",
                version = "v1",
                contact = @Contact(
                        name = "dev"
                )
        )
    )
    @RequiredArgsConstructor
    @Configuration
    public class SwaggerConfig {
        private static final String SECURITY_SCHEME_NAME = "Authorization";

        @Bean
        public OpenAPI openAPI() {
            return new OpenAPI()
                .components(new Components()
                    .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
        }
    }
    ```

* 대상 컨트롤러
    ```java
    import com.example.trelloproject.global.dto.CommonResponseDTO;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.Parameter;
    import io.swagger.v3.oas.annotations.media.Content;
    import io.swagger.v3.oas.annotations.media.ExampleObject;
    import io.swagger.v3.oas.annotations.media.Schema;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import com.example.trelloproject.user.dto.LoginDTO;
    import com.example.trelloproject.user.dto.SignupDTO;
    import com.example.trelloproject.user.service.UserService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @Tag(name = "유저 API", description = "유저 관련 API")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("v1/users")
    public class UserController {

        private final UserService userService;

        @Operation(summary = "회원가입", description = "회원가입을 합니다.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "회원가입 성공",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ResponseEntity.class)) }),
                @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
                @ApiResponse(responseCode = "400", description = "중복된 데이터") })
        @Parameter(name = "signupDTO", 
                description = "회원가입 request dto",
                examples = {
                    @ExampleObject(
                        name = "성공 예제",
                        description = "회원가입에 성공",
                        value = """
                                {
                                "username": "testUser",
                                "password": "1234567890",
                                "email": "email@test.com"
                                }
                                """
                    ),
                    @ExampleObject(
                            name = "실패 예제 - 유효성 검사 실패",
                            description = "이름이 유효성 검사를 만족하지 못함.",
                            value = """
                            {
                            "username": "test",
                            "password": "1234567890",
                            "email": "email@test.com"
                            }
                            """
                    )
                }
        )
        @PostMapping("/signup")
        public ResponseEntity<ResponseDto> signup(@Valid SignupDto signupDto){
            CommonResponseDTO<ResponseDto> responseDto = userService.signup(signupDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }
    ```

* 모델
    ```java
    @Schema(description = "로그인 DTO")
    @Getter
    public class LoginResponseDto {
        @Schema(description = "아이디")
        private Long id;

        @Schema(description = "이름")
        private String name;

        @Schema(description = "유저 역할", defaultValue = "USER", allowableValues = {"USER", "ADMIN"})
        private UserRole role;

        @Schema(description = "수정일자")
        private LocalDateTime modifiedDate;

        public LoginResponseDto(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.role = user.getRole();
            this.modifiedDate = user.getModifiedDate();
        }
    }
    ```
<br>
<br>

# Annotation 
상세 내용은 아래 링크 참조
* [swagger-annotations 2.2.20 API]("https://javadoc.io/static/io.swagger.core.v3/swagger-annotations/2.2.20/index.html")
* [Swagger-2.X---Annotations]("https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations")
### OpenAPI
|Name|	Description|
|-|-|
|@OpenAPIDefinition	|OpenAPI 정의|
|@Info	|OpenAPI 정의에 대한 정보 |
|@Contact	|OpenAPI 정의에 대한 정보 중 컨텍스트 속성에 대한 정보|

### Operations
|Name|	Description|
|-|-|
|@Operation	|HTTP 메소드 설명|
|@Parameter	|@Operation의 단일 매개 변수|
|@RequestBody	|@Operation의 요청 본문|
|@ApiResponse	|@Operation의 응답|
|@Tag	|@Operation나 OpenAPI 정의에 대한 태그|
|@Server	|@Operation나 OpenAPI 정의에 대한 서버|
|@Callback	|request 집합을 표현|
|@Link	|응답에 사용할 수 있는 디자인 타임(design-time) 링크를 표현|

### Media
|Name	|Description|
|-|-|
|@Schema	|입출력 데이터 정의|
|@ArraySchema	|배열 형식에 대한 입출력 데이터 정의|
|@Content	|특정 미디어 유형에 대한 스키마와 예제 제공|



출처:  
https://springdoc.org/  
https://velog.io/@banjjoknim/Swagger  
https://www.baeldung.com/spring-rest-openapi-documentation  
https://javadoc.io/doc/io.swagger.core.v3/swagger-annotations/latest/index.html  
https://javadoc.io/static/io.swagger.core.v3/swagger-annotations/2.2.20/index.html  
https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui  
https://github.com/springdoc/springdoc-openapi  
https://github.com/swagger-api/swagger-ui  
https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations  


https://oingdaddy.tistory.com/272  
https://gruuuuu.github.io/programming/openapi/  
https://jeonyoungho.github.io/posts/Open-API-3.0-Swagger-v3-%EC%83%81%EC%84%B8%EC%84%A4%EC%A0%95/  
https://blog.jiniworld.me/91  
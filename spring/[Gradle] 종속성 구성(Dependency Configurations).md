
## 종속성 구성
종속성 집합에 범위를 정의하는 옵션 

```gradle
dependencies {
    implementation("com.google.guava:guava:30.0-jre")   // Needed to compile and run the app
    runtimeOnly("org.slf4j:slf4j-simple:2.0.13")        // Only needed at runtime
}
```


| 구성            | 설명                                                                 |
|----------------------|----------------------------------------------------------------------|
| api                  | 컴파일과 런타임, 공개 API에 dependency 포함됨.   |
| implementation       | 컴파일과 런타임                            |
| compileOnly          | 컴파일에만     |
| compileOnlyApi       | 컴파일에만, 공개 API에 dependency 포함됨.                |
| runtimeOnly          | 컴파일 클래스 경로에 포함되지 않고 런타임에만 필요       |
| testImplementation   | 테스트 컴파일, 실행                   |
| testCompileOnly      | 테스트 컴파일                                 |
| testRuntimeOnly      | 테스트 실행                                   |

> [!note]
> **api와와 implementation 차이 요약**  
> 
> api는 "외부에 노출되는 종속성"을 지정하여 추이 종속성을 허락함.  
> 즉, 이 모듈을 public API로 가정하기 떄문에, 외부에서 해당 모듈에 직접 접근이 가능하다는 의미.   
> 다만 내부에서만 사용되는 implementation과 다르게 의존성이 복잡해지고(캡슐화 불가) 컴파일 성능이 느려지기 때문에 (빌드 시 전체 다시 빌드) **implementation 사용을 권장함**
> 

---


출처: https://docs.gradle.org/current/userguide/dependency_configurations.html
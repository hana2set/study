# JPA 테스트 클래스 어노테이션

```java
@SpringBootTest   
// SpringApplication 띄울때의 빈들을 모두 생성해줍니다.
@DataJpaTest      
// SpringDataJpa 테스트에 필요한 빈들만 생성해줍니다.
@Transactional    
// 테스트 메소드들이 모두 트랜잭션에 포함되어 최적화 되도록 합니다. 
// 테스트 대상 함수의 실행환경에서는 Transaction이 안걸려 있을 수 있으니 실무에 사용시 주의
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  
// 테스트 용 DB를 따로 설정하지 않고 main 환경 DB를 그대로 사용하도록 합니다.
@Import(JPAConfiguration.class) 
// JPAQueryFactory와 같이 테스트시 필요한 빈들을 정의해놓은 Configuration 설정합니다.
@Rollback(value = false)
// 테스트 데이터가 롤백되지 않고 실제 DB에 반영되도록 합니다.
```


# [TestContainers](https://www.testcontainers.org/)

- 도커 환경에서 데이터베이스를 실행하여 테스트 환경을 쉽게 구축할 수 있게 해주는 라이브러리
- 개발 환경에 데이터베이스를 사용하지 않기 때문에 테스트 때문에 발생하는 더미 데이터를 줄일 수 있다.
- H2와 같은 인메모리 DB를 사용하는 것이 아니라서 실제 환경과 거의 비슷한 환경으로 데이터베이스를 테스트할 수 있다.
- 테스트가 느려지는 단점이 있다.

## 예시
```gradle
// build.gradle

testImplementation "org.testcontainers:testcontainers:1.17.6"
testImplementation 'org.testcontainers:junit-jupiter:1.17.6'
testImplementation 'org.testcontainers:mysql:1.17.6'
```

```yml
# application.yml

spring:
  profiles:
    active: local
  jpa:
    database: mysql
    database-platform: org.hibernate.dialect.MySQL8Dialect
    open-in-view: false
    properties.hibernate:
      hbm2ddl.auto: create-only
      enable_lazy_load_no_trans: false
      implicit_naming_strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
      physical_naming_strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
      default_batch_fetch_size: 100
  data:
    web:
      pageable:
        default-page-size: 20 # page 파라미터 없을 경우에 default 값
        max-page-size: 100 # size 파라미터 없을 경우에 default 값
        one-indexed-parameters: true # 페이지 시작을 1부터 (currentPage - 1)
logging:
  config: classpath:log4j2.xml

decorator:
  datasource:
    p6spy:
      enable-logging: true # p6spy 활성화 여부
​
```


```java
// ContainerDataSourceConfiguration.java

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class ContainerDataSourceConfiguration {
	private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.27"))
		.withDatabaseName("ci")
		.withUsername("teasun")
		.withPassword("pass");

	static {
		MY_SQL_CONTAINER.start();
	}

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
			.type(HikariDataSource.class)
			.url(MY_SQL_CONTAINER.getJdbcUrl())
			.driverClassName(MY_SQL_CONTAINER.getDriverClassName())
			.username(MY_SQL_CONTAINER.getUsername())
			.password(MY_SQL_CONTAINER.getPassword())
			.build();
	}

}
```

```java
// @RepositoryTest.java
// 테스트용 어노테이션 정리를 위한 어노테이션 생성 클래스

@DataJpaTest(excludeAutoConfiguration = {DataSourceAutoConfiguration.class, TestEntityManagerAutoConfiguration.class, /*만든 class*/DataSourceConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ContainerDataSourceConfiguration.class, JpaConfiguration.class, QueryDslConfiguration.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryTest {
}
```


# [FixtureMonkey](https://naver.github.io/fixture-monkey/kr/)

- 네이버에서 만든 테스트 생성 객체를 자동으로 생성해주는 자바 라이브러리
- Mock 객체를 보다 쉽게 생성하기 위해서 사용

### FixtureMonkey 맛보기

```yaml
# build.gradle
testImplementation 'com.navercorp.fixturemonkey:fixture-monkey:0.4.9'
testImplementation 'com.navercorp.fixturemonkey:fixture-monkey-javax-validation:0.4.9'
```

```java
// UserFixture.java

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFixture {
	private Long userId;
	@NotNull
	private UserType type;
	@NotNull
	private UserStatus status;

	public User toEntity() {
		return User.builder()
			.userId(userId)
			.type(type)
			.status(status)
			.build();
	}
}
```

### 생성 예시
```java
var fixturemonkey = FixtureMonkey
		.labMonkeyBuilder()
		.objectIntrospector(BeanArbitraryIntrospector.INSTANCE)
		.plugin(new JavaxValidationPlugin())
		.build();

var user = fixturemonkey.giveMeBuilder(UserFixture.class)
			.set("userId", FixtureMonkeyUtils.getUserId())
			.build()
			.sample()
			.toEntity();
```

# 테스트 간단 정리

- 단위 테스트
    - 각 계층(클래스) 별로 테스트 케이스 작성
- 통합 테스트
    - 실행될 때마다 랜덤하게 변경되는 시나리오를 만들고 그에 따른 데이터를 미리 생성(Docker 환경의 데이터베이스)
    - 모든 엔드포인트에 대해서 테스트
    - 사전에 데이터를 미리 만들어둔 것을 통해서, 결과를 예측하고 검증할 수 있음
- 단위테스트는 Pull Request 에서 검증하는 용도(CI)
- 통합테스트는 정기 배포 당일 생성한 브랜치에 대해서 검증하고 검증이 완료된다면 자동으로 배포하는 프로세스(CD)

# 참조
https://www.inflearn.com/course/the-java-application-test/dashboard  
https://www.inflearn.com/course/the-java-code-manipulation/dashboard  
https://hanamon.kr/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1%EC%9D%98-%EC%A4%91%EC%9A%94%EC%84%B1/


# Maven (pom.xml) --- Gradle (build.gradle)
### pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>jpa-basic</groupId>
    <artifactId>ex1-hello-jpa</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- JPA 하이버네이트 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.3.10.Final</version>
        </dependency>
        <!-- H2 데이터베이스 -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>2.2.224</version>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
</project>
```

### build.gradle
```gradle
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.1.5'
	id 'io.spring.dependency-management' version '1.1.3'
}

group = 'com.study'
version = '0.0.1-SNAPSHOT'

java {
	sourceCompatibility = '17'
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// JPA 설정, default - hibernate
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    // mariaDB
	implementation 'org.mariadb.jdbc:mariadb-java-client'

	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}

```

# JPA 설정 (persistence.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="hello">
        <properties>
            <!-- 필수 속성 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test2"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>

            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.use_sql_comments" value="true"/>
            <property name="hibernate.jdbc.batch_size" value="100"/>
            <property name="hibernate.hbm2ddl.auto" value="update" /> <!-- 사용에 주의 -->
        </properties>
    </persistence-unit>
</persistence>
```
* /resources/META-INF/persistence.xml 위치 고정


    > ### Spring Boot 에서는...
    > * 스프링 부트에서는 persistence.xml가 내부적으로 구현됨 
    > * 아래처럼 스프링 부트 설정 파일만 수정하면 됨 (/resources/application.properties) 
    > * EntityManager까지도 자동으로 생성해줌
    >  ```properties
    > spring.datasource.url=jdbc:mariadb://localhost:3306/memo
    > spring.datasource.username=root
    > spring.datasource.password=root
    > spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
    > 
    > spring.jpa.hibernate.ddl-auto=update
    > 
    > spring.jpa.properties.hibernate.show_sql=true
    > spring.jpa.properties.hibernate.format_sql=true
    > spring.jpa.properties.hibernate.use_sql_comments=true
    > ```


### 설명

* hibernate.dialect DB 속성
    * H2 : org.hibernate.dialect.H2Dialect 
    * Oracle 10g : org.hibernate.dialect.Oracle10gDialect 
    * MySQL : org.hibernate.dialect.MySQL5InnoDBDialect
* hibernate.hbm2ddl.auto : 스키마 자동 생성 __(운영DB에는 사용하면 안됨)__
    * create : DROP + CREATE
    * create-drop : create와 같으나 종료시점에 테이블 DROP
    * update : 변경분만 반영
    * validate : 엔티티와 테이블이 정상 매핑되었는지만 확인
    * none : 사용 X (의미없으나 명시적으로 적어줌)


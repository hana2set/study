
1. Amazon S3 > 버킷 > 버킷 만들기
    - 이 버킷의 퍼블릭 액세스 차단 설정 - 해제
        - 차단하면 
2. IAM > 사용자 > 사용자 생성
    - 직접 연결 선택
        - AmazonS3FullAccess 
    - 사용자 생성
3. IAM > 사용자 > [생성한 역할] > 액세스 키 만들기
    - 키 csv 파일로 저장

# 스프링 연동
1. build.gradle
```gradle
    //AWS s3
    implementation 'org.springframework.cloud:spring-cloud-starter-aws'
```

2. application.yml
```yml
cloud:
	aws:
		s3:
      bucket: ${AWS_BUCKET_NAME}
		stack.auto: false
		region.static: ap-northeast-2
		credentials:
			accessKey: ${AWS_ACCESSKEY}
			secretKey: ${AWS_SECRETKEY}
```
- cloud.aws.stack.auto=false
    > EC2에서 Spring Cloud 프로젝트를 실행시키면 기본으로 CloudFormation 구성을 시작하기 때문에 설정한 CloudFormation이 없으면 프로젝트 실행이 되지 않는다. 해당 기능을 사용하지 않도록 false로 설정.

- cloud.aws.region.static:ap-northeast-2
    > 지역을 한국으로 고정한다.

3. S3Config.java
```
@Configuration
public class S3Config {
   @Value("${cloud.aws.credentials.access-key}")
   private String accessKey;
   @Value("${cloud.aws.credentials.secret-key}")
   private String secretKey;
   @Value("${cloud.aws.region.static}")
   private String region;

   @Bean
   public AmazonS3Client amazonS3Client() {
      BasicAWSCredentialsawsCredentials= new BasicAWSCredentials(accessKey, secretKey);
      return (AmazonS3Client)AmazonS3ClientBuilder.standard()
         .withRegion(region)
         .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
         .build();
   }
}
```
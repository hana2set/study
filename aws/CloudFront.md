# CloudFront 
Amazone에서 제공하는 CDN (Content Delivery Network) 

- CDN
   - 장점
        - 속도 향상 (캐싱)
        - 보안 (방화벽, 캐싱, 분산)
    - 단점
        - 캐시 (최신 동적 컨텐츠에 부적합)
        - 비용

## Architecture
- Route53 → CloudFront → S3

## 생성해보기
- S3에 사용할 bucket 우선 생성
- 생성 후 CloudFront distribution 생성
![image](https://github.com/hana2set/study/assets/97689567/aa32523f-03c3-40c9-9d54-70b2ee9cfc1f)
![image](https://github.com/hana2set/study/assets/97689567/b16d85e4-7536-4692-bcff-6920d15e6746)
![image](https://github.com/hana2set/study/assets/97689567/e0688a01-8785-4c48-9546-9b3fa4fc0bc3)
![image](https://github.com/hana2set/study/assets/97689567/30b668e0-d4d8-4bcf-9c22-203728c3023b)

- 입력한 도메인 관련 Route53 DNS에서 Record 생성하기
![image](https://github.com/hana2set/study/assets/97689567/cf8f6fba-c85b-4143-b626-804383ee1a2f)
- S3에 CloudFront에 대한 Policy 입력해줘야함
![image](https://github.com/hana2set/study/assets/97689567/4503af4d-d97f-4ae9-ad49-a2395fad7aaa)

## Invalidation 
- 캐싱이 되어있는 cdn을 초기화
- 무효화하려는 객체의 경로 입력 (* 가능)
![image](https://github.com/hana2set/study/assets/97689567/301aebc6-46c4-4f2d-a2a4-4e58251da6a2)
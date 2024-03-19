# Amazon Simple Storage Service(Amazon S3)
아마존 스토리지 서비스.
- 용도
    - **웹 사이트 호스팅**
    - **멀티미디어 파일 저장 및 스트리밍**
    - 애플리케이션 데이터 저장
    - 백업 및 복원
    - 아카이브
- 장점 
    - 높은 내구성, 가용성 및 안정성
    - 손쉬운 사용 및 관리
    - 보안성
    - 높은 확장성

## Bucket
최상위 레벨 폴더 형태의 컨테이너.  
S3에 저장되는 파일들은 '객체'이고 실제로 '키'로 식별되나 디렉토리 형태로 표시해줌.
> s3://my-bucket/my_folder/my_file.txt

### 버켓 네이밍 규칙
- **대문자 금지, 언더스코어 금지**
- **버켓 이름은 글로벌 유니크**
- 3자(최소)에서 63자(최대) 사이
- 소문자, 숫자, 점(.) 및 하이픈(-)으로만 구성
- 문자 또는 숫자로 시작하고 끝나야 함
- 두 마침표를 나란히 붙여 사용 불가
- IP 주소 형식(예: 192.168.5.4) 사용 불가

### 용도
- 데이터를 저장하는 컨테이너 역할
- 객체에 대한 공용 또는 개인적인 접근 권한을 설정하기 위한 위치
- 객체에 대한 특별한 이벤트 알림을 설정하기 위한 위치
- [AWS 계정에서 버킷 및 객체 사용에 대한 비용 추적 및 모니터링을 위한 위치](https://docs.aws.amazon.com/ko_kr/AmazonS3/latest/userguide/enable-cloudtrail-logging-for-s3.html)

## Policy
- IAM과 유사. JSON 형식의 문서
- 각종 엑세스 범위를 제한함
- 예시
    - Resource : 버킷 혹은 오브젝트
    - Effect: Allow 혹은 Deny
    - Principal : 대상 유저

    ```jsx

    {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Sid": "AllowUserToGetBucket",
                "Effect": "Allow",
                "Principal": {
                    "AWS": "arn:aws:iam::ACCOUNT-ID:user/USERNAME"
                },
                "Action": "s3:GetBucketLocation",
                "Resource": "arn:aws:s3:::BUCKET-NAME"
            }
        ]
    }
    ```
    IAM 사용자 "USERNAME"이  
    S3에서 "GetBucketLocation" 작업을 수행할 수 있도록  
    "BUCKET-NAME"에 대한 액세스를 허용


### ARN
AWS에서 사용하는 고유한 식별자로 AWS의 모든 리소스에 대한 고유한 식별자 역할

```jsx
//형태
arn:partition:service:region:account-id:resource-id
arn:partition:service:region:account-id:resource-type/resource-id
arn:partition:service:region:account-id:resource-type:resource-id

//예시
arn:aws:iam::123456789012:user/johndoe
arn:aws:s3:::my_corporate_bucket/
arn:aws:ec2:us-east-1:123456789012:vpc/vpc-0e9801d129EXAMPLE
```

- arn: AWS 리소스 이름을 가리키는 고정 문자열
- aws: 리소스가 AWS에서 호스팅되는 것을 나타내는 고정 문자열
- service: AWS 서비스 이름을 나타내는 문자열 (예: s3, lambda, ec2 등)
- region: AWS 리전 이름을 나타내는 문자열 (예: us-east-1, ap-northeast-2 등)
- account-id: AWS 계정 ID를 나타내는 숫자
- resource-id: 해당 리소스의 고유 식별자 (예: S3 버킷 이름, Lambda 함수 이름 등)
    
### ACL
- Access Control List : 접근 권한을 가진 경우들을 명시합니다.

### 적용해보기
1. 버킷 생성 후 Permissions 허용
    - Block public access 가 Off 처리되어있는지 확인
    ![image](https://github.com/hana2set/study/assets/97689567/382802b1-78a7-4ba4-93d8-381893cac080)

2. Butket policy - Edit 클릭
    ![image](https://github.com/hana2set/study/assets/97689567/eeab4f11-788f-4a94-9ddd-5cd3ec62cfec)

3. Buykey ARN 복사, policy generator 클릭 (안하고 직접입력해도 됨)
    ![image](https://github.com/hana2set/study/assets/97689567/861f4e23-8e43-4627-9485-251460ab7f51)

4. Generate Policy
![image](https://github.com/hana2set/study/assets/97689567/16624b60-3188-4849-a13f-5969b3b12ee2)

5. 전 화면에 붙여넣기
![image](https://github.com/hana2set/study/assets/97689567/02926b94-1c6d-4cba-a481-05fdbb6a1eb5)



## 정적호스팅
1. Bucket - 최하단 Static website hosting - Edit 클릭
![image](https://github.com/hana2set/study/assets/97689567/b5c689e3-4ab9-4218-91fa-31cd3f031b46)
![image](https://github.com/hana2set/study/assets/97689567/099424ea-b634-4dd3-b287-d8dd9dfe1b35)

2. Enable 클릭 후 페이지 입력 (index.html)
![image](https://github.com/hana2set/study/assets/97689567/f3213672-2d07-4e3d-9eec-43853c196014)

3. 사이트 생성됨. (참고 : Policy에 GetObject 권한이 있어야함)
![image](https://github.com/hana2set/study/assets/97689567/14f6bee1-8c50-4af9-96a5-d4f4cb88a9b5)


## 도메인 등록하기
- 도메인 없어서 사진으로 대체
- 버켓 이름을 URL명과 일치 시키면 (ex. front.mydomainname.com) Route53에서 해당 도메인에 해당하는 곳에서 Record 생성시 검색됨
![image](https://github.com/hana2set/study/assets/97689567/ef7e2a1a-5bc2-4a75-8aa9-c4c883467764)

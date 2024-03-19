## Users, Groups, Policies

- 사용자(User): AWS 계정에 액세스하는 개별 인물 또는 서비스 
- 그룹(Group): 하나 이상의 사용자를 그룹화하여 이들에게 공통된 권한을 부여
- 정책(Policy): AWS 리소스에 대한 액세스 권한을 지정하는 데 사용되며, 사용자 또는 그룹과 연결함
    - JSON 형식으로 작성되며, 허용 또는 거부할 수 있는 작업 및 AWS 리소스에 대한 액세스 수준을 지정
    - 최소 권한 원칙을 따름


## Policy Structure

```jsx
{
  "Version": "policy-version",
  "Statement": [
    {
      "Effect": "allow-or-deny",
      "Action": ["action-name"],
      "Resource": ["resource-arn"],
      "Condition": {
        "condition-operator": {
          "condition-key": "condition-value"
        }
      }
    }
  ]
}
```

- **`Version`**: 정책의 버전. 현재 "2012-10-17" 고정
- **`Statement`**: 정책의 규칙. 배열 형태로 여러 개의 규칙을 작성 가능
- **`Effect`**: 규칙의 적용 여부. 
  - "allow", "deny"
- **`Action`**: 규칙이 적용되는 작업의 종류. 
  - "s3:ListBucket"
- **`Resource`**: 규칙이 적용되는 리소스의 ARN (Amazon Resource Name). 
  - "arn:aws:s3:::my-bucket/*"
- **`Condition`**: 규칙이 적용되는 조건. 다양한 조건 연산자로 제한 가능. 
  - "IpAddress"

## MFA

MFA는 Multi-Factor Authentication의 약어로, 다중 인증 요소 인증 방식을 의미

보안을 위해 
- 루트유저에는 **반드시 꼭** 적용!
- 추가로 만드는 IAM User에도 **반드시** 적용!


### MFA 적용하기

  ![image](https://github.com/hana2set/study/assets/97689567/00a38504-b7fa-405b-a5bd-ba269790191d)

  ![image](https://github.com/hana2set/study/assets/97689567/1c89ca74-66c2-43fa-874d-899b644020e7)

  - 추천하는 앱 설치하면 됨. microsoft authenticator 설치함.

  ![image](https://github.com/hana2set/study/assets/97689567/5ca24e83-912a-4eb9-b314-cc3e00ea9439)
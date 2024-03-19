# AWS (Amazon Web Services)

- 주요 서비스
    - 컴퓨팅: EC2 (Elastic Compute Cloud), Elastic Beanstalk 등
    - 데이터베이스: RDS (Relational Database Service) 등
    - 스토리지: S3 (Simple Storage Service), EBS (Elastic Block Store) 등
    - 네트워킹: VPC (Virtual Private Cloud), CloudFront, Route 53 등
    - 보안: IAM (Identity and Access Management) 등

    ![image](https://github.com/hana2set/study/assets/97689567/0e795457-2fae-4443-b166-30f7179a39c6)

#### On-premise 서버란? (↔ Cloud 서버)
- 조직 내부에 설치되고 유지보수되는 서버

## Region and Availability Zone
- Resion: 데이터 센터의 물리적 위치
- 각 region은 여러 개의 가용 영역(Availability Zone, AZ)으로 구성되어 있고 네트워크로 연결되어 있음
- 여러개의 AZ를 씀으로 __고가용성__ 확보
#### 고가용성
> 서버와 네트워크 또는 프로그램 등의 정보 시스템이 상당히 오랜 기간 동안 지속적으로 장애 없이 정상 운영이 가능한 성질

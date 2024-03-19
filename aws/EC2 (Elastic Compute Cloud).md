## EC2 (Elastic Compute Cloud)

아마존 클라우드 컴퓨팅 서비스

#### 클라우드 서비스 종류
- IaaS (Infrastructure as a Service)
    - 하드웨어 인프라 제공
    - EC2가 여기 포함
- PaaS (Platform as a Service)
    - 어플리케이션 개발, 실행, 관리 플랫폼 제공용
    - Elastic Beanstalk, Heroku, Google App Engnioe
- SaaS (Software as a Service)
    - 완전한 애플리케이션 제공
    - google drive, Microsoft 365

## EC2 options
- 인스턴스 유형 (Instance Types)
    - CPU, 메모리, 스토리지, 네트워크 리소스 등 설정
- 운영체제
    - Amazon Linux, Ubuntu, Windows
- 스토리지 옵션 (Storage Options)
    - EBS, S3
- 보안그룹 (Security Groups)
- 키 페어 (Key Pair) 
- 탄력적 IP 주소 (Elastic IP Address)
    - 고정 IP 주소 할당
- 사용 가능한 영역 (Availability Zones)
    - 가용 영역 설정

### 인스턴스 유형

[Amazon EC2 인스턴스 유형 – Amazon Web Services](https://aws.amazon.com/ko/ec2/instance-types/?nc1=h_ls)

![image](https://github.com/hana2set/study/assets/97689567/0c2b6afa-9872-49e8-82b2-ec8641f33f38)

- ec2 일반적으로 범용 - T2 씀 (마이크로가 프리티어라서)
    - bust 기능 있음

인스턴스 명칭 설명 - ex) t2.nano
- t - 클래스
- 2 - 버전
- nano - 크기

<br>

### 보안그룹

- 여러 인스턴스에 할당 가능
- time out → 보안규칙 이슈
- connection refuse → ec2 내부 이슈
- 모든 inbound는 디폴트로 막힘
- 모든 outbound는 디폴트로 열림

### 포트
- 22 = ssh(secure shell)로 인스턴스에 원격 접속
- 21 = FTP 파일전송 프로토콜
- 80 = http 웹 접속
- 443 = https 안전한 http 접속, 현재의 스탠다드


<br>

### 키 페어 만들기

![image](https://github.com/hana2set/study/assets/97689567/361297e7-2190-4c61-ada7-8679459451f3)
![image](https://github.com/hana2set/study/assets/97689567/2f94b090-9170-4097-9d7a-a6dd044a9c60)

- window에서 putty를 쓴다 -> .ppk

## SSH 연결

- 다운받은 키 권한 변경
    - linux
        ```
        chmod 400 newkeypair.pem
        ```

    - window 
        ```
        icacls.exe newkeypair.pem /reset
        icacls.exe newkeypair.pem /grant:r %username%:(R)
        icacls.exe newkeypair.pem /inheritance:r
        ```

    ![image](https://github.com/hana2set/study/assets/97689567/5f268c8c-1d4b-443f-978c-deddba829848)
- username, ip 확인 후 입력
    ```
    ssh -i newkeypair.pem ubuntu@3.82.230.198
    ```

- 성공
    ![image](https://github.com/hana2set/study/assets/97689567/165d09d3-6762-4182-b68d-132097e23ff8)

- 위 그림에서 connect 누른 것과 같은 콘솔창임. (기존엔 없었으나 추가됨)


## 인스턴스 띄워보기

<details>
  <summary>nginx를 실행시켜 브라우저로 해당 IP에 접근 가능한지 확인</summary>

<br>

1. 보안그룹 설정 (HTTP, HTTPS port 추가)
    - EC2 > Instances > Security

        ![image](https://github.com/hana2set/study/assets/97689567/f0727416-eede-4cf2-b751-8122e5cd940a)

        - 설정된 security groups 선택

        ![image](https://github.com/hana2set/study/assets/97689567/35afa577-a4cb-4599-962a-2ed246af0990)

        - edit inbound rules 클릭

        ![image](https://github.com/hana2set/study/assets/97689567/ebfb7f82-4f49-42dd-a535-614bb0185b77)

        - add rules -> http, https 선택 - 0.0.0.0/0 (전체 선택) -> save rules 클릭

        ![image](https://github.com/hana2set/study/assets/97689567/b5c241b3-90a4-4e19-b68e-acdec2a023f3)

        - Public IPv4 address -> 외부에서 접근가능한 ip 주소

2. EC2 콘솔에서 확인할 수 있는 UI 띄우기

    ![image](https://github.com/hana2set/study/assets/97689567/cf4412cc-06f7-46c6-ba67-b03615d67948)

    ```
    sudo apt-get update
    sudo apt-get install nginx

    echo "<h1>EC2로 띄워봤음! $(hostname -f)</h1>" > /usr/share/nginx/html/

    sudo systemctl start nginx
    sudo systemctl status nginx
    ```

    ![image](https://github.com/hana2set/study/assets/97689567/660a7c6d-bd43-45ba-a923-4a9bb10b7e0e)

3. nginx 실행 중일 때 Public IPv4 address로 접근하면 정상 페이지 출력


### 확인할 것

- time out → 보안규칙 이슈 (inbound의 방화벽)
- connection refuse → ec2 내부 이슈
- 인스턴스 멈췄다 실행하면 public 도메인 바뀜
    - 고정 IP 필요시 elastic ips

</details>




## EBS
파일 분리

## AMI
파일 분리

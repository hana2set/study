
# ELB (Elastic Load Balancer)

AWS에서 제공하는 로드 밸런싱 서비스로, 다수의 EC2 인스턴스를 사용하여 트래픽을 분산시킴.

- 쓰는 이유 :
    - 요청 분산 (EC2와 연동)
    - 단일 액세스 포인트 공개 (Route 53과 연동)
    - 인스턴스에 대한 헬스 체크
    - HTTPS 제공 (ACM과 연동)
    - 고가용성 제공
    - 공개 트래픽과 내부 트래픽 분리

    ![image](https://github.com/hana2set/study/assets/97689567/4e029a8e-666f-4296-bbf0-d1dda9943a03)

- ELB 종류
    1. Application Load Balancer: 
        - OSI 모델 7계층에서 동작하며, HTTP/HTTPS 트래픽을 처리 
        - 컨테이너화된 애플리케이션과 연동하여 사용
    2. Network Load Balancer: 
        - OSI 모델 4계층에서 동작하며, TCP/UDP 트래픽을 처리
        - 높은 처리량을 필요로 하는 애플리케이션에 적합
    3. Gateway Load Balancer: 
        - OSI 모델 3계층에서 동작하며, 가상 네트워크 게이트웨이 트래픽을 처리
        - 방화벽같은 가상 어플라이언스를 배포 및 관리
    4. ~~Classic Load Balancer - deprecated~~


## 사용해보기

1. EC2 > Instances > Launch an instance 
    - Advanced details > User data - optional 
        - 생성 시 자동 실행되는 명령어

        ![image](https://github.com/hana2set/study/assets/97689567/29ac3240-4ce6-4c12-85de-ad4dd497f232)
        <details>
        <summary>코드</summary>
        
        ```
        #!/bin/bash
        apt-get update
        apt-get install -y nginx
        cat <<EOF > /var/www/html/index.html
        <!DOCTYPE html>
        <html>
        <head>
        <title>Welcome to Nginx</title>
        </head>
        <body>
        <h1>Hello World!</h1>
        <p>AWS deployed by Me!</p>
        <p>private ip is $(hostname -f)</p>
        </body>
        </html>
        EOF
        sudo systemctl start nginx
        ```
        </details>
        <br>
    - Summary > 인스턴스 2개 생성 (로드밸런서 써봐야하니까)

2. EC2 > Load balancers > Create Application Load Balancer

    ![image](https://github.com/hana2set/study/assets/97689567/8250661e-4124-4219-93b9-1d9dcae5c8fb)
    - Network mapping : 모든 지역 선택
    - Security groups : 새로운 security group 생성하기 (분리할 예정)
        + <details><summary>security group 생성</summary>
                    
            ![image](https://github.com/hana2set/study/assets/97689567/8a1b7386-1761-4ba7-b8db-bfe8c8562d87)
            </details>
    - Listeners and routing : target group 필요함 (ec2들)
        + <details><summary>target group 생성</summary>
                    
            ![image](https://github.com/hana2set/study/assets/97689567/81073bf8-2a5b-434d-9402-427f52538e19)

            ![image](https://github.com/hana2set/study/assets/97689567/96c26b6d-56a1-47b5-a576-4f71a2b20b81)
            - 등록할 인스턴스 선택 후 include as pending below

            </details>

3. 등록한 load balancer에서 하단 DNS 주소 확인

    ![image](https://github.com/hana2set/study/assets/97689567/06f99adf-54d0-4194-a93d-4cffd3340918)

4. DNS에서 IP주소가 자동으로 주소가 변경되는 것 확인 (ec2 두개 사용)

    ![Animation](https://github.com/hana2set/study/assets/97689567/37a48255-8907-43b0-a65e-7cf08725684d)


5. 이제 외부에 공개된 ec2 규칙만 수정하면 됨 (로드밸런스를 통해서만 접근 가능하도록)
    1. 새 시큐리티 그룹 생성

        ![image](https://github.com/hana2set/study/assets/97689567/872bf63b-07ed-4009-81c4-eff3db89575a)

    2. 인스턴스에서 시큐리티 그룹 변경

        ![image](https://github.com/hana2set/study/assets/97689567/f84e4b0e-07b9-4f2e-858a-434312f465bf)

6. 인스턴스 직접 접근시 무한로딩 (접근 거부)

## SSL 적용하기?

- SSL (Secure Sockets Layer): 암호화 기반 인터넷 보안 프로토콜

도메인 필요. (route53) - skip함

1. Add listener
2. target groupt 선택 (http여도 상관없음. 내부동작)
3. default SSL/TSL certificate 선택
    - 없으면 request new ACM certificate 클릭 -> CA에서 받아오기
4. 추가하면 HTTPS:443 포트가 열려있을 것. (security에서 정상적으로 열려있다면)

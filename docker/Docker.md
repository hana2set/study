# Docker
애플리케이션을 신속하게 구축, 테스트 및 배포할 수 있는 소프트웨어 플랫폼.
Docker는 Container 라는 가상화 공간을 생성하기 위해  
1. Linux 커널에서 기본적으로 제공하는 프로세스 격리 및 가상화 기능을 통해
2. VM의 장점(가상 공간)을 가지면서 경량화 되어있기 때문에  

현재 업계 표준으로 부상했음.

## Docker vs. VM
### VM
- VM은 가상의 물리 서버를 만듬
- 독립적 OS, VM 관리를 위한 Hypervisor 필요
    - 무거움 -> 도커 등장

![Untitled](https://github.com/hana2set/study/assets/97689567/763cf51e-a39a-4d63-bbe9-8f9fdc3c8b21)

### Docker
- OS가 linux로 고정 -> 개발 환경과 운영 환경 일치
- 독립된 환경으로 격리 -> 변화에 대한 파장이 적음 (library 버전업 등)

<br>

## Docker 설치

Windows 는 WSL 설치해야함.
(Docker Desktop도 설치 권장)

<details>
  <summary>설치하기</summary>
  
## WSL2 설치
> WSL란?  
별도의 가상 머신 또는 이중 부팅 없이 Windows 컴퓨터에서 Linux 환경을 실행할 수 있는 Windows의 기능
1. 관리자 권한으로 cmd 열기
    ```bash
    wsl --install
    ```
2. 2024.03.28 기준으로 Ubuntu 22.04가 기본값으로 설치됨
    ![image](https://github.com/hana2set/study/assets/97689567/89b06e8f-872d-496f-93d4-83ad8fc05e2c)
3. 이후 ubuntu 앱으로 실행시킬 수 있음.

## Docker 설치 
- 설치된 ubuntu 안에서 다음 명령어 순차적으로 실행
    ```bash
    # docker engine gpg 키 등록
    sudo apt-get update
    sudo apt-get install ca-certificates curl gnupg
    sudo install -m 0755 -d /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    sudo chmod a+r /etc/apt/keyrings/docker.gpg

    # apt source 에 docker 관련 추가
    echo \
    "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
    "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
    sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt-get update

    # docker engine 설치
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io \
    docker-buildx-plugin docker-compose-plugin docker-compose

    # docker 그룹에 현재 계정을 등록하여 sudo 없이 docker 명령을 사용하게 함
    sudo usermod -aG docker $USER
    sudo service docker restart
    newgrp docker

    # 새로운 터미널을 열고 확인
    docker version
    docker-compose --version
    ```

- Container 실행테스트
    ```bash
    # nginx 이미지 다운받기
    docker image pull nginx:1.25.3-alpine

    docker images

    docker image history nginx:1.25.3-alpine

    docker run -d -p 8001:80 --name webserver01 nginx:1.25.3-alpine

    docker ps | grep webserver01

    docker port webserver01

    curl localhost:8001
    ```

    ![image](https://github.com/hana2set/study/assets/97689567/723e3532-3758-4e3a-8cfe-2cd2154f6e75)

### Docker Desktop 설정 변경
- 리소스 같이 관리하기
![image](https://github.com/hana2set/study/assets/97689567/bda9bdd7-b974-4b6c-b12c-7a0c5c72298b)

  
</details>



<br>
<br>


# Docker Image

- Docker Container를 만드는 데 사용되는 하나의 템플릿
- 네이밍 규칙: {저장소 이름}/{이미지명}:{태그}
    - docker.io/chlgusdnd3/demo-order:latest

### 특징
- Container 런타임에 필요한 바이너리, 라이브러리 설정값 등을 포함.
- stateless (무상태)
- immutable (불변성)


### 기본 명령어
|커멘드|내용|별칭|주요 옵션|
|-|-|-|-|
|image pull|외부 레포지토리에서 이미지를 내려 받음.|pull||
|image rm|도커 이미지를 삭제|rmi||
|image ls|이미지 목록 출력|images||
|image build|도커 이미지를 생성|build|-t|
|image push|도커 이미지를 외부 레포지토리에 배포|push||
|image inspect|Docker 이미지 구조 확인 (json 형태)|||
|image history|도커 이미지에 대한 정보|||

<br>

* docker pull
    ```bash
    # 명령어
    docker pull {도커 허브 내 이미지 경로:태그명}

    # 예 - redis docker image pull 
    docker pull redis
    docker pull chlgusdnd3/demo-order:latest
    ```


    ```yaml
    C:\Users>docker pull redis
    Using default tag: latest
    latest: Pulling from library/redis
    af107e978371: Pull complete
    b031def5f2c4: Pull complete
    bf7f0c8796d3: Pull complete
    e3b2691a4104: Pull complete
    190b4d7a237a: Pull complete
    797591c7970a: Pull complete
    4f4fb700ef54: Pull complete
    45ce3854ac9a: Pull complete
    Digest: sha256:a7cee7c8178ff9b5297cb109e6240f5072cdaaafd775ce6b586c3c704b06458e
    Status: Downloaded newer image for redis:latest
    docker.io/library/redis:latest

    What's Next?
    View a summary of image vulnerabilities and recommendations → docker scout quickview redis

    C:\Users>
    ```

- docker hub(repository)에 올리기
    ```bash
    docker login

    docker image pull nginx:latest
    docker image pull ubuntu:22.04

    docker images

    docker image tag nginx:latest hana2set/nginx-test:1.0

    docker push hana2set/nginx-test:1.0

    # 올린 이미지 다운
    docker pull hana2set/nginx-test:1.0
    ```

# Docker Container

실행중인 Docker Image를 의미
![Untitled](https://github.com/hana2set/study/assets/97689567/93dc27fe-cb50-4f75-a07d-035e1d2a9eae)


### 기본 명령어
| 커멘드 | 내용 | 별칭 | 주요 옵션 |
| --- | --- | --- | --- |
| container start | 컨테이너 실행 | start | -i |
| container stop | 컨테이너 정지 | stop |  |
| container create | 컨테이너 생성 → 실행을 시키진 않음. | create | —name, -e, -p, -v |
| container run | 도커 이미지를 내려받고, 컨테이너를 생성하여 실행함.<br>(pull → create → start) 3개의 명령어를 순차적으로 실행함. | run | —name, -e, -p, -v |
| container rm | 정지 상태의 컨테이너 삭제 | rm | -f, -v |
| container exec | 실행 중인 컨테이너의 특정 프로그램 실행 | exec | -i, -t |
| container ls | 컨테이너 목록 출력 | ls (ps) | -a, --no-trunc  |
| container cp | 컨테이너와 호스트 간 파일 복사 | cp |  |

- 컨테이너 생성 및 실행
```bash
# redis 이미지로 
docker run --name redis-test redis
docker exec -it redis-test /bin/bash

# ubuntu 이미지로
docker run -it --name first-ubuntu ubuntu /bin/bash
#docker run -i -t --name [컨테이너명] ubuntu:[version] [실행할 기본 쉘]

# (-i interactive, 터미널로 연결, -t pseudo-terminal, 입력이 터미널 디바이스를 통해 들어온다는 것을 알림)
```
![image](https://github.com/hana2set/study/assets/97689567/1d645aa4-b90f-4299-ba09-6198a3dc8546)
```bash
```
- 전체 컨테이너를 이미지 이름과 함께 보기
```bash
docker ls -a --no-trunc
```
![image](https://github.com/hana2set/study/assets/97689567/421f999a-ab8f-4046-a12c-879cc0292d88)


# Dockerfile

Dockerfile은 Docker Image를 빌드하기 위한 파일로, Container에서 수행해야 할 작업을 명시
![image](https://github.com/hana2set/study/assets/97689567/4802638f-3667-4544-9f15-3a852410e586)

``` docker 
# Dockerfile 예제
FROM ubuntu:latest
MAINTAINER Your Name <your-email@example.com>
RUN apt-get update && apt-get install -y nginx
COPY index.html /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### 기본 명령어 
| 커멘드 | 내용 | 비고 | ex |
| --- | --- | --- | --- |
| **FROM** | Baseline Image 명시 | Docker Image가 local에 있는 경우, 도커 허브에서 내려 받지 않음 ||
| **MAINTAINER** | Dockerfile을 작성한 사람의 정보를 입력 |||
| **WORKDIR**| Image 내부의 작업 디렉터리를 지정|||
| **RUN**| Image 생성 시, 실행할 명령어를 입력| 사용자 미지정시, root 로 실행||
| **CMD**| Container 생성 시, 실행할 명령어를 입력||
| **ENTRYPOINT**| Container 시작 시, 실행할 명령어를 입력 (보통 가장 마지막)||
| **COPY, ADD**| 파일을 Docker Image 내부에 삽입||
| EXPOSE| Container가 노출할 포트 설정||
| ENV| Docker Container 실행 환경 변수||
| ARG| docker build 시, —build-arg 옵션을 통해 넘길 인자||

### 최적화
- 빌더 분리로 앱 경량화
    ```bash
    # build 는 gradle 이미지에서 `builder`라는 이름으로.
    FROM gradle:8.5-jdk21-alpine AS builder

    WORKDIR /app
    COPY ./ ./
    RUN gradle clean bootJar


    # `builder 이미지`와 실행파일을 격리해 이미지 생성
    FROM eclipse-temurin:21-jre-alpine

    WORKDIR /app
    COPY --from=builder /app/build/libs/spring-boot-0.0.1-SNAPSHOT.jar .

    EXPOSE 8080

    ENTRYPOINT ["java", "-jar", "spring-boot-0.0.1-SNAPSHOT.jar"]
    ```



<br>

# docker-compose

docker-compose는 여러 개의 Container가 하나의 서비스로 동작해야 할 때,  
**관련된 Container를 한번에 띄울 수 있는 도구**.  
 docker-compose는 yml 파일에 Image를 선택하고, port 및 네트워크, 볼륨 설정 등을 **선언적으로 명시**함.

> Docker 기반의 서비스를 운영할 때,  
> - 운영 환경에서는 kubernetes
> - 개발 환경에서는 docker-compose

<br>

- 장점
    - 설정 편의성
    - 자동 배포
    - 의존성 관리
    - 모니터링과 로깅
    - 확장성
    - 유연성 (여러 환경에 대한 일관성 유지)
    - 보안 강화
    - 유지보수 쉬움


- [compose 파일 사양](https://docs.docker.com/compose/compose-file/)
    - ~~`version`: docker engine과의 호환 버전~~ (더이상 사용되지 않음. 명시용)
      - <details>
           <summary> 버전 정보 </summary>
           
           [compose-versioning](https://docs.docker.com/compose/compose-file/compose-versioning/)
           
           ![image](https://github.com/hana2set/study/assets/97689567/ba726e4c-eb77-4b05-ac3c-33cac82d9176)
        <details>
        
    - `services`: 서비스 정의
        ``` yaml
        services:
            web:
                build:
                    context: .    # Dockerfile 의 위치
                    dockerfile: Dockerfile    # Dockerfile 파일명

                container_name: testapp_web_1    # 생략하는 경우 
                        # 자동으로 부여 docker run 의 --name 옵션과 동일

                ports: "8080:8080"  # docker run 의 -p 옵션과 동일

                expose: "8080"    # 호스트머신과 연결이 아니라 
                        # links로 연결된 서비스 간 통신이 필요할 때 사용

                networks: testnetwork    # networks 를 최상위에 정의한다면 해당 이름을 사용
                        # docker run의 --net 옵션과 동일

                volumes: .:/var/lib/nginx/html    # docker run 의 -v 옵션과 동일

                environment:    # docker run 의 -e옵션과 동일
                    - APPENV=TEST

                command: npm start   # docker run 의 가장 마지막

                restart: always    # docker run 의 --restart 옵션과 동일

                depends_on: db    # 이 옵션에 지정된 서비스가 시작된 이후에 `web`서비스가 실행

                links: db # Docker가 네트워크를 통해 컨테이너를 연결하도록 정의합니다. 
                        # 컨테이너를 연결할 때 Docker는 환경 변수를 만들고 
                        # 컨테이너를 알려진 호스트 목록에 추가하여 서로를 검색할 수 있도록 합니다.

                deploy:    # 서비스의 복제본 개수 등 지정
                    replicas: 3
                    mode: replicated
        ```
    - `volumes`: 볼륨을 마운트
        ```yaml
        volumes:
            logvolume01: {} # 도커볼륨 logvolume01 선언
        ```
    - `networks`: 소속 네트워크 설정. 기본값 default_${project}
    - `healthcheck`: health 체크
        ```yaml
        healthcheck:
            test: ["CMD", "curl", "-f", "http://localhost"]
            interval: 1m30s
            timeout: 10s
            retries: 3
            start_period: 40s
            start_interval: 5s
        ```
        - Dockerfile 우선. docker-compose 파일에서 재지정 가능

- Docker Compose CLI (default: `docker-compose [COMMAND] [SERVICES...]`)
    - `docker-compose up` : 서비스 실행
        - --build : 강제 재빌드
        - -d : 백그라운드로 실행
        - --force-recreate : 강제 컨테이너 재생성
        - ex) 'web'만 백그라운드로 실행 : docker-compose up -d web
        > 실행순서: (이미 있으면 스킵)
        > 1. 서비스를 띄울 네트워크 생성
        > 2. 필요한 볼륨 생성(혹은 이미 존재하는 볼륨과 연결)
        > 3. 필요한 이미지 풀(pull)
        > 4. 필요한 이미지 빌드(build)
        > 5. 서비스 실행 (depends_on 옵션 사용시 서비스 의존성 순서대로 실행)
    - `docker-compose down`: 서비스를 멈추고 삭제
        - --volume : 볼륨도 같이 삭제
    - `docker-compose start` : 서비스 시작
    - `docker-compose stop` : 서비스 정지
    - `docker-compose ps`: 현재 환경에서 실행 중인 각 서비스 상태 표시
    - `docker-compose logs` : 로그 확인
        - -f : 실시간 확인 (follow)
    - `docker-compose exec`: 실행 중인 컨테이너에 해당 명령어를 실행
        ```bash
        docker-compose exec django ./manage.py makemigrations
        docker-compose exec db psql postgres postgres
        ```
    - `docker-compose run`: 명령어를 일회성으로 실행. 특정 명령어 실행 후 컨테이너 종료
        ```bash
        docker-compose exec web echo "hello world" # 이미 실행된 web 컨테이너에서 echo "hello world"를 실행
        docker-compose run web echo "hello world" # web 컨테이너에서 echo "hello world"를 실행하고 컨테이너 종료
        ```

### EC2 연결
- ec2 에 jdk 설치
    ```bash
    sudo yum install -y java-17-amazon-corretto-devel
    ```

- ec2에 docker, docker-compose 설치
    ```bash
    sudo yum install docker

    # Docker 서비스 시작
    sudo systemctl start docker

    # Docker 서비스 작동 상태 확인
    sudo systemctl status docker

    # Docker 서비스를 운영체제 부팅시 자동 시작하도록 설정
    sudo systemctl enable docker

    # docker 명령어를 sudo 없이 사용하기 위해 계정을 docker 그룹에 소속 (계정 재접속 필요)
    sudo usermod -aG docker ec2-user

    # docker-compose 설치
    sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

    # 권한 부여
    sudo chmod +x /usr/local/bin/docker-compose
    ```

# [Docker Volumns](https://docs.docker.com/storage/volumes/)
- 데이터와 컨테이너를 분리하기 위해 사용.
- `/var/lib/docker/volumes/`
- bind mount와 다르게 완전히 도커에 의해 관리되고 시스템 구조에 영향받지 않음.

![image](https://github.com/hana2set/study/assets/97689567/080cf00b-04c5-4bce-b359-4563ee2f40e7)

- 장점
    - 마이그레이션이 쉬움
    - Docker CLI, API로 관리 가능
    - 여러 컨테이너에서 안전하게 공유 가능
    - 기본 바인딩보다 빠름

### bind mount
- 컴퓨터의 특정 폴더나 파일을 컨테이너에 연결

### tmpfs mount
- 메모리에 일시적으로 데이터를 저장

<br>

### 예제
- 볼륨 생성해보기 
    ```bash
    docker volume create datavol
    docker volume ls
    # datavol 볼륨을 /data 경로에 바인드 시킨 알파인 리눅스 컨테이너 생성
    docker container run -ti --rm -v datavol:/data alpine


    # docker container 내에서 demo.txt 파일 생성 후 종료
    echo "볼륨 데모" > /data/demo.txt
    exit

    # Host Machine에서 alpine을 이용한 컨테이너 삭제 확인
    docker container ls 

    # 새로운 이미지와 새로운 컨테이너에서 demo.txt 파일 확인
    docker container run --rm -v datavol:/data ubuntu cat /data/demo.txt
    ```

- tree를 설치해 실제 볼륨 디렉토리 확인
    ```bash
    sudo apt update
    sudo apt install -y tree
    sudo tree -a /var/lib/docker/volumes/datavol 
    ```
- 암시적 볼륨 마운트
    ```bash
    docker run -d --name mysqltest -v /var/lib/mysql mysql:latest
    docker inspect mysqltest 
    ```
    ![image](https://github.com/hana2set/study/assets/97689567/b50956e7-66d1-4be6-bdda-399767942159)
    ```bash
     # `Name` 을 확인법
    docker inspect mysqltest | jq .[].Mounts
    docker volume ls
    ```
- readonly와 readwrite(default) 디렉토리 마운트
    ```bash
    cd ~
    mkdir readonly
    mkdir readwrite
    docker run -ti -v ~/readonly:/readonly:ro -v ~/readwrite:/readwrite:rw ubuntu
    ```

# [Docker Network](https://docs.docker.com/network/)
- container는 격리된 환경(sendbox)이기 때문에, 이를 위한 컨테이너간 통신 기능.  
- 기본적으로 [Bridge 네트워크](https://docs.docker.com/network/drivers/bridge/)방식을 통해 네트워크를 __분리__ 함
    > `Bridged Networking`  
    > 호스트의 네트워크와 게스트의 네트워크를 브릿지하여(연결하여) 게스트 컴퓨터가 네트워킹 하는 방식

### CNM(Container Network Model)
- CNM은 도커 네트워크의 설계도, libnetwork가 그에 대한 표준 구현체 component  


[libnetwork 디자인 설계](https://github.com/moby/libnetwork/blob/master/docs/design.md)  
![image](https://github.com/hana2set/study/assets/97689567/bf87b6f9-ceff-4e27-a5c7-81cc8d2e78d1)

<details>
    <summary>예제</summary>

- 테스트용 서비스 실행
    ```bash
    # back, front 네트워크 생성
    docker network create --driver=bridge back
    docker network create --driver=bridge front

    # 각 서비스를 생성 및 실행
    docker run --name=webapi -itd --net=front ubuntu:14.04
    docker run --name=catalog -itd --net=back ubuntu:14.04
    docker run --name=database -itd --net=back ubuntu:14.04
    
    # catalog 서비스는 기본 back 네트워크 뿐만 아니라 front 네트워크에도 연결
    docker network connect front catalog
    ```
- 네트워크 조회
    ```bash
    # 각 서비스 라우팅 테이블 조회
    docker exec webapi route
    docker exec catalog route
    docker exec database route

    # 네트워크 조회
    docker network inspect front # webapi / catalog
    docker network inspect back # catalog / database
    ```
    ![image](https://github.com/hana2set/study/assets/97689567/4500a9c4-e76b-4afc-8e34-5ac36630ed5b)

- 내부에서 연결 확인
    ```bash
    docker exec -it webapi bash
    # 머신 안에서
    ping -c 1 catalog # 가능
    ping -c 1 database # 연결 불가능
    exit

    # 
    docker exec -it catalog bash
    # 머신 안에서
    ping -c 1 webapi # 가능
    ping -c 1 database # 가능
    exit
    ```
    ![image](https://github.com/hana2set/study/assets/97689567/5c04ed8d-4333-4ec1-8dc0-c8cf9d0bf520)
- 리소스 정리
    ```bash
    docker network disconnect front catalog

    docker stop webapi catalog database

    docker rm webapi catalog database

    docker network rm back

    docker network rm front

    docker network ls
    ```



</details> 

<br>

# 모니터링&로깅 (Container)

## 리소스 모니터링
- `docker stats` || `docker stats [컨테이너 이름 또는 ID]`
- `htop`
- `df` (disk free) : 시스템 전체 디스크 사용량 
    ```bash
    df -h
    ```
- `du` : 디렉토리 별로 사용 공간
    ```bash
    du -sh # GB 단위로
    du -h --max-depth=1 # 한 단계 아래 디렉토리 까지만 
    ```
## 로깅
- 기본적으로 JSON 형태로 도커 내부에 저장 (표준 출력, 표준 에러)
- 경로(ubuntu): `/var/lib/docker/containers/[컨테이너ID]/[컨테이너ID]-json.log`
    ```docker
    docker run --name logs-test --rm -d ubuntu:22.04 /bin/bash -c 'while true; do date; sleep 1; done'

    # logs-test 컨테이너의 로그를 전체 출력하기
    docker logs logs-test

    # logs-test 컨테이너의 로그를 tailing하기
    docker logs -f logs-test

    # 마지막 10줄부터 로그를 계속 보기
    docker logs -f --tail 10 logs-test
    ```
- 로그 로테이션 설정
    ```docker
    docker run -d \
    --log-driver json-file \
    --log-opt max-size=10m \
    --log-opt max-file=10 \
    --name nginxtest \
    --restart always \
    -p 80:80 \
    -p 443:443 \
    nginx:latest
    ```
- compose 파일에서 설정
    ```yaml
    services:
        app:
            ...
            logging:
            driver: 'json-file'
            options:
                max-size: '10m'
                max-file: '10'

    ```



    
# etc.

- docker Architecture
    ![image](https://github.com/hana2set/study/assets/97689567/657366df-94b8-4206-8f11-b8863f44fa51)

- <details> 
    <summary> docker exit code </summary>

    - 0
        - Docker Process가 수행해야 할 모든 Command 또는 Shell을 실행하고 정상 종료
    - 255
        - Docker Image에 정의된 EntryPoint 또는 CMD가 수행이 완료되었을 경우 발생
    - 125
        - Docker run 명령어의 실패로 실제 docker process가 기동되지 않음
    - 126
        - Docker Container 내부에서 Command를 실행하지 못할 경우 발생
    - 127
        - Docker Container 내부에서 Command를 발견하지 못하였을 경우 발생
    - 137
        - kill -9로 인해 종료 됨
    - 141
        - 잘못된 메모리 참조하여 종료 됨
    - 143
        - Linux Signal로 정상 종료 됨
    - 147
        - 터미널에서 입력된 정지 시그널로 종료 됨
    - 149
        - 자식 프로세스가 종료 되어 종료 됨
  > https://medium.com/@seifeddinerajhi/understanding-common-exit-codes-and-error-messages-in-containers-kubernetes-fb8d053e759e

</details>

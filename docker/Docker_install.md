# Docker 설치

Windows 는 WSL 설치해야함.
(Docker Desktop도 설치 권장)
  
### WSL2 설치
> WSL란?  
별도의 가상 머신 또는 이중 부팅 없이 Windows 컴퓨터에서 Linux 환경을 실행할 수 있는 Windows의 기능
1. 관리자 권한으로 cmd 열기
    ```bash
    wsl --install
    ```
2. 2024.03.28 기준으로 Ubuntu 22.04가 기본값으로 설치됨
    ![image](https://github.com/hana2set/study/assets/97689567/89b06e8f-872d-496f-93d4-83ad8fc05e2c)
3. 이후 ubuntu 앱으로 실행시킬 수 있음.

# Docker 설치 (Ubuntu) 
- 설치된 ubuntu 안에서 다음 명령어 순차적으로 실행
    ```bash
    # docker engine gpg 키 등록
    sudo apt-get update
    sudo apt-get install -y ca-certificates curl gnupg
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

    # docker 자동실행 설정
    sudo systemctl enable docker
    sudo systemctl start docker
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

---

출처:
https://docs.docker.com/desktop/install/windows-install/
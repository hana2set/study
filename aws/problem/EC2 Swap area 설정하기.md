## EC2에서 Swap 메모리를 할당하는 이유
EC2 프리티어는 기본적으로 1GB의 메모리를 제공하는데 부족한 메모리량으로 간간히 문제가 되곤 한다.  
이 문제를 해결하기 위해 SWAP 메모리를 지정하곤 한다.
> SWAP 메모리: 디스크 공간을 활용하여 물리적 RAM을 확장한 것. RAM이 부족할 경우 사용하게 됨

- 프리티어 정보
![image](https://github.com/hana2set/study/assets/97689567/857cc74b-f025-453f-82d7-81c5a53aaafe)

- 앱을 빌드하다가 멈춘 모습
![image](https://github.com/hana2set/study/assets/97689567/098040f6-94e0-4d86-b206-6c1595093d0d)


## Swap 메모리 할당
1. 일반적으로 메모리 2배 크기의 Swap area를 권장하기 때문에 /swapfile 파일을 2GB로 할당
    ```cmd
    sudo dd if=/dev/zero of=/swapfile bs=128M count=16
    ```
    ![image](https://github.com/hana2set/study/assets/97689567/40af4f21-e6a8-4682-8812-b8e893105b45)

    - `dd`: 블록 단위로 파일을 복사하거나 파일을 변환하는 명령어
        - `if`: File, 지정한 파일을 입력 대상으로 설정.
        - `of`: File, 지정한 파일을 출력 대상으로 설정.
        - `bs`: Bytes, 한번에 읽고 쓸 최대 바이트 크기
        - `count`: Blocks, 지정한 블록 수 만큼 복사
    - `/dev/zero`: null로 채워진 특수 파일

2. 파일 권한을 보호하고 파일을 스왑으로 포맷
    ```cmd
    sudo chmod 600 /swapfile
    sudo mkswap /swapfile
    ```

3. 스왑 활성화
    ```cmd
    sudo swapon /swapfile
    ```

4. 변경사항을 영구적으로 만들기 위해 /etc/fstab 파일을 수정
    ```cmd
    vim /etc/fstab

    <!-- 추가 -->
    /swapfile none swap sw 0 0
    ```
    ![image](https://github.com/hana2set/study/assets/97689567/b0f7ba53-40ac-4063-a057-1ab47e130ebe)

5. 변경사항 확인
    ```cmd
    swapon --show
    ```
    ![image](https://github.com/hana2set/study/assets/97689567/b6b67d3c-0923-4f59-9353-04e33f12c470)


### 멈췄던 build를 재실행

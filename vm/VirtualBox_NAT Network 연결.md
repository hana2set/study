VirtualBox 7.0.18

# 우분투 설치
- sudo 권한 주기 (재부팅)
  ```sh
  su
  usermod -aG sudo <계정명>
  ```
- 리눅스에서 openssh 설치 필요 (https://zmunz.tistory.com/1)
    ```sh
    # ifconfig 용도
    sudo apt install net-tools

    sudo apt install ssh
    sudo apt install openssh-server
    sudo /etc/init.d/ssh restart

    ifconfig
    ```

# NAT 네트워크 추가
1. 만들기
  ![image](https://github.com/user-attachments/assets/4d66eb3e-0d65-44e1-8d15-0ddf5bc19874)  
  ![image](https://github.com/user-attachments/assets/c1f7d8e8-0887-44e3-b298-ba689e284639)  
2. 포트 포워딩 추가
  ![image](https://github.com/user-attachments/assets/f391de61-1193-4492-8290-8454a795f98b)  

# 가상머신 내부에서 IP 주소 변경
1. 주소 변경하기
  ![image](https://github.com/user-attachments/assets/91217aa8-3751-4e76-8073-9846ec816299)  
  ![image](https://github.com/user-attachments/assets/de599396-6480-4b42-909c-6f1e4ab4bfb6)  
  ![image](https://github.com/user-attachments/assets/8990a7bd-0f8f-401f-b12d-238198996854)  
  Gateway를 x.x.x.1로 하지 않으면 인터넷 연결이 안됨 (https://www.nakivo.com/blog/virtualbox-network-setting-guide/)

2. 네트워크 재연결 후 변경사항 확인
  ![image](https://github.com/user-attachments/assets/e407782b-a5b6-4617-917e-58a5e891e529)

# putty로 포트 포워딩 확인
![image](https://github.com/user-attachments/assets/001e6379-5915-4ea2-a3fd-f1a2c825427f)  
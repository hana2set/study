- 우분투 설치시 터미널 안켜짐
  - settings - Language and Region - `Language를 English (Canada) 로 변경`
- 일반유저 sudo 권한 추가  
([username] is not in the sudoers file. This incident will be reported)
  ```sh
  su
  usermod -aG sudo <계정명>
  ```


- 호스트이름 변경
    ```sh
    # hostname 확인
    hostnamectl
    > temp-host

    # hostname 변경 , root로 해야됨
    su
    hostnamectl set-hostname master

    # 변경된 hostname 확인
    hostnamectl
    > master
    ```

- 게스트 ip 주소 변경
![image](https://github.com/user-attachments/assets/91217aa8-3751-4e76-8073-9846ec816299)  
![image](https://github.com/user-attachments/assets/de599396-6480-4b42-909c-6f1e4ab4bfb6)  
![image](https://github.com/user-attachments/assets/ca37a747-1dd3-4714-88c5-95a67ed98586)
  - 네트워크 재연결
  - ![image](https://github.com/user-attachments/assets/ea3e6ac6-e6b2-4f83-bea8-bc8b1b655fa2)
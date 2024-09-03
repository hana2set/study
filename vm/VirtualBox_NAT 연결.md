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

# VirtualBox 포트포워딩 설정
- Host, Guest에서 ip 주소 확인
- ubuntu - ifconfig
  ![image](https://github.com/user-attachments/assets/7ff9e418-e296-4204-8864-510ea725b76f)
- window - cmd
  ![image](https://github.com/user-attachments/assets/75ee4bd8-e0f3-4a3e-a894-90b31dbc8122)
- 포트포워딩 설정
  ![image](https://github.com/user-attachments/assets/c8d6d60f-29aa-4ead-a10d-062d6e04d1df)  
  ![image](https://github.com/user-attachments/assets/f1f3eda3-71a4-40ea-abeb-97d908db77b4)  
  ![image](https://github.com/user-attachments/assets/1a244398-68f9-4c73-8bef-93cce7073938)  
  ![image](https://github.com/user-attachments/assets/b974e29c-b181-48b1-932d-01d3dd434ebc)
>> 

https://depotceffio.tistory.com/entry/%EA%B0%80%EC%83%81%EB%A8%B8%EC%8B%A0-%EC%9A%B0%EB%B6%84%ED%88%AC-SSH-%EC%9B%90%EA%B2%A9-%EC%A0%91%EC%86%8D%ED%95%98%EA%B8%B0
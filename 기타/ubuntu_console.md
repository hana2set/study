# ubnutu

- tail
    ```bash
    tail -f
    ```
- systemctl: 서비스 관리
    ```bash 
    # 서비스 상태 확인
    systemctl status <서비스명>

    # 서비스 시작
    systemctl start <서비스명>

    # 자동시작
    systemctl enable <서비스명>

    # enabled 된 모든 서비스
    sudo systemctl list-units --state=enabled

    ```
- port 확인
  ```bash
  netstat -an|grep :22
  netstat -tnlp
  ```

- ssh 서버 설정
    ```sh
    sudo vi /etc/ssh/sshd_config
    ```
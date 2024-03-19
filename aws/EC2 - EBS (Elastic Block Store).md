# EBS (Elastic Block Store)
Amazon EC2 인스턴스에 연결할 수 있는 블록 수준 스토리지 볼륨

- 인스턴스의 usb 메모리라고 생각할 수 있음
- 하나의 인스턴스에 여러 EBS
- EBS 상태를 저장하고 싶다 -> EBS snapshot

<br>

- 직접 붙인 EBS는 Delete on termination가 기본적으로 꺼져있음.
    - 설정하면 인스턴스 삭제시 같이 삭제 가능

# EBS snapshot
- 용도
    - 데이터 백업 및 복원
    - 볼륨 확장
    - 데이터 마이그레이션

<br>

- 볼륨에서 우클릭 - create snapshot
- 스냅샷을 만들면 
    - 지역이동 가능
    - 복제 가능
    - 복구 가능
<br>

# 볼륨 장착
- 인스턴스 생성시 자동 생성된 볼륨

    ![image](https://github.com/hana2set/study/assets/97689567/54bca086-f5ad-4cc1-8e8f-82bed0b4a850)
    
    - network - availability zone 이 일치하는 volume만 장착 가능 -> create volume 할때 확인할 것

    ![image](https://github.com/hana2set/study/assets/97689567/6dba7824-fde8-4638-acc8-0e8162f9d517)


- 볼륨 확인

    ![image](https://github.com/hana2set/study/assets/97689567/df95c5f9-0014-4eed-84e7-bc9e01a51220)

- 생성 후 attach

    ![image](https://github.com/hana2set/study/assets/97689567/383b774c-ea42-4619-a977-9ed9f81832de)

- attach 확인

    ![image](https://github.com/hana2set/study/assets/97689567/04af0ab1-18bf-4990-a80d-c8ab2aa48db3)

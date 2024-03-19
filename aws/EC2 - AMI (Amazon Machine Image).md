# AMI (Amazon Machine Image)

EC2 인스턴스를 생성할 때 사용되며 운영 체제와 애플리케이션, 라이브러리 등이 포함 (= 설치 CD)

# AMI 만들어보기

0. 기존 인스턴스에서 테스트용 텍스트 파일 생성 (skip)

    ![image](https://github.com/hana2set/study/assets/97689567/08064699-40ce-4e38-b427-fb7a2f5e4178)

1. 이미지 생성 (기존 Instance를 AMI으로 만들기)

    ![image](https://github.com/hana2set/study/assets/97689567/7af9c68e-421e-4cd6-a3ea-82d063b73a6d)

2. Launch instance from AMI 선택

    ![image](https://github.com/hana2set/study/assets/97689567/dde6e6b4-1b4a-4c5f-8f5f-65874a7e443a)

3. 인스턴스의 파일이 그대로 옮겨진걸 확인할 수 있음. 

    ![image](https://github.com/hana2set/study/assets/97689567/64e2ecc7-92ab-4092-bc3f-d79abeec66fe)  
    (주의 - AMI으로 인스턴스 생성하면 유저가 달라짐으로, 기존 인스턴스와 유저를 맞출 것)


# 개요
파사드란 객체 지향 디자인 패턴으로 하위 시스템의 인터페이스 집합에 대한 간단한 통합 인터페이스를 제공하는 것.
쉽게 말해, 하위 시스템을 캡슐화 한다.

# 이점

1. 단순화
2. 독립성
3. 유지보수성

# 주의사항

1. 과도하게 사용 시 오히려 난독화할 수 있음. 단순화된 보기를 제공하는 데에만 사용해야함
2. 만능 패턴이 아님. 시나리오에 따라 적절히 선택해야함

# 권장사항

1. 최대한 가볍게 유지
2. 결합을 느슨하게
3. 문서화


# 개인 경험 사례
- AirDNS
    - 도메인별 패키지 분리를 위해 각 레포지토리는 하나의 서비스에서만 참조하도록 설계함
    - 주요 도메인인 Room과 Reservation이 서로 순환참조 
        - 방에 대한 예약 목록이 조회가 가능해야함
        - 예약시 방 정보 조회가 가능해야함.
    -  **파사드 패턴**을 Rooms와 Reservations에 도입함으로 순환참조 문제 해결
    - 다음 링크 참조: [Facade Pattern - Spring Framework Guru](https://springframework.guru/gang-of-four-design-patterns/facade-pattern/)
    ![image](https://github.com/hana2set/study/assets/97689567/c83468d7-f1de-469a-9fc4-8b692c1e8d38)
    ![image](https://github.com/hana2set/study/assets/97689567/bda0baee-5f58-4d15-81f8-268332670f40)
    ![image](https://github.com/hana2set/study/assets/97689567/450663e7-1f5b-42a3-8bc9-ff1352dce534)


> 출처:  
https://medium.com/hprog99/mastering-the-facade-design-pattern-in-java-a-comprehensive-guide-with-practical-examples-f3a19f58440e
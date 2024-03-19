# RDS (Relational Database Service)
- 장점 (vs. DB)
    - DB를 위한 인프라를 자동으로 구축(provisioning), 업데이트
    - 지속적 백업, 복구 지원
    - 모니터 대시보드 지원
    - read replicas 지원 (검색 전용 복제)
    - multi AZ 지원 (재난 복구용)
    - 수평/수직 확장성 지원
    - EBS 백업 지원

### Read Replicas

- SELECT문 전용 복제품 생성
    ![image](https://github.com/hana2set/study/assets/97689567/432b1763-7435-4caf-9f39-69cac1635207)

### Multi AZ

- 백업용
    ![image](https://github.com/hana2set/study/assets/97689567/e8d7ff3a-9043-41a3-85eb-8a0341d064ff)
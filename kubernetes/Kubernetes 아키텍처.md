
# 동작 원리 (Flow)

![image](https://github.com/user-attachments/assets/94a34b02-5092-430f-8182-0956a3147f54)

1. 로컬에 docker를 통해 컨테이너 생성
2. 생성한 컨테이너 이미지를 컨테이너 저장소(Docker Hub)에 푸쉬
3. kubectl 명령어로 생성한 컨테이너가 실행되도록 요청함
4. kubectl 명령이 Master node(REST API Server)에 전달됨
5. 스케쥴러를 통해 Work node 선택, pod 할당
6. 스케쥴러가 해당 노드 kubelet에 pod 생성 요청을 보냄
7. kubelet이 docker 데몬에게 실제 컨테이너 생성을 요청
8. docker 데몬이 Docker Hub에서 이미지를 생성 후 실행


# [쿠버네티스 클러스터(Cluster)](https://kubernetes.io/docs/concepts/overview/components/)
__작동 중인 쿠버네티스의 배포.__ Linux 컨테이너를 실행하는 호스트 그룹을 의미
![image](https://github.com/hana2set/study/assets/97689567/3519e8bd-e5e3-402e-8bb0-37a3da0823c8)
![image](https://github.com/hana2set/study/assets/97689567/0037a49f-430f-4b3a-8b78-de2705576f87)

> pod란?
> - 쿠버네티스에서 생성하고 관리할 수 있는 배포 가능한 가장 작은 컴퓨팅 단위
> - 하나 이상의 컨테이너의 그룹.
> - 스토리지 및 네트워크를 공유하고, 해당 컨테이너를 구동하는 방식에 대한 명세를 가짐.
> - 파드의 콘텐츠는 항상 함께 배치되고, 함께 스케줄되며, 공유 콘텍스트에서 실행됨.

### 요소
#### 컨트롤 플레인 컴포넌트(마스터 노드)
- 클러스터에 관한 전반적인 결정(예를 들어, 스케줄링)을 수행
- 클러스터 이벤트를 감지하고 반응
- 구성요소
  - **API 서버 (`kube-apiserver`)**
      - 쿠버네티스 API를 노출하는 쿠버네티스 컨트롤 플레인 컴포넌트
      - API 서버는 쿠버네티스 컨트롤 플레인의 프론트 엔드
      - 클라이언트와 시스템 컴포넌트 간의 메인 커뮤니케이션 허브 역할
      - 요청 처리 및 인증, 인가, 접근 제어

  - **저장소 (`etcd`)**
      - 모든 클러스터 데이터를 저장하는 키-값 저장소로서의 기능
      - 데이터 일관성과 안정성 보장 방법임
      - 분산 시스템을 계속 실행하는 데 필요한 중요한 정보를 보관하고 관리하는 데 사용되는 키-값 저장소

  - **스케쥴러 (`kube-scheduler`)**
      - 노드가 배정되지 않은 새로 생성된 파드를 감지하고, 실행할 노드를 선택하는 컴포넌트
      - 사용 가능한 노드를 평가하고 최적의 노드에 파드를 할당하는 프로세스 진행

  - **컨트롤 매니저 (`kube-controller-manager`)**
      - 컨트롤러 프로세스를 실행하는 컴포넌트
      - 종류 
      - **노드 컨트롤러**: 노드가 다운되었을 때 통지와 대응에 관한 책임을 가짐
      - **잡 컨트롤러**: 일회성 작업을 나타내는 잡 오브젝트를 감시한 다음, 해당 작업을 완료할 때까지 동작하는 파드를 생성함
      - **엔드포인트슬라이스 컨트롤러**: (서비스와 파드 사이의 연결고리를 제공하기 위해) 엔드포인트슬라이스(EndpointSlice) 오브젝트를 채움
      - **서비스어카운트 컨트롤러**: 새로운 네임스페이스에 대한 기본 서비스어카운트(ServiceAccount)를 생성함

  - `cloud-controller-manager`
      - 클라우드별 컨트롤 로직을 포함하는 컴포넌트
      - 클러스터를 클라우드 공급자의 API에 연결하는 역할을 함

#### 노드 컴포넌트 (워커 노드)
- 동작 중인 파드를 유지시키고 쿠버네티스 런타임 환경을 제공
- 구성
  - `컨테이너 런타임` 
      - 컨테이너 실행을 담당하는 소프트웨어(엔진, ex. docker)
      - 쿠버네티스는 containerd, CRI-O와 같은 컨테이너 런타임 및 모든 Kubernetes CRI   (컨테이너 런타임 인터페이스) 구현체를 지원

  - `kube-proxy`
      - 클러스터의 각 노드에서 실행되는 네트워크 프록시
      - 노드의 네트워크 규칙을 유지 관리

  - `kubelet`
    - 모든 노드에서 실행되는 k8s 에이전트
    - 데몬 형태로 동작
      - Pod의 구성 내용을 받아 CRI 에 전달하고 컨테이너들의 동작 상태를 모니터링
      - 클러스터의 각 노드에서 실행되는 에이전트. Kubelet은 파드에서 컨테이너가 확실하게 동작하도록 관리
      - 다양한 메커니즘을 통해 제공된 파드 스펙(PodSpec)의 집합을 받아서 컨테이너가 해당 파드 스펙에 따라 건강하게 동작하는 것을 확실히 함
      - 쿠버네티스를 통해 생성되지 않는 컨테이너는 관리하지 않음

#### 애드온
- 네트워크
  - CNI (Container Network Interface)
    - 컨테이너간 통신을 지워하는 VxLAN
    - Pod Network라고도 부름
    - 다양한 플러그인 (플라넬, 칼리코, 워크넷 등)
- DNS
  - coreDNS
- 대시보드
- 컨테이너 자원 모니터링
  - cAdvisor
- 클러스터 로깅
  - 컨테이너 로그, k8s 운영 로그
  - ELK(ElasticSearch, Logstash, Kibana), EFX(ElasticSearch, Fluentd, Kibana), DataDog

# namespace

단일 클러스트 내에서 사용하는 논리적 리소스 그룹 격리 매커니즘.
![image](https://github.com/user-attachments/assets/2211121c-30d2-47f4-9a52-8bcaac56becc)  
→ 기본 제공하는 네임스페이스

- namespace 조회
    ```bash
    kubectl get namespace
    ```
- namespace 생성
    ```bash
    # 실제 생성할 수 있는지 확인만(dry-run) 하고 ymal 파일로 남기기
    kubectl create namespace orange --dry-run -o yaml > orange-ns.yaml
    kubectl create -f orange-ns.yaml
    ```
- 요청에 네임스페이스 설정
    ```bash
    kubectl run nginx --image=nginx --namespace=<insert-namespace-name-here>
    kubectl get pods --namespace=<insert-namespace-name-here>
    ```
- 선호하는 네임스페이스 설정하기
    ```bash
    kubectl config set-context --current --namespace=<insert-namespace-name-here>
    ```
- 확인하기
    ```bash
    kubectl config view --minify | grep namespace:
    ```
- 네임스페이스에 속하지 않는 쿠버네티스 리소스를 조회하는 방법
    ```bash
    # 네임스페이스에 속하는 리소스
    kubectl api-resources --namespaced=true

    # 네임스페이스에 속하지 않는 리소스
    kubectl api-resources --namespaced=false
    ```
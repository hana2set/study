# 목표
![image](https://github.com/user-attachments/assets/c691c8ed-08d6-4513-80d4-51d7447aa956)

# 시작하기 전
- VirtualBox
  - https://www.virtualbox.org/wiki/Downloads
  - vs VirtualBox
  https://www.shiksha.com/online-courses/articles/vmware-vs-virtualbox-whats-the-difference-blogId-151329
- Ubuntu Download (Ubuntu 24.04 LTS)
  - https://ubuntu.com/download
- PuTTY Download (SSH 접속프로그램)
  - https://www.putty.org/
- MobaXterm Download (SSH 관리)
  - https://mobaxterm.mobatek.net/

- Ubuntu 3개 설치
  - kubernetes 설치 요구사항
    - 2 GB or more of RAM per machine (작으면 앱 설치 공간 부족).
    - 2 CPUs or more.
    - You **MUST** disable swap
  - Guest OS 리눅스 이름 k8s-master, k8s-node1, k8s-node2 으로 설정
    ![image](https://github.com/hana2set/study/assets/97689567/714a355c-e183-40ee-9178-0e6b86684adb)
  - 가상머신 간의 통신이 필요함으로 (외부 연결도 필요 : 인터넷), 일반 NAT 통신이 아닌 [NAT Network 방식](<../vm/VirtualBox_NAT Network 연결.md>)을 통해 연결 후 putty로 연결함.
  - 호스트 이름 변경
    ```sh
    vi /etc/hostname # master -> master.example.com
    ```
  - [docker 설치]("")
  - kubernetes 설치

# [kubernetes 설치](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/)

## 설치 전 환경설정
```bash
# Swap 비활성화
swapoff -a && sed -i '/swap/s/^/#/' /etc/fstab

# 방화벽 제거 (일반적으로 쿠버네티스 시스템 도달 전에 방화벽이 있음)
# 아니면 포트 활성화 해야함 (링크 참조)
sudo systemctl stop ufw
sudo systemctl disabled ufw
```
- 1.24 버전 이하는 Container Runtimes 설치 필요 (kubernetes에 containerd가 포함되어있음)

## [kubeadm, kubelet, kubectl 설치](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/)
- `kubeadm`: 클러스터 관리 명령어
- `kubelet`: 클러스터 내 컨테이너 조작, 통신 등을 관리하는 데몬
- `kubectl`: 클러스터 통신 명령어

```bash
sudo apt-get update
sudo apt-get install -y apt-transport-https ca-certificates curl gpg

# 공개 키 다운로드
# 주의! : Ubuntu 22.04 이전 버전은 `/etc/apt/keyrings`가 기본적으로 없음으로, 만들어줘야함
# sudo mkdir -p -m 755 /etc/apt/keyrings
curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.31/deb/Release.key | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg

# Kubernetes apt저장소를 추가 (버전 일치 확인할 것)
echo 'deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.31/deb/ /' | sudo tee /etc/apt/sources.list.d/kubernetes.list

# kubelet, kubeadm, kubectl 설치 후 버전 고정
sudo apt-get update
sudo apt-get install -y kubelet kubeadm kubectl
sudo apt-mark hold kubelet kubeadm kubectl

# kubelet 활성화
sudo systemctl enable --now kubelet

# CRI 재실행
sudo systemctl daemon-reload
sudo systemctl enable --now containerd
```

## [control-plane 구성](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/#initializing-your-control-plane-node)
- **마스터 노드에서만** 실행할 것 (워크노드에선 실행해선 안됨)
1. kubdadm 초기화
    ```bash
    kubeadm init
    # CRI가 없다고 나올경우 실행
    # sudo systemctl daemon-reload
    # sudo systemctl enable --now containerd
    ```
    ![image](https://github.com/user-attachments/assets/729f51ae-45d6-4f9c-9f34-221daa76438b)
2. 토큰 저장
    ```bash
    # 위 token 저장용 파일 생성
    cat > token.txt
    kubeadm join 10.100.0.104:6443 --token i1geb8.ck9bwoup6j5d17v8 \
        --discovery-token-ca-cert-hash sha256:8b2110388d889716812a4a970949963e0ca55d9c437bb06faef3704cf8f68250

    # 참조 단축키 - ctrl+insert : 복사
    #            - ctrl+shift+insert : 붙여넣기
    #            - ctrl+D : (입력) 종료
    ```
3. 일반 유저도 kubectl 명령어를 사용할 수 있도록 권한 추가
    ```bash
    # 사용할 유저 홈디렉토리 마다 추가해줘야함
    mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config

    # 허가 되었는지 확인
    kubectl get nodes
    ```
    ![image](https://github.com/user-attachments/assets/caf3e391-762d-44ee-81d0-92faa67c5ab8)
4. Pod network 에드온 설치 (그래야 status -> Ready로 변경됨)
   - 종류가 많아서 선택해야 하는데, [Weave Net](https://github.com/rajch/weave#using-weave-on-kubernetes)으로 설치할 예정
   ```bash
   # kubernetes 버전에 따라 버전 변경할 것
   kubectl apply -f https://reweave.azurewebsites.net/k8s/v1.31/net.yaml
   ```
   ![image](https://github.com/user-attachments/assets/f46e13e9-839e-45bf-ad8a-afae917503a1)

## worker node 구성
- 위 저장해둔 token.txt 파일 명령어 그대로 worker node에서 실행
  ```bash
  kubeadm join 10.100.0.104:6443 --token i1geb8.ck9bwoup6j5d17v8 \
        --discovery-token-ca-cert-hash sha256:8b2110388d889716812a4a970949963e0ca55d9c437bb06faef3704cf8f68250
  ```
- cri 관련 에러 발생시, cri 재시작
  ```bash
  rm /etc/containerd/config.toml
  systemctl restart containerd
  ```
- worker node 초기화
  ```sh
  # worker node에서 실행
  kubeadm reset

  # matser node에서 실행
  sudo kubectl delete node wnode01
  ```


--- 

## [cheatsheet(자동완성) 다운로드](https://kubernetes.io/ko/docs/reference/kubectl/cheatsheet/)
```bash
source <(kubectl completion bash) # bash-completion 패키지를 먼저 설치한 후, bash의 자동 완성을 현재 셸에 설정한다
echo "source <(kubectl completion bash)" >> ~/.bashrc # 자동 완성을 bash 셸에 영구적으로 추가한다

# kubeadm도 동일
source <(kubeadm completion bash) 
echo "source <(kubeadm completion bash)" >> ~/.bashrc 
```

---

관련 링크  
https://www.youtube.com/watch?v=lheclzO-G7k&list=PLApuRlvrZKohaBHvXAOhUD-RxD0uQ3z0c&index=71  
https://github.com/237summit/k8s_core_labs
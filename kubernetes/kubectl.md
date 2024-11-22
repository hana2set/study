# kubectl이란?
kubectl는 쿠버네티스 커맨드 라인 도구

```sh
kubectl [command] [TYPE] [NAME] [flags]
```
- command : 자원(obejct)에 실행 명령 (create, get, delete, edit)
- TYPE : 자원의 타입 (node, pod, service)
- NAME : 자원의 이름
- flags : 부가적으로 설정할 옵션 (–help, -o options)

예시
```sh
kubectl get pod webserver -o wide
```

## 자동완성 사용법 ([cheatsheet](https://kubernetes.io/ko/docs/reference/kubectl/cheatsheet/))
```bash
# bash-completion 패키지를 먼저 설치한 후, bash의 자동 완성을 현재 셸에 설정한다
# 자동 완성을 bash 셸에 영구적으로 추가한다
source <(kubectl completion bash) 
echo "source <(kubectl completion bash)" >> ~/.bashrc 
```

- kubeadm도 동일
  
## 자주 쓰는 커맨드
- 모든 명령어 확인
    ```sh
    kubectl --help
    kubectl command --help
    ```
- 기본 명령어
    ```sh
    kubectl run <자원 이름> <옵션> # 컨테이너 파드 생성
    kubectl cretate -f obj.yaml
    kubectl apply -f obj.yaml
    ```
    ```sh
    kubectl get <자원 이름> <객체이름>
    kubectl edit <자원 이름> <객체이름>
    kubectl desribe <자원 이름> <객체이름> # 자세하게 자원 확인
    ```
    ```sh
    kubectl delete <자원 이름> <옵션>
    ```
- 자원 확인
    ```sh
    kubectl api-resources # 명령어 종류 확인
    kubectl --help
    kubectl logs --help
    ```
- 컨테이너 내부로 접속
    ```sh
    kubectl exec <파드 이름> -it -- /bin/bash
    ```
- 모든 파드 상태 확인
    ```sh
    kubectl get pod --all-namespaces
    ```
- nginx pod 생성하기
    ```sh
    kubectl run webserver --image=nginx:1.14 --port 80
    kubectl get pods
    ```
- webserver 파드를 실행할 수 있는 지 yaml 파일로 확인하고 그 내용을 webserver-pod.yaml로 저장 -> pod 실행(생성) 파일로 사용 가능
    ```sh
    kubectl run webserver --image=nginx:1.14 --port 80 --dry-run -o yaml > webserver-pod.yaml
    ```
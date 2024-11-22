- kubeadm init
    ```
    Unfortunately, an error has occurred:
            context deadline exceeded
    ```
  - 상세 내용 <details> <summary> 펼치기 </summary>

    ```
        Unfortunately, an error has occurred:
                context deadline exceeded

        This error is likely caused by:
                - The kubelet is not running
                - The kubelet is unhealthy due to a misconfiguration of the node in some way (required cgroups disable           d)

        If you are on a systemd-powered system, you can try to troubleshoot the error with the following commands:
                - 'systemctl status kubelet'
                - 'journalctl -xeu kubelet'

        Additionally, a control plane component may have crashed or exited when started by the container runtime.
        To troubleshoot, list all containers using your preferred container runtimes CLI.
        Here is one example how you may list all running Kubernetes containers by using crictl:
                - 'crictl --runtime-endpoint unix:///var/run/containerd/containerd.sock ps -a | grep kube | grep -v pa           use'
                Once you have found the failing container, you can inspect its logs with:
                - 'crictl --runtime-endpoint unix:///var/run/containerd/containerd.sock logs CONTAINERID'
        error execution phase wait-control-plane: could not initialize a Kubernetes cluster
        To see the stack trace of this error execute with --v=5 or higher
    ```
    </details>
  - 해결방안
    - cri가 없던걸로 추정됨
    - weave net 연결 `kubectl apply -f https://reweave.azurewebsites.net/k8s/v1.31/net.yaml`
    - https://github.com/kubernetes/kubeadm/issues/3069
  

- `Unable to connect to the server: dial tcp: lookup reweave.azurewebsites.net on 127.0.0.53:53: server misbehaving`
  ```sh
  ```

## kubectl Status `NotReady`
- https://komodor.com/learn/how-to-fix-kubernetes-node-not-ready-error/
- `kubectl get nodes`으로 상태 확인
- conditions 에서 이유 확인 가능
- ![image](https://github.com/user-attachments/assets/e6edc3b8-ba81-4f5d-9d7b-775547eebc81)
- `kubectl get node master -o yaml`로 자세히 보기 가능
- 그래도 안될 경우 `journalctl -u kubelet -f -b`로 시스템 로그 확인
- ![image](https://github.com/user-attachments/assets/7a2ea179-5890-4b51-b00b-5e61c06f49ef)
- 여기서는 node 이름을 찾을 수 없어서 발생한 문제



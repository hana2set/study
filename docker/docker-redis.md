- 설치
```bash
docker run -p 6379:6379 --name docker_redis redis
docker ps -a
docker start docker_redis
docker exec -it docker_redis /bin/bash
```

- 시스템 등록 (실행시 시작)
/etc/systemd/system/redis.service
```bash
systemctl daemon-reload
systemctl enable redis.service
systemctl start redis.service
```
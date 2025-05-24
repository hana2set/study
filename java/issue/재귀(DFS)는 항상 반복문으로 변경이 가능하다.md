### 사실 DFS는 반복문으로 변경이 가능함
```java
// 기존 소스
void dfs(Node node) {
    visited.add(node);
    for (Node neighbor : node.getNeighbors()) {
        if (!visited.contains(neighbor)) {
            dfs(neighbor);
        }
    }
}
```
```java
// 반복문으로 구성
void dfsIterative(Node start) {
    Stack<Node> stack = new Stack<>();
    stack.push(start);

    while (!stack.isEmpty()) {
        Node node = stack.pop();

        if (!visited.contains(node)) {
            visited.add(node);
            for (Node neighbor : node.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
    }
}
```

## DFS를 사용하는 이유? 
- **장점**
  - 직관적임
  - 코드가 간단해짐
- 단점
  - 성능 문제: **Java에서는 Tail Call Optimization (꼬리 재귀 최적화)를 지원하지 않음**
    - 그에 반해 함수 구성은 JIT 최적화(Inline 처리)로 손실이 거의 없음.
     


따라서 상황에 따라 고를 것!

### DFS 구현 방식 비교

| 기준                     | 재귀 DFS                     | 반복문                          |
|--------------------------|------------------------------|------------------------------------|
| 가독성                   | ✅ 간결하고 직관적           | ❌ 스택 관리 등으로 복잡도 증가   |
| 코드 길이                | ✅ 짧고 명확                  | ❌ 길고 반복 구조 필요             |
| 성능 (깊은 탐색)         | ❌ 스택 오버플로우 위험       | ✅ 안정적, 깊이 제한 없음         |
| 메모리 사용량 예측       | ❌ 어려움 (재귀 깊이에 따라 다름) | ✅ 쉬움 (스택 크기 조절 가능)     |
| 메모리 사용 방식         | JVM 호출 스택 사용           | 명시적 스택 (힙 메모리 사용)      |
| 추천 상황                | 알고리즘 문제, 작은 그래프   | 실서비스, 대규모/깊은 그래프      |

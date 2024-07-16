## 값 받기
Scanner sc = new Scanner(System.in);  
sc.next();  
sc.nextInt();  

BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
StringTokenizer st = new StringTokenizer(br.readLine());  
Integer.parseInt(st.nextToken());  


## 함수

int 최대값 2147483647(9자리 안전)
long 최대값 9,223,372,036,854,775,807 (18자리 안전)

"".length();  
"".indexOf();  
"".lastIndexOf();  
"".toCharArray();
"".toUpperCase();
"".toLowerCase();
"".substring(biginIndex, endIndex);
"".replaceAll("a", "b");
"".repeat(int cnt);

"".chars() : IntStream  
"".chars().map().toElse(null);  

String.valueOf(char[] a)  

Integer.parseInt(String s, int radix) - radix진법으로 s를 int로 변환
Integer.toString(int i, int radix) - radix진법으로 i를 변환


StringBuilder sb = new StringBuilder();
sb.append("*");
sb.toString();

// primaitve 타입 Comparator 안됨  
Arrays.sort(arr, Comparator.reverseOrder());  
Arrays.sort(arr, (o1, o2) -> o1.compareTo(o2));

Arrays.asList(arr<not primitive[]>);  

Arrays.copyOfRange(arr, start, end + 1);  
Arrays.deepToString(data);  // [[3, 2, 1], [4, 2, 3, 4]]
Arrays.fill(arr, -1);

Collections.addAll(resultList, array1);


toCharArray >>> split("") -> 성능 2배정도 차이남  
charAt > toCharArray -> 조금상승  


## 빈 Map 한번에 추가
map.computeIfAbsent(value, k -> new ArrayList<>());


## 동적 int[]를 만드는 최선의 방법?
 ArrayList에 더하고 배열로 변경이 최선 (2023/12/07 기준)
* for문 (최적화)
* list.stream().mapToInt(i -> i).toArray();


## 자료구조

Stack<Integer> stack = new Stack<>();
stack.push();
stack.pop();
stack.peek();
stack.clear();

Queue<Integer> queue = new LinkedList<>();
queue.add();
queue.poll();
queue.peek();
queue.clear();

PriorityQueue<Integer> priorityQueueHighest = new PriorityQueue<>(Collections.reverseOrder());  

TreeMap<Integer, String> treeMap = new TreeMap<>(Collections.reverseOrder()); // 입력된 정렬방식에 대해 Key값 자동정렬 


## 대수

### 약수의 개수
```
int N = 1000000000;

int count = 0;
for (int i = 1; i * i <= N; i++) {
	if (i * i == N) count++;
	else if (N % i == 0) count += 2;
}
```
코테기준 Math.sqrt보다 빠름. double 연산 casting 시간이 더해지는 것으로 추측중


## DFS (깊이우선탐색)

```java
class Solution {
    public int solution(int[] numbers, int target) {
        int answer = 0;
        answer = dfs(numbers, 0, 0, target);
        return answer;
    }
    int dfs(int[] numbers, int n, int sum, int target) {
        if(n == numbers.length) {
            if(sum == target) {
                return 1;
            }
            return 0;
        }
        return dfs(numbers, n + 1, sum + numbers[n], target) + dfs(numbers, n + 1, sum - numbers[n], target);
    }
}
```

## BFS (너비우선탐색)



### Sub
#### 문자열 회전
```java
    for (int i = 0; i < s.length(); i++) {
        for (int strtIdx = i; strtIdx < i + s.length(); strtIdx++) {
            int idx = strtIdx % s.length();
            char c = s.charAt(idx);
        }
    }
```




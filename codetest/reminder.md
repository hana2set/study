## 함수

int 최대값 2147483647(9자리 안전)
long 최대값 9,223,372,036,854,775,807 (18자리 안전)

"".length();  
"".indexOf();  
"".lastIndexOf();  
"".toCharArray();  
"".substring(biginIndex, endIndex);  
"".repeat(int cnt);  

"".chars() : IntStream  
"".chars().map().toElse(null);  

String.valueOf(char[] a)  


// primaitve 타입 Comparator 안됨  
Arrays.sort(arr, Comparator.reverseOrder());  
Arrays.asList(arr<not primitive[]>);  



toCharArray >>> split("") -> 성능 2배정도 차이남  
charAt > toCharArray -> 조금상승  


PriorityQueue<Integer> priorityQueueHighest = new PriorityQueue<>(Collections.reverseOrder());  


## 자료구조

Stack<Integer> stack = new Stack<>();
stack.push();
stack.pop();

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
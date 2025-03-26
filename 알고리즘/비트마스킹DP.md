- 원소수가 32개 이하일 경우, int 각 자리 숫자를 통해 집합을 나타냄.
- 비트 연산으로 추가,삭제, 등 가능
- 예시 ([백준 11723번](https://www.acmicpc.net/problem/11723))
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int m = Integer.parseInt(br.readLine());
        int s = 0;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();

            int num;

            // 원소수가 32개 이하일 경우, int 각 자리 숫자를 통해 집합을 나타냄.
            // 비트 연산으로 추가,삭제, 등 가능
            // 기본적으로 1을 자리수만큼 상승( 1 << 자리수 ) 시킨 후 연산자로 숫자를 계산
            switch (command) {
                case "add":
                    // |(or)연산자를 통해 해당 숫자 표현
                    num = Integer.parseInt(st.nextToken());
                    s |= (1 << (num - 1));
                    break;
                case "remove":
                    // 역(~)을 &(and)연산자를 통해 해당 숫자 표현
                    num = Integer.parseInt(st.nextToken());
                    s = s & ~(1 << (num - 1));
                    break;
                case "check":
                    // &(and)연산자를 통해 존재 여부 확인
                    num = Integer.parseInt(st.nextToken());
                    sb.append((s & (1 << (num - 1))) != 0 ? 1 : 0).append('\n');
                    break;
                case "toggle":
                    // ^(XOR)연산자 - 1,0 변환
                    num = Integer.parseInt(st.nextToken());
                    s ^= (1 << (num - 1));
                    break;
                case "all":
                    s |= (~0);
                    break;
                case "empty":
                    s &= 0;
                    break;
            }
        }
        System.out.print(sb);
    }
}
```
## faux bold

대부분 브라우저에서 `font-weight:bold`가 적용된 폰트에 대해, 굵은 폰트가 없다면 **인위적으로 굵게(faux bold)** 처리함.  
그런데 이런 **faux bold**는 브라우저마다 처리 방식이 다르기 때문에, 예기치 못한 랜더링을 발생시킴 (=**일관된 랜더링을 제공할 수 없다**)

<img width="624" height="181" alt="Image" src="https://github.com/user-attachments/assets/64b5322c-73b6-4ecb-9956-1d5ab81ffc4b" />

<img width="501" height="255" alt="Image" src="https://github.com/user-attachments/assets/1bf1b290-8d1d-4375-95a9-4595d0408223" />

모든 faux styles들은 이런 문제를 야기할 수 있으니, 최대한 막는 것이 좋다.
- 예시: 굵기별 폰트를 제공
    ```css
    @font-face {
    font-family: 'caros_eb';
    font-weight: 400;
    src: url(https://fonts.gstatic.com/s/opensans/v43/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsjZ0B4gaVI.woff2) format('woff2');
    }

    @font-face {
    font-family: 'caros_eb';
    font-weight: 700;
    src: url(https://fonts.gstatic.com/s/opensans/v43/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsg-1x4gaVI.woff2) format('woff2');
    }
    ```

> 출처:  
https://stackoverflow.com/questions/79683438/different-behaviour-edge-and-chrome-with-font-weight  
https://www.clien.net/service/board/park/16861847  
https://issues.chromium.org/issues/475259106
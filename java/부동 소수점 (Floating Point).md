# 부동 소수점(Floating-Point) 이란?
프로그래밍에서 소수를 표현하는 방식 중 하나. 소수점이 고정(Fixed-Point)되지 않고 이동한다고 하여 붙여진 이름.  
기본적으로 [`IEEE 754(IEEE 부동소수점 연산 표준)`](https://ko.wikipedia.org/wiki/IEEE_754)을 따른다.

## 구성
- IEEE 754 [단정밀도](https://ko.wikipedia.org/wiki/%EB%8B%A8%EC%A0%95%EB%B0%80%EB%8F%84_%EB%B6%80%EB%8F%99%EC%86%8C%EC%88%98%EC%A0%90%EC%88%98)(float, 32비트): **[부호 1] [지수 8] [가수 23]** 
    <img width="590" height="75" alt="Image" src="https://github.com/user-attachments/assets/64789339-20cc-4978-abd6-fce4e0ed04b9" />
    - 부호: +/-
    - 지수: 자릿수 (소수점 위치를 의미. 2<sup>e</sup> 에서 e의 보수. 여기서는 e+127)
    - 가수: 실제 유효 숫자 (이진수로 )
      - 예를 들어, 숫자가 이진수로 2<sup>4</sup> * 1.0101000 처럼 표현된다면, 4(+127)는 지수, 10101000는 가수
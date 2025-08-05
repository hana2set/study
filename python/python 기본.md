# 데이터 타입
## 자료형
```py
>>> type(10)
<class 'int'>
```

## 변수 
python은 동적 언어. 자료형을 상황에 맞게 자동 결정.
```py
>>> x = 10
>>> type(x)
<class 'int'>
```

## 리스트
```py
>>> a = [1,2,3,4,5]
>>> print(a)
[1, 2, 3, 4, 5]
>>> len(a)
5
>>> a[0]
1
```
- 슬라이싱
    ```py
    >>> a = [1,2,3,4,5]

    >>> a[0:2] # 인덱스 0~2
    [1, 2]

    >>> a[1:] # 인덱스 1부터 끝까지
    [2, 3, 4, 5]

    >>> a[:-2] # 인덱스 처음부터 -2까지
    [1, 2, 3]
    ```

## 딕셔너리
key, value로 저장하기 (java의 map)
```py
>>> me = {'height': 180}
>>> me['height']
180
```

## bool
```py
>>> hungry = True
>>> sleepy = False
>>> type(hungry)
<class 'bool'>
>>> not hungry
False
>>> hungry and sleepy
False
```

# 기본 문법
## if 문
```py
>>> if c > d:
...     print('c is greater than d')
... elif c == d:
...     print('c is equal to d')
... else:
...     print('I don\'t know')
```

## for 문
```py
>>> for x in [1,2,3]:
...     print(x)
1
2
3   
```

## 함수
```py
>>> def hello():
...     print("Hello World!")
...
>>> hello()
Hello World!
```

## 클래스
```py
class 클래스이름:
    def __init__(self, 인수, ...):      # 생성자
        ...
    def 메서드1(self, 인수, ...):        # 메서드1
        ...
    def 메서드2(self, 인수, ...):        # 메서드2
        ...
```

# 외부 라이브러리
## 넘파이(Numpy)
```py
import numpy as np
```


### 배열 생성
```py
>>> x = np.array([1.0, 2.0, 3.0])
>>> print(x)
[1. 2. 3.]
>>> type(x)
<class 'numpy.ndarray'>

# N차원도 가능
>>> A = np.array([[1,2], [3,4]])
>>> print(A)
[[1 2]
 [3 4]]

>>> A.shape ## 각 차원의 크기
(2, 2)
>>> A.dtype ## 원소의 자료형
dtype('int32')
```

### 산술 계산
broadcast 기능이 확대연산을 자동으로 계산해줌.
```py
>>> x = np.array([1.0, 2.0, 3.0])
>>> y = np.array([2.0, 4.0, 6.0])
>>> x + y
array([3., 6., 9.])
>>> x * y
array([ 2.,  8., 18.])
>>> x / 2
array([0.5, 1. , 1.5])      ## broadcast
```

### 원소 접근
```py
>>> X = np.array([[51,55], [14, 19], [0,4]])
>>> X[0]
array([51, 55])

# X를 1차원 배열로 평탄화
>>> X = X.flatten()
>>> print(X)
[51 55 14 19  0  4]

# 인덱스가 0,2,4인 원소 얻기
>>> X[np.array([0, 2, 4])]
array([51, 14,  0])

# X가 15 이상인 원소만 얻기
>>> X>15
array([ True,  True, False,  True, False, False])
>>> X[X>15]
array([51, 55, 19])

```


## matplotlib

그래프 그리기를 위한 라이브러리. 간단한 데이터 시각화에 도움.

```py
>>> import numpy as np
>>> import matplotlib.pyplot as plt
>>> x = np.arange(0, 6, 0.1)
>>> y = np.sin(x)
>>>
>>> plt.plot(x,y)
[<matplotlib.lines.Line2D object at 0x00000206FB3951C0>]
>>> plt.show()
```

<img width="645" height="535" alt="Image" src="https://github.com/user-attachments/assets/b09304d8-7c60-4327-95bd-68f8dcec30ce" />


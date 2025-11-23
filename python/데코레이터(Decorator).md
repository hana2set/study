# 데코레이터(Decorator)
코드를 변경하지 않고  함수나 메서드의 동작을 수정하거나 확장하는 기능. 본질적으로 **함수를 인수로 받아서 새로운 함수를 반환하는 함수**

## Python의 기본적인 내장 데코레이터 (Built-In Decorators)
Python에서는 클래스 정의에 사용되는 일반적인 데코레이터를 제공하고 있음.(builtins.py 참고)

### @staticmethod
클래스 및 인스턴스 전체의 메소드를 정의할 때 사용. 클래스에 대한 정보가 필요 없을 때 사용.

```py
class MathOperations:
    @staticmethod
    def add(x, y):
        return x + y
​
# Using the static method
res = MathOperations.add(5, 3)
print(res)

```

### @classmethod
클래스 자체의 메소드를 정의할 때 사용. **호출된 인스턴스의 클래스를 첫 인수로 반환**한다.
```py
class Employee:
    raise_amount = 1.05

    def __init__(self, name, salary):
        self.name = name
        self.salary = salary

    @classmethod
    def set_raise_amount(cls, amount):
        cls.raise_amount = amount

# Using the class method
Employee.set_raise_amount(1.10)
print(Employee.raise_amount)
```


### @property
"속성"을 정의하는데에 사용. getter, setter를 정의하는 용도라고 생각하면 됨. 
```py
class Circle:
    def __init__(self, radius):
        self._radius = radius

    @property
    def radius(self):
        return self._radius

    @radius.setter
    def radius(self, value):
        if value >= 0:
            self._radius = value
        else:
            raise ValueError("Radius cannot be negative")

    @property
    def area(self):
        return 3.14159 * (self._radius ** 2)

# Using the property
c = Circle(5)
print(c.radius) 
print(c.area)    
c.radius = 10
print(c.area)
```




----
[Decorators in Python](https://www.geeksforgeeks.org/python/decorators-in-python/)  
[Python 코드를 크게 최적화하는 최고의 10가지 Python 내장 데코레이터](https://www.geeksforgeeks.org/python/top-python-built-in-decorators-that-optimize-python-code-significantly/)

# Banker's Algorithm 구현

## Banker's Algorithm이란?

교착상태(데드락) 회피를 위한 알고리즘 중 하나로 다익스트라가 고안한 알고리즘이다. banker's algorithm은 Safety, Resource Request 두 개의 알고리즘으로 구성되어 있다.     

운영체제개론 기말 과제로 Banker's Algorithm을 자바와 파이썬으로 구현했다.

---

### 1. Safety Algorithm
Saftey Algorithm은 프로세스의 상태를 확인하여 안전하게 모든 프로세스가 종료할 수 있는 상태와 순서를 찾는 알고리즘이다.   

#### safety Algorithm의 동작 순서  
    기본적으로 Banker's Algoritm에는 프로세스에 대한 정보가 필요하다.   

    - Max[n][m]: 각 프로세스가 최대로 필요로 하는 자원의 수
    - Allocation[n][m]: 이미 할당되어있는 자원의 수
    - Need[n][m]: Max - Allocation, 앞으로 더 필요한 자원의 수
    - Available[n]: 시스템 안에 남은 자원의 수

    1. 초기화    
    · work = Available
    · finish[i] = false

    2. 종료 가능한 프로세스 탐색
    · finish[i] = false인 프로세스 중에서 Need[i] <= work를 만족하는 프로세스를 찾는다.
    · 프로세스에서 필요한 리소스가 사용가능한 리소스 보다 작을 경우(= 종료 가능한 경우) 종료가 가능하다고 판단하고 해당 프로세스의 Allocation을 work에 더하고 work += Allocaiton, finish[i] = true로 변경한다.
    · 만족하는 경우를 찾지 못한 경우 unsafe 상태로 판단한다.   

    3. 모든 프로세스가 조건을 만족한 경우
    · 모든 프로세스의 finish[i] 가 true가 되면 안전한 상태로 판단하고 이 때 finish[i] = true 가 된 프로세스의 순서를 safe sequence라고 한다.

### 2. Resource Request Algorithm
Resource Request Algorithm은 자원 요청 시 상태를 확인하여 요청의 허용/거부를 판단하는 알고리즘이다.   

#### Resource Requset Algorithm의 동작 순서
    Resource Request Algorithm에서는 request[n] 가 추가된다.  

    - Max[n][m]: 각 프로세스가 최대로 필요로 하는 자원의 수
    - Allocation[n][m]: 이미 할당되어있는 자원의 수
    - Need[n][m]: Max - Allocation, 앞으로 더 필요한 자원의 수
    - Available[n]: 시스템 안에 남은 자원의 수
    - request[n]: 어떤 프로세스가 시스템에게 요청하는 자원의 수

    1. 기본 조건 체크
    · request[k] <= Need[k]
    최대 요구량 보다 자원 요구량이 많은 경우 요청을 거부한다.
    · request[k] <= Availalbe 
    시스템에 남아있는 자원으로 줄 수 있는 요청인지를 판단하여 자원이 부족한 경우 대기한다.

    2. 가상할당
    기본 조건 체크를 통과한 경우 가상 할당을 진행한다. 이 때 원본 배열은 변경되지 않게 배열을 복사해서 사용해야 한다.  
    · Available = Available - request[k] 
    · Allocation[k] = Allocation[k] + request[k] 
    · Need[k] = Need[k] - request[k]

    3. safety Algorithm 실행 
    · 할당된 상태에서 safe algorithm을 실행했을때 safe sequence가 존재하면 safe state로 판단하여 실제 할당을 진행한다. 
    · safe sequence가 없는 경우 unsafe state로 판단하여 요청을 거절하고 상태를 원상복구 한다.

---

### python 구현

파이썬 구현은 vscode, python 3.12.5 버전을 사용하여 구현했다. 파이썬 구현은 파이썬 언어 특유의 간결한 문법과 직관적인 코드로 구현하였다.

#### 1. bankers_algorithm.py
파이썬 코드는 bankers_algorithm.py 파일 안에서 모든 코드를 작성했으며 파이썬 역시 자바 처럼 모듈 별로 코드를 나눠서 작성하는 것을 더 선호한다고 한다. 모듈 별로 분리를 하게 되면 재사용성이 좋아진다.   

파이썬 코드 내에서 BankersAlgorithm을 아래와 같이 작성하고, safety_check_bankers(), resource_request_bankers() 도 클래스 내부의 메소드로 작성했다. >> [bankers_algorithm.py](/python/bankers_algorithm.py)
```
class BankersAlgorithm:
~ 내용 작성 
```

내가 구현한 파이썬 코드와 자바 코드의 가장 큰 차이점은 for문이라고 생각한다. 파이썬 코드에서 2차원 배열의 깊은 복사를 진행할 때 리스트 컨프리헨션을 사용하여 자바보다 짧고 간편하게 코드를 작성했다.   
```
finish = [False for i in range(len(self.maximum))]
work = [work[j] + allocation[i][j] for j in range(len(work))]
.
.
.
```
또한 파이썬 구현에서는 return에 값을 여러개 주어 반환하게 했는데 return은 튜플을 반환하기 때문에 여러개의 결과값들을 반환하는 것 처럼 느껴진다. 실제로는 하나의 튜플을 반환한다. 튜플은 순서가 있는 자료구조로 아래 함수의 리턴값을 여러개의 변수로 받거나(튜플 언패킹), 또는 하나의 튜플로 받아 인덱스로 접근할 수 있다
```
def safety_check_bankers(self, . . . ):
    return True, "python"
-> True -> 튜플의 인덱스 0
-> "python" -> 튜플의 인덱스 1

isSafe, text = safety_check_bankers()
isSafe = return 튜플의 [0], True
text = return 튜플의 [1]. "python"

result = safety_check_bankers()
result[0] -> True
result[1] -> "python"
```

파이썬 구현에서 결과값으로 튜플을 사용한 이유는 다음과 같다.   
튜플은 서로 다른 타입(boolean, String)을 함께 반환하고 immutable(불변성)한 자료형이며, 결과값을 리스트, 딕셔너리 등 다른 자료형보다 속도가 빠르다. 튜플을 사용하여 여러 타입의 값들을 의미있는 하나의 결과값으로 반환하고 불변하여 안전하게 결과값을 전달했다.
--- 

### java 구현

자바 구현은 inteliJ, JDK 17 을 사용해서 구현했다. 자바 구현의 특징은 결과값 반환에 스프링으로 API 설계하고 학습했을 때 사용한 Record와 DTO, enum을 사용했다.

#### 1. Main.java   
프로그램의 시작점으로 클래스 객체를 생성하고 과제에서 요구한 자원의 Max, Available, allocation 배열, safety / resource request 알고리즘을 구현한 메소드를 실행했다. >>[Main.java](/java/src/Main.java)

#### 2. BankersAlgorithm.java   
BankersAlogrithm의 두가지 알고리즘을 수행할 클래스로 메인에서 객체를 생성할 때 자원의 상태를 파라미터로 받아 need를 계산하고 클래스 안에서 구현된 safetyCheckBankers()와 resourceRequestBankers() 메소드를 통해 saftey 알고리즘과 resource request 알고리즘 동작을 구현한다. >> [BankersAlgorithm.java](/java/src/BankersAlgorithm.java)

#### 3. SafetyResult.java / ReqeustResult.java   
메소드의 결과값들을 DTO 패턴을 적용시키기 위한 Record.   
결과 반환 과정에서 객체가 항상 의도된 형태로만 생성되도록 보장하고, 단순히 생성자를 통해 결과를 반환할 경우 발생할 수 있는 상태 값의 불일치나 의미가 모호한 객체 생성을 방지하여 반환 결과의 일관성과 신뢰성을 보장하도록 구현했다 >>[SafetyResult](/java/src/SafetyResult.java), [RequestResult](/java/src/RequestResult.java)

---

### 알고리즘 구현을 통해 배운점
#### 1. 파이썬   
2학년 1학기의 파이썬 강의를 통해 파이썬에 대해 배웠었다. BankersAlgorithm을 파이썬으로 구현하면서 1학기 때 배운 파이썬의 클래스 생성, 리스트 컴프리헨션, mutable, immutable 등에 대해 복습하였다.   

1. 클래스   
파이썬의 클래스 역시 자바의 클래스와 마찬가지로 객체를 생성하고 객체의 동작을 정의한 메소드를 작성할 수 있다. 자바와 비교했을 때 파이썬 클래스의 차이점은 작성방법이 조금씩 다르다는 점이다. 

    * self: 파이썬에서는 메소드나 생성자를 작성할 때 self 라는 키워드를 관례적으로 첫번째 매개변수로 작성해야한다. 객체 생성, 메소드 호출 시 self는 자동으로 전달되므로 직접 전달하지 않아도 된다. self는 객체가 자기자신을 참조한다는 뜻이고 this와 유사하다.

    * __init : init은 생성자로 객체가 생성될 때 자동으로 호출되어 초기화를 담당한다. 자바와 마찬가지로 선택적으로 구현할 수 있다. 

2. 리스트 컴프리헨션   
리스트 안에 for 문을 포함하는 리스트 컴프리헨션(list comprehension)을 사용하면 좀 더 편리하게  반복되거나 특정 조건을 만족하는 리스트를 만들 수 있다. 2차원 리스트를 만들기 위해서 리스크 컴프리헨션을 중첩으로 사용해야 된다는 것을 새롭게 배웠다. bankers_algorithm.py에서는 다음과 같은 코드에서 리스트 컴프리헨션이 사용되었다.
    ```
    def calc_need(self) :
        return [[self.maximum[i][j] - self.allocation[i][j] for j in range(len(self.maximum[i]))] for i in range(len(self.maximum))]
    need[][] 를 생성하기 위해 리스트 컴프리헨션을 중첩으로 사용하고 있는 것을 확인할 수 있다.    
    
    finish = [False for i in range(len(self.maximum))]

    work = [work[j] + allocation[i][j] for j in range(len(work))]

    old_available = self.available[:]
    old_allocation = [i[:] for i in self.allocation]
    old_need = [i[:] for i in self.need]

    [:]는 0번 인덱스부터 끝까지 슬라이싱 하라는 뜻으로 얕은 복사를 할 때 사용된다.
    ```
    파이썬의 코드가 간결하다는 것을 확인 할 수 있다.

3. 튜플   
튜플은 리스트와 비슷하지만 다른 특성을 지니고 있다. 불변하며 순서를 가지고 있는 자료구조다. 또한 다른 타입의 자료형도 하나의 튜플 안에 저장할 수 있다.

    * 불변성(immutable): 튜플은 한 번 정의된 요솟값을 바꿀 수 없다.  

    * 튜플은 하나의 요소만 가지는 경우 ',' 쉼표를 꼭 작성해야 한다. 쉼표가 없으면 t1 = 1 그냥 숫자로 인식된다.
    또한 괄호 없이 작성도 가능하다.
        ```
        t1 = (1, )
        t2 = 1, 2, 3
        ```
    그 외에는 리스트와 동일하게 인덱싱, 슬라이싱, len(), count()등 메소드 사용이 가능하다.   

    튜플은 튜플 언패킹이라는 기능이 있어 튜플 내 요소의 순서에 맞게 여러 변수에 한 번에 할당이 가능하다.
    ```
    t3 = (1, 2, 3)
    x, y, z = t3
    print(x) # 1
    print(y) # 2
    print(z) # 3
    ```
    튜플은 고정된 데이터로 접근 속도가 빠르며 다음과 같은 상황에서 튜플을 사용한다.

    * 데이터가 변경되면 안 될 때 (좌표, 날짜 등)
    * 딕셔너리의 키로 사용할 때 (리스트는 불가능)
    * 함수에서 여러 값을 반환할 때
    * 메모리 효율이 중요할 때

#### 2. 자바
KDT 과정을 통해 자바와 스프링, 객체지향에 대해 배웠다. 이번 과제는 학교 운영체제개론 기말 과제였는데 과제 구현을 통해 자바를 복습할 수 있게 되었다.   

1. 배열 복사
    * 생성자를 통해 받은 자원의 상태를 BankersAlgorithm 내부의 필드로 새롭게 저장했다. 생성자에서 초기화 할 때 깊은 복사를 진행하여 외부에서 전달된 배열과 객체 내부 상태 간의 참조 공유를 방지했다.   

    * 자원 상태를 메인 클래스와 공유하지 않게 하여 자원 상태를 객체 내부에서 일관되게 관리하고 알고리즘의 판단 기준이 되는 시스템 상태를 안정적으로 유지하며 외부 변경으로 인한 오류를 방지하려고 했다.

    * 자바에서 깊은 복사를 어떻게 진행했는지도 다시 복습했다. 
    2차원 배열의 경우 for문을 통해 하나씩 배열을 복사해야 모든 배열의 값을 복사 할 수 있었다. 
    
    * .clone(), arrayCopy() 같은 1차원 배열 복사 메소드들은 2차원 배열의 경우 가장 바깥쪽 배열(행들의 주소를 담고 있는 배열)만 깊은 복사하고, 그 안의 각 행(실제 데이터가 담긴 1차원 배열)들은 여전히 원본과 동일한 메모리 주소를 참조한다. 따라서 for문을 통해 한 행씩 이동해서 arrayCopy() 또는 clone()을 사용하여 깊은 복사를 진행했다. 

2. DTO 패턴(with Record)   
    * 레코드는 모든 멤버 변수를 private final로 선언하여 객체의 **불변성(immutability)**을 보장하며, getter, toString, equals, hashCode 등의 보일러플레이트 코드를 자동으로 생성한다. 이를 통해 불필요한 코드 작성을 줄이고, 적은 코드로도 명확한 의도를 표현할 수 있다. 

    * DTO 패턴을 적용하여 BankersAlgorithm의 내부 상태를 메인에 노출하지 않고 필요한 결과값만 전달하여 캡슐화, 정보 은닉, 계층 분리라는 자바의 객체지향 설계 원칙을 지켰다. 또한 Record에 정적 팩토리 메소드를 만들어  BankersAlgorithm 클래스에서 결과 값을 간결하고 명확하게 생성할 수 있도록 작성하고 알고리즘 내부에서는 결과 객체 DTO 의 생성 방식에 대한 세부 구현을 알 필요 없이 의미에 맞는 메소드 호출만으로 결과를 반환할 수 있게 직관적인 코드를 만들었다.



   




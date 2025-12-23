import copy
from enum import Enum

class BankersAlgorithm: 
    """
    

        아래의 코드에서 self._number 로 할당했는데 변수명의 _ 의 의미는 다음과 같다.
        내부적으로 사용되는 변수
        파이썬기본 키워드와 충돌을 피하기 위한 변수

        __ _가 2개인 변수: 원천적인 접근을 막으려면 __ 더블 언더바를 사용하면 막을 수 있다.
        객체에서 __available, allocation, max에 접근할 수 없음

        리스트 컴프리헨션: 2차원 배열을 만들려면 중첩으로 만들어야 함
        Zip 함수:
        파이썬 클래스: 
        파이썬 for는 forEach, for, for of문을 for in 하나로 제공한다. for(int i = 0; 조건; 반복) -> for i in ragne()
        for i of 반복할 자료형 -> for i in 반복할 자료형
        for(변수 : 반복할 자료형) -> for i in 반복할 자료형

        if(!변수) {} -> 파이썬은 if not 변수: <- 이렇게 사용한다.

        자바는 리턴할 때 한 가지만 리턴할 수 있어 새로운 클래스를 사용하게 되는데? dto로 
        파이썬은 return 값1, 값2, ... 이렇게 여러가지를 튜플로 리턴할 수 있다.
 
    """
    """
    Docstring for __init__
    
    :param self: Description
    :param allocation: Description
    :param available: Description
    :param max: Description
    """

    def __init__(self, allocation, available, maximum):
        
        self.allocation = allocation
        self.available = available
        self.maximum = maximum
        self.need = self.calc_need()

    
    def calc_need(self) :
        return [[self.maximum[i][j] - self.allocation[i][j] for j in range(len(self.maximum[i]))] for i in range(len(self.maximum))]
        

    """
      Safe State를 판단하는 메소드
     
     ====== Safe State Check 알고리즘 순서 ======
     1. work = available;
     2. finish[i] = false;
     3. finish[i] = false && need[i] < work
     4. i번 프로세스 종료 후 work = work + allocation, finish[i] = true;
     5. finish[i] 가 모두 true 이면 safe state, safe sequence를 반환
     
     safe state 여부를 판단하여 SafetyResult를 반환

     * 깊은 복사 deepcopy 사용을 위해 copy 모듈을 import 함
     
    """
    def sefety_check_bankers(self) :

        # 1. 초기화
        work = copy.deepcopy(available)
        finish = [False for i in range(len(self.maximum))]
        safe_sequence = []
        
        # 2. 종료 조건 탐색(finish[i] = false && need[i] <= work)
        while True:
            # 모든 프로세스가 끝날 때 까지 false면 이 시스템은 unsafe 상태이다.
            progress = False

            for i in range(len(maximum)) :
                # 프로세스를 하나씩 확인할 때 종료 가능 여부를 나타낸다.
                isFinish = True

                # 프로세스가 종료된 프로세스이면 다른 프로세스로 이동하여 확인을 반복한다.
                # while 문 안에서 safe sequence를 찾기 위해 반복하기 때문에 종료된 프로세스가 존재할 수 있다.
                # finish[i] 의 인덱스는 종료된 프로세스 하나를 나타내며 인덱스가 곧 safe sequence 이다.
                if finish[i] == True :
                    continue

                # finish[i] = false -> True 인 상태이고 for 문 안에서 need[i][j] < work[j]를 확인한다.
                for j in range(len(maximum[i])) : 
                    # if의 조건식을 종료 조건과 반대로 하여 바로 다른 프로세스를 확인할 수 있게 했다.
                    if self.need[i][j] > work[j] : 
                        isFinish = False
                        break

                # 3. 종료된 프로세스의 자원을 반환
                # i번 프로세스의 j를 확인해서 종료가 가능한 상태가 되면 자원을 반환하고 safe sequence를 기록한다.
                # i = 인덱스가 곧 safe sequence 이다.
                if isFinish == True :

                    # 종료 가능한 다른 프로세스를 탐색할 수 있다. 
                    # i 번 프로세스틑 safe 상태이다.
                    progress = True
                    safe_sequence.append(i)
                    finish[i] = True

                    work = [work[j] + allocation[i][j] for j in range(len(work))]
                
            # for 문을 통해 모든 프로세스를 확인해도 progress가 false 이면 = 종료 가능한 프로세스가 없음 = unsafe 상태이므로
            # 더 이상 확인할 필요가 없기 때문에 while 문을 빠져나온다.
            if not progress :
                break
            
            # safe_sequence의 길이가 maxium의 길이와 동일하게 되면 = safe_sequence가 다 기록되면 
            # safe 상태를 나타내는 True와 safe_sequence를 리턴한다.
            if len(safe_sequence) == len(maximum) :
                return True, safe_sequence
        
        # progress = False 로 while 을 빠져나온 경우 
        # unsafe 상태를 나타내는 False 값만 리턴한다.
        return False
    
    def resource_request_bankers(self, process, request):
        print("ddd")


 
allocation = [
    [0, 1, 0],
    [2, 0, 0],
    [3, 0, 2],
    [2, 1, 1],
    [0, 0, 2]
]

maximum = [
    [7, 5, 3],
    [3, 2, 2],
    [9, 0, 2],
    [2, 2, 2],
    [4, 3, 3]
]

available = [3, 3, 2]

bank1 = BankersAlgorithm(allocation, available, maximum)
print("===== need ===== \n")
print(bank1.need)

print("===== safe stae 검사 ===== \n")
is_safe, sequence = bank1.sefety_check_bankers()
print(f"Safe: {is_safe}, Sequence: {sequence}")
   


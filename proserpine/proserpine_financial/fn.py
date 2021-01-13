#!/usr/bin/env python
# encoding: utf-8

import time

def f(x):
    if x == 0:
        # 终止条件
        return 1
    # 递归式
    return x * f(x - 1)


def div(x):
    """
        x = 0
        while num > 0:
            y = num % 10 # 取余
            x = x + y
            num = num / 10 # 取商
    """
    if x == 0:
        return 0
    return x % 10 + div(x / 10)


def addDigits(num):
    if num < 10:
        return num
    x = div(num)
    return addDigits(x)

# F{0} = 0
# F{1} = 1
# F{n}=F{n-1}+F{n-2}（n≧2）
# [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
# [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233]

def fibo(n):
    if n == 0 or n == 1:
        return n
    return fibo(n - 1) + fibo(n - 2)


def time_compute():
    # 运行 1000 次，耗时：2.36995100975 s
    start = time.time()
    i = 1000
    while i > 0:
        i -= 1
        fibo(20)
    end = time.time()
    print("运行 1000 次，耗时：%s s" % (end - start))


fibo_result = [0, 1]


def fibo_dynamic(n):
    if n < len(fibo_result):
        return fibo_result[n]
    x = fibo_dynamic(n - 2)
    y = fibo_dynamic(n - 1)
    result = x + y
    fibo_result.append(result)
    return result


def time_compute_dynamic():
    # 运行 1000 次，耗时：2.36995100975 s
    start = time.time()
    i = 1000
    while i > 0:
        i -= 1
        fibo_dynamic(20)
    end = time.time()
    print("运行 1000 次，耗时：%s s" % (end - start))

if __name__ == "__main__":
    time_compute()
    time_compute_dynamic()
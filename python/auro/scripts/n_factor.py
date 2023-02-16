#!/usr/bin/env python
# coding=utf-8
import math
import sys


def n_factor(n, expr):
    """

    :param n: 阶乘
    :param expr: 10 的指数
    :return:
    """
    base = 10**expr
    i = 2
    result = [1]
    while i <= n:
        # i : 每一次的乘数
        k = 0  # 用来保留上一次运算的进位值
        for j, s in enumerate(result):
            # j : 索引，s 是当前位
            m = s * i + k
            result[j] = m % base
            k = m // base
        while k > 0:
            result.append(k % base)
            k = k // base
        i += 1

    # 反转取字符串
    result = [str(i) for i in result[::-1]]

    return "".join([result[0]] +
                   ["0" * (expr - len(i)) + i for i in result[1:]])


BEST_EXPR = int(math.log10(sys.maxsize))

if __name__ == "__main__":
    assert n_factor(2, 1) == "2"
    assert n_factor(3, 1) == "6"
    assert n_factor(4, 1) == "24"
    assert n_factor(5, 1) == "120"
    assert n_factor(6, 1) == "720"
    assert n_factor(2, BEST_EXPR) == "2"
    assert n_factor(3, BEST_EXPR) == "6"
    assert n_factor(4, BEST_EXPR) == "24"
    assert n_factor(5, BEST_EXPR) == "120"
    assert n_factor(6, BEST_EXPR) == "720"
    print(n_factor(100, 1))
    print(n_factor(100, BEST_EXPR))
    assert n_factor(100, 1) == n_factor(100, BEST_EXPR)

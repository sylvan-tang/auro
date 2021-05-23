#!/usr/bin/env python
# coding=utf-8
"""
  Create at 18:15 on 2020/3/8
"""
#    0, 1, 2, 3, 4, 5, 6, 7, 8, 9
a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
#      [ 起始索引 : 终止索引（不包含）] ，开区间和闭区间的概念 [1, 6)
print(a[1: 6])
print(a[-9: -4])
print(a[:6])
print(a[2:])
print(a[1::3])
print(a[::-3])

matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]
print(matrix)
print(matrix[:2])
print([row[:2] for row in matrix[:2]])

# 取出下面列表中可以被 5 整除的数
a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
print(a[4::5])

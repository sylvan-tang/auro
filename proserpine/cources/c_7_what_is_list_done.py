#!/usr/bin/env python
# coding=utf-8
"""
  Create at 15:45 on 2020/3/8
"""

"""
列表在定义上有点类似于数列，数列的定义是一列有序的数字组成的集合，第一个数字是数列的第一项，以此类推，到第 n 项
python 中列表（list）的定义就是，具有相同类型的元素组成的有序集合，但是有一点不同的是，第一个元素是第 0 项，直到第 n - 1 项
"""
# 从 0 数到 10（不含10）
a = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]  # 用中括号把元素集合括起来，就可以定义一个列表，元素与元素之间用逗号分开
b = ["Lily", "Lucy", "Tony", "Shiky", ]  # 同样可以把都是字符串的集合括起来，最后的逗号可以加可以不加
c = [1.414, 3.1415926, ]
print(a)
print(b)
print(c)

"""
习题一：将以下通讯录用一个变量定义起来

"""
xiaoming = ("小明", 17701340735)
xiaowang = ("小王", 18211703124)
a = [("小明", 17701340735), ("小王", 18211703124)]
print(a)
b = [xiaoming, xiaowang]
print(b)

"""
习题二：以下那些定义 list 的语句是合法的？
a = [9, 8, 7, 6, 5, 4, 3, 2, 1,]
b = ["1", 2, 3]
c = ["Mirror, ", "mirror, ", "tell me, ", "who is the most beautifully girl in the world?"]
"""

a = [9, 8, 7, 6, 5, 4, 3, 2, 1, ]
b = ["1", 2, 3]  # 逻辑上不合法
print(b)
c = ["Mirror, ", "mirror, ", "tell me, ", "who is the most beautifully girl in the world?"]

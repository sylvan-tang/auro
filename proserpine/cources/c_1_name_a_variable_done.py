#!/usr/bin/env python
# coding=utf-8

"""
变量名命名规则

1.什么是变量名
变量名相当于给一个值起一个名字
拿数学中求三角形第三边长度的问题来举例：
假设有一个三角形，它的第一条边长度为 3，另一条边长度为 4，那么求第三条边边长是多少？
解题过程如下：
令 a = 3 为第一条边长（这里相当于 a 是变量名，3 是值）
令 b = 4 为第二条边长（这里 b 是变量名，4 是值）
令 c 为第三条边边长
则根据三角形的边长公式 a**2 + b**2 = c**2（**2 是python中对数求平方的操作符号，操作符号是什么以后再讲），有：
c = math.sqrt(a**2 + b**2) (math.sqrt 是python中对数求平方根的方法，方法也以后再讲）
那么这一解题过程就有三个变量名，其中 a, b 是直接给值做命名，而 c 是给一个数学公式的计算结果做命名

2.变量名的取名规则

    a. 可以使用使用「英文字母大小写均可」、「英文下划线 _ 」和数字作为变量名的一部分，比如：
        a b c i j k name host name_1 name1 _data _name_1 nameYourDog name_Your_dog NAME_YOUR_DOG
    b. 数字不能放在变量名的第一位，比如
        1name
    c. 需要注意的是，python是一门大小写敏感的语言，即 name 这个变量名和 naMe 是不同的变量名
3.习题一：判断下面那些变量名是符合取名规则，那些不符合
    *host
    host-1
    1_name
    _name_host_title_you
    a-b-c
    a_b_c
    data
4.习题二：把所有和 name 具有相同字母的不同变量名列出来
"""
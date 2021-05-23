#!/usr/bin/env python
# coding=utf-8
"""
  Create at 08:27 on 2020/3/29
"""

# 1. print 能做什么

print("Hello world!")

import sys


def print_1(*args, sep=' ', end='\n', file=None):
    """
    args: 加了 * 表示 args 可以接受多个参数一起传入，从 0 个起步，到无限个
    sep: str 类型，变量之间的分割符
    end: str 类型，结束符号
    :return:
    """
    output = ""
    # 遍历
    for arg in args:
        if arg:
            output += str(arg) + sep
    # 标准输出，一般是输出到控制台
    sys.stdout.write(output + end)

print_1("Hello world!")
print_1()
print_1(1, 2, 3)

# 系统自带的函数
print("你好啊，", "苏纳", "!", "今天是", 2020, "年", 3, "月", 29, "日", "气温是", 10, "度。")
print_1("你好啊，", "苏纳", "!", "今天是", 2020, "年", 3, "月", 29, "日", "气温是", 10, "度。")

dazhaohu = ["你好啊，", "苏纳", "!", "今天是", 2020, "年", 3, "月", 29, "日", "气温是", 10, "度。"]
print(type(dazhaohu))
print(dazhaohu)
print(str(dazhaohu))
print()


# 2. print 是个函数，那函数是什么？
# 封装了一个操作过程的一段代码
# 可以无限制的重复使用

# 3. 如何定义一个简单的函数？
# def 这个开头，就是定义一个函数的开始
# sum_numbers 就是方法名，类似于之前的 print_1
# : 就是函数结构的开始
# return 是一段函数的结束，或者是代码缩紧回到和 def 这个开头对齐的位置
def sum_numbers(a, b):
    return a + b

print(sum_numbers(1, 1))
print("函数已经结束")
print(sum_numbers(1, 3))
print(sum_numbers(3, 3))

# 4. 函数也是会出错的～
# 当参数不满足函数的要求的时候，会报错
# 当函数的操作过程不支持参数的类型的时候，会报错
#

# 5. 那 print 什么时候会出错？
# print_1(1, start="")

# 6. 给参数加上检查

def sum_numbers(a, b):
    if type(a) == int and type(b) == int:
        return a + b
    else:
        print("参数类型必须都是 int")
        return

print(sum_numbers(1, "3"))

print("你好啊，", "苏纳", "!", "今天是", 2020, "年", 3, "月", 29, "日", "气温是", 10, "度。")

def weather_forecast(name, year, month, day, temperature):
    print("你好啊，", name, "!", "今天是", year, "年", month, "月", day, "日", "气温是", temperature, "度。")

weather_forecast("明明", 2020, 3, 28, 3)

weather_forecast("meimei", 2020, 2, 28, 23)

j=1
while j<9:
    i=1
    while i<=j:
        print(f'{i}*{j}={i*j}', end='\t')
        i+=1
    print()
    j+=1
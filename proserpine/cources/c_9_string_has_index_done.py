#!/usr/bin/env python
# coding=utf-8
"""
  Create at 17:15 on 2020/3/8
"""

"""
字符串不止可以当成一个单独的类型，其实还可以认为字符串是一个由单个字符组成的 list
比如 a = "abc" 还是就是由 "a" "b" "c" 一个挨着一个组成的一个数组的集合，只不过它不用中括号括起来
所以字符串也具有 list 的 index 功能
"""
a = "abc"
print(a[0])  # 打印 a
print(a[1])  # 打印 b
print(a[2])  # 打印 c
b = ["a", "b", "c"]
print(b[0])  # 打印 a
print(b[1])  # 打印 b
print(b[2])  # 打印 c

# 但是要注意 a 和 b 是不同的，因为类型上就不一样了

"""
str list 和 tuple 都能通过 len 这个操作符统计长度，长度对应 str 的字符个数，list 和 tuple 的元素个数
"""
a = "abc"
print(len(a))
b = ("a", "b", "c")
print(len(b))
c = ["a", "b", "c"]
print(len(c))  # 长度都是 3

"""
补充一个小知识点，string 除了 + 操作，还有一个类似的操作叫做 join，这个操作比 + 更丰富一点，可以定义多个字符串如何合并到一起
同时有个 split 操作，用于将一个字符串切分成多个字符
"""
songs = [
    "When I was just a little girl",
    "I asked my mother, what will I be",
    "Will I be pretty",
    "Will I be rich",
    "Here's what she said to me", "Que será, será", "Whatever will be, will be"
]
# 粘连
# A-B

print("。".join(songs))  # 用逗号把每句歌词合并到一起，结果如下
"When I was just a little girl。I asked my mother, what will I be。Will I be pretty。Will I be rich。Here's what she said to me。Que será, será。Whatever will be, will be"
# 注意这里是从 list 变成 str
a = ("a", "2")
print(".".join(a))

title = "枫桥夜泊"
line1 = "月落乌啼霜满天"
line2 = "江枫渔火对愁眠"
line3 = "姑苏城外寒山寺"
line4 = "夜半钟声到客船"
print("\n".join([title, line1, line2, line3, line4]))  # 用换行符 "\n" 把每句诗合并再一起，打印出来的效果就是一行一句，结果如下
"""
枫桥夜泊
月落乌啼霜满天
江枫渔火对愁眠
姑苏城外寒山寺
夜半钟声到客船
"""

song = "许多年前 我曾是个朴素的少年 奔跑起来 像是一道春天的闪电"
print(song.split(" "))  # 使用空格将歌词分割成一句一句的：['许多年前', '我曾是个朴素的少年', '奔跑起来', '像是一道春天的闪电']
print(song.split("奔跑起来"))  # 使用"奔跑起来"，将歌词分成前一段和后一段：['许多年前 我曾是个朴素的少年 ', ' 像是一道春天的闪电']
# 注意这里用到的分割符不会在结果里出现，这里是从 str 变成 list
print(song.split("God is girl~"))

"""
习题一：求下面变量的长度
"""
a = "God is girl~"
b = [
    "When I was just a little girl.", "I asked my mother,", "what will I be.",
    "Will I be pretty.", "Will I be rich.",
    "Here's what she said to me.", "Que será, será", "Whatever will be, will be"]

c = ("小明", "初中三年级", "4班", 25, "男")

"""
习题二：将下面 str 转成 list，用觉得合适的分割符
"""
a = "aasssbbcbbcaaaabbc"
print(a.split("bb"))
b = "天天想你,天天问自己,到什麼時候才能告訴你"
print(b.split(","))
print(b.split("天"))

"""
习题三：将下面 list 转成 str，用觉得合适的分割符
"""
a = ["不要问我从哪里来", "我的故乡在远方", "为什么流浪", "流浪远方", " 流浪"]
print("\n".join(a))
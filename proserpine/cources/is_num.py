#!/usr/bin/env python
# coding=utf-8

def is_num(num):
    num_str = str(num)
    i = 0
    n = len(num_str)
    output = True
    while i < n/2:
        if num_str[i] != num_str[n-1-i]:
            output = False
            break
        i += 1
    return output

if __name__ == '__main__':
    print(is_num(1221))
    print(is_num(121))
    print(is_num(11435))

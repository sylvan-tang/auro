#!/usr/bin/env python
# coding=utf-8
"""
n, m
0 同一个团队
1 查询
其他 da pian zi

"""
import sys


def same_team():
    items = [int(i) for i in sys.stdin.readline().strip().split()]
    if len(items) != 2:
        print("require two params in one line")
        return
    n = items[0]
    m = items[1]
    team = [0 for i in range(n)]
    team_num = 1
    for i in range(m):
        line = [int(j) for j in sys.stdin.readline().strip().split()]
        if len(line) != 3:
            print("require three params in one line")
            return
        a, b, c = line[0], line[1], line[2]
        if c == 0:
            group_num = max(team[a-1], team[b-1])
            if group_num == 0:
                group_num = team_num
                team_num += 1

            team[a-1] = group_num
            team[b-1] = group_num
        elif c == 1:
            if team[a-1] == team[b-1]:
                print("we are a team")
            else:
                print("we are not a team")
        else:
            print("da pian zi")


if __name__ == '__main__':
    same_team()

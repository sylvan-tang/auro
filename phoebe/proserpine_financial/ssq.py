# encoding: utf-8

"""
根据概率计算下一期复式双色球号码
"""

import os
from collections import defaultdict

ROOT_PATH = os.path.dirname(os.path.realpath(__file__))
SSQ_HISTORY_PATH = os.path.join(ROOT_PATH, "ssq.history")


def read_history():
    histories = []
    with open(SSQ_HISTORY_PATH, "r") as f:
        line = f.readline()
        while line:
            items = line.strip().split()
            red = [items[2][i:i+2] for i in range(len(items[2]))[::2]]
            blue = items[3]
            histories.append(red + [blue])
            line = f.readline()
    return histories


if __name__ == '__main__':
    print(SSQ_HISTORY_PATH)
    blue_freq = defaultdict(int)
    red_freq = defaultdict(int)
    for history in read_history():
        blue_freq[history[-1]] += 1
        for red in history[:-1]:
            red_freq[red] += 1
    next_red = sorted(red_freq.items(), key=lambda x: x[1], reverse=True)[:6]
    next_red = [red for red, _ in next_red]
    next_blue = sorted(blue_freq.items(), key=lambda x: x[1], reverse=True)[:10]
    next_blue = [blue for blue, _ in next_blue]
    print(next_red)
    print(next_blue)
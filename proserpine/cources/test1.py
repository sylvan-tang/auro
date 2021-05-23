#!/usr/bin/env python
# coding=utf-8

"""
n, m
0 同一个团队
1 查询
其他 da pian zi

"""

import random
import time

chars = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']


def random_gen_words(count):
    while count > 0:
        count -= 1
        word_len = random.randint(3, 7)
        word = ''
        while word_len > 0:
            word_len -= 1
            char = random.choice(chars)
            word = word + char
        yield word


class WordTree(object):
    def __init__(self):
        self.freq = 0
        self.children = {}

    def incr_freq(self):
        self.freq += 1


class MaxFrequencyWords(object):

    def add_word_to_tree(self, word, root):
        for char in word:
            if not char in root.children:
                root.children[char] = WordTree()
            root = root.children[char]
        root.incr_freq()

    def bi_insert(self, output, word, freq):
        left = 0
        right = len(output) - 1
        if freq >= output[left][1]:
            output.insert(0, (word, freq))
            output.pop()
            return
        elif freq <= output[right][1]:
            return
        while left < right - 1:
            tmp = (left + right) / 2
            if freq == output[tmp][1]:
                output.insert(tmp, (word, freq))
                output.pop()
                return
            if freq > output[tmp][1]:
                right = tmp
            else:
                left = tmp
        output.insert(left + 1, (word, freq))
        output.pop()
        return

    def max_frequency(self, count, input_path):
        start = time.time()
        root = WordTree()
        with open(input_path, 'r') as f:
            line = f.readline()
            while line:
                word = line.strip()
                self.add_word_to_tree(word, root)
                line = f.readline()
        print "open file", time.time() - start
        output = []
        for i in range(count):
            output.append(('', 0))
        stack = [('', root)]
        while stack:
            parent, word_tree = stack.pop()
            if word_tree.freq > 0:
                self.bi_insert(output, parent, word_tree.freq)
            for key, value in word_tree.children.items():
                stack.append((parent + key, value))
        return output

    def generate_words(self, count, output_path):
        with open(output_path, 'w') as f:
            for word in random_gen_words(count):
                f.write(word + "\n")


if __name__ == "__main__":
    m = MaxFrequencyWords()
    start = time.time()
    # m.generate_words(100000000, '/Users/sylvan/codes/test.log')
    print m.max_frequency(10, '/Users/sylvan/codes/test.log')
    print time.time() - start

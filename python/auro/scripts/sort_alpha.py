#!/usr/bin/env python
# coding=utf-8

ord_a = ord("A")
ord_z = ord("z")


def sort_alphabet(alphabets):
    queue = [0 for _ in range(ord_a, ord_z + 1)]
    for c in alphabets:
        i = ord(c) - ord_a
        queue[i] += 1
    output = ''
    for i, c in enumerate(queue):
        output = output + unichr(i + ord_a) * c
    return output


if __name__ == "__main__":
    assert sort_alphabet("Hello") == "Hello"
    assert sort_alphabet("aAmyui") == "Aaimuy"
    assert sort_alphabet("zaAmyui") == "Aaimuyz"

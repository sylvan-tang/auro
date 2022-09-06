#!/usr/bin/env python
# coding=utf-8
import unittest

MIN_LEN = 5


def split_str(str, char=""):
    if not char:
        return [c for c in str]
    result = str.split(char)
    for i, item in enumerate(result):
      result[i] = item + "0" * max(MIN_LEN - len(item), 0)
    return result


class TestStringSplit(unittest.TestCase):
    def test_split(self):
        self.assertEqual(split_str("hello"), ["h", "e", "l", "l", "o"])
        self.assertEqual(split_str("hello,word", ","), ["hello", "word0"])
        self.assertEqual(split_str("hello,word", "o"), ["hell0", ",w000", "rd000"])
        self.assertEqual(split_str("hello,word", "word"), ["hello,", "00000"])


class Student(object):
    def __init__(self, name, age, address):
        self.name = name
        self.age = int(age)
        self.address = address


def student_generator(student_str):
    name, age, address = student_str.split("|")
    return Student(name, age, address)


class TestStudentGenerator(unittest.TestCase):
    def test_student_generator(self):
        student = student_generator("张三|24|朝阳区")
        self.assertEqual("张三", student.name)
        self.assertEqual(24, student.age)
        self.assertEqual("朝阳区", student.address)

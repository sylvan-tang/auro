#!/usr/bin/env python
# coding=utf-8

def max_sub_str(a, b):
  # 使用矩阵存储 a b 中对应下标字符是否相等
  matrix = []
  for i, m in enumerate(a):
    matrix.append([])
    for j, n in enumerate(b):
      # 字符相等设为 True
      matrix[-1].append(True if m == n else False)
  sub_str = ""
  for i, m in enumerate(matrix):
    for j, n in enumerate(m):
      if not n:
        continue
      # 遍历矩阵，如果遇到相等字符，则遍历对角线方向上相等的字符，然后尝试替换 sub_str
      k = 0
      while i+k < len(matrix) and j+k < len(b) and matrix[i+k][j+k]:
        # 被遍历的字符设置为不相等，避免重复遍历
        matrix[i+k][j+k] = False
        k += 1
      if k > len(sub_str):
        sub_str = a[i:i+k]
  return sub_str


if __name__ == '__main__':
  assert max_sub_str("Hello,Word!", "word") == "ord"
  assert max_sub_str("Hello,Word!", "ello,") == "ello,"
  assert max_sub_str("Hello,Hello,Word!", "Hello,Word!") == "Hello,Word!"
  assert max_sub_str("Hello,Hello,Word!", "Hello,Wo") == "Hello,Wo"
  assert max_sub_str("Hello,Wo", "Hello,Hello,Word!") == "Hello,Wo"
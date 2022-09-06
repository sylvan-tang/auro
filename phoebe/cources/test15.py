#!/usr/bin/env python
# coding=utf-8
#
# longest common substring
# @param str1 string字符串 the string
# @param str2 string字符串 the string
# @return string字符串
#
class Solution:
    def LCS(self , str1 , str2):
        # write code here
        output = ""
        first_str_dict = {}
        m, n = len(str1), len(str2)
        for i, c in enumerate(str1):
            indexes = first_str_dict.get(c, [])
            indexes.append(i)
            first_str_dict[c] = indexes
        for i, c in enumerate(str2):
            for j in first_str_dict.get(c, []):
                k = 1
                while i+k < n and j + k < m and str1[j+k] == str2[i+k]:
                    k += 1
                if k > len(output):
                    output = str1[j:j+k]

        if not output:
            return -1
        return output

if __name__ == '__main__':
    print(Solution().LCS("1AB2345CD","12345EF"))
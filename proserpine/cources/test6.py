class Solution(object):
    def removeComments(self, source):
        """
        :type source: List[str]
        :rtype: List[str]
        """
        in_block = False
        p = 0
        while p < len(source):
            print p, source[p], in_block
            if not source[p]:
                source.pop(p)
                continue
            if in_block:
                j = source[p].rfind("*/")
                if j == -1:
                    source.pop(p)
                else:
                    source[p-1] = source[p-1] + source[p][j+2:]
                    source.pop(p)
                    p -= 1
                    in_block = False
                continue
            i, j, k = source[p].find("/*"), source[p].rfind("*/"), source[p].find("//")

            print p, source[p], in_block, i, j, k
            if i != -1 and (k > i or k == -1):
                if j == -1:
                    source[p] = source[p][:i]
                    in_block = True
                    p += 1
                else:
                    source[p] = source[p][:i] + source[p][j+2:]
                continue
            if k != -1:
                source[p] = source[p][:k]
                continue
            p += 1
        return source

s = Solution()
s.removeComments(["a/*/b//*c","blank","d//*e/*/f"])

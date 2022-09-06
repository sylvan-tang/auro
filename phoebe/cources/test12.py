import copy

class Solution(object):

    def numberOfPatterns(self, m, n):
        """
        :type m: int
        :type n: int
        :rtype: int
        """
        if m > n:
            return 0
        if m > 9 or m < 1:
            return 0

        reach_path = {
            1: {3: 2, 7: 4, 9: 5},
            2: {8: 5},
            3: {1: 2, 7: 5, 9: 6},
            4: {6: 5},
            5: {},
            6: {4: 5},
            7: {1: 4, 3: 5, 9: 8},
            8: {2: 5},
            9: {1: 5, 3: 6, 7: 8}
        }

        def search(status, last_index, depths):
            if depths == n:
                return 1
            output = 0 if depths < m else 1
            for i in range(1, 10):
                if status & (1 << i) != 0:
                    # 当前位置已经在路径中
                    continue
                if i not in reach_path[last_index] or ((1 << reach_path[last_index][i]) & status):
                    # 当前位置可达
                    output += search(status | (1 << i), i, depths + 1)
            return output

        ans = 4 * search(1 << 1, 1, 1)
        ans += 4 * search(1 << 2, 2, 1)
        ans += search(1 << 5, 5, 1)
        return ans

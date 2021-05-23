#!/usr/bin/env python
# coding=utf-8

def print_matrix_with_direct(matrix, direct):
    if not matrix:
        return
    if direct == 0:
        for i in matrix.pop(0):
            print(i)
    elif direct == 1:
        for row in matrix:
            if not row:
                break
            print(row.pop())
    elif direct == 2:
        for i in matrix.pop()[::-1]:
            print(i)
    else:
        for i in range(1, len(matrix) + 1):
            if not matrix[-i]:
                break
            print(matrix[-i].pop(0))

    print_matrix_with_direct(matrix, (direct + 1) % 4)



def print_matrix(matrix):
    print_matrix_with_direct(matrix, 0)

if __name__ == '__main__':
    print_matrix(
        [
            [1, 2, 3, 10],
            [4, 5, 6, 11],
            [7, 8, 9, 12],
            [13, 14, 15, 16]
        ]
    )


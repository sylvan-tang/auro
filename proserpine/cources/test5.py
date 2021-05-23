def paths(m, n):
    if m < 1 or n < 1:
        return 0
    row = [1 for _ in range(m)]
    for i in range(1, n):
        tmp = [1]
        for j in range(1, m):
            tmp.append(row[j] + tmp[j - 1])
        row = tmp

    return row[-1]

if __name__ == "__main__":
    print(paths(1, 2))
    print(paths(2, 2))
    print(paths(3, 2))
    print(paths(5, 3))
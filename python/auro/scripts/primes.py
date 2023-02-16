# coding: utf-8
import math


@profile
def list_primes(start, end):
    if end < 2:
        return []
    if end == 2:
        return [2]
    primes = [2]
    for i in range(3, end + 1)[::2]:
        root = math.sqrt(i)
        is_primes = True
        for j in primes[:int(root)]:
            if i % j == 0:
                is_primes = False
                break
        if is_primes:
            primes.append(i)

    return primes


if __name__ == '__main__':
    list_primes(1, 1000000)

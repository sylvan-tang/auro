import sys
from typing import Dict


def dragon_count(level: int, count: int) -> int:
    if level == 0:
        return 0
    if level == 1:
        return count
    return dragon_count(level - 1, int(count / 2) * 5 + (count % 2) * 3)


def dragon_count_need(level: int, count: int,
                      dragon_has_owned: Dict[int, int]) -> int:
    if level == 0:
        return count
    if level == 1:
        return count - dragon_has_owned.get(level, 0)
    if level in dragon_has_owned:
        print(f"{level=}, need={count}, owned={dragon_has_owned[level]}")
        count = count - dragon_has_owned.pop(level)
        print(f"left={count}")
    return dragon_count_need(level - 1,
                             int(count / 2) * 5 + (count % 2) * 3,
                             dragon_has_owned)


if __name__ == '__main__':
    level = int(sys.argv[1])
    count = int(sys.argv[2])
    dragon_has_owned = {}
    for i, c in enumerate(sys.argv[3:]):
        if i % 2 == 1:
            continue
        dragon_has_owned[int(c)] = int(sys.argv[4 + i])
    print(dragon_count_need(level, count, dragon_has_owned))
    print(dragon_count(level, count))

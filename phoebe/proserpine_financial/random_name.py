import random

def GBK2312():
    head = random.randint(0xb0, 0xf7)
    body = random.randint(0xa1, 0xfe)
    val = f'{head:x}{body:x}'
    str = bytes.fromhex(val).decode('gb2312')
    return str

count_list = [2,  4]

def generate_name():
    n = random.choice(count_list)
    name = ""
    while n > 0:
        name += GBK2312()
        n -= 1
    return name

if __name__ == "__main__":
    print(generate_name())
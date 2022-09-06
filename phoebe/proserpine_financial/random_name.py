import secrets

head_charts = [c for c in range(0xb0, 0xf7)]
body_charts = [c for c in range(0xa1, 0xfe)]

def GBK2312():
    head = secrets.choice(head_charts)
    body = secrets.choice(body_charts)
    val = f'{head:x}{body:x}'
    str = bytes.fromhex(val).decode('gb2312')
    return str

count_list = [2,  4]

def generate_name():
    n = secrets.choice(count_list)
    name = ""
    while n > 0:
        name += GBK2312()
        n -= 1
    return name

if __name__ == "__main__":
    print(generate_name())

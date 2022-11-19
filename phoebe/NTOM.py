
'''将一个N进制数转换为M进制数 '''


def int_to_10(n, N):
    exp = 0
    result = 0
    n = str(n)
    while n:
        result += int(n[-1])*(N**exp)
        exp += 1
        n = n[:-1]
    return result

# print int_to_10(100,2)


def float_to_10(n, N):
    exp = 1
    result = 0
    n = str(n).split('.')[-1]
    # print n
    while n:
        result += int(n[0])*(N**(-exp))
        exp += 1
        n = n[1:]
    return result


def s_to_int(n, M):
    try:
        n = int(n)
    except:
        print 'n is not a num'
        return None
    result = ''
    while n:
        result = str(n % M)+result
        n = n/M
    if result:
        return int(result)
    return 0


def s_to_float(n, M):
    # n=str(n).split('.')[-1]
    try:
        n = float(n)
    except:
        print 'n is not a num'
        return None
    result = '0.'
    while n:
        # print n
        n = n * M
        result = result+str(int(n))
        n = n - int(n)
    if result:
        return float(result)
    return 0


# print s_to_float(0.5,2)

# print float_to_10(100,2)
def n_to_m(num, N, M):
    if M < 2:
        return False, 'Can not change because target hexadecimal is smaller than 2'
    try:
        num = float(num)
    except:
        return False, 'This is not a Number!'
    num1 = int(num)
    num2 = num - num1
    # print num1,num2
    n10 = int_to_10(num1, N)
    f10 = float_to_10(num2, N)
    # print n10,f10
    s1 = s_to_int(n10, M)
    s2 = s_to_float(f10, M)
    result = s1 + s2
    return result

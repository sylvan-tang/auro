#!/usr/bin/env python
# coding=utf-8
convert_dict = {
  u'零': 0,
  u'一': 1,
  u'二': 2,
  u'三': 3,
  u'四': 4,
  u'五': 5,
  u'六': 6,
  u'七': 7,
  u'八': 8,
  u'九': 9,
  u'十': 10,
  u'百': 100,
  u'千': 1000,
  u'万': 10000,
  u'亿': 100000000,

}


def convert_num(n):
  # 定义一个栈来保存数字
  stack = []
  for c in n:
    # 获取 c 对应的数字
    unit = convert_dict[c]
    if unit < 10:
      # 小于 10 的数字直接加入到栈中，该数字的单位为 0
      stack.append((unit, 0))
      # 跳到下一个循环
      continue
    # 找到栈中第一个单位小于 unit 的位置
    i = 0
    while i < len(stack) and stack[i][1] > unit:
      i += 1
    if i < len(stack):
      # 找到后把 i 开始的元素都叠加到 s
      s = sum([s[0] for s in stack[i:]])
      # 保留单位大于 unit 的部分
      stack = stack[:i]
      # 在栈底添加 s * unit 的数字，并更新该数字的单位为 unit
      stack.append((s * unit, unit))
    else:
      # 找不到单位小于 unit 的元素，这个时候直接追加数字和单位到栈底
      stack.append((unit, unit))
  return sum([s[0] for s in stack])


if __name__ == '__main__':
  assert convert_num(u'三万五千四百亿五千三百二十三万七千八百二十') == 3540053237820
  assert convert_num(u'三万五千四百零六亿五千三百二十三万七千八百二十') == 3540653237820
  assert convert_num(u'三万五千零六亿五千三百二十三万七千八百二十') == 3500653237820
  assert convert_num(u'三万五千零六亿五千三百二十三万零二十') == 3500653230020
  
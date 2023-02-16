#!/usr/bin/env python
# coding=utf-8


class Node:

    def __init__(self, val):
        """
        节点结构, next 指向下一个节点
        :param val: 值
        """
        self.val = val
        self.next = None


class LinkList:

    def __init__(self, n):
        """
        初始化一个有 n 个节点的链表
        :param n:
        """
        if n < 1:
            # 节点个数小于 0，空链表
            self.head = None
            return
        # 初始化 head
        self.head = Node(0)
        # 定义一个指向队尾的指针 tail
        tail = self.head
        for i in range(1, n):
            # 生成一个新的节点
            node = Node(i)
            # 链表尾部增加新的节点
            tail.next = node
            # tail 指针指向新的节点
            tail = tail.next

    def visit(self):
        """
        遍历链表并打印每个节点值
        :return:
        """
        # 定一个一个指向当前节点的指针 cur
        cur = self.head

        # 循环遍历链表直到 cur 指针指向了 None
        while cur:
            # 打印当前节点值
            print("%s -> " % cur.val, end='')
            # 移动指针到下一个节点
            cur = cur.next
        # 打印转行
        print("")

    def reserve(self, k):
        """
        每 k 个节点反转链表
        :param k:
        :return: head 节点改变
        """
        # 定义指针 start，end，start 只每 k 次做一次重定义，end 每次都指向下一个节点
        start = self.head
        end = self.head
        # 将当前 head 置为 None
        self.head = None
        # 计数
        count = 0

        # 循环至 end 为队尾节点，注意，队尾节点不是 None
        while end.next:
            count += 1

            # 每 k 次做一次反转
            if count % k == 0:
                # 临时指针指向 end 的下一个节点，防止链表断开
                tmp = end.next
                # 当前节点的下一个节点指向当前链表头
                end.next = self.head
                # 链表头指向这一次反转的起点
                self.head = start
                # 重置起点和终点
                start = tmp
                end = tmp
            else:
                # 没到 k 的整数，end 移向下一个节点
                end = end.next
        # 最后如果有剩下的链表，将链表尾部指向当前链表头，链表头指向 start
        end.next = self.head
        self.head = start


link = LinkList(10)
link.visit()
link.reserve(3)
link.visit()

#!/usr/bin/env python
# coding=utf-8


def run_task(task, n):
    task_step = {}
    queue = ["" for i in task]
    max_step = 0
    for i in task:
        if i in task_step:
            current_index = task_step[i] + n + 1
        else:
            current_index = max_step
            max_step += 1
        if current_index >= len(queue):
            queue.extend(["" for _ in task])
            queue[current_index] = i
            task_step[i] = current_index
            continue
        while queue[current_index] != "":
            current_index += 1
        task_step[i] = current_index
        if current_index >= len(queue):
            queue.extend(["" for _ in task])
        queue[current_index] = i
    while queue[-1] == "":
        queue.pop()
    print(queue)
    return len(queue)


if __name__ == '__main__':
    print(run_task(["A", "A", "A", "A", "B", "B"], 3))

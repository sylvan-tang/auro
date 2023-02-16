#!/usr/bin/env python
# coding=utf-8
import numpy


def compute_distance(p1, p2):
    return numpy.sqrt((p1[0] - p2[0])**2 + (p1[1] - p2[1])**2)


def compute_point(p1, p2, d, distance):
    a = distance / d
    return (a * (p2[0] - p1[0]) + p1[0], a * (p2[1] - p1[1]) + p1[1])


def k_avg(points, k):
    n = len(points)
    if n < 3:
        return
    if k >= n:
        return
    distances = []
    for i, p1 in enumerate(points[:-1]):
        distances.append(compute_distance(p1, points[i + 1]))
    distances.append(compute_distance(points[-1], points[0]))
    distance = sum(distances) / k
    i = 0
    d = distances[0]
    while distance - d > 0:
        distance = distance - d
        i += 1
        d = distances[i]
    p1 = points[i]
    p2 = points[(i + 1) % n]
    return compute_point(p1, p2, d, distance)

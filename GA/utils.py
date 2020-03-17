from math import floor
from random import uniform


def generateNewValue(lim1, lim2):
    return floor(uniform(lim1, lim2))


def binToDec(x):
    val = 0
    for bit in x:
        val = val*2+bit
    return val

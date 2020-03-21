from collections import Counter

from GA.GUI import plot
from GA.ga import *
from mpl_toolkits import mplot3d
import matplotlib.pyplot as plt
import numpy as np



def plotAFunction(xref, yref, x, y, xoptimal, yoptimal, message):
    plt.plot(xref, yref, 'b-')
    plt.plot(x, y, 'ro', xoptimal, yoptimal, 'bo')
    plt.title(message)
    plt.show()
    plt.pause(0.9)
    plt.clf()


if __name__ == '__main__':
    seed(1)

    retea = network

    print(retea)
    noDim = retea['NoNodes']
    gaParam = {'popSize':100, 'noGen':100, 'pc':0.8, 'pm':0.1}
    probParam = {'min': 1, 'max':12, 'function':modularity, 'noDim':noDim, 'noBits':6}
    ga = GA(gaParam, probParam)
    ga.initialisation()
    ga.evaluation()

    xref = [[generateNewValue(probParam['min'], probParam['max']) for _ in range(noDim)] for _ in range(0, 10)]
    xref.sort()
    yref = [modularity(xi) for xi in xref]

    plt.ion()
    plt.plot(xref, yref, 'b-')
    plt.xlabel('x values')
    plt.ylabel('y = f(x) values')
    plt.show()

    allBestFitnesses = []
    allAvgFitnesses = []
    generations = []
    best = {}

    for g in range(gaParam['noGen']):
        allPotentialSolutionsX = [c.repres for c in ga.population]
        allPotentialSolutionsY = [c.fitness for c in ga.population]

        bestSolX = ga.bestChromosome().repres
        bestSolY = ga.bestChromosome().fitness

        allBestFitnesses.append(bestSolY)
        allAvgFitnesses.append(sum(allPotentialSolutionsY) / len(allPotentialSolutionsY))
        generations.append(g)

        print("Y : ", allPotentialSolutionsY[-1])
        print("X : ", allPotentialSolutionsX[-1])

        print("Average fitness", allAvgFitnesses[-1])

        #plotAFunction(xref, yref, allPotentialSolutionsX, allPotentialSolutionsY, bestSolX, bestSolY,
        #             'generation: ' + str(g))

        #ga.oneGeneration()
        ga.oneGenerationElitism()
        #ga.oneGenerationSteadyState()

        bestChromo = ga.bestChromosome()
        best[bestChromo.fitness] = bestChromo.repres
        print('Best solution in generation ' + str(g+1) + ' is: x = ' + str(bestChromo.repres) + ' f(x) = ' + str(
            bestChromo.fitness))

    print(allBestFitnesses)
    print("Ultima solutie gasita ", allPotentialSolutionsX[-1])
    print("Sol with best fitness", best[min(allBestFitnesses)])
    print("Nr comunit : ", len(Counter(best[min(allBestFitnesses)]).keys()))
    plot(best[min(allBestFitnesses)])
    for i in range(network['NoNodes']):
        print(i, " ", best[min(allBestFitnesses)][i])
    #plot(allPotentialSolutionsX[-1])


    plt.ioff()
    best, = plt.plot(generations, allBestFitnesses, 'ro', label='best')
    mean, = plt.plot(generations, allAvgFitnesses, 'bo', label='mean')
    plt.legend([best, (best, mean)], ['Best', 'Mean'])
    plt.show()

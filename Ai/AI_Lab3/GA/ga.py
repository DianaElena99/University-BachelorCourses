from random import randint
from GA.GUI import modularity, network
from GA.utils import binToDec, generateNewValue

class Chromosome:
        def __init__(self, problParam):
            self.__problParam = problParam
            self.__repres = [generateNewValue(problParam['min'], problParam['max']) for _ in range(problParam['noDim'])]
            self.__fitness = 0.0

        @property
        def repres(self):
            return self.__repres

        @property
        def fitness(self):
            return self.__fitness

        @repres.setter
        def repres(self, l=[]):
            self.__repres = l

        @fitness.setter
        def fitness(self, fit=0.0):
            self.__fitness = fit

        def crossover(self, c):
            r = randint(0, len(self.__repres) - 1)
            newrepres = []
            for i in range(r):
                newrepres.append(self.__repres[i])
            for i in range(r, len(self.__repres)):
                newrepres.append(c.__repres[i])
            offspring = Chromosome(c.__problParam)
            offspring.repres = newrepres
            return offspring

        def mutation(self):
            pos = randint(0, len(self.__repres) - 1)
            self.__repres[pos] = generateNewValue(self.__problParam['min'], self.__problParam['max'])

        def __str__(self):
            return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)

        def __repr__(self):
            return self.__str__()

        def __eq__(self, c):
            return self.__repres == c.__repres and self.__fitness == c.__fitness


class GA:
    def __init__(self, param, problParam):
        self.__param = param
        self.__problParam = problParam
        self.__population = []

    @property
    def population(self):
        return self.__population

    def initialisation(self):
        for _ in range(0, self.__param['popSize']):
            c = Chromosome(self.__problParam)
            self.population.append(c)

    def evaluation(self):
        for c in self.population:
            print(c.repres)
            c.fitness = self.__problParam['function'](c.repres)

    def bestChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness < best.fitness):
                best = c
        return best

    def worstChromosome(self):
        worst = self.__population[0]
        for c in self.__population:
            if (c.fitness > worst.fitness):
                worst = c
        return worst

    def selection(self):
        pos1 = randint(0, self.__param['popSize'] - 1)
        pos2 = randint(0, self.__param['popSize'] - 1)
        if (self.__population[pos1].fitness < self.__population[pos2].fitness):
            return pos1
        else:
            return pos2

    def oneGeneration(self):
        newPop = []
        for _ in range(self.__param['popSize']):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            newPop.append(off)
        self.__population = newPop
        self.evaluation()

    def oneGenerationElitism(self):
        newPop = [self.bestChromosome()]
        for _ in range(self.__param['popSize']-1):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            newPop.append(off)
        self.__population = newPop
        self.evaluation()

    def oneGenerationSteadyState(self):
        for _ in range(self.__param['popSize']):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            off.fitness = self.__problParam['function'](off.repres)
            worst = self.worstChromosome()
            if (off.fitness < worst.fitness):
                worst = off

from random import seed


if __name__ == '__main__':
    seed(1)
    retea = network
    print(retea)
    noDim = retea['NoNodes']
    gaParam = {'popSize':8, 'noGen':6, 'pc':0.8, 'pm':0.1}
    probParam = {'min': 1, 'max':retea['NoNodes'], 'function':modularity, 'noDim':noDim, 'noBits':6}
    ga = GA(gaParam, probParam)
    ga.initialisation()
    ga.evaluation()

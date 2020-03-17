import numpy as np
import networkx as nx
import matplotlib.pyplot as plt
import warnings
from GA.Repo import ImportGraph as loader

warnings.simplefilter('ignore')


def initRetea():
    net = loader("polbook.gml")
    return net

network = initRetea()

def modularity(communities):
    noNodes = network['NoNodes']
    mat = network['mat']
    degrees = network['degrees']
    noEdges = network['noEdges']

    M = 2 * noEdges
    Q = 0.0

    print("Communities ", communities)
    for i in range(0, noNodes):
        for j in range(0, noNodes):
            if (communities[i] == communities[j]):
               Q += (mat[i][j] - degrees[i] * degrees[j] / M)

    return Q * 1 / M


def plot(communities):
    #A = np.matrix(network["mat"])
    #G = nx.from_numpy_matrix(A)
    #pos = nx.spring_layout(G)  # compute graph layout
    #plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
    #nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu)
    #nx.draw_networkx_edges(G, pos, alpha=0.3)
    #plt.show(G)

    A = np.matrix(network["mat"])
    G = nx.from_numpy_matrix(A)
    pos = nx.spring_layout(G)  # compute graph layout
    plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
    nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu, node_color=communities)
    nx.draw_networkx_edges(G, pos, alpha=0.3)
    plt.show(G)

if __name__ == '__main__':
    pass

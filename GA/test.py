import networkx as nx
import numpy as np
import matplotlib.pyplot as plt
import warnings

def reteaBarabasiAlbert():
    #g = nx.generators.barabasi_albert_graph(50, 10)
    #g = nx.watts_strogatz_graph(30,3,0.1)
    g = nx.petersen_graph()
    retea = {}
    retea['NoNodes'] = len(g.nodes())
    retea['noEdges'] = len(g.edges())
    mat = [[0 for _ in range(len(g.nodes()))] for _ in range(len(g.nodes()))]
    degrees = [0 for i in range(len(g.nodes()))]
    for e in g.edges():
        mat[int(e[0])][int(e[1])] = 1
        mat[int(e[1])][int(e[0])] = 1
        degrees[int(e[0])] += 1
        degrees[int(e[1])] += 1
    retea['degrees'] = degrees
    retea['mat'] = mat
    return retea

def reteaErdos():
    g = nx.watts_strogatz_graph(30,3,0.1)
    print(g.nodes())
    print(g.edges())

if __name__ == '__main__':
    warnings.simplefilter('ignore')
#    reteaErdos()

    retea = reteaBarabasiAlbert()

    A = np.matrix(retea["mat"])
    G = nx.from_numpy_matrix(A)
    pos = nx.spring_layout(G)  # compute graph layout
    plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
    nx.draw_networkx_nodes(G, pos, node_size=600, cmap=plt.cm.RdYlBu, node_color=[i for i in range(retea['NoNodes'])])
    nx.draw_networkx_edges(G, pos, alpha=0.3)
    plt.show(G)

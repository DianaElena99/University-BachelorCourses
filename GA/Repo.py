def LoadData(path):
    f = open(path, "r")
    network = {}
    lines = f.readlines()
    n = int(lines[0])
    network['NoNodes'] = n
    mat = [[0 for _ in range(n)] for _ in range(n)]

    for i in range(1, len(lines)):
        line = lines[i]
        clean = line.split(" ")
        fin = [int(x) for x in clean]
        mat[i-1] = fin

    network["mat"] = mat
    degrees = []
    noEdges = 0
    for i in range(n):
        d = 0
        for j in range(n):
            if (mat[i][j] == 1):
                d+=1
            if j>i:
                noEdges+=mat[i][j]
        degrees.append(d)
    network["noEdges"] = noEdges
    network["degrees"] = degrees
    f.close()
    return network


def ImportGraph(path):
    import networkx as nx

    G = nx.read_gml(path)

    network = {}
    # print(G.edges)
    print(nx.degree(G))
    dict = {}
    mat = [[0 for i in range(len(G.nodes))] for j in range(len(G.nodes))]
    network["mat"] = mat
    network["NoNodes"] = len(mat)
    i = 0
    for nod in G.nodes():
        dict[nod] = i
        i += 1
    print(dict)

    grade = [0 for i in range( len(mat))]
    for x in nx.degree(G):
        grade[dict[x[0]]] = x[1]
    network['degrees'] = grade
    network['noEdges'] = G.number_of_edges()

    for edges in G.edges():
        x = edges[0]
        y = edges[1]
        mat[dict[x]][dict[y]] = 1
    print(network)
    return network


if __name__ == '__main__':
    # graph = LoadData("net-1.txt")
    ImportGraph("krebs.gml")

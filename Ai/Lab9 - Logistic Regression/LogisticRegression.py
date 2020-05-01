import pandas as pd
from math import exp
from random import shuffle
from matplotlib import pyplot as plt
from numpy.linalg import inv
import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler

def cost(theta, x, y):
    h = sigmoid(x @ theta)
    m = len(y)
    cost = 1 / m * np.sum(
        -y * np.log(h) - (1 - y) * np.log(1 - h)
    )
    grad = 1 / m * ((y - h) @ x)
    return cost, grad


def sigmoid(z):
    return 1 / (1 + np.exp(-z))


def predict(classes, thetas, x):
    x = np.insert(x, 0, 1, axis=1)
    preds = [np.argmax(
        [sigmoid(xi @ theta) for theta in thetas]
    ) for xi in x]
    return [classes[p] for p in preds]


def fit(x, y, max_iter=5000, alpha=0.1):
    x = np.insert(x, 0, 1, axis=1)
    thetas = []
    classes = np.unique(y)
    costs = np.zeros(max_iter)

    for c in classes:
        # one vs. rest binary classification
        binary_y = np.where(y == c, 1, 0)

        theta = np.zeros(x.shape[1])
        for epoch in range(max_iter):
            costs[epoch], grad = cost(theta, x, binary_y)
            theta += alpha * grad

        thetas.append(theta)
    return thetas, classes, costs


def score(classes, theta, x, y):
    return (predict(classes, theta, x) == y).mean()



if __name__ == '__main__':
    url = "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"
    df = pd.read_csv(url, header=None, names=[
        "Sepal length (cm)",
        "Sepal width (cm)",
        "Petal length (cm)",
        "Petal width (cm)",
        "Species"
    ])
    df.head()

    df['Species'] = df['Species'].astype('category').cat.codes
    data = np.array(df)
    np.random.shuffle(data)
    num_train = int(.8 * len(data))  # 80/20 train/test split
    x_train, y_train = data[:num_train, :-1], data[:num_train, -1]
    x_test, y_test = data[num_train:, :-1], data[num_train:, -1]

    plt.scatter(x_train[:, 2], x_train[:, 3], c=y_train, alpha=0.5)
    plt.xlabel("Petal Length (cm)")
    plt.ylabel("Petal Width (cm)")
    plt.show()

    plt.clf()
    thetas, classes, costs = fit(x_train[:, 2:], y_train)
    plt.plot(costs)
    plt.xlabel('Number Epochs')
    plt.ylabel('Cost')
    plt.show()


    plt.clf()
    plt.scatter(x_train[:, 2], x_train[:, 3], c=y_train, alpha=0.5)
    plt.xlabel("Petal Length (cm)")
    plt.ylabel("Petal Width (cm)")
    for theta in [thetas[0], thetas[2]]:
        j = np.array([x_train[:, 2].min(), x_train[:, 2].max()])
        k = -(j * theta[1] + theta[0]) / theta[2]
        plt.plot(j, k, color='k', linestyle="--")

    plt.show()

    print(f"Train Accuracy: {score(classes, thetas, x_train[:, 2:], y_train):.3f}")
    print(f"Test Accuracy: {score(classes, thetas, x_test[:, 2:], y_test):.3f}")

    thetas, classes, costs = fit(x_train, y_train)
    print(f"Train Accuracy: {score(classes, thetas, x_train, y_train):.3f}")
    print(f"Test Accuracy: {score(classes, thetas, x_test, y_test):.3f}")

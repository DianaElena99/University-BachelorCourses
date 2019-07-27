#pragma once
#include <QAbstractListModel>
#include <QAbstractTableModel>
#include "car.h"
#include <qdebug.h>

class MyList : public QAbstractListModel {
	std::vector<Car> cars;
public:
	MyList(vector<Car>& cars_) :cars{ cars_ } {
	}

	int rowCount(const QModelIndex& parent = QModelIndex()) const override {
		return cars.size();
	}

	QVariant data(const QModelIndex& idx, int role = Qt::DisplayRole)const override {
		if (role == Qt::DisplayRole) {
			auto c = cars[idx.row()].getNr() + " --- " +cars[idx.row()].getType() + " --- " + cars[idx.row()].getBrand();
			return QString::fromStdString(c);
		}
		if (role == Qt::UserRole) {
			auto c = cars[idx.row()].getNr();
			return QString::fromStdString(c);
		}
		if (role == Qt::BackgroundRole) {
			if (idx.row() % 2 == 0) {
				return QColor(176, 255, 147);
			}
		}
		return QVariant{};
	}

	void setCars(const vector<Car>& cars) {
		this->cars = cars;
		QModelIndex topLeft = createIndex(0, 0);
		QModelIndex bottomRight = createIndex(rowCount(), 1);
		emit dataChanged(topLeft, bottomRight);
	}
};

class MyTable : public QAbstractTableModel {
	std::vector<Car> cars;
public:
	MyTable(const std::vector<Car>& cars) :cars{ cars } {
	}

	int rowCount(const QModelIndex& parent = QModelIndex()) const override {
		return cars.size();
	}
	int columnCount(const QModelIndex& parent = QModelIndex())const override {
		return 4;
	}
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole)const override {
		if (role == Qt::DisplayRole) {
			Car c = cars[index.row()];
			if (index.column() == 0) {
				return QString::fromStdString(c.getNr());
			}
			else if (index.column() == 1) {
				return QString::fromStdString(c.getBrand());
			}
			else if (index.column() == 2) {
				return QString::fromStdString(c.getType());
			}
			else if (index.column() == 3) {
				return QString::fromStdString(c.getModel());
			}
		}

		if (role == Qt::BackgroundRole) {
			if (index.row() % 2) {
				return QColor(122, 173, 255);
			}
		}
		return QVariant{};
	}

	void syncView(vector<Car>all) {
		cars = all;
		auto TopLeft = createIndex(0, 0);
		auto BottomRight = createIndex(rowCount(), columnCount());
		emit dataChanged(TopLeft, BottomRight);
	}
};
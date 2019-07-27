#include "stdafx.h"
#include "mainwindow.h"
#include <QtWidgets/QApplication>
#include <assert.h>
void testAllExtins() {
	RepoFile r{ "test.txt" };
	Service s{ r };
	vector<Car> v = r.getAll();
	assert(v.size() == 5);
	vector<Car> u = s.sortByBrand();
	u = s.sortByNr();
	u = s.sortByBrandAndModel();
	s.remove_car("PH-12-BJF");
	assert(s.getAllCars().size() == 4);
	s.undo();
	assert(s.getAllCars().size() == 5);
	MainWindow w(s);
	Observable o;
	o.notify();
}

int main(int argc, char *argv[])
{
	RepoFile repof{ "car.txt" };
	Service serv{ repof };

	QApplication a(argc, argv);
	//CosCRUDWidget C{ serv };
	//CosRDOnly altC;
	MainWindow w(serv);
	w.show();
	testAllExtins();
	//CosCRUDWidget c(serv);
	//c.show();
	//CosReadOnlyGUI cc;
	//cc.show();
	return a.exec();
}

#include "stdafx.h"
#include "taskuri.h"
#include <QtWidgets/QApplication>

void testAll() {
	Repo repTest;
	Service servTest{ repTest };
	servTest.getAllSorted();
}

int main(int argc, char *argv[])
{
	testAll();
	QApplication a(argc, argv);

	RepoFile repf;
	Service ser{ repf };
	//auto x = ser.getAllSorted();
	Taskuri w{ ser };
	w.show();

	Secondary s1{ "open", ser };
	Secondary s2{ "close", ser };
	Secondary s3{ "inprogress", ser};
	s1.show();
	s2.show();
	s3.show();
	return a.exec();
}

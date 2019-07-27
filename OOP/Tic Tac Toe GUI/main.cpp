#include "stdafx.h"
#include "gui.h"
#include <QtWidgets/QApplication>
#include "test.h"
int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
	RepoFile repof;
	Service s{ repof };
	test();

	Gui w{ s };
	w.show();
	return a.exec();
}

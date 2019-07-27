#pragma once
#include "observer.h"
#include <QtWidgets/QWidget>
#include "service.h"

class Secondary : public QWidget, public Observer{
	Service& serv;
	string status;
	QPushButton* open = new QPushButton{ "open" };
	QPushButton* close = new QPushButton{ "close" };
	QPushButton* inprogress = new QPushButton{ "inprogress" };
	QTableWidget* tabel = new QTableWidget;
	void loadGUI() {
		QVBoxLayout* mly = new QVBoxLayout;
		setLayout(mly);
		mly->addWidget(tabel);
		mly->addWidget(open);
		mly->addWidget(close);
		mly->addWidget(inprogress);
	}
	void update() override {
		loadList(serv.getAllSorted());
	}
	void loadList(vector<Task> vect) {
		tabel->clear();
		tabel->setColumnCount(4);
		tabel->setRowCount(vect.size());
		int i=0;
		for (auto c :vect) {
			auto cel0 = QString::number(c.getID());
			QTableWidgetItem* it0 = new QTableWidgetItem(cel0);
			tabel->setItem(i, 0, it0);

			auto cel1 = QString::fromStdString(c.getStatus());
			QTableWidgetItem* it1 = new QTableWidgetItem(cel1);
			tabel->setItem(i, 1, it1);

			auto cel2 = QString::fromStdString(c.getDesc());
			QTableWidgetItem* it2 = new QTableWidgetItem(cel2);
			tabel->setItem(i, 2, it2);

			QString lq = QString::fromStdString(c.getProgs());
			QTableWidgetItem* it3 = new QTableWidgetItem(lq);
			tabel->setItem(i, 3, it3);
			i++;
		}
	}
	void connectSigns() {
		QObject::connect(open, &QPushButton::clicked, [&]() {
			string o = "open";
			auto selitem = tabel->selectedItems().at(0);
			int ID = selitem->text().toInt();
			serv.Update(ID, o);
			loadList(serv.getAllSorted());
		});

		QObject::connect(close, &QPushButton::clicked, [&]() {
			string c = "close";
			auto selitem = tabel->selectedItems().at(0);
			int ID = selitem->text().toInt();
			serv.Update(ID, c);
			loadList(serv.getAllSorted());

		});

		QObject::connect(inprogress, &QPushButton::clicked, [&]() {
			string pr = "inprogress";
			auto selitem = tabel->selectedItems().at(0);
			int ID = selitem->text().toInt();
			serv.Update(ID, pr);
			loadList(serv.getAllSorted());

		});
	}
public:
	Secondary(string stare, Service& serv_):status{stare}, serv{serv_}{
		serv.add_observer(this);
		loadGUI();
		loadList(serv.getAllSorted());
		connectSigns();
	}
};

class Taskuri : public QWidget, public Observer
{
	Service& serv;
	void initGUI() {
		QHBoxLayout* lay = new QHBoxLayout;
		setLayout(lay);
		QWidget* wnd = new QWidget;
		QVBoxLayout* date = new QVBoxLayout;
		wnd->setLayout(date);
		date->addWidget(tbl);
		date->addWidget(Add);
		date->addWidget(SearchProg);
		lay->addWidget(wnd);
		QWidget* formu = new QWidget;
		QVBoxLayout* field = new QVBoxLayout;
		formu->setLayout(field);
		field->addWidget(i);
		field->addWidget(id);
		field->addWidget(sta);
		field->addWidget(st);
		field->addWidget(des);
		field->addWidget(desc);
		field->addWidget(pro);
		field->addWidget(progs);
		lay -> addWidget(formu);
}
	
	void connectSign() {
		QObject::connect(Add, &QPushButton::clicked, [&]() {
			int i = id->text().toInt();
			for (auto c : serv.getAllSorted()) {
				if (c.getID() == i)return;
			}
			string d = desc->text().toStdString();
			if (d == "" || d==" ")return;
			string stare = st->text().toStdString();
			if (!(stare == "open" || stare == "close" || stare == "inprogress")) { return; }
			QStringList lista = progs->text().split(" ");
			if (lista.size() < 1 || lista.size() > 4)return;
			string pr = progs->text().toStdString();
			serv.Add(i, d, stare, pr);
			loadInitial(serv.getAllSorted());
		});

		QObject::connect(progs, &QLineEdit::textEdited, [&]() {
			string text = progs->text().toStdString();
			vector<Task> v;
			for (auto pr : serv.getAllSorted()) {
				if (pr.getProgs().find(text) != string::npos) {
					v.push_back(pr);
				}
			}
			loadInitial(v);
		});


	}

	void loadInitial(vector<Task> vect) {
		tbl->clear();
		tbl->setColumnCount(4);
		tbl->setSortingEnabled(false);
		tbl->setRowCount(serv.getAllSorted().size());
		int i = 0;
		for (auto c : vect) {
			auto cel0 = QString::number(c.getID());
			QTableWidgetItem* it0 = new QTableWidgetItem(cel0);
			it0->setCheckState(Qt::Unchecked);
			tbl->setItem(i, 0, it0);

			auto cel1 = QString::fromStdString(c.getStatus());
			QTableWidgetItem* it1 = new QTableWidgetItem(cel1);
			it1->setTextColor(Qt::blue);
			tbl->setItem(i, 1, it1);

			auto cel2 = QString::fromStdString(c.getDesc());
			QTableWidgetItem* it2 = new QTableWidgetItem(cel2);
			it2->setBackgroundColor(Qt::green);
			tbl->setItem(i, 2, it2);

			QString lq = QString::fromStdString(c.getProgs());
			QTableWidgetItem* it3 = new QTableWidgetItem(lq);
			it3->setIcon(QApplication::style()->standardIcon(QStyle::SP_ArrowBack));
			tbl->setItem(i, 3, it3);
			
			i++;
		}
		tbl->setSortingEnabled(true);
	}
public:
	Taskuri(Service& serv_, QWidget *parent = Q_NULLPTR) :serv{ serv_ } {
		serv.add_observer(this);
		initGUI();
		loadInitial(serv.getAllSorted());
		connectSign();
	}

	void update() override {
		loadInitial(serv.getAllSorted());
	}
private:
	QPushButton* Add = new QPushButton{ "add" };
	QPushButton* SearchProg = new QPushButton{"search"};
	QTableWidget* tbl = new QTableWidget;
	QFormLayout* form = new QFormLayout;
	QLineEdit* id = new QLineEdit;
	QLineEdit* st = new QLineEdit;
	QLineEdit* desc = new QLineEdit;
	QLineEdit* progs = new QLineEdit;
	QLabel* i = new QLabel{ "id : " };
	QLabel* sta = new QLabel{ "status : " };
	QLabel* des = new QLabel{ "desc : " };
	QLabel* pro = new QLabel{ "progs : " };

};

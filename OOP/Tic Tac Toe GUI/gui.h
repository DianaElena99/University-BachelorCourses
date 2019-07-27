#pragma once

#include <QtWidgets/QWidget>
#include "service.h"

/*
Clasa de interfata cu utilizatorul
*/
class Gui : public QWidget
{

public:
	Gui(Service& serv, QWidget *parent = Q_NULLPTR) :serv{ serv } {
		setup();
		init(serv.All());
		connect();
	}

private:
	Service& serv;
	QTableWidget* tabel;

	//text-fielduri
	QLineEdit* Lid = new QLineEdit;
	QLineEdit* Ldim = new QLineEdit;
	QLineEdit* Lnext = new QLineEdit;
	QLineEdit* Ltabla = new QLineEdit;
	QLineEdit* Lstare = new QLineEdit;

	//label
	QLabel* id = new QLabel{ "id : " };
	QLabel* dim = new QLabel{ "dim : " };
	QLabel* next = new QLabel{ "next : " };
	QLabel* tabla = new QLabel{ "tabla : " };
	QLabel* stare = new QLabel{ "status : " };

	QPushButton* modifica = new QPushButton{ "modifica joc" };
	QPushButton* adauga = new QPushButton{ "adauga joc" };
	QPushButton* b11 = new QPushButton{ "" };
	QPushButton* b12 = new QPushButton{ "" };
	QPushButton* b13 = new QPushButton{ "" };
	QPushButton* b14 = new QPushButton{ "" };
	QPushButton* b15 = new QPushButton{ "" };
	QPushButton* b21 = new QPushButton{ "" };
	QPushButton* b22 = new QPushButton{ "" };
	QPushButton* b23 = new QPushButton{ "" };
	QPushButton* b24 = new QPushButton{ "" };
	QPushButton* b25 = new QPushButton{ "" };
	QPushButton* b31 = new QPushButton{ "" };
	QPushButton* b32 = new QPushButton{ "" };
	QPushButton* b33 = new QPushButton{ "" };
	QPushButton* b34 = new QPushButton{ "" };
	QPushButton* b35 = new QPushButton{ "" };
	QPushButton* b41 = new QPushButton{ "" };
	QPushButton* b42 = new QPushButton{ "" };
	QPushButton* b43 = new QPushButton{ "" };
	QPushButton* b44 = new QPushButton{ "" };
	QPushButton* b45 = new QPushButton{ "" };
	QPushButton* b51 = new QPushButton{ "" };
	QPushButton* b52 = new QPushButton{ "" };
	QPushButton* b53 = new QPushButton{ "" };
	QPushButton* b54 = new QPushButton{ "" };
	QPushButton* b55 = new QPushButton{ "" };

	//TABELUL cu jocurile 
	QTableWidget* tbl = new QTableWidget;
	void init(vector<Game>v) {
		tbl->clear();
		tbl->setRowCount((int)v.size());
		tbl->setColumnCount(5);

		sort(v.begin(), v.end(), [](Game& g1, Game& g2) {return g1.getStare() < g2.getStare(); });
		int i = 0;
		for (auto g : v) {
			tbl->setSortingEnabled(false);
			QTableWidgetItem* i0 = new QTableWidgetItem(QString::fromStdString(to_string(g.getID())));
			tbl->setItem(i, 0, i0);
			
			QTableWidgetItem* i1 = new QTableWidgetItem(QString::number(g.getDIM()));
			tbl->setItem(i, 1, i1);

			QTableWidgetItem* i2 = new QTableWidgetItem(QString::fromStdString(g.getTable()));
			tbl->setItem(i, 2, i2);
			
			QTableWidgetItem* i3 = new QTableWidgetItem(QString::fromStdString(g.getNext()));
			tbl->setItem(i, 3, i3);

			QTableWidgetItem* i4 = new QTableWidgetItem(QString::fromStdString(g.getStare()));
			tbl->setItem(i, 4, i4);
			tbl->setSortingEnabled(true);
			i++;
		}
	};

	/*
	Functie care initializeaza componentele ferestrei
	*/
	void setup() {
		QHBoxLayout* mainL = new QHBoxLayout;
		setLayout(mainL);
		mainL->addWidget(tbl);
		QWidget* wnd = new QWidget;
		QVBoxLayout* wndL = new QVBoxLayout;
		wnd->setLayout(wndL);
		wndL->addWidget(id);
		wndL->addWidget(Lid);
		wndL->addWidget(dim);
		wndL->addWidget(Ldim);
		wndL->addWidget(next);
		wndL->addWidget(Lnext);
		wndL->addWidget(tabla);
		wndL->addWidget(Ltabla);
		wndL->addWidget(stare);
		wndL->addWidget(Lstare);
		wndL->addWidget(modifica);
		wndL->addWidget(adauga);
		mainL->addWidget(wnd);

		QWidget* randul1 = new QWidget;
		QVBoxLayout* r1 = new QVBoxLayout;
		randul1->setLayout(r1);
		r1->addWidget(b11);
		r1->addWidget(b12);
		r1->addWidget(b13);
		r1->addWidget(b14);
		r1->addWidget(b15);

		QWidget* randul2 = new QWidget;
		QVBoxLayout* r2 = new QVBoxLayout;
		randul2->setLayout(r2);
		r2->addWidget(b21);
		r2->addWidget(b22);
		r2->addWidget(b23);
		r2->addWidget(b24);
		r2->addWidget(b25);

		QWidget* randul3 = new QWidget;
		QVBoxLayout* r3 = new QVBoxLayout;
		randul3->setLayout(r3);
		r3->addWidget(b31);
		r3->addWidget(b32);
		r3->addWidget(b33);
		r3->addWidget(b34);
		r3->addWidget(b35);

		QWidget* randul4 = new QWidget;
		QVBoxLayout* r4 = new QVBoxLayout;
		randul4->setLayout(r4);
		r4->addWidget(b41);
		r4->addWidget(b42);
		r4->addWidget(b43);
		r4->addWidget(b44);
		r4->addWidget(b45);

		QWidget* randul5 = new QWidget;
		QVBoxLayout* r5 = new QVBoxLayout;
		randul5->setLayout(r5);
		r5->addWidget(b51);
		r5->addWidget(b52);
		r5->addWidget(b53);
		r5->addWidget(b54);
		r5->addWidget(b55);

		mainL->addWidget(randul1);
		mainL->addWidget(randul2);
		mainL->addWidget(randul3);
		mainL->addWidget(randul4);
		mainL->addWidget(randul5);
	};

	/*Functie care conecteaza semnalele */
	void connect() {
		QObject::connect(modifica, &QPushButton::clicked, [this]() {
			int idd = Lid->text().toInt();
			int dimm = Ldim->text().toInt();
			if (dimm != 3 && dimm != 5 && dimm != 4)return;

			string tablaa = Ltabla->text().toStdString();
			if (tablaa.size() != dimm * dimm)return;
			for (auto car : tablaa) {
				if (car != 'X' && car != 'O' && car != '-')return;
			}

			string nexxt = Lnext->text().toStdString();
			if (nexxt != "X" && nexxt != "O")return;

			string stat = Lstare->text().toStdString();
			if (stat != "InDerulare" && stat != "Neinceput" && stat != "Terminat")return;
			serv.update(idd, dimm, tablaa, nexxt, stat);
			init(serv.All());
		});

		QObject::connect(adauga, &QPushButton::clicked, [this]() {
			int idd = Lid->text().toInt();
			int dimm = Ldim->text().toInt();
			string tablaa = Ltabla->text().toStdString();
			string nexxt = Lnext->text().toStdString();
			string stat = "Neinceput";
			serv.addGame(idd, dimm, tablaa, nexxt, stat);
			init(serv.All());
		});

		QObject::connect(b11, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b11->text() == "") {
				b11->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b12, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b12->text() == "") {
				b12->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b13, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b13->text() == "") {
				b13->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b14, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b14->text() == "") {
				b14->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b15, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b15->text() == "") {
				b15->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b21, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b21->text() == "") {
				b21->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b22, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b22->text() == "") {
				b22->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b23, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b23->text() == "") {
				b23->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b24, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b24->text() == "") {
				b24->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b25, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b25->text() == "") {
				b25->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b31, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b31->text() == "") {
				b31->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b32, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b32->text() == "") {
				b32->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b33, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b33->text() == "") {
				b33->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b34, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b34->text() == "") {
				b34->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b35, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b35->text() == "") {
				b35->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b41, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b41->text() == "") {
				b41->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b42, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b42->text() == "") {
				b42->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b43, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b43->text() == "") {
				b43->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b44, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b44->text() == "") {
				b44->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o);
				Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b45, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b45->text() == "") {
				b45->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b51, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b51->text() == "") {
				b51->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b52, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b52->text() == "") {
				b52->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b53, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b53->text() == "") {
				b53->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b54, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b54->text() == "") {
				b54->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(b55, &QPushButton::clicked, [this]() {
			QString next_ = Lnext->text();
			if (b55->text() == "") {
				b55->setText(next_);
				string ne = next_.toStdString();
				const string x = "X";
				const string o = "O";
				if (ne == x)
					Lnext->setText(QString::fromStdString(o));
				if (ne == o)
					Lnext->setText(QString::fromStdString(x));
			}
		});

		QObject::connect(tbl, &QTableWidget::itemSelectionChanged, [this]() {
			if (tbl->selectedItems().isEmpty()) {
				Lid->setText("");
				Ldim->setText("");
				Lnext->setText("");
				return;
			}

			QTableWidgetItem* item = tbl->selectedItems().at(0);
			QString i = item->text();
		
			int dimm;
			string tabl;
			string nextie;
			for (auto elem : serv.All()) {
				if (QString::number(elem.getID()) == i) {
					dimm = elem.getDIM();
					tabl = elem.getTable();
					nextie = elem.getNext();

				}
			}
			Lid->setText(i);
			Ltabla->setText(QString::fromStdString(tabl));
			Lnext->setText(QString::fromStdString(nextie));
			

			if (dimm == 3) {
				b11->setText(QString::fromStdString(to_string(tabl.at(0))));
				b12->setText(QString::fromStdString(to_string(tabl.at(1))));
				b13->setText(QString::fromStdString(to_string(tabl.at(2))));
				b14->setText("");
				b15->setText("");

				b21->setText(QString::fromStdString(to_string(tabl.at(3))));
				b22->setText(QString::fromStdString(to_string(tabl.at(4))));
				b23->setText(QString::fromStdString(to_string(tabl.at(5))));
				b24->setText("");
				b25->setText("");

				b31->setText(QString::fromStdString(to_string(tabl.at(6))));
				b32->setText(QString::fromStdString(to_string(tabl.at(7))));
				b33->setText(QString::fromStdString(to_string(tabl.at(8))));
				b34->setText("");
				b35->setText("");

				b51->setText("");
				b52->setText("");
				b53->setText("");
				b54->setText("");
				b55->setText("");

				b41->setText("");
				b42->setText("");
				b43->setText("");
				b44->setText("");
				b45->setText("");
			}

			if (dimm == 4) {
				b11->setText(QString::fromStdString(to_string(tabl.at(0))));
				b12->setText(QString::fromStdString(to_string(tabl.at(1))));
				b13->setText(QString::fromStdString(to_string(tabl.at(2))));
				b14->setText(QString::fromStdString(to_string(tabl.at(3))));
				b15->setText("");

				b21->setText(QString::fromStdString(to_string(tabl.at(4))));
				b22->setText(QString::fromStdString(to_string(tabl.at(5))));
				b23->setText(QString::fromStdString(to_string(tabl.at(6))));
				b24->setText(QString::fromStdString(to_string(tabl.at(7))));
				b25->setText("");

				b31->setText(QString::fromStdString(to_string(tabl.at(8))));
				b32->setText(QString::fromStdString(to_string(tabl.at(9))));
				b33->setText(QString::fromStdString(to_string(tabl.at(10))));
				b34->setText(QString::fromStdString(to_string(tabl.at(11))));
				b35->setText("");

				b41->setText(QString::fromStdString(to_string(tabl.at(12))));
				b42->setText(QString::fromStdString(to_string(tabl.at(13))));
				b43->setText(QString::fromStdString(to_string(tabl.at(14))));
				b44->setText(QString::fromStdString(to_string(tabl.at(15))));
				b45->setText("");

				b51->setText("");
				b52->setText("");
				b53->setText("");
				b54->setText("");
				b55->setText("");
			}

			if (dimm == 5) {
				b11->setText(QString::fromStdString(to_string(tabl.at(0))));
				b12->setText(QString::fromStdString(to_string(tabl.at(1))));
				b13->setText(QString::fromStdString(to_string(tabl.at(2))));
				b14->setText(QString::fromStdString(to_string(tabl.at(3))));
				b15->setText(QString::fromStdString(to_string(tabl.at(4))));

				b21->setText(QString::fromStdString(to_string(tabl.at(5))));
				b22->setText(QString::fromStdString(to_string(tabl.at(6))));
				b23->setText(QString::fromStdString(to_string(tabl.at(7))));
				b24->setText(QString::fromStdString(to_string(tabl.at(8))));
				b25->setText(QString::fromStdString(to_string(tabl.at(9))));
				
				b31->setText(QString::fromStdString(to_string(tabl.at(10))));
				b32->setText(QString::fromStdString(to_string(tabl.at(11))));
				b33->setText(QString::fromStdString(to_string(tabl.at(12))));
				b34->setText(QString::fromStdString(to_string(tabl.at(13))));
				b35->setText(QString::fromStdString(to_string(tabl.at(14))));

				b41->setText(QString::fromStdString(to_string(tabl.at(15))));
				b42->setText(QString::fromStdString(to_string(tabl.at(16))));
				b43->setText(QString::fromStdString(to_string(tabl.at(17))));
				b44->setText(QString::fromStdString(to_string(tabl.at(18))));
				b45->setText(QString::fromStdString(to_string(tabl.at(19))));

				b51->setText(QString::fromStdString(to_string(tabl.at(20))));
				b52->setText(QString::fromStdString(to_string(tabl.at(21))));
				b53->setText(QString::fromStdString(to_string(tabl.at(22))));
				b54->setText(QString::fromStdString(to_string(tabl.at(23))));
				b55->setText(QString::fromStdString(to_string(tabl.at(24))));
			}

		});
	};
};

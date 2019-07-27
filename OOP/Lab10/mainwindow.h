#pragma once
#include <unordered_map>
#include "service.h"
#include "mylist.h"
#include "car.h"
#include <QtWidgets/QMainWindow>
#include <QTableWidget>
#include <QLabel>
#include <QItemSelectionModel>

class CosRDOnly : public QWidget, public Observer{
private:
	int num;
	WashList& was;

	void setup(){
		this->setWindowTitle("RD_ONLY GUI");
		resize(500, 500);
	}

public:
	explicit CosRDOnly(WashList& was_) :was{was_} {
		setup();
		this->was.add_observer(this);
	};

protected:
	void update() override {
		this->num = this->was.listAll().size();
		this->repaint();
	}
	void paintEvent(QPaintEvent* ev) override
	{
		QPainter p{ this };
		//p.drawImage(0, 0, QImage("cat.jpg"));
		unordered_map<string, vector<Car>> h;
		for (Car c : was.listAll()){
			h[c.getType()].push_back(c);
		}
		int poz = 0;
		for (auto& x : h) {
		
			for (int i = 0; i < x.second.size(); i++) {
				qDebug() << x.second.size() << endl;
				p.drawRect(60*poz+15, 15, 50, 40 + i * 20);
				if (poz % 2)
					p.fillRect(QRect(60 * poz + 15, 15, 50, 40 + i * 20), QBrush(Qt::blue));
				else
					p.fillRect(QRect(60 * poz + 15, 15, 50, 40 + i * 20), QBrush(Qt::black));

			}
			poz+=1;
		}
		/*for (int i = 0; i < this->num; ++i) {
			p.drawRect(15 + poz * 60, 15 , 30 , 40 + poz * 20);
			poz++;
		}*/	
	}
};

class CosCRUDWidget : public QWidget, public Observer{
private:
	Service& s;
	QTableWidget* washList = new QTableWidget;
	QPushButton* btnRandomList = new QPushButton{ "Add random" };
	QPushButton* btncsv = new QPushButton{ "Export list to CSV" };
	QPushButton* btnhtml = new QPushButton{ "Export list to HTML" };
	QPushButton* btnAddtoList = new QPushButton{ "Add to washlist" };
	QPushButton* btnClear = new QPushButton{ "Clear Washlist" };
	QPushButton* btnExit = new QPushButton{ "Exit" };

	QLineEdit* nrCarsWashList = new QLineEdit("");
	QLabel* lblDim = new QLabel("Number of cars in washList");

	QLineEdit* txtNR = new QLineEdit("");
	QLineEdit* txtType = new QLineEdit("");
	QLineEdit* txtBrand = new QLineEdit("");
	QLineEdit* txtModel = new QLineEdit("");

	QLabel* lblNumber = new QLabel("Number of cars");
	QLineEdit* txtNumber = new QLineEdit;

	void update() override {
		populateWashList(s.listAll());
	}

	void setUI()
	{
		this->setWindowTitle("Cart Read Only");
		QHBoxLayout* mainLayout = new QHBoxLayout;
		setLayout(mainLayout);
		
		QWidget* btns = new QWidget;
		
		QFormLayout* form = new QFormLayout;
		form->addRow(tr(" ID : "), txtNR);
		form->addRow(tr(" Type : "), txtType);
		form->addRow(tr(" Brand : "), txtBrand);
		form->addRow(tr(" Model : "), txtModel);
		form->addWidget(btnExit);
		btns->setLayout(form);

		mainLayout->addWidget(btns);

		QWidget* altWidget = new QWidget;

		QVBoxLayout* washBtn = new QVBoxLayout;
		altWidget->setLayout(washBtn);
		washBtn->addWidget(btnClear);
		washBtn->addWidget(btncsv);
		washBtn->addWidget(btnhtml);
		washBtn->addWidget(btnRandomList);
		washBtn->addWidget(btnAddtoList);
		washBtn->addWidget(lblNumber);
		washBtn->addWidget(txtNumber);
		washBtn->addWidget(washList);
		washList->setSelectionBehavior(QAbstractItemView::SelectRows);
		washBtn->addStretch();
		mainLayout->addWidget(altWidget);
	}

	void connectSignal()
	{
		QObject::connect(btnRandomList, &QPushButton::clicked, [&]() {
			int number = txtNumber->text().toInt();
			vector<Car> rez = s.addRandom(number);
			populateWashList(s.listAll());
		});
		QObject::connect(btnAddtoList, &QPushButton::clicked, [&]() {
			std::string number = txtNR->text().toStdString();
			vector<Car> rez = s.addToList(number);
			populateWashList(s.listAll());
		});

		QObject::connect(btnhtml, &QPushButton::clicked, [&]() {
			s.exportListToHTML();
		});

		QObject::connect(btncsv, &QPushButton::clicked, [&]() {
			s.exportListToCSV();
		});

		QObject::connect(btnClear, &QPushButton::clicked, [&]() {
			s.clearList();
		});

		QObject::connect(btnExit, &QPushButton::clicked, [&]() {
			QMessageBox::information(nullptr, "Bye", "See you later aligator"); this->setVisible(false); });
	
		QObject::connect(washList, &QTableWidget::itemSelectionChanged, [&]() {
			if (washList->selectedItems().isEmpty())
			{
				txtNR->setText("");
				txtType->setText("");
				txtBrand->setText("");
				txtModel->setText("");
				return;
			}
			QTableWidgetItem* selitem = washList->selectedItems().at(0);
			QString id = selitem->text();
			txtNR->setText(id);
			Car c = s.find_car(id.toStdString());
			txtType->setText(QString::fromStdString(c.getType()));
			txtBrand->setText(QString::fromStdString(c.getBrand()));
			txtModel->setText(QString::fromStdString(c.getModel()));

		});
	
	}

	void populateWashList(vector<Car>v)
	{
		washList->clear();
		sort(v.begin(), v.end(), [](Car& c1, Car& c2) {return c1.getType() < c2.getType(); });
		washList->setRowCount((int)v.size());
		washList->setColumnCount(3);
		int i = 0;
		unordered_map<string, vector<Car>> statistic;
		for (const auto& e : v)
		{

			QTableWidgetItem* item = new QTableWidgetItem{ QString::fromStdString(e.getNr()) };
			item->setTextAlignment(Qt::AlignRight | Qt::AlignVCenter);
			washList->setSortingEnabled(false);
			washList->setItem(i, 0, item);
			washList->setSortingEnabled(true);
			
			QTableWidgetItem* item0 = new QTableWidgetItem{ QString::fromStdString(e.getType()) };
			item0->setTextAlignment(Qt::AlignRight | Qt::AlignVCenter);
			washList->setSortingEnabled(false);
			washList->setItem(i, 1, item0);
			washList->setSortingEnabled(true);
			
			statistic[e.getType()].push_back(e);
			QTableWidgetItem* count = new QTableWidgetItem{ QString::fromStdString(to_string(statistic[e.getType()].size())) };
			count->setTextAlignment(Qt::AlignRight | Qt::AlignVCenter);
			washList->setSortingEnabled(false);
			washList->setItem(i, 2, count);
			washList->setSortingEnabled(true);

			if (statistic[e.getType()].size()>=3)
			{
				item->setData(Qt::BackgroundRole, QColor(255,0,0));
				item0->setData(Qt::BackgroundRole, QColor(255, 0, 0));
				count->setData(Qt::BackgroundRole, QColor(255, 0, 0));
			}
			i++;
		}
			
	}
	
public:
	CosCRUDWidget(Service& s_) :s{ s_ } {
		setUI(); 
		this->s.getCart().add_observer(this);
		connectSignal(); 
		populateWashList(s.listAll());
	};
};

///////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

class MainWindow : public QWidget, public Observer
{
	Q_OBJECT

public:
	MainWindow(Service& serv_) :serv{ serv_ }{
		this->serv.getCart().add_observer(this);
		initUI();
		loadInitialState();
		initSignalsSlots();
	};


private:
	void initSignalsSlots();
	void loadInitialState();

	int i = 0;
	void showAllCars()
	{
		vector<Car> rez = serv.getAllCars();
		for (auto& c : rez)
		{
			std::string car = c.getNr() + "," + c.getType() + "," + c.getBrand() + "," + c.getModel();

			QListWidgetItem* item = new QListWidgetItem{ QString::fromStdString(car) };
			menuList->addItem(item);
			if (i % 2 == 0) {
				item->setData(Qt::BackgroundRole, QColor(204, 255, 204));
			}
			i++;
		}
	}

	void update() override {
		populateWashList(serv.listAll());
		txtNrInWashlist->setText(QString::number(serv.listAll().size()));
	 }

	void populateList(std::vector<Car>elems){
		tbV->setSortingEnabled(false);
		MyTable* model = new MyTable{ elems };
		tbV->setModel(model);
		tbV->setSortingEnabled(true);
	}

	void updateCar(){
		std::string id = txtNr->text().toStdString();
		std::string type = txtType->text().toStdString();
		std::string brand = txtBrand->text().toStdString();
		std::string model = txtModel->text().toStdString();
		Car c(id, type, brand, model);
		serv.update_car(c);
		populateList(serv.getAllCars());

	}

	void removeCar(){
		std::string id = txtNr->text().toStdString();
		serv.remove_car(id);
		populateList(serv.getAllCars());

	}

	void populateWashList(vector<Car>v){
		washList->clear();
		sort(v.begin(), v.end(), [](Car & c1, Car & c2) {return c1.getType() < c2.getType(); });
		washList->setRowCount((int)v.size());
		washList->setColumnCount(3);
		int i = 0;
		unordered_map<string, vector<Car>> statistic;
		for (const auto& e : v)
		{

			QTableWidgetItem* item = new QTableWidgetItem{ QString::fromStdString(e.getNr()) };
			item->setTextAlignment(Qt::AlignRight | Qt::AlignVCenter);
			washList->setSortingEnabled(false);
			washList->setItem(i, 0, item);
			washList->setSortingEnabled(true);

			QTableWidgetItem * item0 = new QTableWidgetItem{ QString::fromStdString(e.getType()) };
			item0->setTextAlignment(Qt::AlignRight | Qt::AlignVCenter);
			washList->setSortingEnabled(false);
			washList->setItem(i, 1, item0);
			washList->setSortingEnabled(true);

			statistic[e.getType()].push_back(e);
			QTableWidgetItem * count = new QTableWidgetItem{ QString::fromStdString(to_string(statistic[e.getType()].size())) };
			count->setTextAlignment(Qt::AlignRight | Qt::AlignVCenter);
			washList->setSortingEnabled(false);
			washList->setItem(i, 2, count);
			washList->setSortingEnabled(true);

			if (statistic[e.getType()].size() >= 3)
			{
				item->setData(Qt::BackgroundRole, QColor(255, 0, 0));
				item0->setData(Qt::BackgroundRole, QColor(255, 0, 0));
				count->setData(Qt::BackgroundRole, QColor(255, 0, 0));
			}
			i++;
		}
		MyTable* model = new MyTable{ v };
		tbW->setModel(model);

	}
	

	void undo(){
		serv.undo();
		populateList(serv.getAllCars());
	}

	void filterByType(){
		std::string type = txtType->text().toStdString();
		vector<Car> elems = serv.filterByType(type);
		MyList* filterList = new MyList(elems);
		QListView* lv = new QListView;
		lv->setModel(filterList);
		for (i = 0; i <= elems.size(); i++) {
			filterList->setData(filterList->index(i, 0), QBrush(Qt::green), Qt::BackgroundRole);
		}
		lv->show();
	}

	void filterByBrand(){
		std::string brand = txtBrand->text().toStdString();
		vector<Car> elems = serv.filterByBrand(brand);
		MyList* filterList = new MyList(elems);
		QListView* lv = new QListView;
		lv->setModel(filterList);
		for (i = 0; i <= elems.size(); i++) {
			filterList->setData(filterList->index(i, 0), QBrush(Qt::green), Qt::BackgroundRole);
		}
		lv->show();
	}

	void raport(){
		unordered_map<string, vector<Car>> rep = serv.report();
		QListWidget* sl = new QListWidget;
		for (auto& e : rep)
		{
			QListWidgetItem* item = new QListWidgetItem{ QString::fromStdString(e.first) };
			item->setBackgroundColor(QColor(151, 122, 255));
			item->setTextColor(QColor(255, 232, 238));

			sl->addItem(item);
			int i = 0;
			for (const Car& c: e.second)
			{
				
				QListWidgetItem* item_secundar = new QListWidgetItem{ QString::fromStdString(c.getNr() + "," + c.getType() + "," + c.getBrand() + "," + c.getModel() + " ----  " + to_string(i+1))  };
				i++;
				item_secundar->setTextColor(QColor(255, 232, 238));
				item_secundar->setBackgroundColor(QColor(0, 0, 0));
				if (i >= 3) {
					item_secundar->setBackgroundColor(QColor(109, 41, 60));
				}
				sl->addItem(item_secundar);
			}
		}
	
		sl->show();
	}

	void washListSizeChange(){
		connect(this, &MainWindow::washListSizeChange, this, [=]() {
			int dim = serv.listAll().size();
			this->txtNrInWashlist->setText(QString::number(dim));
		});
	}

	void addCartoWashList(){
		std::string number = txtNr->text().toStdString();
		vector<Car> rez = serv.addToList(number);
		populateWashList(serv.listAll());
	}

	void clearWashList(){
		washList->clear();
		serv.clearList();
	}

	Service& serv;
	
	QListWidget* menuList = new QListWidget;
	QTableView* tbV = new QTableView;
	QTableView* tbW = new QTableView;

	QPushButton* btnCosCRUD = new QPushButton("WashList CRUD");
	QPushButton* btnCosRDO = new QPushButton("RD_ONLY");

	QPushButton* btnAdd = new QPushButton("Add");
	QPushButton* btnModify = new QPushButton("Update");
	QPushButton* btnDelete = new QPushButton("Delete");
	QPushButton* btnFilter = new QPushButton("Filter by Type");
	QPushButton* btnFilterBrand = new QPushButton("Filter by Brand");
	QPushButton* btnSort = new QPushButton("Sort by id");
	QPushButton* btnSortBrand = new QPushButton("Sort by brand");
	QPushButton* btnSortModel = new QPushButton("Sort by model");
	QPushButton* btnReport = new QPushButton("Raport");
	QPushButton* btnExit = new QPushButton("Exit");
	QPushButton* btnUndo = new QPushButton("Undo");

	QTableWidget* washList = new QTableWidget;
	QPushButton* btnRandomList = new QPushButton{ "Add random" };
	QPushButton* btncsv = new QPushButton{ "Export list to CSV" };
	QPushButton* btnhtml = new QPushButton{ "Export list to HTML" };
	QPushButton* btnAddtoList = new QPushButton{ "Add to washlist" };
	QPushButton* btnClear = new QPushButton{ "Clear Washlist" };

	QLabel* lblNr = new QLabel("NR  ");
	QLabel* lblNrInWashList = new QLabel("Currently in washList  ");
	QLabel* lblBrand = new QLabel("BRAND ");
	QLabel* lblModel = new QLabel("MODEL ");
	QLabel* lblType = new QLabel("TYPE ");

	QLineEdit* txtNr = new QLineEdit;
	QLineEdit* txtNrInWashlist = new QLineEdit;
	QLineEdit* txtBrand = new QLineEdit;
	QLineEdit* txtModel = new QLineEdit;
	QLineEdit* txtType = new QLineEdit;

	QListWidgetItem* item;

	void initUI()
	{

		QHBoxLayout* mainL = new QHBoxLayout;
		setLayout(mainL);
		QTableView* tbv;
		mainL->addWidget(tbV);
		QVBoxLayout* edit = new QVBoxLayout;

		edit->addWidget(lblNr);
		edit->addWidget(txtNr);
		edit->addWidget(lblType);
		edit->addWidget(txtType);
		edit->addWidget(lblBrand);
		edit->addWidget(txtBrand);
		edit->addWidget(lblModel);
		edit->addWidget(txtModel);
		edit->addWidget(lblNrInWashList);
		edit->addWidget(txtNrInWashlist);
		edit->addWidget(btnExit);

		QWidget* editLine = new QWidget;
		editLine->setLayout(edit);
		mainL->addWidget(editLine);

		QVBoxLayout* btnL = new QVBoxLayout;
		
		btnL->addWidget(btnAdd);
		btnL->addWidget(btnModify);
		btnL->addWidget(btnDelete);
		btnL->addWidget(btnFilter);
		btnL->addWidget(btnFilterBrand);
		btnL->addWidget(btnSort);
		btnL->addWidget(btnReport);
		btnL->addWidget(btnSortBrand);
		btnL->addWidget(btnSortModel);
		btnL->addWidget(btnUndo);
		
		QWidget* btnW = new QWidget;
		btnW->setLayout(btnL);
		mainL->addWidget(btnW);

		QWidget* cos = new QWidget;
		
		QHBoxLayout* b2 = new QHBoxLayout();
		cos->setLayout(b2);
		mainL->addWidget(cos);
		b2->addWidget(btnCosCRUD);
		b2->addWidget(btnCosRDO);	

		QWidget* cos2 = new QWidget;
		QVBoxLayout* cosButtons = new QVBoxLayout();
		cos2->setLayout(cosButtons);
		mainL->addWidget(cos2);
		cosButtons->addWidget(btnClear);
		cosButtons->addWidget(btnAddtoList);
		cosButtons->addWidget(btnRandomList);
		cosButtons->addWidget(tbW);

		
	}

signals:
	void nrCarsWashListChanged(int dim);

};

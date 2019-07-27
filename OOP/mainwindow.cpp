#include "stdafx.h"
#include "mainwindow.h"

void MainWindow::initSignalsSlots()
{
	QObject::connect(btnExit, &QPushButton::clicked, [&]() {
		QMessageBox::information(nullptr, "Byeeee", "Adios, amigos"); this->setVisible(false); });

	QObject::connect(btnAdd, &QPushButton::clicked, this, [&]() {
		try {
			std::string car = txtNr->text().toStdString() + "," + txtType->text().toStdString() + "," + txtBrand->text().toStdString() + "," + txtModel->text().toStdString();
			serv.create_car(txtNr->text().toStdString(), txtType->text().toStdString(), txtBrand->text().toStdString(), txtModel->text().toStdString());
			populateList(serv.getAllCars());
		}
		catch (RepoException&)
		{
			QMessageBox::warning(nullptr, "Validare", "Nu mai da date la bungheala");
		}
	});
	QObject::connect(btnDelete, &QPushButton::clicked, this, &MainWindow::removeCar);
	QObject::connect(btnModify, &QPushButton::clicked, this, &MainWindow::updateCar);
	QObject::connect(btnSort, &QPushButton::clicked, this, [&]() {
		populateList(serv.sortByNr());
	});
	QObject::connect(btnSortBrand, &QPushButton::clicked, this, [&]() {
		populateList(serv.sortByBrand());
	});
	QObject::connect(btnSortModel, &QPushButton::clicked, this, [this]() {
		populateList(serv.sortByBrandAndModel());
	});
	QObject::connect(btnFilter, &QPushButton::clicked, this, &MainWindow::filterByType);
	QObject::connect(btnFilterBrand, &QPushButton::clicked, this, &MainWindow::filterByBrand);
	QObject::connect(btnReport, &QPushButton::clicked, this, &MainWindow::raport);
	QObject::connect(btnUndo, &QPushButton::clicked, this, &MainWindow::undo);
	QObject::connect(btnClear, &QPushButton::clicked, this, &MainWindow::clearWashList);
	QObject::connect(btncsv, &QPushButton::clicked, this, [this]() {serv.exportListToCSV(); });
	QObject::connect(btnhtml, &QPushButton::clicked, this, [this]() {serv.exportListToHTML(); });
	QObject::connect(btnAddtoList, &QPushButton::clicked, this, &MainWindow::addCartoWashList);
	QObject::connect(btnRandomList, &QPushButton::clicked, [&]() {
		int number = txtNr->text().toInt();
		vector<Car> rez = serv.addRandom(number);
		populateWashList(serv.listAll());
	});	
	

	QObject::connect(tbV->selectionModel(), &QItemSelectionModel::selectionChanged, [&]() {

		if (tbV->selectionModel()->selectedIndexes().isEmpty()) {
			txtNr->setText("");
			txtType->setText("");
			txtBrand->setText("");
			txtModel->setText("");
			return;
		}
		int selRow = tbV->selectionModel()->selectedIndexes().at(0).row();
		auto cel0Index = tbV->model()->index(selRow, 0);
		QString celTitle = tbV->model()->data(cel0Index, Qt::DisplayRole).toString();
		txtNr->setText(celTitle);
		for (auto& c : serv.getAllCars()) {
			if (c.getNr() == celTitle.toStdString()) {
				txtBrand->setText(QString::fromStdString(c.getBrand()));
				txtType->setText(QString::fromStdString(c.getType()));
				txtModel->setText(QString::fromStdString(c.getModel()));
			}
		}
	});

	QObject::connect(btnCosCRUD, &QPushButton::clicked, [&]() {
		CosCRUDWidget* cos = new CosCRUDWidget(serv);
		cos->show();
	});

	QObject::connect(btnCosRDO, &QPushButton::clicked, [&]() {
		CosRDOnly* altCos = new CosRDOnly(serv.getCart());
		altCos->show();
	});

	QObject::connect(this, &MainWindow::nrCarsWashListChanged, [&](int dim) {
		this->txtNrInWashlist->insert(QString::fromStdString(std::to_string(dim)));
	});
}

void MainWindow::loadInitialState()
{
	populateList(serv.getAllCars());
}

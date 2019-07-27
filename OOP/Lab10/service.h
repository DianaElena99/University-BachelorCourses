#pragma once
#include <algorithm>
#include <unordered_map>
#include "car.h"
#include "undo.h"
#include "washList.h"
#include "export.h"
#include "repo.h"
using namespace std;

class Service
{
private:
	Repo& rep;
	WashList lista;
	vector<Undo*> undoActions;

public:
	WashList& getCart() { return lista; }
	Service(Repo& r) noexcept :rep{ r } {};
	Service(const Service& ot) = delete;
	void operator=(const Service& ot) = delete;
	void create_car(string id, string ty, string br, string mo);
	void remove_car(string id);
	void update_car(Car& c_);
	vector<Car> filterByType(string& type);
	vector<Car> filterByBrand(string& br);
	vector<Car> sortByNr()const;
	vector<Car> sortByBrand()const;
	vector<Car> sortByBrandAndModel()const;
	unordered_map<string, vector<Car>>report();
	Car find_car(string id)
	{
		vector<Car> v = getAllCars();
		for (auto& c : v)
		{
			if (c.getNr() == id)
				return c;
		}
	}
	const vector<Car>& getAllCars() const
	{
		return rep.getAll();
	}

	void undo()
	{
		if (undoActions.empty())
		{
			throw exception();
		}

		Undo* act = undoActions.back();
		act->doUndo();
		undoActions.pop_back();
		delete act;
	}

	const vector<Car>& addToList(const string& id)
	{
		const Car& c = rep.find_car(id);
		lista.addOne(c);
		return lista.listAll();
	}

	const vector<Car>& addRandom(int number)
	{
		lista.addRandom(number, rep.getAll());
		return lista.listAll();
	}
	void clearList()
	{
		lista.clearAll();
	}
	const vector<Car>& listAll()
	{
		return lista.listAll();
	}

	unordered_map<string, vector<Car>> histogram() {
		vector<Car> cars = lista.listAll();
		unordered_map<string, vector<Car>> res;
		for (Car c : cars)
		{
			res[c.getType()].push_back(c);
		}
		return res;
	}

	void exportListToCSV() const;
	void exportListToHTML() const;
};

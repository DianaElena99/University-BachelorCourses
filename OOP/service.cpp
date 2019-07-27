#include "stdafx.h"
#include "service.h"

void Service::create_car(string id, string ty, string br, string mo)
{
	Car c_{ id, ty, br, mo };
	rep.add_car(c_);
	undoActions.push_back(new UndoAdd{ rep, c_ });
}

void Service::remove_car(string id)
{
	vector<Car> v = rep.getAll();
	auto found = find_if(v.begin(), v.end(), [&](Car c_)
	{
		return c_.getNr() == id;
	});

	if (found == v.end())
		return;
	rep.remove_car(*found);
	undoActions.push_back(new UndoDelete{ rep, *found });
}

void Service::update_car(Car& c_)
{
	rep.update_car(c_);
	undoActions.push_back(new UndoUpdate{ rep, c_ });
}

vector<Car> Service::filterByType(string & type)
{
	vector<Car> res;
	auto& allCars = rep.getAll();
	copy_if(allCars.begin(), allCars.end(), back_inserter(res), [=](const Car& c_)
	{return c_.getType() == type;
	});
	return res;
}

vector<Car> Service::filterByBrand(string & br)
{
	vector<Car> res;
	auto& allCars = rep.getAll();
	copy_if(allCars.begin(), allCars.end(), back_inserter(res), [=](const Car& c_)
	{return c_.getBrand() == br;
	});
	return res;
}

vector<Car> Service::sortByNr() const
{
	auto v = rep.getAll();
	sort(v.begin(), v.end(), [](const Car& c1, const Car& c2)
	{
		return c1.getNr() < c2.getNr();
	});
	return v;
}

vector<Car> Service::sortByBrand() const
{
	auto v = rep.getAll();
	sort(v.begin(), v.end(), [](const Car& c1, const Car& c2)
	{
		return c1.getBrand() < c2.getBrand();
	});
	return v;
}

vector<Car> Service::sortByBrandAndModel() const
{
	auto v = rep.getAll();
	sort(v.begin(), v.end(), [](const Car& c1, const Car& c2)
	{
		if (c1.getBrand() == c2.getBrand())
			return c1.getModel() < c2.getModel();
		return c1.getBrand() < c2.getBrand();
	});
	return v;
}
unordered_map<string, vector<Car>> Service::report()
{
	unordered_map<string, vector<Car>> res;
	vector<Car> cars = rep.getAll();
	for (Car c : cars)
	{
		res[c.getType()].push_back(c);
	}
	return res;
}

void Service::exportListToCSV() const
{
	exportToCSV("list.csv", lista.listAll());
}

void Service::exportListToHTML() const
{
	exportToHTML("lista.html", lista.listAll());
}

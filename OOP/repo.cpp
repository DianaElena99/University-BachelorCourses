#include "stdafx.h"
#include <algorithm>    
#include <fstream>
#include "repo.h"
void Repo::update_car(Car& c_new)
{
	auto found = find_if(all.begin(), all.end(), [&](const Car& cc) {
		return cc.getNr() == c_new.getNr();
	});

	if (found == all.end())
	{
		throw RepoException("Inexistent car");
	}
	all.erase(found);
	all.push_back(c_new);
}

Car Repo::find_car(string id)
{
	auto found = find_if(all.begin(), all.end(), [&](const Car& cc)
	{
		return (cc.getNr() == id);
	});

	if (found == all.end())
		throw RepoException("Inexistent Car");
	return *found;
}


void RepoFile::loadFromFile()
{
	ifstream in(fName);
	if (!in.is_open())
	{
		throw RepoException("Unable to open file");
	}

	while (!in.eof())
	{
		string id, ty, br, mo;
		in >> id;
		if (in.eof())break;
		in >> ty;
		in >> br;
		in >> mo;

		Car c{ id.c_str(), ty.c_str(), br.c_str(), mo.c_str() };
		Repo::add_car(c);
	}
	in.close();
}

void RepoFile::writeToFile()
{
	std::ofstream out("output.csv");
	if (!out.is_open())
	{
		throw RepoException("Couldn't open file for writing output\n");
	}
	for (auto& car : getAll())
	{
		out << car.getNr() << "," << car.getType() << ","
			<< car.getBrand() << "," << car.getModel() << endl;
	}
	out.close();
}

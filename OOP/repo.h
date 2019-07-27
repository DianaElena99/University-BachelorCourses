#pragma once
#include "car.h"
#include <algorithm>
#include <string>
#include <vector>
using namespace std;

class RepoException
{
private:
	string msg;
public:
	RepoException(string mes) { this->msg = mes; }
	string getMsg() const { return msg; }
};

class Repo
{
private:
	vector<Car> all;
public:
	Repo() = default;
	Repo(const Repo& ot) = delete;
	virtual void add_car(Car& c)
	{
		auto found = find_if(all.begin(), all.end(), [&](const Car& cc) {
			return cc.getNr() == c.getNr();
		});
		if (found != all.end())
		{
			throw RepoException("Already existent car");
		}

		if (c.getNr() == "" || c.getModel() == "" || c.getBrand() == "" || c.getType() == "")
			throw RepoException("");
		all.push_back(c);
	}


	virtual void remove_car(Car& c)
	{
		auto found = find_if(all.begin(), all.end(), [&](const Car& cc) {
			return cc.getNr() == c.getNr();
		});

		if (found == all.end())
		{
			throw RepoException("Inexistent car");
		}
		auto rez = all.erase(found); 
	}


	virtual void update_car(Car& c_new);

	Car find_car(string id);

	const vector<Car>& getAll() const
	{
		return all;
	}
};

class RepoFile : public Repo
{
private:
	string fName;
	Repo repoFile;
	void loadFromFile();
	void writeToFile();

public:

	RepoFile(string _fName)
	{
		Repo();
		this->fName = _fName;
		loadFromFile();
	}

	void add_car(Car& c) override
	{
		Repo::add_car(c);
		writeToFile();
	}

	void remove_car(Car& c)override
	{
		Repo::remove_car(c);
		writeToFile();
	}
};
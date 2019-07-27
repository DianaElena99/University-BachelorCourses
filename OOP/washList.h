#pragma once
#include "repo.h"
#include "observer.h"
#include <random>
#include <chrono>
using namespace std;

class WashList : public Observable
{
private:
	vector<Car> inList;
public:
	WashList() = default;
	void addRandom(unsigned int howMany, vector<Car>all)
	{
		shuffle(all.begin(), all.end(), default_random_engine(random_device{}()));
		while (inList.size() < howMany && all.size() > 0)
		{
			inList.push_back(all.back());
			all.pop_back();
			this->notify();
		}
	}

	void clearAll()
	{
		inList.clear();
		this->notify();
	}

	void addOne(const Car& c)
	{
		inList.push_back(c);
		this->notify();
	}

	const vector<Car>& listAll() const
	{
		return inList;
	}
};
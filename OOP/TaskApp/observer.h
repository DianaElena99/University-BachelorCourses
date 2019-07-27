#pragma once
#include <vector>
#include <algorithm>

class Observer
{
public:
	Observer() {};
	virtual ~Observer() {};
	virtual void update() = 0;
};

class Observable
{
private:
	std::vector<Observer*> observers;
public:
	void add_observer(Observer* obs)
	{
		this->observers.push_back(obs);
	}
protected:
	void notify()
	{
		for (auto& obs : this->observers)
		{
			obs->update();
		}
	}
	virtual ~Observable(){}
};
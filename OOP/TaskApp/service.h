#pragma once
#include "repo.h"
#include <algorithm>

class Service : public Observable {
private:
	Repo& repo;
public:
	Service(Repo& repo_) :repo{ repo_ } {

	}
	void Add(int id, string& desc, string& stat, string progs) {
		Task T = Task{ id, desc, stat, progs };
		repo.add(T);
		this->notify();
	}

	void Update(int id, string newStat){
		repo.update(id, newStat);
		this->notify();
	}

	vector<Task> getAllSorted() const {
		auto v = repo.getAll();
		sort(v.begin(), v.end(), [](const Task& t1, const Task& t2) {
			return t1.getStatus() < t2.getStatus();
		});
		return v;
	}

};
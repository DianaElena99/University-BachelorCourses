#pragma once
#include <fstream>
#include <sstream>
#include "task.h"
#include <algorithm>
class Repo {
protected:
	vector<Task> tasks;
public:
	Repo() = default;
	const vector<Task>& getAll() const { return tasks; }
	virtual void add(Task& t) {
		auto found = find_if(tasks.begin(), tasks.end(), [&](Task& tt) {
			return tt.getID() == t.getID();
		});

		if (found != tasks.end()) {
			return;
		}
		tasks.push_back(t);
	}

	virtual void update(int id, string newStat) {
		auto found = find_if(tasks.begin(), tasks.end(), [&](Task& tt) {
			return tt.getID() == id;
		});

		if (found != tasks.end()) {
			(*found).setStatus(newStat);
		}
	}

};


class RepoFile : public Repo {
private:
	string fname;
	void loadFromFile() {
		ifstream in("taskuri.txt");
		string id, des, progs, st;
		int idINT;
		while (getline(in, id, ',')) {
			idINT = stoi(id);
			getline(in, des, ',');
			getline(in, st, ',');
			getline(in, progs, '\n');
			Task t{ idINT, des, st, progs };
			tasks.push_back(t);
		}
		in.close();
	}
	void writeToFile() {

	}
public:
	RepoFile() :Repo(), fname{ "taskuri.txt" }{
		loadFromFile();
	}
	void add(Task& t) override {
		Repo::add(t);
		writeToFile();
	}

	void update(int id, string newStat)override {
		Repo::update(id, newStat);
		writeToFile();
	}
};
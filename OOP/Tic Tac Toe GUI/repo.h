#pragma once
#include "domain.h"
#include <fstream>
class Repo {
protected:
	vector<Game> all;
public:
	Repo() = default;
	/*
	Adauga un joc in aplicatie
	*/
	virtual void add(Game& g) {
		all.push_back(g);
	}

	/*
	Modifica un joc din aplicatie
	*/
	virtual void update(Game& g) {
		int i = 0;
		for (auto gg : all) {
			if (gg.getID() == g.getID()) {
				all.erase(all.begin() + i);
				all.push_back(g);
				return;
			}
			i++;
		}
	}

	/*
	Functie care returneaza un vector cu toate jocurile din aplicatie
	*/
	const vector<Game>& getAll() const { return all; }
};

/*
Clasa de repository pentru fisiere
*/
class RepoFile : public Repo{
private:
	/*
	Functie care citeste datele dintr-un fisier
	*/
	void read() {
		ifstream in("input.txt");
		while (!in.eof()) {
			int id_, dim_; string tabla_, stare_, next_;
			in >> id_ >> dim_;
			in >> tabla_ >> next_ >> stare_;
			Game g = Game{ id_, dim_, tabla_, stare_, next_ };
			all.push_back(g);
		}
	}

	/*
	Functie care scrie intr-un fisier 
	*/
	void write() {
		ofstream out("input.txt");
		int i = 0;
		for (auto g : all) {
			i++;

			out << g.getID() << " " << g.getDIM() << " "
				<< g.getTable() << " " << g.getNext() << " " << g.getStare();
			if (i < all.size()) out << endl;
		}
		out.close();
	}

	string fname;
public:
	/*
	Constructorul pentru clasa RepoFile
	*/
	RepoFile() :fname{ "input.txt" } {
		read();
	}

	/*
	Functie de adaugare in fisier, suprascrie functia din clasa de baza
	*/
	void add(Game& g) override {
		Repo::add(g);
		write();
	}

	/*
	Functie de modificare in fisier, suprascrie functia din clasa de baza
	*/
	void update(Game& g) override {
		Repo::update(g);
		write();
	}
};
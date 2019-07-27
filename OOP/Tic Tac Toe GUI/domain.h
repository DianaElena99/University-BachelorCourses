#pragma once
#include <string>
#include <vector>
#include <algorithm>
#include <iostream>
#include <fstream>
using namespace std;

class Game {
	int id;
	int dim;
	string tabla;
	string stare;
	string next;

public:
	/*
	Constructor penntru joc
	*/
	Game(int id_, int dim_, string& tabla, string& stare, string& next_):
		id{ id_ }, dim{ dim_ }, tabla{ tabla }, stare{ stare }, next{ next_ }{
	}

	/*
	Functii getter si setter
	*/
	int getID() { return id; }
	int getDIM() { return dim; }

	const string getTable() {
		return tabla;
	}

	const string getStare() { return stare; }
	const string getNext() { return next; }

	void setStare(string st) {
		stare = st;
	}

	void setTabla(string tb) {
		tabla = tb;
	}

	void setNext(string nx) {
		next = nx;
	}

	void setDim(int di) {
		dim = di;
	}
};
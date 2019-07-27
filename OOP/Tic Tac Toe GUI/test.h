#pragma once
#include "service.h"

void test() {
	RepoFile repoTest;
	Service servTest{ repoTest };
	vector<Game> v = servTest.All();
	int i = v.at(0).getDIM();
	int j = v.at(0).getID();
	string s = v.at(0).getStare();
	string t = v.at(0).getTable();
	string n = v.at(0).getNext();
	servTest.addGame(10, 3, "XOXOXOXOX", "O", "Terminat");
	servTest.update(10, 3, "---------", "X", "Neinceput");
	servTest.update(10, 3, "----XO---", "O", "InDerulare");
}
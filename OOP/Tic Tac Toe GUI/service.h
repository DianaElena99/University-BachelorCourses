#pragma once
#include "repo.h"


class Service {
private:
	Repo& rep;
public :
	/*
	Constructor pt service, primeste o referinta la repo
	*/
	Service(Repo& rep_):rep{rep_}{}

	/*
	Adauga un loc in aplicatie
	dim - dimensiunea tabelei
	tabla - tabla de joc
	nextPlayer - urmatorul jucator, poate fi X sau O
	status - starea jocului
	*/
	void addGame(int id, int dim, string tabla, string nextPlayer, string status) {

		Game g = Game{ id, dim, tabla, status, nextPlayer };
		rep.add(g);
	}

	/*
	Modifica un joc din aplicatie
	dim - dimensiunea tabelei
	tabla - tabla de joc
	nextPlayer - urmatorul jucator, poate fi X sau O
	status - starea jocului
	*/
	void update(int id, int dim, string tabla, string nextPlayer, string status) {

		Game g = Game{ id, dim, tabla, status, nextPlayer };
		rep.update(g);
	}

	/*
	returneaza un vector cu toate jocurile
	*/
	const vector<Game>& All()const{
		return rep.getAll();
	}
};
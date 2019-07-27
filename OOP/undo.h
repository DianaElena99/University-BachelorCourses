#pragma once
#include "repo.h"
#include "car.h"
class Undo
{
public:
	virtual void doUndo() = 0;
	virtual ~Undo() = default;
};

class UndoAdd : public Undo
{
	Car addedCar;
	Repo& rep;
public:
	UndoAdd(Repo& rep_, const Car& c) :
		rep{ rep_ }, addedCar{ c }{};
	void doUndo() override
	{
		rep.remove_car(addedCar);
	}
};

class UndoDelete : public Undo
{
	Car removedCar;
	Repo& rep;
public:
	UndoDelete(Repo& rep_, const Car& c) :
		rep{ rep_ }, removedCar{ c }{};
	void doUndo() override
	{
		rep.add_car(removedCar);
	}
};

class UndoUpdate : public Undo
{
	Car updatedCar;
	Repo& rep;
public:
	UndoUpdate(Repo& rep_, const Car& c) :
		rep{ rep_ }, updatedCar{ c }{};
	void doUndo() override
	{
		rep.update_car(updatedCar);
	}

};
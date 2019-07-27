#pragma once
#include <string>
#include <assert.h>
using namespace std;
class Car
{
private:
	string nr;
	string type;
	string brand;
	string model;
public:
	Car(string nr_, string type_, string brand_, string model_) :
		nr{ nr_ }, type{ type_ }, brand{ brand_ }, model{ model_ }{};
	Car(const Car& ot) : nr{ ot.nr }, type{ ot.type }, brand{ ot.brand }, model{ ot.model }{};

	string getNr() const noexcept
	{
		return nr;
	}

	string getType() const noexcept
	{
		return type;
	}

	string getBrand() const noexcept
	{
		return brand;
	}

	string getModel() const noexcept
	{
		return model;
	}
};


#pragma once
#include "washList.h"
#include "car.h"
#include <fstream>
#include <vector>
#include <string>
using namespace std;

void exportToHTML(const string& fName, const vector<Car>&cars);
void exportToCSV(const string& fName, const vector<Car>& cars);

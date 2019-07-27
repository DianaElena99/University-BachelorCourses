#pragma once
#include <string>
#include <vector>
using namespace std;
class Task {
private:
	string desc;
	int id; 
	string progs;
	string status;
public:
	Task(int id, string& desc, string& status, string& progs) :id{ id }, desc{ desc }, status{ status }, progs{ progs } {
	}

	int getID() const { return id; }
	void setStatus(const string& newstat) {
		this->status = newstat;
	}
	string getStatus() const { return status; }
	string getDesc() const { return desc; }
	string getProgs() const { return progs; }
 };
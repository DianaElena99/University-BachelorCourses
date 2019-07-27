#include "stdafx.h"
#include "export.h"

void exportToHTML(const string & fName, const vector<Car>& cars)
{
	ofstream out(fName, std::ios::trunc);
	if (!out.is_open())
	{
		throw exception();
	}
	out << "<html> " << endl << "<body>";
	out << "<table border=\"1\" style=\"width:100% \" > " << endl;
	for (const auto& c : cars)
	{
		out << "<tr>" << endl;
		out << "<td>" << c.getNr() << " " << c.getType() << " "
			<< c.getBrand() << " " << c.getModel() << endl;
		out << "</tr>" << endl;
	}

	out << "</table>" << endl << "</body></html>" << endl;
	out.close();
}

void exportToCSV(const string & fName, const vector<Car>& cars)
{
	ofstream out(fName, std::ios::trunc);
	if (!out.is_open())
	{
		throw exception();
	}

	for (const auto& c : cars)
	{
		out << c.getNr() << "," << c.getType() << ","
			<< c.getBrand() << "," << c.getModel() << endl;
	}

	out.close();
}

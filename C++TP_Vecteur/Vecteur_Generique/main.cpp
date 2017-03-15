/*OUBAH ROCHE TP2*/
#include "vecteur.h"
#include <string>
#include <iostream>

using namespace std;

int main()
{
	Vecteur<int> v1;
	Vecteur<float> v2;
	cin >> v1;
	cin >> v2;
	cout << v1 + v2 << endl;
	cout << "float*int "<<v2 * v1 << endl;
	cout << "int*float " << v1 * v2 << endl;
	
	Vecteur<string> v4;
	Vecteur<string> v5;
	cin >> v4;
	cin >> v5;
	cout << v4+v5 << endl;//on aura une concatenation
	//cout << v4*v5 << endl; //ne marchera pas ici

	return 0;
}

/*OUBAH ROCHE TP2*/
#include "vecteur.h"
#include <string>
#include <iostream>

using namespace std;

/** \brief Programme principal */
int main()
{

	/*Vecteur* v = lireVecteur(std::cin);
	afficherVecteur(v, std::cout);
	cout << "Saisir la coordonne du vecteur que vous voulez modifier : " << endl;
	int coordonnee;
	cin >> coordonnee;
	cout << "Saisir la nouvelle valeur pour cette coordonnee : " << endl;
	int valeur;
	cin >> valeur;
	v->set(valeur, coordonnee);
	afficherVecteur(v, std::cout);*/

	/*Vecteur * abc = lireVecteur(std::cin);
	Vecteur * abcd = lireVecteur(std::cin);
	Vecteur v = add(abc, abcd);
	afficherVecteur(&v,std::cout);
	delete abc;
	delete abcd;*/

	Vecteur a = Vecteur(5,5);
	Vecteur * b = new Vecteur(4,4);
	Vecteur c = (*b)+a;
	afficherVecteur(&c, std::cout);

	Vecteur somme ( a + (*b) + c );
	afficherVecteur(&somme, std::cout);
	cout << a*a <<endl;
	delete b;

	Vecteur detest=Vecteur(4);
	cin >> detest;
	cout<<detest<<endl;

	return 0;
}

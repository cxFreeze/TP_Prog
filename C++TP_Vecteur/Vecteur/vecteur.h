/*OUBAH ROCHE TP2*/
#include <iostream>
#ifndef _VECTEUR_H
#define _VECTEUR_H

// Declaration de la classe vecteur
class Vecteur {
private :
  // attributs
	int dimension;
	float * vecteur;

public :
   //Constructeurs
	Vecteur(int dimensions=3, float valeurInitiale=0);
	Vecteur(const Vecteur & v);//constructeur de copie
	~Vecteur();

	float get(int indice) const;
	void set(int indice,float valeur);
	int dimensions() const;

	void copierVecteur(const Vecteur & v);

	//Surdefinition d'operateurs
	Vecteur & operator= (const Vecteur & v);
	Vecteur operator+ (const Vecteur & v) const;
	const float & operator[] (int indice) const;
	float & operator[] (int indice);

private :
  // methodes privees d'implementation (si besoin)
};

// Prototypes des fonctions
void afficherVecteur(const Vecteur * v, std::ostream & out = std::cout);
Vecteur * lireVecteur(std::istream & in = std::cin);
Vecteur add(const Vecteur * v1, const Vecteur * v2);//aditionner 2 vecteurs

//Surdefinition d'operateurs
double operator *(const Vecteur & v1, const Vecteur & v2);
std::ostream & operator<< (std::ostream & out, const Vecteur & v);
std::istream & operator>>(std::istream & in, Vecteur & v);
#endif

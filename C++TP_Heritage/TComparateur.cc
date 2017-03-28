#include "TComparateur.h"

//Constructeur
TComparateur::TComparateur(bool sensTri)
{
	choixTri = sensTri;
}

//Destructeur
TComparateur::~TComparateur()
{
}

bool TComparateur::operator()(const Vehicule * v1, const Vehicule * v2) const
{
	//Tri croissant
	if (choixTri)
		return compare(v1, v2);
	//Tri decroissant
	else
		return compare(v1, v2);
}

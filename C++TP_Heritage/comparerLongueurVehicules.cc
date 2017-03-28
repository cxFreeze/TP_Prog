#include "comparerLongueurVehicules.h"

ComparerLongueurVehicules::ComparerLongueurVehicules(bool sensTri) : TComparateur(sensTri) {
}

ComparerLongueurVehicules::~ComparerLongueurVehicules() {}

/*bool ComparerLongueurVehicules::operator () (const Vehicule * v1, const Vehicule * v2) const
{
	if(choixTri)
		return v1->getLongueur() < v2->getLongueur(); //croissant
	else
		return v1->getLongueur() > v2->getLongueur(); //decroissant
}*/

bool ComparerLongueurVehicules::compare(const Vehicule * v1, const Vehicule * v2) const
{
	if (choixTri)
		return v1->getLongueur() < v2->getLongueur(); //croissant
	else
		return v1->getLongueur() > v2->getLongueur(); //decroissant
}


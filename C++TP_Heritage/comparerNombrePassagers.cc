#include "comparerNombrePassagers.h"

ComparerNombrePassagers::ComparerNombrePassagers(bool sensTri) : TComparateur(sensTri)
{
}

ComparerNombrePassagers::~ComparerNombrePassagers()
{
}

/*bool ComparerNombrePassagers::operator()(const Vehicule * v1, const Vehicule * v2) const
{
	if (choixTri)
		return v1->getPassagers() < v2->getPassagers(); //croissant
	else
		return v1->getPassagers() > v2->getPassagers(); //d�croissant
}*/

bool ComparerNombrePassagers::compare(const Vehicule * v1, const Vehicule * v2) const
{
	if (choixTri)
		return v1->getPassagers() < v2->getPassagers(); //croissant
	else
		return v1->getPassagers() > v2->getPassagers(); //d�croissant
}

#ifndef COMPARERLONGUEURVEHICULES_H_
#define COMPARERLONGUEURVEHICULES_H_

#include "TComparateur.h"
#include "vehicule.h"

class ComparerLongueurVehicules : public TComparateur {
public:
	//constructeur
	ComparerLongueurVehicules(bool sensTri = true);

	//destructeur
	virtual ~ComparerLongueurVehicules();

	//virtual bool operator () (const Vehicule * v1, const Vehicule * v2) const; //ancienne methode que l'on enleve pour le patron de conception

	virtual bool compare(const Vehicule * elem1, const Vehicule * elem2) const; //methode a redefinir pour le patron de conception
};

#endif  /* COMPARERLONGUEURVEHICULES_H_ */

#ifndef CYCLE_H_
#define CYCLE_H_

#include "vehicule.h"
#include <iostream>

class Cycle : public Vehicule {
public:
	//constructeur
	Cycle();

	//constructeur de copie
	Cycle(const Cycle & cycle);

	/// destructeur
	~Cycle(void);

	/// determiner le tarif du cycle
	double	calculerTarif(void)const;

	/// afficher les caracteristiques du cycle
	void afficher(std::ostream & s = std::cout) const;

	Cycle* clone() const;
};

#endif /* CYCLE_H_ */

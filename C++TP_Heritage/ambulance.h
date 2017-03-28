#ifndef AMBULANCE_H_
#define AMBULANCE_H_

#include "auto.h"
#include <iostream>

class Ambulance : public Auto {
public:

	//constructeur
	Ambulance(unsigned int nbp, bool toutTerrain);

	//constructeur de copie
	Ambulance(const Ambulance & ambulance);

	/// destructeur
	~Ambulance(void);

	//! determiner le tarif de l'ambulance
	double	calculerTarif(void)const;

	//! afficher les caracteristiques de l'ambulance
	void afficher(std::ostream & s = std::cout) const;

	bool estToutTerrain() const;

	//methode de clonage
	Ambulance* clone() const;

};



#endif /* AMBULANCE_H_ */

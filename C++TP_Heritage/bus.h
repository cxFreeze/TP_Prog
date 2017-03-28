#ifndef BUS_H_
#define BUS_H_

#include "vehicule.h"
#include <iostream>

class Bus : public Vehicule {
public:

	 //constructeur
	 Bus(unsigned int lg, unsigned int nbp);

	 //constructeur de copie
	 Bus(const Bus & bus);

	 /// destructeur
	 virtual ~Bus(void);

	 //! determiner le tarif du vehicule
	 virtual double	calculerTarif(void) const;

	 //! afficher les caracteristiques du vehicule
	 virtual void afficher(std::ostream & s = std::cout) const;

	 //methode de clonage
	 Bus* clone() const;

};



#endif /* BUS_H_ */

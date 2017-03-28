#ifndef AUTO_H_
#define AUTO_H_

#include "vehicule.h"
#include <iostream>

class Auto : public Vehicule {
public:

	//constructeur
	Auto(unsigned int nbp, bool toutTerrain);

	//constructeur de copie
	Auto(const Auto & autoo);

	 /// destructeur
	 ~Auto(void);

	 //! determiner le tarif du vehicule
	 double	calculerTarif(void)const ;

	 //! afficher les caracteristiques du vehicule
	 void afficher(std::ostream & s = std::cout) const;

	 bool estToutTerrain() const;

	 //methode de clonage
	 Auto* clone() const;

protected:
	 bool toutTerrain;
};



#endif /* AUTO_H_ */

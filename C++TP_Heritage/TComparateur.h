#ifndef TCOMPARATEUR_H_
#define TCOMPARATEUR_H_

#include "vehicule.h"

class TComparateur {
public:
	//Constructeur
	TComparateur(bool sensTri = true);

	//Destructeur
	virtual ~TComparateur();


	bool operator () (const Vehicule * v1, const Vehicule * v2) const;

	// cette methode virtuelle sera redefinie dans les classes filles et appelee dans operator ()
	virtual bool compare (const Vehicule * v1, const Vehicule * v2) const = 0;

protected:
	bool choixTri;
};

#endif  /* TCOMPARATEUR_H_ */

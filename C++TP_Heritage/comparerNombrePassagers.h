#ifndef COMPARERNOMBREPASSAGERS_H_
#define COMPARERNOMBREPASSAGERS_H_

#include "TComparateur.h"
#include "vehicule.h"

class ComparerNombrePassagers : public TComparateur {
public:
	//constructeur
	ComparerNombrePassagers(bool sensTri = true);

	//destructeur
	virtual ~ComparerNombrePassagers();


	//virtual bool operator () (const Vehicule * v1, const Vehicule * v2) const;
	virtual bool compare(const Vehicule * elem1, const Vehicule * elem2) const;
};

#endif  /* COMPARERNOMBREPASSAGERS_H_ */

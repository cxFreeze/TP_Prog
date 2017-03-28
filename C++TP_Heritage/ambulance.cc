#include <cassert>
#include <assert.h>
#include <iostream>
#include <string>
#include "ambulance.h"
#include "auto.h"

//constructeur
Ambulance::Ambulance(unsigned int nbp, bool toutTerrain) : Auto(nbp, toutTerrain)
{}

//constructeur de copie
Ambulance::Ambulance(const Ambulance & ambulance) : Auto(ambulance.getPassagers(), ambulance.estToutTerrain())
{}

//destructeur
Ambulance::~Ambulance(void)
{}


double Ambulance::calculerTarif(void) const
{
	return 0.0;
}

void Ambulance::afficher(std::ostream & s) const
{
	if (toutTerrain)
		s << "Ambulance tout-terraine Longueur : " << this->getLongueur() << "  Nombre de passagers : " << this->getPassagers() << std::endl;
	else
		s << "Ambulance Longueur : " << this->getLongueur() << " Nombre de passagers : " << this->getPassagers() << std::endl;
}

bool Ambulance::estToutTerrain() const
{
	return toutTerrain;
}

//methode de clonage
Ambulance * Ambulance::clone() const
{
	return new Ambulance(*this);
}

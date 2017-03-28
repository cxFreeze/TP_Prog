#include "cycle.h"

Cycle::Cycle() : Vehicule ()
{
}

Cycle::Cycle(const Cycle & cycle)
{
}

Cycle::~Cycle(void)
{}

double Cycle::calculerTarif(void) const
{
	return 20.00 + this->getPassagers()*tarifPassager;
}

void Cycle::afficher(std::ostream & s) const
{
	s << "Cycle Longueur : " << this->getLongueur() << " Nombre de passagers : " << this->getPassagers() << std::endl;
}

Cycle * Cycle::clone() const
{
	return new Cycle(*this);
}

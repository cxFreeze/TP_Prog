#include <cassert>
#include <assert.h>
#include <iostream>
#include <string>
#include "vehicule.h"
#include "bus.h"

using namespace std;

//constructeur
Bus::Bus(unsigned int lg,unsigned int nbp):Vehicule(lg,nbp){}

//constructeur de copie
Bus::Bus(const Bus & bus) : Vehicule(bus.getLongueur(), bus.getPassagers())
{
}

// destructeur
Bus::~Bus(){}

//methode pour calculer le tarif du bus
double Bus::calculerTarif() const{
	return (double) 200+50*this->getLongueur()+this->getPassagers()*tarifPassager;
}


void Bus::afficher(std::ostream & s) const
{
	s<<"Bus Longueur : "<< this->getLongueur() <<" Nombre de passagers : "<< this->getPassagers() <<endl;
}

//permet de cloner un bus
Bus * Bus::clone() const
{
	return new Bus(*this);
}

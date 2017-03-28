#include <cassert>
#include <assert.h>
#include <iostream>
#include <string>
#include "vehicule.h"
#include "auto.h"

using namespace std;

//constructeur, on verifie que le nombre de passagers (nbp) >= 0
Auto::Auto(unsigned int nbp, bool toutTerrain):Vehicule(2,nbp){
	assert(nbp >=0);
	this->toutTerrain=toutTerrain;
}

//constructeur de copie
Auto::Auto(const Auto & autoo) : Vehicule(autoo.longueur, autoo.passagers)
{
	this->toutTerrain = autoo.toutTerrain;
}

// destructeur
Auto::~Auto(){
}

double Auto::calculerTarif() const{
	if(toutTerrain == true) return 350;
	else return 100;
}

/* Afficher une auto */
void Auto::afficher(std::ostream & s) const
{
	if(toutTerrain)
		s<<"Voiture tout-terrain Longueur : "<< this->getLongueur() <<"  Nombre de passagers : "<< this->getPassagers() <<endl;
	else
		s<<"Voiture Longueur : "<< this->getLongueur() <<" Nombre de passagers : "<< this->getPassagers() <<endl;
}

bool Auto::estToutTerrain() const {
	return toutTerrain;
}

Auto * Auto::clone() const
{
	return new Auto(*this);
}

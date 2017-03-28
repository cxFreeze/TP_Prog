#include <cassert>
#include <assert.h>
#include <iostream>
#include <string>
#include "vehicule.h"

using namespace std;
//------------------------------------------------------------------------
// classe Vehicule
//------------------------------------------------------------------------

// constructeur
Vehicule::Vehicule(unsigned int lg, unsigned int nbp){
	assert(lg>=0 && nbp >=0);
	longueur=lg;
	passagers=nbp;
}

// destructeur
Vehicule::~Vehicule(){
}

// longueur d'un vehicule
unsigned int Vehicule::getLongueur() const{
	return longueur;
}

// nombre de personnes dans le vehicule
unsigned int Vehicule::getPassagers() const{
	return passagers;
}

// operateur d'affichage
std::ostream & operator << (std::ostream & sortie, const Vehicule & v){
	v.afficher(sortie);
	return sortie;
}

#include <cassert>
#include <assert.h>
#include <iostream>
#include <string>
#include "ferry.h"
#include <list>

using namespace std;
//------------------------------------------------------------------------
// classe Ferry
//------------------------------------------------------------------------

// constructeur
Ferry::Ferry(unsigned int lg, unsigned int nbp){
	assert(lg>=1 && nbp >=1);
	longueur=lg;
	passagers=nbp;
}

//destructeur
Ferry::~Ferry(){
	listeVehicule::const_iterator it = m_vehicules.begin();
	for (; it != m_vehicules.end(); ++it) {
		delete (*it); //on parcourt avec iterator et on delete
	}
}

//methode pour ajouter un vehicule dans le ferry
bool  Ferry::ajouter(const Vehicule * pv){
	if (!estPlein(pv)){return false;} // on check d'abord si c'est plein
	else{
		m_vehicules.push_back(pv);
		return true;
	}
}

//methode pour calculer le tarif total dans le ferry
double Ferry::calculerTarif() const{
	double tarifTotal=0;
	listeVehicule::const_iterator it = m_vehicules.begin();
	for (; it != m_vehicules.end(); ++it){
		tarifTotal+=(*it)->calculerTarif();
	}
	return tarifTotal;
}

//methode pour savoir si le ferry est plein
bool Ferry::estPlein(const Vehicule * pv) const{
	double longueurTotale=0, nbpassagersTotaux = 0;
	listeVehicule::const_iterator it = m_vehicules.begin();
	for (; it != m_vehicules.end(); ++it){
		longueurTotale+=(*it)->getLongueur();
		nbpassagersTotaux+=(*it)->getPassagers();
	 }
	//on recupere longueur et nb de passgaers deja dans le ferry et on verifie que le nouveau vehicule peut entrer
	if(longueur-longueurTotale >= pv->getLongueur() && passagers - nbpassagersTotaux >= pv->getPassagers())	return true;
	else return false;
}

//donne longueur du ferry
unsigned int Ferry::getLongueur() const {
	return longueur;
}

//donne le nombre de passagers
unsigned int Ferry::getPassagers() const{
	return passagers;
}

//affiche le contenu du ferry
void Ferry::afficher(std::ostream & s)const {
	s<<"Ferry Longueur : "<< this->getLongueur() <<"m Nombre de vehicules : "
		<<m_vehicules.size()<<" Nombre de passagers : "
		<< this->getPassagers()<<endl;
	listeVehicule::const_iterator it = m_vehicules.begin();
	for (; it != m_vehicules.end(); ++it) {
		(*it)->afficher();
	}
	s <<"tarif total des vehicules transportes : "<< calculerTarif() <<" euros\n" << endl;
}

//redefinition de l'operateur <<
std::ostream & operator << (std::ostream & sortie, const Ferry & ferry){
	ferry.afficher(sortie);
	return sortie;
}

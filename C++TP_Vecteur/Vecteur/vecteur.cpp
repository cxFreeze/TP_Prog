/*OUBAH ROCHE TP2*/
#include <cassert>
#include <assert.h>
#include <iostream>
#include <string>
#include "vecteur.h"

using namespace std;


/**
* Constructeur de vecteur
* @param dimension 		dimension du vecteur
* @param coordonnees		coordonnées pour chacune des dimensions du vecteur
*/
Vecteur::Vecteur(int nbDimensions, float valeur){
	assert(nbDimensions>=1);
	this->dimension = nbDimensions;
	this->vecteur = new float[this->dimension];
	for(int i=0; i<this->dimension; i++){
		this->set(i,valeur);
	}
	cout<<"Création d'un vecteur de dimensions : "<<this->dimensions()<<" et de valeurs initialisées à "<<valeur<<endl;
}

/**
*Constructeur par recopie de Vecteur
*@param v			vecteur à recopier
*/
Vecteur::Vecteur(const Vecteur & v){
	copierVecteur(v);
}

/**
* Destructeur de vecteur
*/
Vecteur::~Vecteur()
{
	delete[] this->vecteur;
}

/**
* Méthode permettant de consulter la valeur d'une coordonnée
* @param indice		indice de la coordonnée
* @pre	indice >= 0 && indice < dimension
*/
float Vecteur::get(int indice) const{
	assert(indice>=0 && indice<this->dimension);
	return this->vecteur[indice];
}

/**
* Méthode permettant de changer la valeur d'une coordonnée
* @param index		indice de la coordonnée
* @param valeur	nouvelle valeur de la coordonnée
* @pre	indice >= 0 && indice < dimension
*/
void Vecteur::set(int indice, float valeur){
	assert(indice>=0 && indice<this->dimension);
	this->vecteur[indice] = valeur;
}

/**
* Méthode permettant la consultation du nombre de dimension du vecteur
*/
int Vecteur::dimensions() const{
	return this->dimension;
}

/*
* Méthode copierVecteur
* Copie le vecteur v en paramètre ainsi que les valeurs de ses coordonnées
*/
void Vecteur::copierVecteur(const Vecteur & v){
	dimension=v.dimensions();
	vecteur = new float[dimension];
	for(int i=0; i<dimension;i++){
		vecteur[i] = v.get(i);
	}
}

/**
* Surdéfinition de l'opérateur d'affectation
* @param v		vecteur à ajouter
*/
Vecteur & Vecteur::operator =(const Vecteur & v){
	if(this != &v){
		//Désallouer la mémoire
		if(vecteur!=NULL) delete[] vecteur;
		//copier le contenu
		copierVecteur(v);
	}
	return (*this);
}

/*
* redéfinition de l'opérateur d'addition
* Permet d'additionner plusieurs vecteurs entre eux
*/
Vecteur Vecteur::operator+ (const Vecteur & v)const{
	return add(this,&v);
}

/*
* redéfinition de l'opérateur []
* Permet la consultation (usage du const) des coordonnées d'un vecteur
*/
const float & Vecteur::operator[] (int indice) const{
	return *(this->vecteur+indice);
}

/*
* redéfinition de l'opérateur []
* Permet la consultation et la modification des coordonnées d'un vecteur
*/
float & Vecteur::operator[] (int indice){
	assert(indice>=0 && indice<this->dimension);
	return *(this->vecteur+indice);
}


/**
* Fonction qui affiche un vecteur
* @param v		le vecteur à afficher
*/
void afficherVecteur(const Vecteur * v, std::ostream & out){
	for(int i=0; i< v->dimensions(); i++){
		out<<(float)v->get(i)<<",";
	}
}

/**
* Fonction qui crée et initialise un vecteur dont les caractéristiques
* sont fournies par l'utilisateur
* @return		pointeur sur vecteur alloué dynamiquement
*/
Vecteur * lireVecteur(std::istream & in)
{
	cout << "Saisir le nombre de dimensions du vecteur : " << endl;
	int i;
	in >> i;
	Vecteur* v = new Vecteur(i);
	float x;
	cout << "Saisir les valeurs de chaque coordonnees : " << endl;
	for (i = 0; i < v->dimensions() ; i++) {
		in >> x;
		v->set(i,x);
	}
	cout << endl;
	return v;
}

/*
* fonction add pour additionner 2 vecteurs pas necessairement de même dimensions
* Additionne deux vecteur et retourne le vecteur resultant
*/
Vecteur add(const Vecteur * v1, const Vecteur * v2)
{
	int max = v1->dimensions() > v2->dimensions() ? v1->dimensions() : v2->dimensions();
	Vecteur v = Vecteur(max);
	for (int i = 0; i < v.dimensions(); i++) {
		if (i < v1->dimensions() && i < v2->dimensions())
			v.set(i,v1->get(i) + v2->get(i));
		else if (i < v2->dimensions() && i >= v1->dimensions())
			v.set(i,v2->get(i));
		else if (i < v1->dimensions() && i >= v2->dimensions())
			v.set(i,v1->get(i));
	}
	return v;
}

/*
* redéfinition de l'opérateur de multiplication
* @Pre-condition : les vecteurs sont de mêmes dimensions
* Permet de calculer le produit scalaire de deux vecteurs
*/
double operator *(const Vecteur &v1,const Vecteur &v2){
	assert(v1.dimensions() == v2.dimensions());
	double res = 0;
	for(int i=0;i <v1.dimensions(); i++)
		res+=v1.get(i)*v2.get(i);

	return res;
}

/*
* redéfinition de l'opérateur <<
* Permet d'afficher les valeurs des n dimensions du vecteur v
*/
std::ostream & operator<<(std::ostream & out, const Vecteur & v){
	for(int i=0; i< v.dimensions(); i++){
			out<<(float)v.get(i)<<" ";
		}
	return out;
}

/*
* redéfinition de l'opérateur >>
* Permet la saisie d'un vecteur
*/
std::istream & operator>>(std::istream & in, Vecteur & v){
	float val;
	cout << "Saisir les valeurs de chaque coordonnees : " << endl;
	for(int i=0;i<v.dimensions();i++){
		in >> val;
		v.set(i,val);
	}
	return in;
}

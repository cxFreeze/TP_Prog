/*OUBAH ROCHE TP2*/
#include <iostream>
#include <cassert>
#ifndef _VECTEUR_H
#define _VECTEUR_H

using namespace std;

// Declaration de la classe generique vecteur
template <class T> class Vecteur {
private:
	// attributs
	int dimension;
	T * vecteur;
	void destroy();//methode qui sera appelee dans destructeur
public:
	Vecteur(int dimensions = 3, T valeur = T()); //Appel du constructeur par defaut de T
	~Vecteur(); //destructeur
	Vecteur(const Vecteur<T> & v); //constructeur de copie
	template <class U> Vecteur(const Vecteur<U> & v); //constructeur de copie generique 

	T get(int indice) const;
	void set(int indice, T valeur);
	int dimensions() const;
	void copierVecteur(const Vecteur<T> & v);

	Vecteur & operator = (const Vecteur<T> & v);
	template <class U>	Vecteur operator + (const Vecteur<U> & v) const;
	const T & operator [] (int indice) const;
	T & operator [] (int indice);
};

/*********************************************
* Definition methodes de la classe *
*********************************************/

template<class T> Vecteur<T>::Vecteur(int nbDimensions, T valeur) {
	assert(nbDimensions >= 1);
	this->dimension = nbDimensions;
	this->vecteur = new T[this->dimension];
	for (int i = 0; i<this->dimension; i++) {
		this->set(i, valeur);
	}
	cout << "Creation d'un vecteur de dimensions : " << this->dimensions() << " et de valeurs initialisees a " << valeur << endl;
}

template<class T> Vecteur<T>::Vecteur(const Vecteur<T> & v) {
	copierVecteur(v);
}

/*
* Constructeur de copie generique
*/
template <class T> template <class U>
Vecteur<T>::Vecteur(const Vecteur<U> & v) {
	dimension = v.dimensions();
	vecteur = new T[dimension];
	for (int i = 0; i<dimension; i++) {
		vecteur[i] = (T) v[i];
	}
}

template<class T> Vecteur<T>::~Vecteur()
{
	this->destroy();//on appel notre fonction destroy pour le destructeur
}

template<class T> void Vecteur<T>::destroy()
{
	delete[] this->vecteur;
}

template<class T> T Vecteur<T>::get(int indice) const {
	assert(indice >= 0 && indice<this->dimension);
	return this->vecteur[indice];
}

template<class T> void Vecteur<T>::set(int indice, T valeur) {
	assert(indice >= 0 && indice<this->dimension);
	this->vecteur[indice] = valeur;
}

template<class T> int Vecteur<T>::dimensions() const {
	return this->dimension;
}

template<class T> void Vecteur<T>::copierVecteur(const Vecteur<T> & v) {
	dimension = v.dimensions();
	vecteur = new T[dimension];
	for (int i = 0; i<dimension; i++) {
		vecteur[i] = v.get(i);
	}
}

template<class T> Vecteur<T> & Vecteur<T>::operator= (const Vecteur<T> & v) {
	if (this != &v) {
		//Desallouer la memoire
		if (vecteur != NULL) vecteur->destroy();
		//copier le contenu
		copierVecteur(v);
	}
	return (*this);
}

template<class T> template<class U>
Vecteur<T> Vecteur<T>::operator + (const Vecteur<U> & v)const {
	return add(this, &v);
}

template<class T> const T & Vecteur<T>::operator[] (int indice) const {
	return *(this->vecteur + indice);
}

template<class T> T & Vecteur<T>::operator[] (int indice) {
	assert(indice >= 0 && indice<this->dimension);
	return *(this->vecteur + indice);
}

template<class T> std::ostream & operator << (std::ostream& out, const Vecteur<T> & v) {
	for (int i = 0; i< v.dimensions(); i++) {
		out << v[i] << " ";
	}
	return out;
}

template<class T> std::istream & operator >> (std::istream& in, Vecteur<T> & v) {
	T val;
	cout << "Saisir les valeurs de chaque coordonnees : " << endl;
	for (int i = 0; i<v.dimensions(); i++) {
		in >> val;
		v[i] = val;
	}
	return in;
}

/**************************************************
* Fonctions utilisees par la classe Vecteur<T>	  *
**************************************************/

template<class T> void afficherVecteur(const Vecteur<T> * v, std::ostream & out) {
	for (int i = 0; i< v->dimensions(); i++) {
		out << v[i] << ",";
	}
}

/*
* fonction add pour additionner 2 vecteurs pas necessairement de même dimensions
* Additionne deux vecteur et retourne le vecteur resultant
*/
template<class T, class U> Vecteur<T> add(const Vecteur<T> * v1, const Vecteur<U> * v2) {
	int max = v1->dimensions() > v2->dimensions() ? v1->dimensions() : v2->dimensions();
	Vecteur<T> v = Vecteur<T>(max);
	for (int i = 0; i < v.dimensions(); i++) {
		if (i < v1->dimensions() && i < v2->dimensions())
			v.set(i, v1->get(i) + v2->get(i));
		else if (i < v2->dimensions() && i >= v1->dimensions())
			v.set(i, v2->get(i));
		else if (i < v1->dimensions() && i >= v2->dimensions())
			v.set(i, v1->get(i));
	}
	return v;
}

/*
* redefinition de l'operateur de multiplication
* @Pre-condition : les vecteurs sont de mêmes dimensions
* Permet de calculer le produit scalaire de deux vecteurs
*/
template<class T, class U> T operator * (Vecteur<T> const& v1, Vecteur<U> const& v2) {
	assert(v1.dimensions() == v2.dimensions());
	T res = T();
	for (int i = 0; i <v1.dimensions(); i++)
		res += v1[i] * v2[i];
	return res;
}

/**
* Fonction qui cree et initialise un vecteur dont les caracteristiques
* sont fournies par l'utilisateur
* @return		pointeur sur vecteur alloue dynamiquement
*/
template<class T> Vecteur<T> * lireVecteur(std::istream & in) {
	cout << "Saisir le nombre de dimensions du vecteur : " << endl;
	int i;
	in >> i;
	Vecteur<T> * v = new Vecteur<T>(i);
	T x;
	cout << "Saisir les valeurs de chaque coordonnees : " << endl;
	for (i = 0; i < v->dimensions(); i++) {
		in >> x;
		v->set(i, x);
	}
	cout << endl;
	return v;
}
#endif

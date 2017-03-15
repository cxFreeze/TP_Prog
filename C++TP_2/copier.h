/*OUBAH ROCHE TP2*/
#include "liste.hh"
#include <iostream>

/*cherche une valeur dans une liste triee par valeurs croissantes*/
template <class T>
typename Liste<T>::iterator findSupValueSortedListe(Liste<T> & list, const T & val){
	typename Liste<T>::iterator it = list.begin();
	while(it != list.end() && *it < val){
		it++;
	}
	return it;
}

template <class T> Liste<T> sortListe(Liste<T> & list) {
	Liste<T> * aTrier = new Liste<T>();
	Liste<T> & ref = *aTrier;
	typename Liste<T>::iterator it = list.begin();
	while (ref.size() < list.size()) {
		ref.insert(findSupValueSortedListe(ref, *it), *it);
		++it;
	}
	return ref;
}

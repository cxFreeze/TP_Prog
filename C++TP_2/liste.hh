/*OUBAH ROCHE TP2*/
#ifndef LISTE_HH_
#define LISTE_HH_
#include <iostream>
#include <cassert>
#include "cyclicNode.h"

template <class T> class Liste {

protected:
	typedef DataStructure::cyclicNode<T> Chainon;
private:
	Chainon * sentinelle;
	int m_size;
public:
	//Constructeur : initialise sentinelle
	Liste() : m_size(0) {
		sentinelle = new Chainon();
	}

private:
	/*
	* initialise une liste a partir d'une autre
	*/
	void copy(const Liste<T> & list) {
		m_size = 0; //sera incremente par push_back
		sentinelle = new Chainon();
		Liste<T>::const_iterator it = list.begin();
		while (it != list.end()) {
			this->push_back(*it);
			it++;
		}
	}

public:

	// Constructeur copie
	Liste(const Liste<T> & list) {
		copy(list);
	}

	/*
	* surcharge de l'operateur =
	*/
	Liste<T> & operator = (const Liste<T> & list) {
		copy(list);
		return *this;
	}

	/*
	* surcharge de l'operateur +
	*/
	Liste<T> operator + (Liste<T> & list) {
		return add(*this, list);
	}

	/*
	* additionner deux listes
	*/
	Liste<T> add(Liste<T> & l1, Liste<T> & l2) {
		Liste<T> l3(l1);
		Liste<T>::iterator it = l2.begin();
		while (it != l2.end()) {
			l3.push_back(*it);
			++it;
		}
		return l3;
	}

	/*
	* renvoi true si vide
	*/
	bool empty() const {
		return m_size == 0;
	}

	/*
	* renvoi la taille de la liste
	*/
	int size() const {
		return m_size;
	}

	/*
	* renvoi le premier element
	* @pre Liste non vide
	*/
	T & front() {
		assert(!this->empty());
		return sentinelle->next()->data();
	}

	/*
	* renvoi le premier element
	* @pre Liste non vide
	*/
	const T & front() const {
		assert(!this->empty());
		return sentinelle->next()->data();
	}

	/*
	* renvoi le dernier element
	* @pre Liste non vide
	*/
	T & back() {
		assert(!this->empty());
		return sentinelle->previous()->data();
	}

	/*
	* renvoi le dernier element
	* @pre Liste non vide
	*/
	const T & back() const {
		assert(!this->empty());
		return sentinelle->previous()->data();
	}

	/*
	* ajouter l'element a la fin de la liste
	*/
	void push_back(const T & val) {
		Chainon * c = new Chainon(val);
		sentinelle->insertBefore(c);
		m_size++;
	}

	/*
	* ajouter l'element au debut de la liste
	*/
	void push_front(const T & val) {
		Chainon * c = new Chainon(val);
		sentinelle->insertAfter(c);
		m_size++;
	}

	/*
	* supprime le dernier element
	* @pre Liste non vide
	*/
	void pop_back() {
		assert(!this->empty());
		Chainon * prev = sentinelle->previous();
		sentinelle->previous()->detach();
		delete(prev);
		m_size--;
	}

	/*
	* supprime le premier element
	* @pre Liste non vide
	*/
	void pop_front() {
		assert(!this->empty());
		Chainon * prev = sentinelle->next();
		sentinelle->next()->detach();
		delete(prev);
		m_size--;
	}

	/*
	* Desctructeur
	*/
	virtual ~Liste() {
		while (sentinelle->next() != sentinelle) {
			pop_back();
		}
		delete sentinelle;
	}

	/* --- Const_Iterator --- */

	class const_iterator {

	private:
		friend class Liste;
		const Chainon * current;
		const Chainon * const sentinelle;
	public:
		/*Constructeur
		* si end = true : initialise sur la fin de la liste, au debut sinon.
		*/
		const_iterator(Chainon * senti, bool end)
			:sentinelle(senti) {
			if (end) {
				current = sentinelle;
			}
			else {
				current = sentinelle->next();
			}
		}

		/*
		* surcharge post-incrementation
		*/
		const_iterator & operator ++() {
			assert(current != sentinelle);
			current = current->next();
			return *this;
		}

		/*
		* surcharge pre-incrementation
		*/
		const_iterator & operator ++(int useless) {
			assert(current != sentinelle);
			current = current->next();
			return *this;
		}

		/*
		* surcharge post-decrementation
		*/
		const_iterator & operator --() {
			assert(current != sentinelle->next());
			current = current->previous();
			return *this;
		}

		/*
		* surcharge pre-decrementation
		*/
		const_iterator & operator --(int useless) {
			assert(current != sentinelle->next());
			current = current->previous();
			return *this;
		}

		/*
		* Surcharge de l'operateur * pour acceder aux donnees stockees dans un chainon
		*/
		const T & operator * () const {
			assert(current != sentinelle);
			return current->data();
		}

		/*
		* Surcharge de l'operateur -> pour acceder a l'adresse des donnees stockees dans un chainon
		*/
		const T * operator -> () const {
			assert(current != sentinelle);
			return &(current->data());
		}

		/*
		* Surcharge de l'operateur ==
		*/
		bool operator == (const const_iterator & it) const {
			return this->current == it.current;
		}

		/*
		* Surcharge de l'operateur !=
		*/
		bool operator != (const const_iterator & it) const {
			return this->current != it.current;
		}

		//Destructeur
		~const_iterator() {}
	};

	/* --- Const_Iterator --- */


	/* --- Iterator --- */

	class iterator {

	private:
		friend class Liste;
		Chainon * current;
		Chainon * const sentinelle;
	public:
		/* Constructeur
		* si end = true : initie sur la fin de la liste, au debut sinon.
		*/
		iterator(Chainon * senti, bool end)
			:sentinelle(senti) {
			if (end) {
				current = sentinelle;
			}
			else {
				current = sentinelle->next();
			}
		}

		/*
		* surcharge de l'operateur de post-incrementation
		*/
		iterator & operator ++() {
			assert(current != sentinelle);
			current = current->next();
			return *this;
		}

		/*
		* surcharge de l'operateur de pre-incrementation
		*/
		iterator & operator ++(int useless) {
			assert(current != sentinelle);
			current = current->next();
			return *this;
		}

		/*
		* surcharge de l'operateur de post-decrementation
		*/
		iterator & operator --() {
			assert(current != sentinelle->next());
			current = current->previous();
			return *this;
		}

		/*
		* surcharge de l'operateur de pre-decrementation
		*/
		iterator & operator --(int useless) {
			assert(current != sentinelle->next());
			current = current->previous();
			return *this;
		}

		/*
		* Surcharge de l'operateur * pour acceder aux donnees stockees dans un chainon
		*/
		T & operator * () {
			assert(current != sentinelle);
			return current->data();
		}

		/*
		* Surcharge de l'operateur -> pour acceder a l'adresse des donnees stockees dans un chainon
		*/
		T * operator -> () {
			assert(current != sentinelle);
			return &(current->data());
		}

		/*
		* surcharge de l'operateur ==
		*/
		bool operator == (const iterator & it) const {
			return this->current == it.current;
		}

		/*
		* surcharge de l'operateur !=
		*/
		bool operator != (const iterator & it) const {
			return this->current != it.current;
		}

		//Destructeur
		~iterator() {}
	};

	/* --- Iterateur --- */


public:

	/*
	* Renvoi un iterateur sur le premier element de la liste si non vide ou un iterateur sur la sentinelle
	*/
	const_iterator begin() const {
		const_iterator it(sentinelle, this->empty());
		return it;
	}

	/*
	* Renvoi un iterateur sur le premier element de la liste si non vide ou un iterateur sur la sentinelle
	*/
	iterator begin() {
		iterator it(sentinelle, this->empty());
		return it;
	}

	/*
	* Renvoi un iterateur sur le dernier element de la liste si non vide ou un iterateur sur la sentinelle
	*/
	const_iterator end() const {
		const_iterator it(sentinelle, true);
		return it;
	}

	/*
	* Renvoi un iterateur sur le dernier element de la liste si non vide ou un iterateur sur la sentinelle
	*/
	iterator end() {
		iterator it(sentinelle, true);
		return it;
	}

	/*
	* Insere val avant la postion de l'iterateur
	* si position = end() val est insere a la derniere position
	*/
	iterator insert(iterator position, const T & val) {
		Chainon * c = new Chainon(val);
		if (position == this->end()) {
			sentinelle->insertBefore(c);
		}
		else {
			Chainon * current = sentinelle->next();
			/*while (current != sentinelle && current->data() != *position) {
				current = current->next();
			}*/
			current->insertBefore(c);
		}
		m_size++;
		return position--;
	}

	/*
	* supprime l'element a la position pointe par l'iterateur en parametre
	* @pre : iterator != end()
	*/
	iterator erase(iterator position) {
		assert(position != this->end());
		Chainon * current = sentinelle->next();
		/*while (current != sentinelle && current->data() != *position) {
			current = current->next();
		}*/
		iterator ret = position++;
		current->detach();
		delete(current);
		m_size--;
		return ret;
	}
};


template <class T>
typename Liste<T>::iterator
find(typename Liste<T>::iterator premier,
	typename Liste<T>::iterator dernier,
	const T & val) {

	typename Liste<T>::iterator find = premier;
	while (find != dernier && *find != val) {
		find++;
	}
	return find;
}

template <class T>
std::ostream & operator << (std::ostream& out, Liste<T> & list) {
	display_list(list);
	return out;
}

template <class T>
void display_list(const Liste<T> & list) {
	typename Liste<T>::const_iterator it = list.begin();
	while (it != list.end()) {
		std::cout << "[" << *it << "] -> ";
		it++;
	}
	std::cout << std::endl;
}


#endif  LISTE_HH_

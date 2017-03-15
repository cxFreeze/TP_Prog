/*OUBAH ROCHE TP2*/
#include <deque>
#include <gtest/gtest.h>
#include <cstdlib>

#include "liste.hh"

int tab_int[] = {0,1,2,3};

template<class T>
Liste<T> createListe(T tab[], int tab_size){
	Liste<T> list;
	for(int i=0;i<tab_size;i++){
		list.push_back(tab[i]);
	}
	return list;
}

TEST(TestList, iterator_plus){
	Liste<int> list = createListe(tab_int,4);
	Liste<int>::iterator it = list.begin(); //it = 0
	it++;				//it = 1;
	EXPECT_EQ(*it,1);
	it++; //Postfix		//it = 2
	++it; //Prefix		//it = 3
	EXPECT_EQ(*it,3);
	it++; //After last element
	ASSERT_DEATH(it++,".*current != sentinelle.*");
}

TEST(TestList, iterator_minus){
	Liste<int> list = createListe(tab_int,4);
	Liste<int>::iterator it = list.end(); //it = sentinelle
	it--; 				//it = 3;
	EXPECT_EQ(*it,3);
	it--; //Postfix		//it = 2
	--it; //Prefix		//it = 1
	EXPECT_EQ(*it,1);
	--it; 				//it = 0
	ASSERT_DEATH(it--,".*current != sentinelle->next().*");
}

TEST(TestList, iterator_equal){
	Liste<int> list = createListe(tab_int,2);
	Liste<int>::iterator it_plus = list.begin(); //it_plus=0
	Liste<int>::iterator it_minus = list.end();
	it_minus--; //it_minus = 1
	ASSERT_TRUE(!(it_plus == it_minus)) << "Expected it1 = 0 et it2 = 1, but it1 = " << *it_plus << " it2 = " << *it_minus;
	it_minus--; //it_minus =0
	ASSERT_TRUE(it_plus == it_minus) << "Expected it1 = 0 et it2 = 0, but it1 = " << *it_plus << " it2 = " << *it_minus;
}

TEST(TestList, iterator_different){
	Liste<int> list = createListe(tab_int,2);
	Liste<int>::iterator it_plus = list.begin(); //it_plus = 0
	Liste<int>::iterator it_minus = list.end();
	it_minus--; //it_minus = 1
	ASSERT_TRUE(it_plus != it_minus) << "Expected it1 = 0 et it2 = 1, but it1 = " << *it_plus << " it2 = " << *it_minus;
	it_minus--; //it_minus =0
	ASSERT_TRUE(!(it_plus != it_minus)) << "Expected it1 = 0 et it2 = 0, but it1 = " << *it_plus << " it2 = " << *it_minus;
}

TEST(TestList, listeVide){
	Liste<double> list;
	Liste<double>::iterator it = list.begin();
	Liste<double>::iterator it2 = list.end();
	ASSERT_TRUE( it == it2 ) << "Erreur";
}

TEST(TestList, iterator_etoile){
	Liste<int> list = createListe(tab_int,2);
	Liste<int>::iterator it = list.begin();
	EXPECT_EQ(*it,0) << "Valeur attendue : 1, valeur retournee : " << *it;

	it++;
	*it = 404;
	EXPECT_EQ(*it,404) << "Valeur attendue : 404, valeur retournee : " << *it;
}

TEST(TestList, iterator_etoile_ASSERT){
	Liste<int> list;
	Liste<int>::iterator it = list.begin();

	ASSERT_DEATH( *it , "ssertion.*current != sentinelle.*failed");
}

TEST(TestList, iterator_fleche){
	std::string tab_string[] = {"Test","Bonsoir","String"};
	Liste<std::string> list = createListe(tab_string, 3);
	Liste<std::string>::iterator it = list.begin();
	it++; //It = "J'adore ce TP"
	std::string st = "jour";
	it->replace(3,6,st);			//Pas possible sur un const_iterator
	EXPECT_EQ(*it,"Bonjour") << "Valeur attendue : \"Bonjour\", retournee : " << *it;
}

TEST(TestList, iterator_fleche_ASSERT){
	Liste<std::string> list;
	Liste<std::string>::iterator it = list.begin();

	ASSERT_DEATH( it->length() , ".*current != sentinelle.*failed");
}

TEST(TestList, iterator_loop){
	Liste<int> list = createListe(tab_int,4);
	int i=0;
	for(Liste<int>::iterator it=list.begin();it!=list.end();it++){
		EXPECT_EQ(*it,tab_int[i]) << "Expected value : " << i << "returned value : " << *it;
		++i;
	}
	EXPECT_EQ(i,4) << "iterations = " << i << ", expected 4." ;
}

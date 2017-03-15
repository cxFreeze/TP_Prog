/*OUBAH ROCHE TP2*/
#include <deque>
#include <gtest/gtest.h>
#include <cstdlib>

#include "liste.hh"

int const_tab_int[] = {0,1,2,3};

template<class T>
const Liste<T> createConstListe(T tab[], int tab_size){
	Liste<T> list;
	for(int i=0;i<tab_size;i++){
		list.push_back(tab[i]);
	}
	return list;
}

TEST(TestList, const_iterator_plus){
	const Liste<int> & list = createConstListe(const_tab_int, 4);
	Liste<int>::const_iterator it = list.begin(); //it = 0
	it++; 				//it = 1;
	EXPECT_EQ(*it,1);
	it++; //Postfix		//it = 2
	++it; //Prefix		//it = 3
	EXPECT_EQ(*it,3);
	it++; //After last element
	ASSERT_DEATH(it++,".*current != sentinelle.*");
}

TEST(TestList, const_iterator_minus){
	const Liste<int> & list = createConstListe(const_tab_int, 4);
	Liste<int>::const_iterator it = list.end(); //it = sentinelle
	it--; 				//it = 3;
	EXPECT_EQ(*it,3);
	it--; //Postfix		//it = 2
	--it; //Prefix		//it = 1
	EXPECT_EQ(*it,1);
	--it; 				//it = 0
	ASSERT_DEATH(it--,".*current != sentinelle->next().*");
}

TEST(TestList, const_iterator_equal){
	const Liste<int> & list = createConstListe(const_tab_int, 2); //Liste = {0, 1}
	Liste<int>::const_iterator it_plus = list.begin(); //it_plus=0
	Liste<int>::const_iterator it_minus = list.end();
	it_minus--; //it_minus = 1
	ASSERT_TRUE(!(it_plus == it_minus)) << "Expected it1 = 0 et it2 = 1, but it1 = " << *it_plus << " it2 = " << *it_minus;
	it_minus--; //it_minus =0
	ASSERT_TRUE(it_plus == it_minus) << "Expected it1 = 0 et it2 = 0, but it1 = " << *it_plus << " it2 = " << *it_minus;
}

TEST(TestList, const_iterator_different){
	const Liste<int> & list = createConstListe(const_tab_int, 2); //Liste = {0, 1}
	Liste<int>::const_iterator it_plus = list.begin(); //it_plus = 0
	Liste<int>::const_iterator it_minus = list.end();
	it_minus--; //it_minus = 1
	ASSERT_TRUE(it_plus != it_minus) << "Expected it1 = 0 et it2 = 1, but it1 = " << *it_plus << " it2 = " << *it_minus;
	it_minus--; //it_minus =0
	ASSERT_TRUE(!(it_plus != it_minus)) << "Expected it1 = 0 et it2 = 0, but it1 = " << *it_plus << " it2 = " << *it_minus;
}

TEST(TestList, constlisteVide){
	const Liste<double> list;
	Liste<double>::const_iterator it = list.begin();
	Liste<double>::const_iterator it2 = list.end();
	ASSERT_TRUE( it == it2 ) << "Erreur";
}

TEST(TestList, const_iterator_etoile){
	const Liste<int> & list = createConstListe(const_tab_int, 2);
	Liste<int>::const_iterator it2 = list.begin();
	EXPECT_EQ(*it2,0) << "Valeur attendue : 1, valeur retournee : " << *it2;

	it2++;
	EXPECT_EQ(*it2,1) << "Valeur attendue : 2, valeur retournee : " << *it2;
}

TEST(TestList, const_iterator_etoile_ASSERT){
	const Liste<int> list;
	Liste<int>::const_iterator it = list.begin();

	ASSERT_DEATH( *it , "ssertion.*current != sentinelle.*failed");
}

TEST(TestList, const_iterator_fleche){
	std::string tab_string[] = {"Test","Bonsoir","String"};

	const Liste<std::string> & list = createConstListe(tab_string, 3);
	Liste<std::string>::const_iterator it = list.begin();
	it++; //It sur "Bonsoir"
	EXPECT_EQ(it->length(),7) << "length(), valeur attendue : 7, retournee : " << it->length();
}

TEST(TestList, const_iterator_fleche_ASSERT){
	const Liste<std::string> list;
	Liste<std::string>::const_iterator it = list.begin();

	ASSERT_DEATH( it->length() , ".*current != sentinelle.*failed");
}

TEST(TestList, const_iterator_loop){
	const Liste<int> & list = createConstListe(const_tab_int,4);
	int i=0;
	for(Liste<int>::const_iterator it=list.begin();it!=list.end();it++){
		EXPECT_EQ(*it,const_tab_int[i]) << "Expected value : " << i << "returned value : " << *it;
		++i;
	}
	EXPECT_EQ(i,4) << "iterations = " << i << ", expected 4." ;
}

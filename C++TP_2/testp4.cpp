/*OUBAH ROCHE TP2*/
#include <gtest/gtest.h>
#include <iostream>

#include "liste.hh"
#include "copier.h"

template<class T>
Liste<T> createListe(T tab[], int tab_size){
	Liste<T> list;
	for(int i=0;i<tab_size;i++){
		list.push_back(tab[i]);
	}
	return list;
}

TEST(TestList, list_operator_affect){
	//Test empty list
	Liste<int> list_empty;
	Liste<int> list_copy_empty = list_empty;
	EXPECT_EQ(list_empty.size(),list_copy_empty.size()) << "Expected list size equals but l1.size() = " << list_empty.size() << ", l2.size() = " << list_copy_empty.size();

	//Test 2 elements list
	Liste<int> list_init;
	list_init.push_back(10);
	list_init.push_back(99);

	Liste<int> list_copy = list_init;
	EXPECT_EQ(list_init.size(),list_copy.size()) << "Expected list size equals but l1.size() = " << list_init.size() << ", l2.size() = " << list_copy.size();
	Liste<int>::iterator it = list_copy.begin();
	EXPECT_EQ(*it,10) << "Expected first element = 10 but is " << *it;
	it++;
	EXPECT_EQ(*it,99) << "Expected first element = 99 but is " << *it;
}

TEST(TestList, list_operator_add){
	std::string str1[] = {"T ","E"};
	std::string str2[] = {"S ","T"};
	std::string str_add[] = {"T ","E","S ","T"};
	std::string str_ret[] = {"T ","E","S ","T","T ","E","S ","T"};

	Liste<std::string> list_1 = createListe(str1, 2);
	Liste<std::string> list_2 = createListe(str2, 2);

	Liste<std::string> list_add = list_1 + list_2;
	Liste<std::string>::iterator it1 = list_add.begin();

	int i=0;
	while(it1 != list_add.end()){
		EXPECT_EQ(*it1,str_add[i]) << "Expected value = " << str_add[i] << " but returned : " << *it1;
		++it1;
		++i;
	}

	i=0;

	Liste<std::string> list_ret = list_1 + list_2 + list_add;
	Liste<std::string>::iterator it2 = list_ret.begin();
	while(it2 != list_ret.end()){
		EXPECT_EQ(*it2,str_ret[i]) << "Expected value = " << str_ret[i] << " but returned : " << *it2;
		it2++;
		i++;
	}
}

TEST(TestList, list_operator_output){
	Liste<std::string> list_1;
	list_1.push_back("T ");
	list_1.push_back("E");
	Liste<std::string> list_2;
	list_2.push_back("S ");
	list_2.push_back("T");

	//Addition of 2 lists
	Liste<std::string> list_add = list_1 + list_2;
	//Visual test
	std::cout << list_add;

	//Addition of 3 lists
	Liste<std::string> list_ret = list_1 + list_2 + list_add;
	//Visual test
	std::cout << list_ret;
}

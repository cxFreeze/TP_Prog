/*OUBAH ROCHE TP2*/
#include <gtest/gtest.h>
#include <iostream>

#include "liste.hh"
#include "copier.h"

TEST(TestList,testFindSupValue){
	//Test empty list
	Liste<int> list_empty;
	Liste<int>::iterator it_empty = findSupValueSortedListe(list_empty,3);
	ASSERT_TRUE(it_empty == list_empty.end()) << "Expected it on list_empty.end() but returned : " << *it_empty;

	Liste<int> list;
	list.push_back(0);
	list.push_back(3);
	list.push_back(5);
	list.push_back(8);
	list.push_back(10);

	Liste<int>::iterator it_find_exact_value = findSupValueSortedListe(list,8);
	EXPECT_EQ(*it_find_exact_value,8) << "Expected it = 8, but returned : " << *it_find_exact_value;

	Liste<int>::iterator it_find_absent_value = findSupValueSortedListe(list,4);
	EXPECT_EQ(*it_find_absent_value,5) << "Expected it = 5, but returned : " << *it_find_absent_value;

	Liste<int>::iterator it_end = findSupValueSortedListe(list,16);
	ASSERT_TRUE(it_end == list.end()) << "Expected it = end(), but returned : " << *it_end;
}

TEST(TestList, testSortList){
	//Test empty list
	Liste<int> list_empty;
	Liste<int> list_copy = sortListe(list_empty);
	EXPECT_EQ(list_copy.size(),0) << "Expected size of list copy = 0 but size = " << list_copy.size();

	//Test 5 elements list
	int tab[] = {0,5,10,15,20};

	Liste<int> list;
	for(int i=0;i<5;i++){
		list.push_back(tab[i]); //Add all the tab elements to the list
	}
	Liste<int> copy = sortListe(list); //Getting the sorted copy of list
	Liste<int>::iterator it = list.begin();
	for(int j=0;j<5 && (it != copy.end());j++){
		EXPECT_EQ(tab[j],*it) << "Expected value = " << tab[j] << " but returned : " << *it;
		it++;
	}
	std::cout << copy;
}

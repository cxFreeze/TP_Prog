/*OUBAH ROCHE TP2*/
#include <deque>
#include <gtest/gtest.h>
#include <cstdlib>

#include "liste.hh"

TEST(TestList, testFind){

	Liste<int> list;
	list.push_back(0);
	list.push_back(5);
	list.push_back(10);
	list.push_back(15);
	list.push_back(20);
	Liste<int>::iterator it_found = find(list.begin(),list.end(),10);
	Liste<int>::iterator it_not_found = find(list.begin(),list.end(),99);
	Liste<int>::iterator it_bad_range = find(list.begin(),list.begin()++,15);
	Liste<int>::iterator it_val_10 = list.begin();
	it_val_10++; it_val_10++; //Set the iterator to the 10 value
	ASSERT_TRUE(it_val_10 == it_found) << "Expected *it_found = 10, but returned : " << *it_found;
	ASSERT_TRUE(it_not_found == list.end()) << "Expected it_not_found = after last element but returned : " << *it_not_found;
	ASSERT_TRUE(it_bad_range == list.begin()++) << "Expected it_bad_range = 5 but returned : " << *it_bad_range;
}


TEST(TestList, testInsert){
	Liste<int> list;
	list.push_back(0);
	list.push_back(10);
	list.push_back(20);

	//Insert on middle of list (2nd element)
	Liste<int>::iterator it_insert_5 = list.begin();
	it_insert_5++; //Set the iterator to the 10 value
	Liste<int>::iterator it_returned = list.insert(it_insert_5,5);
	EXPECT_EQ(list.size(),4) << "Expected size of list = 4, but size() = " << list.size();
	EXPECT_EQ(*it_returned,5) << "Expected value of returned iterator = 5, but returned : " << *it_returned;
	ASSERT_TRUE(list.begin()++ == it_returned) << "Expected iterator on second element = iterator returned previously, value : " << *it_returned;

	//Insert on list.end()
	Liste<int>::iterator it_end_returned = list.insert(list.end(),25);
	ASSERT_TRUE(it_end_returned == list.end()--) << "Expected iterator returned = last element, value : " << *it_end_returned;
	EXPECT_EQ(*it_end_returned,25) << "Expected value of returned iterator = 25, value : " << *it_end_returned;

	//Insert on list.begin()
	Liste<int>::iterator it_begin_returned = list.insert(list.begin(),99);
	ASSERT_TRUE(it_begin_returned == list.begin()) << "Expected iterator returned = first element, value : " << *it_begin_returned;
	EXPECT_EQ(*it_begin_returned,99) << "Expected value of returned iterator = 99, value : " << *it_begin_returned;
}

TEST(TestList, testErase){
	Liste<int> list;
	list.push_back(0);
	list.push_back(5);
	list.push_back(10);
	list.push_back(15);
	list.push_back(20);

	//Erase on list.begin()
	Liste<int>::iterator it_erase_begin = list.erase(list.begin());
	EXPECT_EQ(list.size(), 4) << "Expected first element deleted, so size = 5-1, but size = "<< list.size();
	EXPECT_EQ(*list.begin(),5) << "Expected first element deleted (0) so new first = 10, but returned : " << *list.begin();

	//Erase on list.end()
	ASSERT_DEATH(list.erase(list.end()), ".*position != .*end().*");

	//List should look like {5,10,15,20}

	//Erase on middle of list
	Liste<int>::iterator it_erase = list.erase(list.begin()++); //Supposed to delete the second element
	EXPECT_EQ(list.size(), 3) << "Expected second element deleted, so size = 3, but size = "<< list.size();
	EXPECT_EQ(*list.begin()++,15) << "Expected second element deleted (10) so new first = 15, but returned : " << *list.begin();
	ASSERT_TRUE(it_erase == list.begin()++) << "Expected second element deleted, so iterator returned on third element before removing (10), but returned " << *it_erase;
}

#include <deque>
#include <gtest/gtest.h>
#include <cstdlib>
#include "liste.hh"

enum Position { front, alea, back };

// To use a test fixture, derive a class from testing::Test.
class TestList : public testing::Test
{
protected:

  // The UTC time (in seconds) when the test starts
  time_t m_start_time;

  // SetUp() is run immediately before a test starts.
  virtual void SetUp() {
    m_start_time = time(NULL);
    srand48(m_start_time);
  }

  // TearDown() is invoked immediately after a test finishes.
  virtual void TearDown() {
    // Gets the time when the test finishes
    const time_t end_time = time(NULL);

    // Asserts that the test took no more than ~1 second.
    EXPECT_TRUE(end_time - m_start_time <= 1)
      << "The test took too long.";
  }

  //------------------------------------------------------------------------
  // fonctions utilitaires
  //------------------------------------------------------------------------

  // ajouter des élts dans un tableau et une liste
  // avec push_front ou push_back
  void
  ajouter_elts(
	       Liste<double> & liste,
	       std::deque<double> & array,
	       Position ici = alea
	       )
  {
    const int array_size = 100 + rand() % 100;
    for (int i = 0; i < array_size; ++i) {
      double x = 5 + drand48();
      bool ajoutTete = (ici == front) || (ici == alea && rand() % 2 == 0);
      if (ajoutTete) {
	// ajout en tête
	array.push_front(i * x * x);
	liste.push_front(i * x * x);
      }
      else {
	// ajout en fin
	array.push_back(i * x + 1);
	liste.push_back(i * x + 1);
      }
    }
  }

  // ajouter des éléments dans un tableau
  void
  ajouter_elts(
	       std::deque<double> & array
	       )
  {
    const int array_size = 100 + rand() % 100;
    for (int i = 0; i < array_size; ++i) {
      array.push_back(5 + drand48());
    }
  }

  // comparer les élts d'une liste et ceux d'un deque
  // vide la liste de son contenu
  // requiert size, front, pop_front, back, pop_back
  template <class T>
  void
  list_equals_deque(
		    Liste<T> & list,
		    const std::deque<T> & array,
		    Position ici
		    )
  {
    unsigned int nb = array.size();
    EXPECT_EQ(
	      (unsigned int) nb,
	      (unsigned int) list.size())
      << "Erreur taille liste";
    unsigned int ideb = 0, ifin = array.size() - 1;
    while (! list.empty())
      {
	EXPECT_EQ(
		  (unsigned int) nb,
		  (unsigned int) list.size())
	  << "Erreur taille liste";

	// ôter soit en tête, soit en queue
	bool retraitTete = (ici == front) || (ici == alea && rand() % 2 == 0);
	if (retraitTete) {
	  EXPECT_EQ(
		    array[ideb],
		    list.front())
	    << "Erreur élément de rang " << ideb;
	  list.pop_front();
	  ++ideb;
	}
	else {
	  EXPECT_EQ(
		    array[ifin],
		    list.back())
	    << "Erreur élément de rang " << ifin;
	  list.pop_back();
	  --ifin;
	}
	nb -= 1;
      }
  }
};

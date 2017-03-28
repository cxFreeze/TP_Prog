#include <iostream>
#include <cstdlib>					// srand, rand
#include <time.h>					// time
#include "ferry.h"
#include "auto.h"
#include "bus.h"
#include "comparerLongueurVehicules.h"
#include "comparerNombrePassagers.h"
#include "ambulance.h"
#include "cycle.h"

int main(void) 
{
  // initialiser "le hasard"
  srand( time(NULL) );

  // creer un ferry dont la capacite est comprise :
  // - entre  50 et 100 unites de longueur
  // - entre 150 et 250 personnes
  Ferry jules(50 + rand() % 50, 150 + rand() % 100);
  std::cout << "\nContenu initial du ferry\n" << jules;

  while (true) {
    Vehicule * pv;			// designe un vehicule a ajouter

    // Tirer au hasard le type de vehicule
    switch (rand() % 4) {
    case 0 :			// Auto : nombre de personnes, tout terrain ?
      pv = new Auto(rand() % 5, (rand() % 3) == 0);
      break;

    case 1 :			// Bus : longueur, nombre de personnes
      pv = new Bus(10 + rand() % 10, 20 + rand() % 60);
      break;

     case 2 :			// Ambulance : nombre de personnes, tout terrain ?
       pv = new Ambulance(rand() % 5, (rand() % 3) == 0);
       break;

	 case 3 :
		 pv = new Cycle();
		 break;

    default:			// Auto : nombre de personnes, tout terrain ?
		pv = new Auto(rand() % 5, (rand() % 3) == 0 );
		break;
    }

    // Essayer d'ajouter ce nouveau vehicule dans le ferry
    std::cout << "Ajout de : " << *pv << std::endl;
    bool plein = ! jules.ajouter(pv);

    if (plein) {
      // Ajout impossible : fin de remplissage
      std::cout << std::flush;
      std::cerr << "\n*** Depassement de capacite ***\n";
      delete pv;
      break;
    }
    else {
      // Ajout effectue
      std::cout << "\nContenu du ferry\n" << jules;
    }
  }

   // trier par longueur croissante
   jules.trier<ComparerLongueurVehicules>();
   std::cout << "\nFerry trie par longueur croissante\n" << jules;

   // trier par longueur decroissante
   jules.trier<ComparerLongueurVehicules>(false);
   std::cout << "\nFerry trie par longueur decroissante\n" << jules;

   // trier par nbpassagers croissant croissante
   jules.trier<ComparerNombrePassagers>();
   std::cout << "\nFerry trie par nb passagers croissant\n" << jules;

   // trier par longueur decroissante
   jules.trier<ComparerNombrePassagers>(false);
   std::cout << "\nFerry trie par nb passagers decroissante\n" << jules;

  std::cout << "Fin du programme\n";

  return 0;
}

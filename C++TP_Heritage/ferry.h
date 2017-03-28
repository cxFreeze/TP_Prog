// -*- c++ -*-

#ifndef _FERRY_H
#define _FERRY_H
// choisir selon le conteneur utilise
#include <vector>
//#include <deque>
//#include <list>
#include "vehicule.h"
#include <algorithm>
#include "TComparateur.h"

/**
 * Un ferry transporte des vehicules
 */
class Ferry {
public:
  /**
     constructeur : initialiser un ferry vide
     @param longueur  : capacite du ferry en unites de longueur
     @param personnes : capacite du ferry en nombre de passagers
  */
  Ferry (unsigned int longueur, unsigned int personnes);

  /// destructeur
  virtual ~Ferry(void);

  /** ajouter un vehicule dans le ferry.
      sans effet s'il n'y a plus de place
      @param pv : designe le vehicule a ajouter
      @return vrai si l'ajout a eu lieu, faux sinon
  */
  virtual bool ajouter(const Vehicule * pv);

  //! determiner le tarif de l'ensembles des vehicules presents dans le ferry
  virtual double calculerTarif(void) const;

  //! afficher les informations sur le contenu du ferry
  virtual void afficher(std::ostream & s = std::cout) const;

  //methode pour verifier si le ferry est plein
  virtual bool estPlein(const Vehicule * pv) const;

  // donne la longueur du ferry
  unsigned int getLongueur(void) const;

  //donne le nombre de passagers du ferry
  unsigned int getPassagers(void) const;

  /** trier le ferry selon l’ordre defini par le comparateur
	@param TComparateur : type du comparateur
  */
  template <class TComparateur> void trier(TComparateur comparateur = TComparateur()) {
	  std::sort(m_vehicules.begin(), m_vehicules.end(), comparateur);
  }

private :
  unsigned int longueur;		///< longueur du ferry
  unsigned int passagers;		///< nombre de personnes dans le ferry
  typedef std::vector<Vehicule const *> listeVehicule; // liste de vehicules
  listeVehicule m_vehicules;
};

// operateur d'affichage
std::ostream & operator << (std::ostream & sortie, const Ferry & ferry);

#endif // _FERRY_H

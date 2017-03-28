// -*- c++ -*-
#ifndef _VEHICULE_H
#define _VEHICULE_H

#include <iostream>
/**
 * definition abstraite d'un Vehicule
 */

class Vehicule {
public:
  /** @param lg : longueur du vehicule
      @param nbp : nombre de personnes transportees
  */
  Vehicule(unsigned int lg = 1, unsigned int nbp = 1);

  /// destructeur
  virtual ~Vehicule(void);

  //! determiner le tarif du vehicule
  virtual double calculerTarif(void) const = 0; //methode virtuelle pure

  //! afficher les caracteristiques du vehicule
  virtual void	afficher(std::ostream & s = std::cout) const = 0; //methode virtuelle pure

  //! determiner la longueur d'un vehicule
  unsigned int getLongueur(void) const;

  /// determiner le nombre de personnes dans le vehicule
  unsigned int getPassagers() const;

  /// Methode polymorphe de clonage
  virtual Vehicule* clone() const = 0; //methode virtuelle pure

protected:
  unsigned int longueur;		///< longueur du vehicule
  unsigned int passagers;		///< nombre de personnes dans le vehicule
protected:
  static const double tarifPassager = 15; ///< tarif passager
};
// operateur << pour la classe Vehicule
std::ostream & operator << (std::ostream & sortie, const Vehicule & v);

#endif // _VEHICULE_H

package rationnel;

import types.Rationnel;
import util.Couple;
import util.Outils;

/**
 * Cette classe RationnelCouple implémente l'interface Rationnel.
 * Elle permet d'instancier des objets de types rationnels c'est-à-dire
 * avec un numérateur et un dénominateur.
 * L'attribut de cette classe est un couple d'Integer
 * Cette classe nous a permis d'avoir une implémentation interne
 * différente à celle de RationnelSimple.
 * @author ROCHE CORENTIN - OUBAH KARIM
 *
 */
public class RationnelCouple implements Rationnel {

	Couple<Integer, Integer> couple;
	
  /**
   * initialiser un rationnel à partir d'un entier : nb/1
   * @param num : valeur du numérateur
   */
	public RationnelCouple(int num){
		couple = new Couple<Integer, Integer>(num,1);
	}
	
	  /**
	   * initialiser un rationnel à partir d'un autre
	   * @param r : rationnel à dupliquer
	   */
	public RationnelCouple(Rationnel r){
		couple = new Couple<Integer, Integer>(r.getNumerateur(),r.getDenominateur());
	}
	
  /**
   * initialiser un rationnel avec numerateur et dénominateur
   * @param num : valeur du numérateur
   * @param den : valeur du dénominateur
   * @pre den != 0
   * @post fraction irréductible et dénominateur > 0
   */
	public RationnelCouple(int numerateur, int denominateur){
		assert denominateur!=0 : " ∗∗∗ PRÉ −CONDITION NON VÉRIFIÉE : denominateur doit etre != 0 ";
		
		if(numerateur>0 && denominateur<0){
			numerateur=-numerateur;
			denominateur=-denominateur;
		}
		if(numerateur<0 && denominateur<0){
			numerateur=-numerateur;
			denominateur=-denominateur;
		}
		if(numerateur!=0){
			couple = new Couple<Integer, Integer>(numerateur/Outils.pgcd(Math.abs(numerateur), Math.abs(denominateur)),denominateur/Outils.pgcd(Math.abs(numerateur), Math.abs(denominateur)));
		}
		else{
			couple = new Couple<Integer, Integer>(0,1);
		}
	}
	
	
	  /**
	   * comparer (égalité) deux rationnels
	   * @param r : rationnel à comparer au rationnel this
	   * @return vrai si le rationnel this est égal au rationnel paramètre
	   */
	public boolean equals(Rationnel r) {
		return valeur() == r.valeur();
	}
	
	
	  /**
	   * additionner deux rationnels
	   * @param r : rationnel à additionner avec le rationnel this
	   * @return nouveau rationnel somme du rationnel this et du rationnel paramètre
	   */
	public Rationnel somme(Rationnel r) {
		int numerateur=this.couple.getFirst()*r.getDenominateur()+r.getNumerateur()*this.couple.getSecond();
		int denominateur=this.couple.getSecond()*r.getDenominateur();
		return new RationnelCouple(numerateur,denominateur);
	}

	
	  /**
	   * inverser le rationnel this
	   * @return nouveau rationnel inverse du rationnel this
	   * @pre numérateur != 0
	   */
	public Rationnel inverse() {
		assert this.couple.getFirst()!=0:" ∗∗∗ PRÉ −CONDITION NON VÉRIFIÉE : numerateur doit etre != 0 ";
		return new RationnelCouple(this.couple.getSecond(),this.couple.getFirst());
	}

	
	  /**
	   * calculer la valeur réelle du rationnel this
	   * @return valeur réelle du rationnel this
	   */
	public double valeur() {
		return (double) getNumerateur()/getDenominateur();
	}

	
	/**
	 * pour obtenir le numérateur
	 * @return numérateur de type entier
	 */
	public int getNumerateur() {
		return this.couple.getFirst();
	}

	/**
	 * pour obtenir le dénominateur
	 * @return dénominateur de type entier
	 */
	public int getDenominateur() {
		return this.couple.getSecond();
	}

	/**
	 * méthode de l'interface Comparable<Rationnel>
	 * comparaison ordonnée du rationnel this et du rationnel autre
	 */
	public int compareTo(Rationnel autre) {
		if(valeur()>autre.valeur()) return 1;
		else if (valeur()==autre.valeur()) return 0;
		return -1;
	}
	
	
	  /**
	   *  @return représentation affichable d'un rationnel
	   * numerateur / denominateur
	   * ou numerateur (si denominateur = 1)
	   */
	@Override
	public String toString(){
		return this.couple.getFirst()+"/"+this.couple.getSecond();
	}
}

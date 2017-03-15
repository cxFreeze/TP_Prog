package rationnel;

import types.Rationnel;
import util.Outils;

/**
 * Cette classe RationnelSimple implémente l'interface Rationnel.
 * Elle permet d'instancier des objets de types rationnels c'est-à-dire
 * avec un numérateur et un dénominateur.
 * Les attributs de cette classe sont deux entiers, le numérateur et le dénominateur.
 * @author ROCHE CORENTIN - OUBAH KARIM
 *
 */
public class RationnelSimple implements Rationnel {
	
	private int numerateur, denominateur;
	
  /**
   * initialiser un rationnel avec numerateur et dénominateur
   * @param num : valeur du numérateur
   * @param den : valeur du dénominateur
   * @pre den != 0
   * @post fraction irréductible et dénominateur > 0
   */
	public RationnelSimple(int numerateur, int denominateur){
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
			this.numerateur=(numerateur/Outils.pgcd(Math.abs(numerateur), Math.abs(denominateur)));
			this.denominateur=(denominateur/Outils.pgcd(Math.abs(numerateur), Math.abs(denominateur)));
		}
		else{
			this.numerateur=0;
			this.denominateur=1;
		}
		
	}
	
	  /**
	   * initialiser un rationnel à partir d'un autre
	   * @param r : rationnel à dupliquer
	   */
	  public RationnelSimple(Rationnel r){
		  this.numerateur=r.getNumerateur();
		  this.denominateur=r.getDenominateur();
	  }
	  
	  /**
	   * initialiser un rationnel à partir d'un entier : nb/1
	   * @param num : valeur du numérateur
	   */
	  public RationnelSimple(int num){
		  this.numerateur=num;
		  this.denominateur=1;
	  }

	  /**
	   * comparer (égalité) deux rationnels
	   * @param r : rationnel à comparer au rationnel this
	   * @return vrai si le rationnel this est égal au rationnel paramètre
	   */	
	public boolean equals(Rationnel r) {
		return valeur()==r.valeur();
	}

	  /**
	   * additionner deux rationnels
	   * @param r : rationnel à additionner avec le rationnel this
	   * @return nouveau rationnel somme du rationnel this et du rationnel paramètre
	   */
	public Rationnel somme(Rationnel r) {
		
		int numerateur=this.numerateur*r.getDenominateur()+r.getNumerateur()*this.denominateur;
		int denominateur=this.denominateur*r.getDenominateur();
		System.out.println(numerateur+" "+denominateur);
		return new RationnelSimple(numerateur,denominateur);
	}

	  /**
	   * inverser le rationnel this
	   * @return nouveau rationnel inverse du rationnel this
	   * @pre numérateur != 0
	   */
	public Rationnel inverse() {
		assert this.numerateur!=0:" ∗∗∗ PRÉ −CONDITION NON VÉRIFIÉE : numerateur doit etre != 0 ";
		return new RationnelSimple(this.denominateur,this.numerateur);
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
		return this.numerateur;
	}

	/**
	 * pour obtenir le dénominateur
	 * @return dénominateur de type entier
	 */
	public int getDenominateur() {
		return this.denominateur;
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
		return this.numerateur+"/"+this.denominateur;
	}
}

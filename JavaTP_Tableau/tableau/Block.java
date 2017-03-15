package tableau;


import types.Array;
import types.Tableau;

/**
 * La classe Block<T> repr�sentera un tableau de
 * capacit� fixe mais de taille variable
 * 
 * @author OUBAH KARIM - ROCHE CORENTIN
 * @param <T>
 */
public class Block <T> implements Tableau<T> {

	Array<T> tableau;
	private int taille;
	
	/**
	 * Constructeur
	 * @pre capacite>0
	 * @param capacite
	 */
	public Block (int capacite){
		assert capacite>0 : "capacite>0";
		taille=0;
		this.tableau = new Array<T>(capacite);
	}
	

	  /**
	   * D�terminer la taille du tableau
	   * @return nombre d'�l�ments pr�sents dans le tableau
	   */
	  public int size(){
		  return this.taille;
	  }

	  /**
	   * D�terminer si le tableau est vide
	   * @return vrai si le tableau est vide
	   */
	  public boolean empty(){
		  return this.taille==0 ? true : false;
	  }

	  /**
	   * D�terminer si le tableau est plein
	   * @return vrai s'il n'est plus possible d'ajouter d'�l�ment dans le tableau
	   */
	  public boolean full(){
		  return this.taille==tableau.length();
	  }
	  /**
	   * Renvoyer l'�l�ment d'indice i
	   * 
	   * @param i : indice de l'�l�ment � consulter
	   * @pre 0 <= i < this.size()
	   * @return valeur de l'�l�ment d'indice i
	   */
	  public T get(int i){
		  assert i>=0 & i<taille : "0 <= i < this.size()";
		  return tableau.get(i);
	  }

	  /**
	   * Modifier l'�l�ment d'indice i
	   * 
	   * @param i : indice de l'�l�ment � modifier
	   * @pre 0 <= i < this.size()
	   * @param v : nouvelle valeur de l'�l�ment d'indice i
	   */
	  public void set(int i, T v){
		  assert i>=0 & i<taille : "0 <= i < this.size()";
		  tableau.set(i, v);
	  }

	  /**
	   * Ajouter un �l�ment en fin de tableau
	   * 
	   * @param x : �l�ment � ajouter en fin de tableau
	   * @pre : ! this.full()
	   */
	  public void push_back(T x){
		  assert !this.full() : "Le tableau est plein";
		  tableau.set(taille, x);
		  taille++;
	  }

	  /**
	   * Supprimer le dernier �l�ment du tableau
	   * @pre : ! this.empty()
	   */
	  public void pop_back(){
		  assert !this.empty() : "Le tableau est vide";
		  taille--;
	  }

}

package tableau;

import types.Tableau;

/**
 * La classe Tableau2x<T> repr�sentera un
 * tableau de taille et de capacit� variables.
 * 
 * Nous avons utilis� la classe Block<T> pour le stockage
 * car on peut utiliser ses m�thodes et ainsi �viter
 * de r��crire du code inutilement
 * 
 * @author OUBAH KARIM - ROCHE CORENTIN
 * @param <T>
 */
public class Tableau2x<T> implements Tableau<T>{
	
	Block<T> tableau;
	
	/**
	 * Constructeur
	 * @pre capacite>0
	 * @param capacite initiale du tableau
	 */
	public Tableau2x(int capacite){
		assert capacite>0 : "capacite>0";
		this.tableau = new Block<T>(capacite);
	}

	  /**
	   * D�terminer la taille du tableau
	   * @return nombre d'�l�ments pr�sents dans le tableau
	   */
	public int size() {
		return tableau.size();
	}

	  /**
	   * D�terminer si le tableau est vide
	   * @return vrai si le tableau est vide
	   */
	public boolean empty() {
		return tableau.empty();
	}

	  /**
	   * D�terminer si le tableau est plein
	   * @return toujours false car il ne peut pas l'�tre
	   */
	public boolean full() {
		return false;
	}

	/**
	   * Renvoyer l'�l�ment d'indice i
	   * 
	   * @param i : indice de l'�l�ment � consulter
	   * @return valeur de l'�l�ment d'indice i
	   */
	public T get(int i) {
		return tableau.get(i);
	}

	  /**
	   * Modifier l'�l�ment d'indice i
	   * 
	   * @param i : indice de l'�l�ment � modifier
	   * @param v : nouvelle valeur de l'�l�ment d'indice i
	   */
	public void set(int i, T v) {
		tableau.set(i, v);
	}

	  /**
	   * Ajouter un �l�ment en fin de tableau
	   * 
	   * @param x : �l�ment � ajouter en fin de tableau
	   */
	public void push_back(T x) {
		if (tableau.full()) { //si le tableau de type block est complet, on en cr�e un autre de capacit� double
			Block<T> tmp = new Block<T>(2*size());
			for (int i = 0; i<size() ; i++)//on copie les valeurs dedans
				tmp.push_back(tableau.get(i));//on ajoute la valeur � la fin
			this.tableau=tmp;
		}
		tableau.push_back(x);
	}

	  /**
	   * Supprimer le dernier �l�ment du tableau
	   */
	public void pop_back() {
		tableau.pop_back();
	}	
	
}

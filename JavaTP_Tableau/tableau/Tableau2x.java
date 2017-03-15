package tableau;

import types.Tableau;

/**
 * La classe Tableau2x<T> représentera un
 * tableau de taille et de capacité variables.
 * 
 * Nous avons utilisé la classe Block<T> pour le stockage
 * car on peut utiliser ses méthodes et ainsi éviter
 * de réécrire du code inutilement
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
	   * Déterminer la taille du tableau
	   * @return nombre d'éléments présents dans le tableau
	   */
	public int size() {
		return tableau.size();
	}

	  /**
	   * Déterminer si le tableau est vide
	   * @return vrai si le tableau est vide
	   */
	public boolean empty() {
		return tableau.empty();
	}

	  /**
	   * Déterminer si le tableau est plein
	   * @return toujours false car il ne peut pas l'être
	   */
	public boolean full() {
		return false;
	}

	/**
	   * Renvoyer l'élément d'indice i
	   * 
	   * @param i : indice de l'élément à consulter
	   * @return valeur de l'élément d'indice i
	   */
	public T get(int i) {
		return tableau.get(i);
	}

	  /**
	   * Modifier l'élément d'indice i
	   * 
	   * @param i : indice de l'élément à  modifier
	   * @param v : nouvelle valeur de l'élément d'indice i
	   */
	public void set(int i, T v) {
		tableau.set(i, v);
	}

	  /**
	   * Ajouter un élément en fin de tableau
	   * 
	   * @param x : élément à ajouter en fin de tableau
	   */
	public void push_back(T x) {
		if (tableau.full()) { //si le tableau de type block est complet, on en crée un autre de capacité double
			Block<T> tmp = new Block<T>(2*size());
			for (int i = 0; i<size() ; i++)//on copie les valeurs dedans
				tmp.push_back(tableau.get(i));//on ajoute la valeur à la fin
			this.tableau=tmp;
		}
		tableau.push_back(x);
	}

	  /**
	   * Supprimer le dernier élément du tableau
	   */
	public void pop_back() {
		tableau.pop_back();
	}	
	
}

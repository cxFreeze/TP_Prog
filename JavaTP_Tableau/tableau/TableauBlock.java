package tableau;

import types.Tableau;

/**
 * La classe TableauBlock<T> représentera
 * un tableau de taille et de capacité variables.
 * 
 * Le tableau est un Tableau2x contenant des blocs
 * 
 * Connaissant la capacité des blocs et l’indice i d’un élément à récupérer dans le TableauBlock:
 * indice du bloc = i/capacitebloc
 * indice de l’élément dans ce bloc = i%capacitebloc
 * 
 * @author OUBAH KARIM - ROCHE CORENTIN
 * @param <T>
 */
public class TableauBlock <T> implements Tableau<T>{

	Tableau2x<Block<T>> tableau;//Le tableau est un Tableau2x contenant des blocs
	int capacitebloc;
	int nbblock;//variable pour connaitre le nombre de block
	
	/**
	 * Constructeur
	 * 
	 * @param capacite
	 * @param capacitebloc
	 */
	public TableauBlock(int capacite, int capacitebloc){
		this.capacitebloc=capacitebloc;
		tableau= new Tableau2x<Block<T>>(capacite);
	}
	
	/**
	 * on appelle l'autre constructeur
	 * avec une capacitebloc de 128
	 * 
	 * @param capacite
	 */
	public TableauBlock(int capacite){
		this(capacite,128);
	}
	
	  /**
	   * Déterminer le nombre d'éléments
	   * @return nombre d'éléments présents dans le tableau
	   */
	public int size() {
		if(nbblock>0)	return (int)(nbblock-1)*capacitebloc+tableau.get(nbblock-1).size();
		else return 0;
	}

	  /**
	   * Déterminer si le tableau est vide
	   * @return vrai si le tableau est vide
	   */
	public boolean empty() {
		if(size()==0) return true;
		else return false;
	}

	  /**
	   * Déterminer si le tableau est plein
	   * @return toujours false car il ne peut pas l'être, c'est un Tableau2x
	   */
	public boolean full() {
		return tableau.full();
	}

	  /**
	   * Renvoyer l'élément d'indice i
	   * 
	   * @param i : indice de l'élément à consulter
	   * @return valeur de l'élément d'indice i
	   */
	public T get(int i) {
		return tableau.get(i/capacitebloc).get(i%capacitebloc);
	}

	  /**
	   * Modifier l'élément d'indice i
	   * 
	   * @param i : indice de l'élément à  modifier
	   * @param v : nouvelle valeur de l'élément d'indice i
	   */
	public void set(int i, T v) {
		tableau.get(i/capacitebloc).set(i%capacitebloc, v);
	}

	  /**
	   * Ajouter un élément en fin de tableau
	   * 
	   * @param x : élément à ajouter en fin de tableau
	   */
	public void push_back(T x) {
		if(size()%capacitebloc==0){//si le dernier block est plein, on crée un nouveau bloc
			nbblock++;
			tableau.push_back(new Block<T>(capacitebloc));
			tableau.get(tableau.size()-1).push_back(x);
		}
		else{
			tableau.get(tableau.size()-1).push_back(x);
		}
	}

	  /**
	   * Supprimer le dernier élément du tableau
	   */
	public void pop_back() {
		if(size()%capacitebloc==0){//si le dernier block est plein
			//on retranche 1 pour ne pas sortir du tableau
			tableau.get((size()/capacitebloc)-1).pop_back();
		}
		else{
			tableau.get((size())/capacitebloc).pop_back();
		}
		
		if(tableau.get(nbblock-1).empty())//si le block est vide, on décrémente le nb de block
			nbblock--;
	}
}

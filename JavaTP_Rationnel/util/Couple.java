package util;

/**
 * Cette classe Couple permet d'instancier des objets de types Couple.
 * Les attributs de cette classe sont deux variables de types T1 et T2.
 * Nous avons ici utilisé la générécité.
 * Cette classe va être utilisée pour avoir une implémentation interne
 * différente à celle de RationnelSimple pour la classe RationnelCouple.
 * @author ROCHE CORENTIN - OUBAH KARIM
 *
 * @param <T1>
 * @param <T2>
 */
public class Couple<T1, T2>{
	private T1 numerateur;
	private T2 denominateur;
	
	/**
	 * Constructeur permettant d'instancier un couple.
	 * @param num de type T1	
	 * @param denom de type T2
	 */
	public Couple (T1 num, T2 denom){
		this.numerateur=num;
		this.denominateur=denom;
	}

	/**
	 * Getter
	 * @return Renvoi le numérateur de type T1
	 */
	public T1 getFirst() {

		return this.numerateur;
	}
	
	/**
	 * Getter
	 * @return Renvoi le dénominateur de type T2
	 */
	public T2 getSecond() {

		return this.denominateur;
	}
	
	/**
	 * Setter pour modifier le numérateur
	 * @param num de type T1
	 */
	public void setFirst(T1 num){
		this.numerateur=num;
	}
	
	/**
	 * Setter pour modifier le dénominateur
	 * @param denom de type T2
	 */
	public void setSecond(T2 denom){
		this.denominateur=denom;
	}
	
	/**
	 * Test l'egalité des deux couples
	 * @param num de type T1
	 * @param denom de type T2
	 * @return boolean
	 */
	public boolean equals(Couple<T1, T2> couple){
		return this.numerateur==couple.getFirst() && this.denominateur==couple.getSecond() ? true : false;
	}
	
}

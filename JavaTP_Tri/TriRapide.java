package tp4;

/**
 * Classe permettant de réaliser un tri rapide
 * @author ROCHE - OUBAH
 *
 */
public class TriRapide {

	/**
	 * Partager les éléments d’un sous-tableau en 2 parties.
	 * @param T : tableau à partager
	 * @param binf indice du premier élément du sous-tableau à partager
	 * @param bsup indice du dernier élément du sous-tableau à partager
	 * @pre 0<=binf, bsup<T.length
	 * @post partage les éléments de T compris entre les indices binf et bsup 
	 * et met le pivot à sa place. 
	 * @return indice auquel a été placé le pivot.
	 */
	static int partager(int[] T, int binf, int bsup){
		assert binf>=0 && bsup<T.length : "0<=binf, bsup<T.length";
		int pivot=T[binf];//on selectionne le pivot à la borne binf
		int temp;//variable temporaire permettant de mettre le pivot à sa place
		while(binf<bsup){
			//ici on va mettre les éléments < au pivot à gauche
			//et les éléments > à droite
			while(T[binf]<=pivot && binf<bsup){
				binf++;//on partitionne le tableau
			}
			while (T[bsup]>pivot && binf<bsup){
				bsup--;//on partitionne le tableau
			}
			if(binf<bsup){
				//on réalise l'échange
				temp=T[binf];
				T[binf]=T[bsup];
				T[bsup]=temp;
				binf++;
				bsup--;
			}
		}
		//retour de l'indice du pivot
		if(T[binf]>pivot) return binf-1;
		else return binf;
	}
	
	/**
	 * triRapide : trier récursivement un sous-tableau (algorithme du tri rapide)
	 * @param T : tableau à trier
	 * @param binf indice du premier élément du sous-tableau à trier
	 * @param bsup indice du dernier élément du sous-tableau à trier
	 * @pre 0<=binf, bsup<T.length
	 */
	static void triRapide(int[] T, int binf, int bsup){
		assert binf>=0 && bsup<T.length : "0<=binf, bsup<T.length";
		int pos;//variable pour récupérer la position du pivot
		int temp;//variable pour placer le pivot
		if(binf<bsup){
			pos=partager(T, binf, bsup);//récupération de la position à laquelle doit se trouver le pivot
			//Réalisation de l'échange
			temp=T[binf];
			T[binf]=T[pos];
			T[pos]=temp;
			//on recommence en prenant 2 sous-tableaux
			triRapide(T, binf, pos-1);
			triRapide(T, pos+1, bsup);
		}
	}
	
	/**
	 * trier : trier un tableau par ordre croissant avec l'algorithme du tri rapide
	 * @param T : tableau à trier
	 * @param nb : nombre d'éléments à trier dans le tableau
	 * @pre 1<nb<=T.length
	 */
	public static void trier(int[] T, int nb){
		assert nb>1 && nb<=T.length :"1<nb<=T.length";
		triRapide(T, 0, nb-1);
	}
}

package tp4;

/**
 * Classe permettant de r�aliser un tri rapide
 * @author ROCHE - OUBAH
 *
 */
public class TriRapide {

	/**
	 * Partager les �l�ments d�un sous-tableau en 2 parties.
	 * @param T : tableau � partager
	 * @param binf indice du premier �l�ment du sous-tableau � partager
	 * @param bsup indice du dernier �l�ment du sous-tableau � partager
	 * @pre 0<=binf, bsup<T.length
	 * @post partage les �l�ments de T compris entre les indices binf et bsup 
	 * et met le pivot � sa place. 
	 * @return indice auquel a �t� plac� le pivot.
	 */
	static int partager(int[] T, int binf, int bsup){
		assert binf>=0 && bsup<T.length : "0<=binf, bsup<T.length";
		int pivot=T[binf];//on selectionne le pivot � la borne binf
		int temp;//variable temporaire permettant de mettre le pivot � sa place
		while(binf<bsup){
			//ici on va mettre les �l�ments < au pivot � gauche
			//et les �l�ments > � droite
			while(T[binf]<=pivot && binf<bsup){
				binf++;//on partitionne le tableau
			}
			while (T[bsup]>pivot && binf<bsup){
				bsup--;//on partitionne le tableau
			}
			if(binf<bsup){
				//on r�alise l'�change
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
	 * triRapide : trier r�cursivement un sous-tableau (algorithme du tri rapide)
	 * @param T : tableau � trier
	 * @param binf indice du premier �l�ment du sous-tableau � trier
	 * @param bsup indice du dernier �l�ment du sous-tableau � trier
	 * @pre 0<=binf, bsup<T.length
	 */
	static void triRapide(int[] T, int binf, int bsup){
		assert binf>=0 && bsup<T.length : "0<=binf, bsup<T.length";
		int pos;//variable pour r�cup�rer la position du pivot
		int temp;//variable pour placer le pivot
		if(binf<bsup){
			pos=partager(T, binf, bsup);//r�cup�ration de la position � laquelle doit se trouver le pivot
			//R�alisation de l'�change
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
	 * @param T : tableau � trier
	 * @param nb : nombre d'�l�ments � trier dans le tableau
	 * @pre 1<nb<=T.length
	 */
	public static void trier(int[] T, int nb){
		assert nb>1 && nb<=T.length :"1<nb<=T.length";
		triRapide(T, 0, nb-1);
	}
}

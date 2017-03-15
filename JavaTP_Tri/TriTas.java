package tp4;

/**
 * Classe permettant de r�aliser un TriTas
 * @author ROCHE - OUBAH
 *
 */
public class TriTas {

	/**
	 * ajouter tnb[p] au tas form� par les p premiers �l�ments du tableau tnb.
	 * @param tnb : tableau dont les p premiers �l�ments forment un tas
	 * @param p : indice de l'�l�ment � ajouter au tas
	 * @pre 1<=p<tnb.length
	 * @post les p+1 premiers �l�ments du tableau tnb forment un tas
	 */
    static void ajouterTas(int[] tnb, int p){
        assert p>=1 && p<tnb.length :"1<=p<tnb.length";
        int rang=p;
        int tmp; // variable temporaire pour �change de valeurs
        while (tnb[rang]>tnb[(int)((rang-1)/2)]){ // tant que le p�re de l'�l�ment est plus petit que lui
                if (rang >0){
                        tmp=tnb[rang]; // �change de l'�l�ment avec son p�re
                        tnb[rang]=tnb[(int)((rang-1)/2)];
                        tnb[(int)((rang-1)/2)]=tmp;
                        rang=(int)((rang-1)/2); // mise a jour du rang de l'�l�ment
                }
        }
   }
	
	/**
	 * supprimer l'�l�ment maximum du tas et r�organiser le reste du tas.
	 * @param tnb : tableau dont les p premiers �l�ments forment un tas.
	 * @param p : nombre d'�l�ments dans le tas
	 * @pre 1<p<=tnb.length
	 * @post : place l'�l�ment maximum en tnb[p-1] ; les p-1 premiers �l�ments
	 * de tnb forment un tas
	 */
	static void supprimerMax(int[] tnb, int p){
		assert p>1 && p<=tnb.length :"1<p<=tnb.length";
        int nbtas=p-1; //indice du dernier �l�ment du tas
        int tmp; //variable temporaire pour les �changes
        int curr=0; //variable d�sigant la position dans le tableau de l'�l�ment � traiter
        //�change du max avec le dernier �l�ment du tas
        tmp=tnb[curr];
        tnb[curr]=tnb[nbtas];
        tnb[nbtas]=tmp;
        nbtas--; //d�crementation de l'indice du dernier �l�ment du tas apr�s la sortie du max
       
        while (2*curr+2<=nbtas && tnb[curr]<tnb[curr*2+2] || 2*curr+1<=nbtas && (tnb[curr]<tnb[curr*2+1])){
        /*v�rifie si le tas est assez grand pour que les fils existent 
        ET qu'un des fils est plus grand que le p�re  */
       
                		/*v�rifie si le tas est assez grand pour que les fils existent
        				ET si le fils de droite et plus grand que le fils de gauche*/
                        if (2*curr+2<=nbtas && tnb[curr*2+2]>tnb[curr*2+1]){
                        		//�change du p�re avec son fils de droite
                                tmp=tnb[curr]; 
                                tnb[curr]=tnb[curr*2+2];
                                tnb[curr*2+2]=tmp;
                                curr=curr*2+2; //l'�l�ment � traiter prends la place de son fils de droite
                        }
                        
                        else{
                        		//�change du p�re avec son fils de gauche
                                tmp=tnb[curr];
                                tnb[curr]=tnb[curr*2+1];
                                tnb[curr*2+1]=tmp;
                                curr=curr*2+1; //l'�l�ment � traiter prends la place de son fils de gauche
                        }
                }
               
        }

	/**
	 * trier un tableau d'entiers par ordre croissant avec l'algorithme du tri par tas
	 * @param tnb : tableau � trier
	 * @param nb : nombres d'�l�ments dans le tableau
	 * @pre 1<=nb<=tnb.length
	 */
	public static void trier(int[] tnb, int nb){
		assert nb>=1 && nb<=tnb.length :"1<=nb<=tnb.length";
		int cpt=1;
        while (cpt < nb){ // ajout de tout les �l�ments du tableau au tas
                TriTas.ajouterTas(tnb,cpt);
                cpt++;
        }
        for(int i=0;i<nb-1;i++){ // suppression et tri de chaque �l�ment du tas
                supprimerMax(tnb,nb-i);
        }
	}
}
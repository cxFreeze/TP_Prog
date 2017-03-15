package tp4;

/**
 * Classe permettant de réaliser un TriTas
 * @author ROCHE - OUBAH
 *
 */
public class TriTas {

	/**
	 * ajouter tnb[p] au tas formé par les p premiers éléments du tableau tnb.
	 * @param tnb : tableau dont les p premiers éléments forment un tas
	 * @param p : indice de l'élément à ajouter au tas
	 * @pre 1<=p<tnb.length
	 * @post les p+1 premiers éléments du tableau tnb forment un tas
	 */
    static void ajouterTas(int[] tnb, int p){
        assert p>=1 && p<tnb.length :"1<=p<tnb.length";
        int rang=p;
        int tmp; // variable temporaire pour échange de valeurs
        while (tnb[rang]>tnb[(int)((rang-1)/2)]){ // tant que le père de l'élément est plus petit que lui
                if (rang >0){
                        tmp=tnb[rang]; // échange de l'élément avec son père
                        tnb[rang]=tnb[(int)((rang-1)/2)];
                        tnb[(int)((rang-1)/2)]=tmp;
                        rang=(int)((rang-1)/2); // mise a jour du rang de l'élément
                }
        }
   }
	
	/**
	 * supprimer l'élément maximum du tas et réorganiser le reste du tas.
	 * @param tnb : tableau dont les p premiers éléments forment un tas.
	 * @param p : nombre d'éléments dans le tas
	 * @pre 1<p<=tnb.length
	 * @post : place l'élément maximum en tnb[p-1] ; les p-1 premiers éléments
	 * de tnb forment un tas
	 */
	static void supprimerMax(int[] tnb, int p){
		assert p>1 && p<=tnb.length :"1<p<=tnb.length";
        int nbtas=p-1; //indice du dernier élément du tas
        int tmp; //variable temporaire pour les échanges
        int curr=0; //variable désigant la position dans le tableau de l'élément à traiter
        //échange du max avec le dernier élément du tas
        tmp=tnb[curr];
        tnb[curr]=tnb[nbtas];
        tnb[nbtas]=tmp;
        nbtas--; //décrementation de l'indice du dernier élément du tas après la sortie du max
       
        while (2*curr+2<=nbtas && tnb[curr]<tnb[curr*2+2] || 2*curr+1<=nbtas && (tnb[curr]<tnb[curr*2+1])){
        /*vérifie si le tas est assez grand pour que les fils existent 
        ET qu'un des fils est plus grand que le père  */
       
                		/*vérifie si le tas est assez grand pour que les fils existent
        				ET si le fils de droite et plus grand que le fils de gauche*/
                        if (2*curr+2<=nbtas && tnb[curr*2+2]>tnb[curr*2+1]){
                        		//échange du père avec son fils de droite
                                tmp=tnb[curr]; 
                                tnb[curr]=tnb[curr*2+2];
                                tnb[curr*2+2]=tmp;
                                curr=curr*2+2; //l'élément à traiter prends la place de son fils de droite
                        }
                        
                        else{
                        		//échange du père avec son fils de gauche
                                tmp=tnb[curr];
                                tnb[curr]=tnb[curr*2+1];
                                tnb[curr*2+1]=tmp;
                                curr=curr*2+1; //l'élément à traiter prends la place de son fils de gauche
                        }
                }
               
        }

	/**
	 * trier un tableau d'entiers par ordre croissant avec l'algorithme du tri par tas
	 * @param tnb : tableau à trier
	 * @param nb : nombres d'éléments dans le tableau
	 * @pre 1<=nb<=tnb.length
	 */
	public static void trier(int[] tnb, int nb){
		assert nb>=1 && nb<=tnb.length :"1<=nb<=tnb.length";
		int cpt=1;
        while (cpt < nb){ // ajout de tout les éléments du tableau au tas
                TriTas.ajouterTas(tnb,cpt);
                cpt++;
        }
        for(int i=0;i<nb-1;i++){ // suppression et tri de chaque élément du tas
                supprimerMax(tnb,nb-i);
        }
	}
}
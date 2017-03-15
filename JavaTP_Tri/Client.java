package tp4;

import java.io.IOException;
import java.io.InputStream;
import outilsTris.OutilsTris;

/**
 * Programme client:
 *  � saisir le nom d�un fichier de donn�es
 *	� lire le fichier et initialiser le tableau de nombres
 *	� trier le tableau de nombres
 *	� v�rifier (� l�aide d�une fonction � �crire) que le tableau est effectivement tri�
 *	� enregistrer le tableau tri� dans un fichier
 *	� afficher la dur�e du tri
 *	� v�rifier � l'aide d'une commande si les fichiers sont identiques
 * @author ROCHE - OUBAH
 *
 */
public class Client {
	
	/**
	 * M�thode main
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		int[] tnontrie;//tableau non tri�
		System.out.println("Saisir le nom d'un fichier de donn�es :");
		String nomfichier = new java.util.Scanner(System.in).nextLine();
		tnontrie = lireTableau(nomfichier);//r�cup�ration du tableau
		long t1 = OutilsTris.getInstantPresent();//pour le temps
		
		
		/******AU CHOIX *******/
		//TriTas.trier(tnontrie, tnontrie.length);//tri du tableau
		TriRapide.trier(tnontrie, tnontrie.length);//tri du tableau
		
		System.out.println("Dur�e du tri : "+(OutilsTris.getInstantPresent()-t1)+" ms");
		
		if(estTrie(tnontrie,lireTableau(nomfichier.substring(0,13)+"_trie"))){//si tableau bien tri�
			OutilsTris.enregistrerTableau(tnontrie, tnontrie.length, "nous_"+nomfichier);//on enregistre dans un fichier
			System.out.println("Tableau tri� et fichier enregistr�");
			System.out.println("VERIFICATION AVEC LA COMMANDE DIFF POUR LINUX OU FC POUR WINDOWS");
			estTrieCommandeDiff(nomfichier.substring(0,13)+"_trie", "nous_"+nomfichier);
		}
		else{
			System.out.println("Tableau non tri� et fichier non enregistr�");
		}	
	}
	
	/**
	 * M�thode pour lire un tableau
	 * @param nomFichier
	 * @return tableau
	 */
	public static int[] lireTableau(String nomFichier){		
		return OutilsTris.lireTableau(nomFichier);
	}
	
	/**
	 * M�thode pour enregistrer un tableau dans un fichier
	 * @param tnb
	 * @param nbelt
	 * @param nomFichier
	 * @param nomAlgo
	 */
	public static void enregistrerTableau(int[] tnb, int nbelt, String nomFichier, String nomAlgo){
		OutilsTris.enregistrerTableau(tnb, nbelt, nomFichier, nomAlgo);
	}
	
	/**
	 * M�thode pour r�cup�rer le temps
	 * @return Instant pr�sent
	 */
	public static long getInstantPresent(){
		return OutilsTris.getInstantPresent();
	}
	
	/**
	 * M�thode pour v�rifier si un tableau est tri�
	 * @param tab
	 * @param tabtriedebase
	 * @return true si tri�, false si non tri�
	 */
	public static boolean estTrie(int[] tab, int[] tabtriedebase) {
		for(int i=0;i<tab.length;i++){
			if(tab[i]!=tabtriedebase[i]) return false;
		}
		return true;
	}
	
	/**
	 * M�thode pour v�rifier que deux fichiers sont identiques avec commande syst�me
	 * @param file1
	 * @param file2
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void estTrieCommandeDiff(String file1, String file2) throws IOException, InterruptedException{
		if(System.getenv("os").toUpperCase().contains("WINDOWS")){//si l'OS est Windows, la commande est fc
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "fc "+file1+" "+file2);
	        pb.redirectError();
	        Process p = pb.start();
	        InputStreamConsumer isc = new InputStreamConsumer(p.getInputStream());//r�cup�ration du r�sultat de la commande
	        isc.start();
	        isc.join();
	        System.out.println(InputStreamConsumer.res);
		}
		else{//si l'OS n'est pas Windows, la commande est diff
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "diff "+file1+" "+file2);
	        pb.redirectError();
	        Process p = pb.start();
	        InputStreamConsumer isc = new InputStreamConsumer(p.getInputStream());//r�cup�ration du r�sultat de la commande
	        isc.start();
	        isc.join();
	        System.out.println(InputStreamConsumer.res);
		}
	}
	
	/**
	 * Classe permettant de r�cup�rer le r�sultat d'une commande syst�me
	 * @author ROCHE - OUBAH
	 *
	 */
    static class InputStreamConsumer extends Thread{
        private InputStream is;
        public static String res;
        
        public InputStreamConsumer(InputStream is){
            this.is=is;
        }
        
        @Override
        public void run(){
        	res="";
            int value=-1;
            try{
                while((value=is.read())!=-1){
                    res+=(char)value;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	
}

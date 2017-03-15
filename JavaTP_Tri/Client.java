package tp4;

import java.io.IOException;
import java.io.InputStream;
import outilsTris.OutilsTris;

/**
 * Programme client:
 *  — saisir le nom d’un fichier de données
 *	— lire le fichier et initialiser le tableau de nombres
 *	— trier le tableau de nombres
 *	— vérifier (à l’aide d’une fonction à écrire) que le tableau est effectivement trié
 *	— enregistrer le tableau trié dans un fichier
 *	— afficher la durée du tri
 *	— vérifier à l'aide d'une commande si les fichiers sont identiques
 * @author ROCHE - OUBAH
 *
 */
public class Client {
	
	/**
	 * Méthode main
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		int[] tnontrie;//tableau non trié
		System.out.println("Saisir le nom d'un fichier de données :");
		String nomfichier = new java.util.Scanner(System.in).nextLine();
		tnontrie = lireTableau(nomfichier);//récupération du tableau
		long t1 = OutilsTris.getInstantPresent();//pour le temps
		
		
		/******AU CHOIX *******/
		//TriTas.trier(tnontrie, tnontrie.length);//tri du tableau
		TriRapide.trier(tnontrie, tnontrie.length);//tri du tableau
		
		System.out.println("Durée du tri : "+(OutilsTris.getInstantPresent()-t1)+" ms");
		
		if(estTrie(tnontrie,lireTableau(nomfichier.substring(0,13)+"_trie"))){//si tableau bien trié
			OutilsTris.enregistrerTableau(tnontrie, tnontrie.length, "nous_"+nomfichier);//on enregistre dans un fichier
			System.out.println("Tableau trié et fichier enregistré");
			System.out.println("VERIFICATION AVEC LA COMMANDE DIFF POUR LINUX OU FC POUR WINDOWS");
			estTrieCommandeDiff(nomfichier.substring(0,13)+"_trie", "nous_"+nomfichier);
		}
		else{
			System.out.println("Tableau non trié et fichier non enregistré");
		}	
	}
	
	/**
	 * Méthode pour lire un tableau
	 * @param nomFichier
	 * @return tableau
	 */
	public static int[] lireTableau(String nomFichier){		
		return OutilsTris.lireTableau(nomFichier);
	}
	
	/**
	 * Méthode pour enregistrer un tableau dans un fichier
	 * @param tnb
	 * @param nbelt
	 * @param nomFichier
	 * @param nomAlgo
	 */
	public static void enregistrerTableau(int[] tnb, int nbelt, String nomFichier, String nomAlgo){
		OutilsTris.enregistrerTableau(tnb, nbelt, nomFichier, nomAlgo);
	}
	
	/**
	 * Méthode pour récupérer le temps
	 * @return Instant présent
	 */
	public static long getInstantPresent(){
		return OutilsTris.getInstantPresent();
	}
	
	/**
	 * Méthode pour vérifier si un tableau est trié
	 * @param tab
	 * @param tabtriedebase
	 * @return true si trié, false si non trié
	 */
	public static boolean estTrie(int[] tab, int[] tabtriedebase) {
		for(int i=0;i<tab.length;i++){
			if(tab[i]!=tabtriedebase[i]) return false;
		}
		return true;
	}
	
	/**
	 * Méthode pour vérifier que deux fichiers sont identiques avec commande système
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
	        InputStreamConsumer isc = new InputStreamConsumer(p.getInputStream());//récupération du résultat de la commande
	        isc.start();
	        isc.join();
	        System.out.println(InputStreamConsumer.res);
		}
		else{//si l'OS n'est pas Windows, la commande est diff
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "diff "+file1+" "+file2);
	        pb.redirectError();
	        Process p = pb.start();
	        InputStreamConsumer isc = new InputStreamConsumer(p.getInputStream());//récupération du résultat de la commande
	        isc.start();
	        isc.join();
	        System.out.println(InputStreamConsumer.res);
		}
	}
	
	/**
	 * Classe permettant de récupérer le résultat d'une commande système
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

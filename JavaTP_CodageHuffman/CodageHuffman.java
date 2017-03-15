package tp5;
import types.ABinHuffman;
import types.Couple;
import types.ListeABH;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

import outilsHuffman.OutilsHuffman;

/**
 * Réalisation du codage d'un texte par la méthode de Huffman
 */

public class CodageHuffman
{
  public static void main (String[] args)
  {
	  
    //------------------------------------------------------------------------
    // 0. Saisir le nom du fichier à coder (À FAIRE)
    //------------------------------------------------------------------------
	  System.out.print("Saisir le nom du fichier à coder : ");
	  String nomFichier= new Scanner(System.in).nextLine().toString();
	  
	  long time=OutilsHuffman.getInstantPresent();

    //------------------------------------------------------------------------
    // 1. Lire le texte (DONNÉ)
    //------------------------------------------------------------------------
    char [] texte = OutilsHuffman.lireFichier(nomFichier);
    System.out.println("temp de lecture du fichier :");

    //------------------------------------------------------------------------
    // 2. Calculer la table des fréquences des caractères (À FAIRE)
    //------------------------------------------------------------------------
    int [] tableFrequences = calculerFrequences(texte);

    //------------------------------------------------------------------------
    // 3. Enregistrer la table de fréquences dans le fichier de sortie (DONNÉ)
    //------------------------------------------------------------------------
    OutilsHuffman.enregistrerTableFrequences(tableFrequences, nomFichier + ".code");

    //------------------------------------------------------------------------
    // 4. Construire l'arbre de codage de Huffman (DONNÉ - À FAIRE)
    //------------------------------------------------------------------------
    ABinHuffman arbreCodageHuffman = construireArbreHuffman(tableFrequences);

    //------------------------------------------------------------------------
    // Afficher l'arbre de codage de Huffman (DÉJÀ FAIT)
    //------------------------------------------------------------------------
    System.out.println("Arbre de Huffman associé au texte " + nomFichier);
    DecodageHuffman.afficherHuffman(arbreCodageHuffman);

    //------------------------------------------------------------------------
    // 5. Construire la table de codage associée (À FAIRE)
    //------------------------------------------------------------------------
    String [] tablecodage = construireTableCodage(arbreCodageHuffman);

    //------------------------------------------------------------------------
    // 5.1. afficher la table de codage (À FAIRE)
    //------------------------------------------------------------------------
    System.out.println("Table de codage associée au texte " + nomFichier);
    afficherTableCodage(tablecodage);

    //------------------------------------------------------------------------
    // 6. coder le texte avec l'arbre de Huffman (À FAIRE)
    //------------------------------------------------------------------------
    StringBuilder texteCode = coderTexte(texte, tablecodage);

    //------------------------------------------------------------------------
    // 7. enregistrer le texte codé (DONNÉ)
    //------------------------------------------------------------------------
    OutilsHuffman.enregistrerTexteCode(texteCode, nomFichier + ".code");

    //------------------------------------------------------------------------
    // xx. calculer et afficher les stats (À FAIRE)
    //------------------------------------------------------------------------
    // calculer la taille du fichier non codé
    // calculer la taille du fichier codé
    double decode = OutilsHuffman.tailleFichier(nomFichier);
    double code=OutilsHuffman.tailleFichier(nomFichier+ ".code");
    System.out.println("Taux de compression : " + (code/decode)*100+"%");
    
    System.out.println("Durée du codage : "+(OutilsHuffman.getInstantPresent()-time)+" ms");
    
  }

  /**
   * 2. calculer la fréquence d'apparition de chaque caractère
   * @param  tcar tableau des caractères du texte
   * @return tableau de fréquence des caractères, indicé par les caractères
   */
  public static int [] calculerFrequences(char [] tcar)
  {
	  int[]res = new int [256];
	  for (int i=0; i<tcar.length;i++){
		  res[(int) tcar[i]]++;//on incrémente l'indice correspondant au caractère de 1
	  }
	  return res;
  }

  /**
   * 4. construire un arbre de codage de Huffman par sélection et combinaison
   * des éléments minimaux
   * @param tableFrequences table des fréquences des caractères
   * @return arbre de codage de Huffman
   */
  public static ABinHuffman construireArbreHuffman(int [] tableFrequences)
  {
	  ListeABH liste = faireListeAbinHuffman(tableFrequences);//récupération de la listeABH triée
	  
	  while(liste.size()>1){//tant que la liste contient plus qu'un élément
		  ABinHuffman temp = new ABinHuffman();//on crée un arbre temporaire
		  temp.setValeur(new Couple<Character, Integer>('.',0));//on initialise sa valeur
		  temp.setGauche(liste.get(0));//on crée son fils gauche qui correspond au 1er élément de la liste
		  liste.remove(0);//on supprime cet élément
		  temp.setDroit(liste.get(0));//on crée le fils gauche qui correspondra au 1er élément car on a supprimé l'ancien
		  liste.remove(0);//on supprime cet élément
		  int somme = temp.filsDroit().getValeur().deuxieme()+temp.filsGauche().getValeur().deuxieme();
		  //on modifie la fréquence initiale de 0 avec la somme
		  temp.setValeur(new Couple<Character, Integer>('.',somme));
		  
		  ListIterator<ABinHuffman> it = liste.listIterator();//ListIterator pour parcourrir la liste et placer l'arbre au bon endroit
		  while(it.hasNext()){
			  if(somme<it.next().getValeur().deuxieme()) {//lorsqu'on arrive au bon endroit
				  it.previous();//on retourne une fois en arrière
				  break;
			  }
		  }
		  it.add(temp);//on ajoute l'arbre au bon endroit
	  }
	  return liste.get(0);//à la fin, il ne reste qu'un seul élément, il sera renvoyé
  }

  /**
   * 4.1 Faire une liste triée dont chaque élément est un arbreBinaire<br>
   * comprenant un unique sommet dont l'étiquette est un couple
   * <caractère, fréquence>, trié par fréquence croissante
   * @param tableFrequences : table des fréquences des caractères
   * @return		      la liste triée
   */
  private static ListeABH faireListeAbinHuffman(int [] tableFrequences)
  {
	  ArrayList<Couple<Character, Integer>> temp = new ArrayList<Couple<Character, Integer>>();//on crée une liste qui va contenir les couples
	  for(int i=0;i<tableFrequences.length;i++)
		  if(tableFrequences[i]!=0)//si c'est différent de 0, alors le caractère existe
			  temp.add(new Couple<Character, Integer>((char)i, tableFrequences[i]));//on ajoute le couple à la liste
	  
	  trierInsertion(temp);//on trie notre liste de manière croissante
	  
	  ListeABH res = new ListeABH();
	  for(int i=0; i<temp.size();i++){//on parcourt l'ArrayList
		  ABinHuffman ab = new ABinHuffman();//on créé l'abre
		  ab.setValeur(temp.get(i));//on initialise la valeur de l'arbre avec le couple
		  res.add(ab);//on ajoute chaque arbre à la listeABH res
	  }
	  return res;
  }
  
	/**
	 * Méthode insérant un ABinHuffman dans le tableau.
	 * Puis un tri par insertion est effectué.
	 * @param nouveau Rationnel
	 */
	static void trierInsertion(ArrayList<Couple<Character, Integer>> liste) {
		for (int i = 1; i < liste.size(); i++) {
			int j;
			j = i;
			while (j > 0 && liste.get(j - 1).deuxieme().compareTo(liste.get(j).deuxieme())>0) {
				Couple<Character,Integer> couple = (liste.get(j));
				liste.set(j, liste.get(j-1));
				liste.set(j-1, couple);
				j--;
			}
		}
	}

  /**
   * 5. construire la table de codage correspondant à l'arbre de Huffman
   * @param abinHuff : arbre de Huffman
   * @return table de (dé)codage indicé par lex caractères
   */
  public static String [] construireTableCodage(ABinHuffman abinHuff)
  {
	  String[] res = new String[256];//tableau de String contenant la table
	  for(int i=0;i<res.length;i++){
		  if (abinHuff.existeGauche()){
			//on appelle la fonction auxiliaire pour démarrer la récursivité
			  construireTableCodageAuxiliaire(abinHuff.filsGauche(),i,new StringBuilder().append("0"),res);
		  }
		  if (abinHuff.existeDroit()){
			//on appelle la fonction auxiliaire pour démarrer la récursivité
			  construireTableCodageAuxiliaire(abinHuff.filsDroit(),i,new StringBuilder().append("1"),res);
		  }
	  }
	  return res;
  }
  
  /**
   * 
   * @param a arbre
   * @param i indice
   * @param s codage du caractère
   * @param res table de (dé)codage indicé par lex caractères
   */
  public static void construireTableCodageAuxiliaire(ABinHuffman a, int i, StringBuilder s, String[] res){
	  if(a.existeGauche()) {
		  construireTableCodageAuxiliaire(a.filsGauche(),i,s.append("0"),res);//on rappelle cette fonction et on ajoute 0 au code du caractère
		  s.deleteCharAt(s.length()-1);//à chaque fois qu'on remonte dans l'arbre, on supprime la dernière occurence du code de caractère
	  }
	  if(a.existeDroit()) {//si l'arbre a un fils droit
		  construireTableCodageAuxiliaire(a.filsDroit(),i,s.append("1"),res);//on rappelle cette fonction et on ajoute 0 au code du caractère
		  s.deleteCharAt(s.length()-1);//à chaque fois qu'on remonte dans l'arbre, on supprime la dernière occurence du code de caractère
	  }
	  if(a.estFeuille()){//si l'arbre est une feuille
		  if(a.getValeur().premier().compareTo(new Character((char)i))==0){
			  res[i]=s.toString();//on ajoute le codage du caractère au tableau de string
		  }
	  }
  }

  /**
   * 5.1. Afficher la table de codage associée au texte
   * @param tablecodage : table de codage associée au texte
   */
  public static void afficherTableCodage(String [] tablecodage)
  {
	  for(int i=0; i<tablecodage.length; i++){
		  if(tablecodage[i]!=null){
			  System.out.println("indice "+(char)i+" codage :"+tablecodage[i]);
		  }
	  }
  }

  /**
   * 6. Coder un texte à l'aide de la table de codage
   * @param texte à coder
   * @param tablecodage : table de codage associée au texte
   * @return texte codé
   */
  public static StringBuilder coderTexte(char [] texte, String [] tablecodage)
  {
	  StringBuilder res = new StringBuilder();
	  for(int i=0; i<texte.length; i++){
		  res.append(tablecodage[(int)texte[i]]);
	  }
	  return res;
  }
  
  /**
   * 
   * @param texte
   * @param nomFichierDecode
   */
  public static void  enregistrerTexte (StringBuilder texte , String nomFichierDecode){
	  FileOutputStream fop;
	  File file;
	  BufferedWriter fsor;
	  OutputStreamWriter outputStreamWriter;
	  try {
			file = new File(nomFichierDecode);//on crée le fichier
			fop = new FileOutputStream(file);//on crée le FileOutputStream
			outputStreamWriter = new OutputStreamWriter(fop);//instanciation du OutputStreamWriter
			fsor=new BufferedWriter(outputStreamWriter);//instanciation du BufferedWriter
			fsor.write(texte.toString());//on écrit
			fsor.close();//on ferme le BufferedWriter
	  } catch (IOException e) {
		  //on capture l'exception
			e.printStackTrace();
	  }
  }
  
}// CodageHuffman

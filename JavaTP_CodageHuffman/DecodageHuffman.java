package tp5;
import types.ABinHuffman;

import java.util.Scanner;

import outilsHuffman.OutilsHuffman;

/**
 * Réalisation du décodage d'un texte par la méthode de Huffman
 */

public class DecodageHuffman
{
  public static void main (String[] args)
  {
    //------------------------------------------------------------------------
    // 0. Saisir le nom du fichier à décoder (À FAIRE)
    //------------------------------------------------------------------------
	System.out.print("Saisir le nom du fichier à décoder : ");
	String nomFichier= new Scanner(System.in).nextLine().toString();

	long time=OutilsHuffman.getInstantPresent();
    //------------------------------------------------------------------------
    // 1. Lire et construire la table de fréquences (DONNÉ)
    //------------------------------------------------------------------------
    int [] tableFrequences = OutilsHuffman.lireTableFrequences(nomFichier);

    //------------------------------------------------------------------------
    // 2. Construire l'arbre de Huffman (DONNÉ)
    //------------------------------------------------------------------------
    ABinHuffman arbreHuffman = CodageHuffman.construireArbreHuffman(tableFrequences);

    //------------------------------------------------------------------------
    // 2.1 afficher l'arbre de Huffman (À FAIRE)
    //------------------------------------------------------------------------
    System.out.println("Arbre de Huffman associé au texte " + nomFichier);
    afficherHuffman(arbreHuffman);

    //------------------------------------------------------------------------
    // 3. Lire le texte codé (DONNÉ)
    //------------------------------------------------------------------------
    String texteCode = OutilsHuffman.lireTexteCode(nomFichier);

    //------------------------------------------------------------------------
    // 4. Décoder le texte (À FAIRE)
    //------------------------------------------------------------------------
    StringBuilder texteDecode = decoderTexte(texteCode, arbreHuffman);

    //------------------------------------------------------------------------
    // 5. Enregistrer le texte décode (DONNÉ)
    //------------------------------------------------------------------------
    System.out.println("texte décodé:\n\n" + texteDecode);
    CodageHuffman.enregistrerTexte(texteDecode, nomFichier + ".decode");
    
    System.out.println("Durée du décodage : "+(OutilsHuffman.getInstantPresent()-time)+" ms");

  }

  /**
   * 4. décoder une chaîne (non vide) encodée par le codage de Huffman
   * @param texteCode    : chaîne de "0/1" à décoder
   * @param arbreHuffman : arbre de (dé)codage des caractères
   */
  public static StringBuilder decoderTexte(String texteCode, ABinHuffman arbreHuffman)
  {
	  StringBuilder res = new StringBuilder();  
	  int cpt=0;//compteur
	  ABinHuffman arbre;//arbre temporaire
	  while (cpt!=texteCode.length()){
		  arbre = arbreHuffman;
		  while (!arbre.estFeuille() && cpt!=texteCode.length()){
			  if (arbre.existeGauche() && texteCode.charAt(cpt)=='0'){//si fils gauche et code égal à 0
				  arbre = arbre.filsGauche();
			  }
			  else if (arbre.existeDroit() && texteCode.charAt(cpt)=='1'){//si fils droit et code égal à 1
				  arbre = arbre.filsDroit();
			  }
			  
			  if (arbre.estFeuille()) {
				  res.append(arbre.getValeur().premier().charValue());//on ajoute le caractère au résultat
			  }
			  cpt++;
		  }
	  }
	  return res;
  }

  /**
   * 2.1 afficher un arbre de Huffman
   * @param a : arbre binaire de Huffman
   */
  public static void afficherHuffman(ABinHuffman a)
  {
	  if (a.existeGauche()){//si l'arbre a un fils gauche
		  //on appelle la fonction auxiliaire pour démarrer la récursivité
		  afficherHuffmanAuxiliaire(a.filsGauche(),new StringBuilder().append("0"));
	  }
	  if (a.existeDroit()){//si l'arbre a un fils droit
		  //on appelle la fonction auxiliaire pour démarrer la récursivité
		  afficherHuffmanAuxiliaire(a.filsDroit(),new StringBuilder().append("1"));
	  }
	  
	  System.out.println("-------------------------------------------------");
  }
  
  /**
   * Fonction auxiliaire qui est utilisée pour sauvegarder le code du caractère
   * @param a ABinHuffman à parcourir
   * @param s StringBuilder contenant le code du caractère
   */
  public static void afficherHuffmanAuxiliaire(ABinHuffman a, StringBuilder s){
	  if(a.existeGauche()) {//si l'arbre a un fils gauche
		  afficherHuffmanAuxiliaire(a.filsGauche(),s.append("0"));//on rappelle cette fonction et on ajoute 0 au code du caractère
		  s.deleteCharAt(s.length()-1);//à chaque fois qu'on remonte dans l'arbre, on supprime la dernière occurence du code de caractère
	  }
	  if(a.existeDroit()) {//si l'arbre a un fils droit
		  afficherHuffmanAuxiliaire(a.filsDroit(),s.append("1"));//on rappelle cette fonction et on ajoute 1 au code du caractère
		  s.deleteCharAt(s.length()-1);//à chaque fois qu'on remonte dans l'arbre, on supprime la dernière occurence du code de caractère
	  }
	  if(a.estFeuille()){//si l'arbre est une feuille
		  System.out.println("<"+a.getValeur().premier().charValue()+","+a.getValeur().deuxieme()+"> : "+s);
	  }
  }
  
  
} // DecodageHuffman

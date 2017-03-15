package util;

import java.util.Scanner;

import rationnel.RationnelCouple;
import rationnel.RationnelSimple;
import types.Rationnel;

/**
 * Cette classe nous a permis de tester nos différentes classes.
 * @author ROCHE CORENTIN - OUBAH KARIM
 *
 */
public class clientRationnel {

	public static void main(String[] args) {
		
		int taille = 15;
		Rationnel[] tab = new Rationnel[taille];
		Rationnel r1 = null, r2 = null, somme = null;
		int i = 0;
		do {
			System.out.println("Saisir un numerateur et un dénominateur (!=0) pour le rationnel :");
			r1 = lireRationnel(new Scanner(System.in));
			insererRationnel(r1, tab, i);
			i++;
			System.out.println(r1.toString());
			System.out.println("Inverse : " + r1.inverse().toString());
			insererRationnel(r1.inverse(), tab, i);
			i++;
			if (r2 == null) {
				somme = r1;
				insererRationnel(somme, tab, i);
				i++;
				if (r1.valeur() < 0)
					System.out.println(r1.valeur() + "<" + 0);
				else {
					System.out.println(r1.valeur() + ">=" + 0);
				}
				if (r1.valeur() == 0)
					System.out.println(r1.valeur() + "=0");
				else
					System.out.println(r1.valeur() + "!=0");
			} else {
				if (r1.compareTo(r2) == 1)
					System.out.println(r1.valeur() + ">" + r2.valeur());
				else if (r1.compareTo(r2) == 0)
					System.out.println(r1.valeur() + "=" + r2.valeur());
				else
					System.out.println(r1.valeur() + "<" + r2.valeur());
				somme = r1.somme(r2);
				if (r1.equals(r2))
					System.out.println(r1.valeur() + "=" + r2.valeur());
				else
					System.out.println(r1.valeur() + "!=" + r2.valeur());
				insererRationnel(somme, tab, i);
				i++;
			}
			System.out.println("La somme du rationnel saisie et du précédent est : " + somme.valeur());
			r2 = r1;
			afficher(tab,i);
		} while (r1.valeur() != 0);

		System.out.print("Vous avez saisi un rationnel égal à 0, le programme est terminé");
	}

	/**
	 * Méthode permettant à l'utilisateur de saisir un rationnel
	 * @param input 
	 * @return
	 */
	static Rationnel lireRationnel(Scanner input) {
		return makeRationnel(input.nextInt(), input.nextInt());
	}

	/**
	 * Méthode permettant de créer un rationnel avec une alternance
	 * en fonction de la parité du numérateur
	 * @param num int
	 * @param den int
	 */
	static Rationnel makeRationnel(int num, int den) {
		if(num%2==0) return new RationnelSimple(num, den);
		else return new RationnelCouple(num, den);
	}

	/**
	 * Affiche le contenu d'un tableau de Rationnel
	 * @param lesRationnels
	 * @param nb
	 */
	static void afficher(Rationnel[] lesRationnels, int nb) {
		System.out.println("/********TABLEAU*********/");
		for (int i = 0; i < nb & i < lesRationnels.length; i++)
			System.out.print(lesRationnels[i]+" ");
		System.out.println("");
		System.out.println("/********FIN TABLEAU*********/");
	}

	/**
	 * Méthode insérant un rationnel dans le tableau.
	 * Puis un tri par insertion est effectué.
	 * @param nouveau Rationnel
	 * @param lesRationnels
	 * @param nb place du rationnel dans le tableau avant tri
	 */
	static void insererRationnel(Rationnel nouveau, Rationnel[] lesRationnels, int nb) {
		lesRationnels[nb] = nouveau;
		for (int i = 1; i < lesRationnels.length && i<=nb; i++) {
			int j;
			j = i;
			while (j > 0 && lesRationnels[j - 1].valeur() > lesRationnels[j].valeur()) {
				Rationnel tmp = lesRationnels[j];
				lesRationnels[j] = lesRationnels[j - 1];
				lesRationnels[j - 1] = tmp;
				j--;
			}
		}
	}
}

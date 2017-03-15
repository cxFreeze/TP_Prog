package main;

import java.util.Random;
import java.util.Scanner;
import tableau.Block;
import tableau.Tableau2x;
import tableau.TableauBlock;
import types.Tableau;

/**
 * Classe main
 * 
 * @author OUBAH KARIM - ROCHE CORENTIN
 */
public class NombresPremiers {

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		Tableau<Integer> tab1 = new TableauBlock<Integer>(100);
		System.out.println("Veuillez saisir un nombre >=2 : ");
		int n = new Scanner(System.in).nextInt();
		int temp = calculerNombresPremiers(n, tab1);
		testAffichage(tab1,temp);
		Tableau<Integer> tab2 = remplirHasard(5);
		testAffichage(tab2,temp);
		System.out.println("NOMBRE ELEMENTS SUPPRIMES "+eliminerPresents(tab1, tab1));
	}

	/**
	 * Fonction permettant de savoir si un entier est premier
	 * @param n entier à tester
	 * @param premiers tableau de nombres premiers
	 * @return true si nombre premier, false sinon
	 */
	public static boolean estPremier(int n, Tableau<Integer> premiers){
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        for(int i=0;i<premiers.size();i++){
        	if(n==premiers.get(i)) return true;
        	if(n%premiers.get(i) == 0) return false;
        }
        	
        return true;
	}
	
	/**
	 * Fonction permettant de calculer les nombres premiers dans l'intervalle [2;n]
	 * @param n
	 * @param res Tableau vide
	 * @return dernier élément testé si le tableau est complet durant le calcul ou le nombre n sinon
	 */
	public static int calculerNombresPremiers(int n, Tableau<Integer> res){
		assert n>=2 : "n doit être >=2";
		res.push_back(2);
		for (int i=3;i<n;i++)
			if (estPremier(i,res)){
				res.push_back(i);
				if(res.full()) return i;
			}
		return n;
	}
	
	/**
	 * Affichage du tableau et du dernier entier testé
	 * @param tab
	 * @param dernier
	 */
	public static void testAffichage(Tableau<Integer> tab, int dernier){
		for(int i=0;i<tab.size();i++)
			System.out.println(tab.get(i));
		System.out.println("Dernier entier testé : "+dernier);
	}
	
	/**
	 * Méthode permettant de remplir un tableau
	 * avec des nombres au hasard compris entre [0;n[
	 * @param n
	 * @return res : le tableau d'éléments rempli
	 */
	public static <T> Tableau<Integer> remplirHasard(int n){
		Tableau<Integer> res = new TableauBlock<Integer>(n);
		for (int i = 0; i<n ; i++){
			res.push_back((int)(Math.random() * (n-0)) + 0);
		}
		return res;
	}
	
	/**
	 * Méthode permettant d'éliminer de tab1 les nombres présents dans tab2
	 * @param tab1 non trié
	 * @param tab2 trié
	 * @return le nombre d'élément supprimer
	 */
	public static int eliminerPresents(Tableau<Integer> tab1, Tableau<Integer> tab2){
		int res=0;
		int temp;
		for (int i = tab1.size()-1; i>=0; i--){
			if(tab1.get(i).compareTo(tab2.get(0))>0 | tab1.get(i).compareTo(tab2.get(tab2.size()-1))<0){
				//on vérifie si l'élément est bien plus grand que le plus petit de tab2
				//qu'il est plus petit que le plus grand de tab2
				if (rechercheDichotomique(tab2, tab1.get(i))!=-1){//il est présent
					res++;
					//on transpose à la fin du tableau l'élément à éliminer avec le dernier élément
					temp=tab1.get(i);
					tab1.set(i, tab1.get(tab1.size()-1));
					tab1.set(tab1.size()-1, temp);
					tab1.pop_back();//on supprime l'élément
				}
			}
		}
		return res;
	}
	

	/**
	 * Algorithme de recherche dichotomique
	 * @param tab d'élément
	 * @param élément à trouver
	 * @return true si l'élément est dans tab, false sinon
	 */
	public static int rechercheDichotomique(Tableau<Integer> tab, Integer element){
		int idebut=0;
		int ifin=tab.size()-1;
		int milieu;
		/*
		 * si les valeurs sont égales, la tâche est accomplie
		 * sinon on recommence dans la moitié du tableau pertinente.
		 */
		while (idebut<=ifin){
			milieu=(int)(idebut+ifin)/2;
			if(tab.get(milieu).compareTo(element)==0){
				return milieu;
			}
			if(tab.get(milieu).compareTo(element)==-1){
				idebut=milieu+1;
			}else{
				ifin=milieu-1;
			}
		}
		return -1;
	}
	
}

package simulation;

import facturation.Facture;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import messagerie.ListOperateur;
import messagerie.Operateur;
import messagerie.Telephone;

public class Simulateur {

    public static void main(String[] args) {

        ListOperateur listOperateur = new ListOperateur();//liste des opérateurs

        // les noms
        String[] noms = {
            "Samuel", // +33(0)700000001
            "Sébastien", //		 2
            "Aurélie", //		 3
            "Léa", //		 4
            "Pierre", //		 5
            "Géraldine", //		 6
            "Bastien", //		 7
            "Claude", //		 8
        };

        // créer des opérateurs
        Operateur breizhtel = new Operateur("BreizhTel");
        Operateur orange = new Operateur("Orange");
        Operateur free = new Operateur("Free");
        String[] lesForfaits = {"illimite", "1h", "acte"};

        // SDD pour mémoriser les téléphones
        Map<String, Telephone> lesTelephones = new TreeMap<String, Telephone>();

        // générateur de nombres aléatoires
        Random generateurAleatoire = new Random();
        // souscrire des abonnements
        for (int i = 0; i < noms.length; ++i) {
            try {
                Telephone newtel = listOperateur.getOperateurAuHasard().souscrire(noms[i], lesForfaits[generateurAleatoire.nextInt(lesForfaits.length)]);
                lesTelephones.put(noms[i], newtel);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("*** Erreur : impossible d'abonner " + noms[i]);
            }
        }

        listOperateur.getListOperateurEtAbonnes();

        lesTelephones.get("Samuel").allumer();
        lesTelephones.get("Samuel").appeler("+33(0)700000004", "Où c'est qu't'es ?", new Date(2012, 12, 13, 15, 45, 10));
        lesTelephones.get("Samuel").envoyerSMS("+33(0)700000004", "Léa, réponds, nom d'une pipe !", new Date(2012, 12, 13, 15, 45, 10));
        lesTelephones.get("Samuel").envoyerSMS("+33(0)700000004", "Léa, alleeez, sois pas vache !", new Date(2012, 12, 13, 15, 46, 0));
        lesTelephones.get("Samuel").envoyerSMS("+33(0)700000004", "Léa, je plaisantais !!!!!!!!!!", new Date(2012, 12, 13, 15, 46, 10));
        lesTelephones.get("Léa").allumer();
        lesTelephones.get("Samuel").appeler("+33(0)700000004", "Où c'est qu't'es ?", new Date(2012, 12, 13, 15, 47, 5));
        lesTelephones.get("Samuel").cloreAppel(new Date(2012, 12, 13, 18, 50, 5));
        lesTelephones.get("Léa").getAbonneOperateur().consulterBoiteVocale();
        lesTelephones.get("Léa").getAbonneOperateur().synchroniser();
        lesTelephones.get("Léa").consulterBoiteSMS();
        lesTelephones.get("Claude").allumer();
        lesTelephones.get("Claude").appeler("+33(0)700000001", "Je suis Claude", new Date(2017, 12, 13, 18, 45, 10));
        lesTelephones.get("Claude").getAbonneOperateur().parler("Bonjour, ca va ?");
        lesTelephones.get("Samuel").getAbonneOperateur().parler("non, au revoir");
        lesTelephones.get("Samuel").cloreAppel(new Date(2017, 12, 13, 18, 46, 10));
        lesTelephones.get("Samuel").eteindre();

        // ------------------------------------------------------------------------
        //			FACTURATION
        // ------------------------------------------------------------------------
        for (Map.Entry<String, Telephone> abonne : lesTelephones.entrySet()) {
            //on affiche un historique des communications
            abonne.getValue().getAbonneOperateur().getOperateur().getHistorique(abonne.getValue().getAbonneOperateur());
            //on facture $$$$$$$$$$$
            System.out.println(new Facture(abonne.getValue().getAbonneOperateur()));
        }
    }
} // Simulateur

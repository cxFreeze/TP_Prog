package messagerie;

import communication.AbstractCommunication;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe permettant d'instancier une liste pour gérer l'application avec plusieurs opérateurs
 * @author OUBAH-ROCHE
 */
public class ListOperateur {

    private static ArrayList<Operateur> listeDesOperateurs;//liste des opérateurs

    public ListOperateur() {
        listeDesOperateurs = new ArrayList<Operateur>();
    }

    public static void ajouterOperateur(Operateur operateur) {
        listeDesOperateurs.add(operateur);
    }

    /**
     * Cette méthode permet de savoir si un opérateur a attribué le numéro recherché
     * @param numero
     * @return l'abonné opérateur en question
     */
    public static AbonneOperateur numeroExistant(String numero) {
        for (int i = 0; i < listeDesOperateurs.size(); i++) {
            //pour chaque opérateur, on regarde dans sa TreeMap lesTelephones s'il a le numéro, si oui on retourne l'abonné
            if (listeDesOperateurs.get(i).getLesTelephones().containsKey(numero)) {
                return listeDesOperateurs.get(i).getLesTelephones().get(numero);
            }
        }
        return null;
    }

    /**
     * Méthode permettant d'obtenir les communications d'un opérateur pour un numéro donné
     * @param numero
     * @return Map avec pour clé le numéro de tel et pour valeur la liste des communications
     */
    public static Map<String, ArrayList<AbstractCommunication>> getLesCommunicationsDunOperateur(String numero) {
        for (int i = 0; i < listeDesOperateurs.size(); i++) {
            if (listeDesOperateurs.get(i).getLesTelephones().containsKey(numero)) {
                return listeDesOperateurs.get(i).getLesCommunications();
            }
        }
        return null;
    }

    public ArrayList<Operateur> getListeDesOperateurs() {
        return listeDesOperateurs;
    }

    /**
     * Méthode permettant d'affecter un opérateur au hasard pour la simulation
     * @return 
     */
    public Operateur getOperateurAuHasard() {
        return ListOperateur.listeDesOperateurs.get((int) ((listeDesOperateurs.size() - 1 - 0 + 1) * Math.random() + 0));
    }

    /**
     * Pour chaque opérateur, on affiche ses abonnés
     */
    public void getListOperateurEtAbonnes() {
        System.out.println("Affichage des abonnés pour chaque opérateur");
        for (int i = 0; i < listeDesOperateurs.size(); i++) {
            listeDesOperateurs.get(i).afficheLesAbonnes();
        }
        System.out.println("----------------------");
        System.out.println();
    }

    /**
     * Méthode permettant d'obtenir le nombre total d'abonnés tout opérateur confondus
     * Elle va être utilisé pour attriber les numéros de téléphone afin d'avoir des nombres croissants
     * @return 
     */
    public static int getNombreTotalAbonnes() {
        int res = 0;
        for (int i = 0; i < listeDesOperateurs.size(); i++) {
            res += listeDesOperateurs.get(i).getLesTelephones().size();
        }
        return res;
    }

}

package messagerie;

import java.util.ArrayList;

import communication.MessageVocal;

/**
 * Classe permettant de créer une boite vocale
 * @author OUBAH-ROCHE
 */
public class BoiteVocale {

    private ArrayList<MessageVocal> listeMessagesVocaux;//liste contenant les messages vocaux
    private int nbmessage = 0;

    public BoiteVocale() {
        this.listeMessagesVocaux = new ArrayList<MessageVocal>();
    }

    public ArrayList<MessageVocal> getListeMessagesVocaux() {
        return listeMessagesVocaux;
    }

    public void ajouteBoiteVocaleTelephone(MessageVocal message) {
        nbmessage++;
        listeMessagesVocaux.add(message);
    }

    public int getNbmessage() {
        return nbmessage;
    }

    /**
     * Méthode permettant de savoir cb de messages ont été consulté pour la facturation
     * @return nombre de message consulté
     */
    public int getNbmessageConsulte() {
        int res = 0;
        for (int i = 0; i < listeMessagesVocaux.size(); i++) {
            //on vérifie si l'attribut consulte==true
            if (listeMessagesVocaux.get(i).isConsulte()) {
                res++;
            }
        }
        return res;
    }
}

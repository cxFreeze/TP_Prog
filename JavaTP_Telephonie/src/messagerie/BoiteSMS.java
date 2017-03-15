package messagerie;

import communication.MessageSMS;
import java.util.ArrayList;

/**
 * Classe permettant de créer une BoiteSMS
 * @author OUBAH-ROCHE
 */
public class BoiteSMS {

    private ArrayList<MessageSMS> listeSMS;//liste contenant les MessageSMS
    private int nbmessage = 0;//nombre de message dans la boiteSMS

    public BoiteSMS() {
        this.listeSMS = new ArrayList<MessageSMS>();
    }

    public ArrayList<MessageSMS> getListeSMS() {
        return listeSMS;
    }

    public void ajouteBoiteSMSTelephone(MessageSMS message) {
        nbmessage++;
        listeSMS.add(message);
    }

    public int getNbmessage() {
        return nbmessage;
    }

}

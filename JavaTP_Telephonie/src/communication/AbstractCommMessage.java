package communication;

import facturation.NumeroTelephone;
import java.util.Date;

/**
 * Classe abstraite pour créer des messages vocaux ou sms
 * @author OUBAH-ROCHE
 */
public abstract class AbstractCommMessage extends AbstractCommunication {

    public AbstractCommMessage(NumeroTelephone appele, NumeroTelephone appelant, Date debutComm) {
        super(appele, appelant, debutComm);
    }

}

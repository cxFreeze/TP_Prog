package communication;

import facturation.NumeroTelephone;
import java.util.Date;

/**
 * Classe pour créer une communication de type message vocal
 * @author OUBAH-ROCHE
 */
public class CommMessageVocal extends AbstractCommMessage {

    private String message;

    public CommMessageVocal(String message, NumeroTelephone appele, NumeroTelephone appelant, Date debutComm) {
        super(appele, appelant, debutComm);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

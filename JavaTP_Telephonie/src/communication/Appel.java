package communication;

import facturation.NumeroTelephone;
import java.util.Date;

/**
 * Classe pour instancier un appel
 * @author OUBAH-ROCHE
 */
public class Appel extends AbstractCommMessage {

    private Date finComm;

    public Appel(NumeroTelephone appele, NumeroTelephone appelant, Date debutComm, Date finComm) {
        super(appele, appelant, debutComm);
        this.finComm = finComm;
    }

    public Date getFinComm() {
        return finComm;
    }

    public void setFinComm(Date finComm) {
        this.finComm = finComm;
    }

}

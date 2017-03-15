package communication;

import facturation.NumeroTelephone;
import java.util.Date;

/**
 * Classe abstraite pour créer des communications
 * @author OUBAH-ROCHE
 */
public abstract class AbstractCommunication {

    private NumeroTelephone appele;
    private NumeroTelephone appelant;
    private Date debutComm;

    public AbstractCommunication(NumeroTelephone appele, NumeroTelephone appelant, Date debutComm) {
        this.appele = appele;
        this.appelant = appelant;
        this.debutComm = debutComm;
    }

    public Date getDebutComm() {
        return debutComm;
    }

    public void setDebutComm(Date debutComm) {
        this.debutComm = debutComm;
    }

    public NumeroTelephone getAppele() {
        return appele;
    }

    public void setAppele(NumeroTelephone appele) {
        this.appele = appele;
    }

    public NumeroTelephone getAppelant() {
        return appelant;
    }

    public void setAppelant(NumeroTelephone appelant) {
        this.appelant = appelant;
    }

}

package facturation;

/**
 * Classe pour créer des attributs communs aux forfaits
 * @author OUBAH-ROCHE
 */
public abstract class Forfait {

    private float prixDeBase;
    private float prixSMS;
    private float prixMinuteCommunication;
    private float prixConsultationBoiteVocale;

    public Forfait(float prixDeBase, float prixSMS, float prixMinuteCommunication, float prixConsultationBoiteVocale) {
        this.prixDeBase = prixDeBase;
        this.prixSMS = prixSMS;
        this.prixMinuteCommunication = prixMinuteCommunication;
        this.prixConsultationBoiteVocale = prixConsultationBoiteVocale;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public float getPrixDeBase() {
        return prixDeBase;
    }

    public void setPrixDeBase(float prixDeBase) {
        this.prixDeBase = prixDeBase;
    }

    public float getPrixSMS() {
        return prixSMS;
    }

    public void setPrixSMS(float prixSMS) {
        this.prixSMS = prixSMS;
    }

    public float getPrixMinuteCommunication() {
        return prixMinuteCommunication;
    }

    public void setPrixMinuteCommunication(float prixMinuteCommunication) {
        this.prixMinuteCommunication = prixMinuteCommunication;
    }

    public float getPrixConsultationBoiteVocale() {
        return prixConsultationBoiteVocale;
    }

    public void setPrixConsultationBoiteVocale(float prixConsultationBoiteVocale) {
        this.prixConsultationBoiteVocale = prixConsultationBoiteVocale;
    }

}

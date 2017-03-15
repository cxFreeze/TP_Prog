package messagerie;

import communication.CommSMS;
import communication.MessageSMS;
import java.util.Date;

import facturation.Forfait;
import facturation.NumeroTelephone;

/**
 * Informations d'Abonné gérées par l'opérateur
 *
 * @author OUBAH-ROCHE
 */
public class AbonneOperateur implements GestionCommunication {

    /**
     * Attributs *
     */
    private String nomAbonne;
    private final BoiteSMS boiteSMS;
    private final BoiteVocale boiteVocale;
    private final NumeroTelephone numeroTelephone;
    private Forfait forfait;
    private Telephone telephone;
    private Operateur operateur;

    public AbonneOperateur(Operateur operateur, Telephone telephone, String nom, BoiteSMS boiteSMS, BoiteVocale boiteVocale, Forfait forfait) {
        this.telephone = telephone;
        nomAbonne = nom;
        //on récupère le nombre le plus grand attribué pour créer le numéro de téléphone
        numeroTelephone = new NumeroTelephone(ListOperateur.getNombreTotalAbonnes());
        this.boiteSMS = boiteSMS;
        this.boiteVocale = boiteVocale;
        this.forfait = forfait;
        this.operateur = operateur;
    }
    //------------------------------------------------------------------------
    // méthodes de l'interface GestionCommunication
    //------------------------------------------------------------------------

    @Override
    public String toString() {
        return "nomAbonne=" + nomAbonne + ", numeroTelephone=" + numeroTelephone.getNumero() + ", forfait=" + forfait + ", operateur=" + operateur;
    }

    /**
     * Méthode pour appeler quelqu'un
     *
     * @param numero
     * @param msgVocalSiOccupe
     * @param dateDebut
     * @return
     */
    @Override
    public boolean appeler(String numero, String msgVocalSiOccupe, Date dateDebut) {
        return this.telephone.appeler(numero, msgVocalSiOccupe, dateDebut);
    }

    /**
     * Méthode pour envoyer un sms
     *
     * @param numero
     * @param sms
     * @param dateSMS
     */
    @Override
    public void envoyerSMS(String numero, String sms, Date dateSMS) {
        assert ListOperateur.numeroExistant(numero) != null : "numéro inconnu";
        operateur.posterSMS(this, numero, sms, dateSMS);
    }

    @Override
    public void recevoirSMS(MessageSMS message) {
        System.out.println(message);
    }

    @Override
    public boolean accepterAppel(String numeroAppelant) {
        return this.telephone.accepterAppel(numeroAppelant);
    }

    @Override
    public void cloreAppel(Date fin) {
        this.getOperateur().cloreAppel(this, fin);
    }

    //------------------------------------------------------------------------
    // autres méthodes
    //------------------------------------------------------------------------
    // transférer sur le téléphone les SMS du serveur
    public void synchroniser() {
        assert this.telephone.getEstAllume() : "pas allumé";

        //on ajoute à la boiteSMS du téléphone tous les messages stochés sur la boiteSMS du serveur en partant du dernier synchronisé
        for (int i = this.telephone.getBoiteSMS().getNbmessage(); i < this.boiteSMS.getListeSMS().size(); i++) {
            this.telephone.getBoiteSMS().ajouteBoiteSMSTelephone(this.boiteSMS.getListeSMS().get(i));
        }

        //idem pour les messages vocaux
        for (int i = this.boiteVocale.getListeMessagesVocaux().size(); i < this.boiteVocale.getNbmessage(); i++) {
            //on envoie un sms à l'abonné pour le prévenir qu'il y a des messages vocaux dans sa boite
            this.telephone.getBoiteSMS().ajouteBoiteSMSTelephone(
                    new MessageSMS(
                            new CommSMS(
                                    "nouveau message vocale de la part de " + this.boiteVocale.getListeMessagesVocaux().get(i).getAppelant(),
                                    this.telephone.getAbonneOperateur().getNumeroTelephone(),
                                    this.boiteVocale.getListeMessagesVocaux().get(i).getAppelant(),
                                    this.boiteVocale.getListeMessagesVocaux().get(i).getCommMsg().getDebutComm()
                            )
                    )
            );
        }
    }

    /**
     * Consultation de la boite vocale
     */
    public void consulterBoiteVocale() {
        for (int i = 0; i < this.boiteVocale.getListeMessagesVocaux().size(); i++) {
            //on passe l'attribut consulte à true pour savoir qu'il a été vu pour la facturation
            this.boiteVocale.getListeMessagesVocaux().get(i).setConsulte(true);
        }
    }

    boolean estHorsLigne() {
        return !this.telephone.getEstAllume();
    }

    boolean estLibre() {
        return this.telephone.getEstAllume() && this.telephone.getLibre();
    }

    /**
     * Pour parler en appel
     *
     * @param message
     */
    public void parler(String message) {
        if (!this.estHorsLigne() && !this.estLibre()) {//tel allumé et en appel, on parle
            System.out.println(message);
        }
    }

    /**
     * GETTERS & SETTERS*
     */
    public NumeroTelephone getNumeroTelephone() {
        return this.numeroTelephone;
    }

    public String getNomAbonne() {
        return nomAbonne;
    }

    public void setNomAbonne(String nomAbonne) {
        this.nomAbonne = nomAbonne;
    }

    public Forfait getForfait() {
        return forfait;
    }

    public void setForfait(Forfait forfait) {
        this.forfait = forfait;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }

    public Operateur getOperateur() {
        return operateur;
    }

    public void setOperateur(Operateur operateur) {
        this.operateur = operateur;
    }

    public BoiteSMS getBoiteSMS() {
        return boiteSMS;
    }

    public BoiteVocale getBoiteVocale() {
        return boiteVocale;
    }

} // AbonneOperateur

package messagerie;

import communication.CommSMS;
import communication.MessageSMS;
import java.util.Date;
import java.util.Scanner;

/**
 * utilisation du téléphone par l'abonné
 *
 * @author OUBAH-ROCHE
 */
public class Telephone implements GestionCommunication {

    private boolean estLibre = false;
    private boolean estAllume = false;
    private final BoiteSMS boiteSMS;
    private AbonneOperateur abonneOperateur;

    /**
     *
     * @param abonneOperateur
     * @param boiteSMS
     */
    public Telephone(AbonneOperateur abonneOperateur, BoiteSMS boiteSMS) {
        this.boiteSMS = boiteSMS;
        this.abonneOperateur = abonneOperateur;
    }

    //------------------------------------------------------------------------
    // méthodes de l'interface GestionCommunication
    //------------------------------------------------------------------------
    /**
     *
     * @param numero
     * @param msgVocalSiOccupe
     * @param dateDebut
     * @return
     */
    @Override
    public boolean appeler(String numero, String msgVocalSiOccupe, Date dateDebut) {
        assert getEstAllume() && getLibre() : "votre téléphone n'est pas allumé ou déjà en appel";
        if (this.abonneOperateur.getOperateur().etablirCommunication(this.abonneOperateur, numero, msgVocalSiOccupe, dateDebut)) {
            estLibre = false;
            return true;
        } else {
            estLibre = true;
            return false;
        }
    }

    /**
     *
     * @param numero
     * @param sms
     * @param dateSMS
     */
    @Override
    public void envoyerSMS(String numero, String sms, Date dateSMS) {
        assert getEstAllume() : "téléphone pas allumé";
        this.abonneOperateur.envoyerSMS(numero, sms, dateSMS);
    }

    /**
     *
     * @param message
     */
    @Override
    public void recevoirSMS(MessageSMS message) {
        assert getEstAllume() : "téléphone pas allumé";
        System.out.println("Message de " + message.getAppelant().getNumero() + " " + ((CommSMS) message.getCommMsg()).getMessage());
    }

    /**
     *
     * @param numeroAppelant
     * @return
     */
    @Override
    public boolean accepterAppel(String numeroAppelant) {
        assert getEstAllume() : "téléphone pas allumé";
        if (!this.estLibre) {
            return false;
        }
        System.out.println("\nAppel de la part de " + numeroAppelant + ". Saisir oui pour décrocher, non pour raccrocher :");

        if (new Scanner(System.in).nextLine().toUpperCase().equals("OUI")) {
            this.estLibre = false;
            return true;
        } else {
            this.estLibre = true;
            return false;
        }

    }

    /**
     *
     * @param fin
     */
    @Override
    public void cloreAppel(Date fin) {
        assert getEstAllume() : "téléphone pas allumé";
        if (!getLibre()) {
            this.abonneOperateur.cloreAppel(fin);
            this.estLibre = true;
        } else {
            System.out.println("Vous n'étiez pas en appel, il n'y a pas d'appel à cloturer");
        }
    }

    //------------------------------------------------------------------------
    // méthodes propres
    //------------------------------------------------------------------------
    public void allumer() {
        if (!getEstAllume()) {
            System.out.println("Téléphone de " + abonneOperateur.getNomAbonne() + " allumé");
            estAllume = true;
            estLibre = true;
            this.abonneOperateur.synchroniser();
        }
    }

    public void eteindre() {
        estAllume = false;
        estLibre = false;
    }

    public boolean getEstAllume() {
        return this.estAllume;
    }

    public boolean getLibre() {
        return this.estLibre;
    }

    public AbonneOperateur getAbonneOperateur() {
        return abonneOperateur;
    }

    public void setAbonneOperateur(AbonneOperateur abonneOperateur) {
        this.abonneOperateur = abonneOperateur;
    }

    public void consulterBoiteSMS() {
        for (int i = this.boiteSMS.getListeSMS().size() - 1; i >= 0; i--) {
            recevoirSMS(this.boiteSMS.getListeSMS().get(i));
        }
        this.boiteSMS.getListeSMS().clear();
    }

    public BoiteSMS getBoiteSMS() {
        return boiteSMS;
    }

} // Telephone

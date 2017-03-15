package facturation;

import communication.AbstractCommunication;
import communication.Appel;
import communication.CommSMS;
import java.util.ArrayList;
import messagerie.AbonneOperateur;

/**
 * Classe permettant de réaliser la facturation
 * @author OUBAH-ROCHE
 */
public class Facture {

    private final AbonneOperateur abonneOperateur;
    private final float prix;
    private static int cpt = 0;//nombre de facture
    private final int numeroFacture;

    public Facture(AbonneOperateur abonneOperateur) {
        numeroFacture = ++cpt;
        this.abonneOperateur = abonneOperateur;
        prix = etablirFacturation();
    }

    @Override
    public String toString() {
        return "Facture Numero " + numeroFacture + " {" + abonneOperateur + ", prix=" + String.format("%.2f", prix) + "€" + '}';
    }

    /**
     * Méthode pour établir la facturation
     * @return float montant facture
     */
    private float etablirFacturation() {
        float res = 0;
        int smsEnvoyes = 0;//on compte le nombre de sms envoyés
        long minutecommunication = 0;//on compte le nombre de minutes de communicatiob
        //le nombre de message consulté n'a pas besoin d'être compté car on a une méthode dans la classe BoiteVocale pour cela
        
        //instruction permettant d'obtenir la liste des communications de l'abonné
        ArrayList<AbstractCommunication> list = abonneOperateur.getOperateur().getLesCommunications().
                get(abonneOperateur.getNumeroTelephone().getNumero());
        
        if (abonneOperateur.getForfait() instanceof ForfaitIllimite) {
            return 40f;//on retourne 40 s'il a le forfait illimté
        } else if (abonneOperateur.getForfait() instanceof Forfait1H) {
            for (int i = 0; i < list.size(); i++) {
                //on compte le nombre de sms et de minute de communication
                if (list.get(i) instanceof CommSMS && list.get(i).getAppelant().getNumero() == abonneOperateur.getNumeroTelephone().getNumero()) {
                    smsEnvoyes++;
                } else if (list.get(i) instanceof Appel && list.get(i).getAppelant().getNumero() == abonneOperateur.getNumeroTelephone().getNumero()) {
                    Appel appel = (Appel) list.get(i);
                    if (appel.getFinComm() != null) {
                        //cette formule permet d'obtenir en minute le nombre d'un appel
                        //on ne facture pas un appel en cours tant qu'il n'est pas terminé, d'où le if(appel.getFinComm() != null)
                        minutecommunication += (appel.getFinComm().getTime() - appel.getDebutComm().getTime()) / 60000;
                    }
                }
            }
            System.out.println("sms envoyés :" + smsEnvoyes + " minute de communication :" + minutecommunication+" consultations boite vocale:"+abonneOperateur.getBoiteVocale().getNbmessageConsulte());
            if (minutecommunication > 60) {
                return (float) smsEnvoyes * abonneOperateur.getForfait().getPrixSMS()
                        + (minutecommunication - 60) * abonneOperateur.getForfait().getPrixMinuteCommunication()
                        + abonneOperateur.getBoiteVocale().getNbmessageConsulte() * abonneOperateur.getForfait().getPrixConsultationBoiteVocale();
            } else {
                return (float) smsEnvoyes * abonneOperateur.getForfait().getPrixSMS()
                        + abonneOperateur.getBoiteVocale().getNbmessageConsulte() * abonneOperateur.getForfait().getPrixConsultationBoiteVocale();
            }

        } else if (abonneOperateur.getForfait() instanceof ForfaitActe) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof CommSMS && list.get(i).getAppelant().getNumero().equals(abonneOperateur.getNumeroTelephone().getNumero())) {
                    smsEnvoyes++;
                } else if (list.get(i) instanceof Appel && list.get(i).getAppelant().getNumero().equals(abonneOperateur.getNumeroTelephone().getNumero())) {
                    Appel appel = (Appel) list.get(i);
                    minutecommunication += (appel.getFinComm().getTime() - appel.getDebutComm().getTime()) / 60000;
                }
            }
            System.out.println("sms envoyés :" + smsEnvoyes + " minute de communication :" + minutecommunication+" consultations boite vocale:"+abonneOperateur.getBoiteVocale().getNbmessageConsulte());
            return (float) smsEnvoyes * abonneOperateur.getForfait().getPrixSMS()
                    + minutecommunication * abonneOperateur.getForfait().getPrixMinuteCommunication()
                    + abonneOperateur.getBoiteVocale().getNbmessageConsulte() * abonneOperateur.getForfait().getPrixConsultationBoiteVocale();
        }
        return res;
    }
}

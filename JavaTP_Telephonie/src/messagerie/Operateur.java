package messagerie;

import communication.AbstractCommunication;
import communication.Appel;
import communication.CommMessageVocal;
import communication.CommSMS;
import communication.MessageSMS;
import java.util.Date;
import java.util.Map;

import communication.MessageVocal;
import facturation.Forfait;
import facturation.Forfait1H;
import facturation.ForfaitIllimite;
import facturation.NumeroTelephone;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Un Opérateur gère des abonnés et des communications
 *
 * @author OUBAH-ROCHE
 */
public class Operateur {

    private final String nom;//nom de l'opérateur
    private final Map<String, AbonneOperateur> lesTelephones;
    private final Map<String, ArrayList<AbstractCommunication>> lesCommunications;

    public Operateur(String nom) {
        this.nom = nom;
        lesTelephones = new HashMap<String, AbonneOperateur>();//on utilise une hashmap pour associer un numéro à un abonné car l'accès est rapide
        lesCommunications = new HashMap<String, ArrayList<AbstractCommunication>>();//on utilise une table de hachage car c'est intéressant pour stocker de nombreuses valeurs
        ListOperateur.ajouterOperateur(this);//ajout de l'opérateur à la liste des opérateurs
    }

    @Override
    public String toString() {
        return nom;
    }

    /**
     * Une personne souscrit un abonnement et reçoit un téléphone
     *
     * @param nomPersonne
     * @param nomForfait
     * @return
     */
    public Telephone souscrire(String nomPersonne, String nomForfait) {
        Telephone tel = new Telephone(null, new BoiteSMS());//on crée le téléphone sans l'attribue à l'abonné qui n'existe pas encore
        Forfait f;
        //on crée le forfait
        if (nomForfait.equals("1h")) {
            f = new Forfait1H(20f, 0.07f, 0.15f, 0.07f);
        } else if (nomForfait.equals("acte")) {
            f = new Forfait1H(0, 0.07f, 0.15f, 0.07f);
        } else {
            f = new ForfaitIllimite(40, 0, 0, 0);
        }

        //on crée l'abonné
        AbonneOperateur abonneOperateur = new AbonneOperateur(this, tel, nomPersonne,
                new BoiteSMS(),
                new BoiteVocale(),
                f
        );

        tel.setAbonneOperateur(abonneOperateur);//on attribue le téléphone à l'abonné
        lesTelephones.put(abonneOperateur.getNumeroTelephone().getNumero(), abonneOperateur);//on ajoute l'abonné à l'arbre des téléphones avec pour clé son num de tel
        lesCommunications.put(abonneOperateur.getNumeroTelephone().getNumero(), new ArrayList<AbstractCommunication>());//on ajoute à la table de hachage la liste des communications vide avec pour clé le numéro de l'abonné
        return tel;
    }

    /**
     * Établir une communication
     *
     * @param emetteur
     * @param numeroDestinataire
     * @param msgVocal : message en cas d'indisponibilité
     * @param dateAppel
     * @return vrai si la communication a été établie
     */
    public boolean etablirCommunication(AbonneOperateur emetteur, String numeroDestinataire, String msgVocal, Date dateAppel) {
        assert ListOperateur.numeroExistant(numeroDestinataire) != null : "numéro inconnu";
        //on récupère le téléphone du destinataire car il existe
        Telephone destinataire = ListOperateur.numeroExistant(numeroDestinataire).getTelephone();
        if (destinataire.getLibre() && destinataire.getEstAllume()) {
            //on ajoute aux communications de l'émetteur un appel avec pour date de fin null
            lesCommunications.get(emetteur.getNumeroTelephone().getNumero()).add(new Appel(new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel, null));
            //on ajoute aux communications du destinataire un appel avec pour date de fin null, elle sera changé dans la méthode cloreAppel
            ListOperateur.getLesCommunicationsDunOperateur(numeroDestinataire).get(numeroDestinataire).add(new Appel(new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel, null));
            if (destinataire.accepterAppel(emetteur.getNumeroTelephone().getNumero())) {//on demande au destinataire d'accepter ou non l'appel
                System.out.println("Appel en cours entre " + emetteur.getNumeroTelephone().getNumero() + " et " + numeroDestinataire);
                return true;
            } else {//s'il refuse, on lui envoie un message vocal
                //on crée le mssage
                MessageVocal msg = new MessageVocal(new CommMessageVocal(msgVocal, new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel));
                //on récupère l'abonné
                //AbonneOperateur abonne = ListOperateur.numeroExistant(numeroDestinataire);
                AbonneOperateur abonne = destinataire.getAbonneOperateur();
                //on ajoute le message à sa boite vocale
                abonne.getBoiteVocale().ajouteBoiteVocaleTelephone(msg);
                //on clore l'appel pour l'appelant
                cloreAppel(emetteur, dateAppel);
                //on ajoute le message vocal aux communications de l'émetteur
                lesCommunications.get(emetteur.getNumeroTelephone().getNumero()).add(new CommMessageVocal(msgVocal, new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel));
                //si le téléphone du destinaire est allumé, on synchronise
                if (destinataire.getEstAllume()) {
                    abonne.synchroniser();
                }
                System.out.println("Le numéro " + numeroDestinataire + " est hors ligne ou occupé, un message sera laissé dans sa boite vocale : " + msgVocal);
                //on clore l'appel pour le destinataire
                abonne.cloreAppel(dateAppel);
                return false;
            }
        } else {
            //ici on envoie un message vocal directement car le destinataire n'est pas disponible
            //on ajoute les appels aux communications des deux personnes, appels qui auront la même durée de début et de fin
            lesCommunications.get(emetteur.getNumeroTelephone().getNumero()).add(new Appel(new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel, null));
            ListOperateur.getLesCommunicationsDunOperateur(numeroDestinataire).get(numeroDestinataire).add(new Appel(new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel, null));
            MessageVocal msg = new MessageVocal(new CommMessageVocal(msgVocal, new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel));
            //AbonneOperateur abonne = ListOperateur.numeroExistant(numeroDestinataire);
            AbonneOperateur abonne = destinataire.getAbonneOperateur();
            abonne.getBoiteVocale().ajouteBoiteVocaleTelephone(msg);
            cloreAppel(emetteur, dateAppel);
            lesCommunications.get(emetteur.getNumeroTelephone().getNumero()).add(new CommMessageVocal(msgVocal, new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateAppel));
            System.out.println("Le numéro " + numeroDestinataire + " est hors ligne ou occupé, un message sera laissé dans sa boite vocale : " + msgVocal);
            abonne.cloreAppel(dateAppel);
            //si le téléphone du destinaire est allumé, on synchronise
            if (destinataire.getEstAllume()) {
                abonne.synchroniser();
            }
            return false;
        }
    }

    /**
     * poster un SMS
     *
     * @param emetteur
     * @param numeroDestinataire
     * @param sms : le texte du SMS
     * @param dateEnvoi
     */
    public void posterSMS(AbonneOperateur emetteur, String numeroDestinataire, String sms, Date dateEnvoi) {
        //on instancie le sms
        MessageSMS msg = new MessageSMS(new CommSMS(sms, new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateEnvoi));
        AbonneOperateur abonne = ListOperateur.numeroExistant(numeroDestinataire);//on récupère l'abonné destinataire
        if (abonne != null) {
            abonne.getBoiteSMS().ajouteBoiteSMSTelephone(msg);//on ajoute le message à la boiteSMS du destinataire
            //on ajoute le sms aux communications de l'emetteur et du récepteur
            lesCommunications.get(emetteur.getNumeroTelephone().getNumero()).add(new CommSMS(sms, new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateEnvoi));
            ListOperateur.getLesCommunicationsDunOperateur(numeroDestinataire).get(numeroDestinataire).add(new CommSMS(sms, new NumeroTelephone(numeroDestinataire), emetteur.getNumeroTelephone(), dateEnvoi));
            if (abonne.getTelephone().getEstAllume()) {
                abonne.synchroniser();
            }
            System.out.println("SMS envoyé de " + emetteur.getNumeroTelephone().getNumero() + " à " + numeroDestinataire + " : " + sms);
        } else {
            System.out.println("Ce numéro n'existe pas");
        }
    }

    /**
     * un abonné met fin à une communication
     *
     * @param abonne : celui qui clôt
     * @param fin date de fin de communication
     */
    public void cloreAppel(AbonneOperateur abonne, Date fin) {
        int size = lesCommunications.get(abonne.getNumeroTelephone().getNumero()).size();//on récupère la taille de la liste des communications pour l'abonné
        ((Appel) lesCommunications.get(abonne.getNumeroTelephone().getNumero()).get(size - 1)).setFinComm(fin);//on modifie la date de fin qui auparavent était fixée à null

        //on cloture l'appel pour l'appelant ou le destinataire en fonction du numéro qui a appelé
        String numeroAppele = lesCommunications.get(abonne.getNumeroTelephone().getNumero()).get(size - 1).getAppele().getNumero();
        String numeroAppelant = lesCommunications.get(abonne.getNumeroTelephone().getNumero()).get(size - 1).getAppelant().getNumero();
        size = ListOperateur.getLesCommunicationsDunOperateur(numeroAppele).get(numeroAppele).size();
        if (!(ListOperateur.getLesCommunicationsDunOperateur(numeroAppele).get(numeroAppele).get(size - 1) instanceof CommMessageVocal)) {
            ((Appel) ListOperateur.getLesCommunicationsDunOperateur(numeroAppele).get(numeroAppele).get(size - 1)).setFinComm(fin);
        }
        size = ListOperateur.getLesCommunicationsDunOperateur(numeroAppelant).get(numeroAppelant).size();
        if (!(ListOperateur.getLesCommunicationsDunOperateur(numeroAppelant).get(numeroAppelant).get(size - 1) instanceof CommMessageVocal)) {
            ((Appel) ListOperateur.getLesCommunicationsDunOperateur(numeroAppelant).get(numeroAppelant).get(size - 1)).setFinComm(fin);
        }

        if (!abonne.getNumeroTelephone().getNumero().equals(numeroAppele)) {
            System.out.println("Fin de l'appel entre " + abonne.getNumeroTelephone().getNumero() + " et " + numeroAppele);
        }
    }

    public Map<String, AbonneOperateur> getLesTelephones() {
        return lesTelephones;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Méthode permettant d'afficher l'historique des communications d'un abonné
     *
     * @param abonneOperateur
     */
    public void getHistorique(AbonneOperateur abonneOperateur) {
        System.out.println("\n--------------------------------------------");
        System.out.println("HISTORIQUE DES COMMUNICATIONS POUR " + abonneOperateur.getNomAbonne() + " Numéro : " + abonneOperateur.getNumeroTelephone().getNumero());
        //on récupère la liste des communications puis on va la parcourrir
        ArrayList<AbstractCommunication> list = ListOperateur.getLesCommunicationsDunOperateur(abonneOperateur.getNumeroTelephone().getNumero()).get(abonneOperateur.getNumeroTelephone().getNumero());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof CommMessageVocal) {
                if (list.get(i).getAppelant().getNumero() == abonneOperateur.getNumeroTelephone().getNumero()) {
                    //si messagevocal envoyé
                    System.out.println("ENVOI - Message vocal le "
                            + list.get(i).getDebutComm() + " : "
                            + ((CommMessageVocal) list.get(i)).getMessage()
                            + " -- from " + list.get(i).getAppelant().getNumero()
                            + " -- to " + list.get(i).getAppele().getNumero());
                } else {
                    //si messagevocal recu
                    System.out.println("RECEPTION - Message vocal le "
                            + list.get(i).getDebutComm() + " : "
                            + ((CommMessageVocal) list.get(i)).getMessage()
                            + " -- from " + list.get(i).getAppelant().getNumero()
                            + " -- to " + list.get(i).getAppele().getNumero());
                }
            } else if (list.get(i) instanceof CommSMS) {
                if (list.get(i).getAppelant().getNumero() == abonneOperateur.getNumeroTelephone().getNumero()) {
                    System.out.println("ENVOI SMS - le "
                            + list.get(i).getDebutComm() + " : "
                            + ((CommSMS) list.get(i)).getMessage()
                            + " -- from " + list.get(i).getAppelant().getNumero()
                            + " -- to " + list.get(i).getAppele().getNumero());
                } else {
                    System.out.println("RECEPTION SMS - le "
                            + list.get(i).getDebutComm() + " : "
                            + ((CommSMS) list.get(i)).getMessage()
                            + " -- from " + list.get(i).getAppelant().getNumero()
                            + " -- to " + list.get(i).getAppele().getNumero());
                }
            } else if (list.get(i) instanceof Appel) {
                //si la date de fin != null, l'appel est en terminé
                if (((Appel) list.get(i)).getFinComm() != null) {
                    if (list.get(i).getAppelant().getNumero() == abonneOperateur.getNumeroTelephone().getNumero()) {
                        System.out.println("Appel émis débutant le "
                                + list.get(i).getDebutComm() + " "
                                + "se terminant le " + ((Appel) list.get(i)).getFinComm()
                                + " -- from " + list.get(i).getAppelant().getNumero()
                                + " -- to " + list.get(i).getAppele().getNumero());
                    } else {
                        System.out.println("Appel recu débutant le "
                                + list.get(i).getDebutComm() + " "
                                + "se terminant le " + ((Appel) list.get(i)).getFinComm()
                                + " -- from " + list.get(i).getAppelant().getNumero()
                                + " -- to " + list.get(i).getAppele().getNumero());
                    }
                } else {//sinon il est en cours et non facturé
                    if (list.get(i).getAppelant().getNumero() == abonneOperateur.getNumeroTelephone().getNumero()) {
                        System.out.println("/!\\NON FACTURE Appel émis et EN COURS débutant le "
                                + list.get(i).getDebutComm() + " "
                                + " -- from " + list.get(i).getAppelant().getNumero()
                                + " -- to " + list.get(i).getAppele().getNumero());
                    } else {
                        System.out.println("Appel recu et en cours débutant le "
                                + list.get(i).getDebutComm() + " "
                                + " -- from " + list.get(i).getAppelant().getNumero()
                                + " -- to " + list.get(i).getAppele().getNumero());
                    }
                }
            }
        }
        System.out.println("--------------------------------------------");
    }

    public Map<String, ArrayList<AbstractCommunication>> getLesCommunications() {
        return lesCommunications;
    }

    /**
     * Méthode pour afficher les abonnés d'un opérateur
     */
    public void afficheLesAbonnes() {
        for (Map.Entry<String, AbonneOperateur> abonne : lesTelephones.entrySet()) {
            System.out.println(abonne.getValue());
        }
    }
} // Operateur

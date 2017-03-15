package facturation;

/**
 * Classe pour affecter les numéros de téléphone aux abonnés de manière automatique et croissante
 * @author OUBAH-ROCHE
 */
public class NumeroTelephone {

    private String numero;

    public NumeroTelephone(int cpt) {
        cpt++;
        numero = "+33(0)7";
        //boucle pour générer un numéro par ordre croissant
        for (int i = new String("" + cpt).length(); i < 8; i++) {
            numero += "0";
        }
        numero += cpt;
    }

    public NumeroTelephone(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

}

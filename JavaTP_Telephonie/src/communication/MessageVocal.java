package communication;

/**
 * Classe pour instancier un MessageVocal
 * @author OUBAH-ROCHE
 */
public class MessageVocal extends AbstractMessage {

    private boolean consulte = false;

    public MessageVocal(AbstractCommMessage abstractCommMessage) {
        super(abstractCommMessage);
    }

    public boolean isConsulte() {
        return consulte;
    }

    public void setConsulte(boolean consulte) {
        this.consulte = consulte;
    }

}

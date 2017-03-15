package communication;

import facturation.NumeroTelephone;

public abstract class AbstractMessage {

    private AbstractCommMessage commMsg;

    public AbstractMessage(AbstractCommMessage commMsg) {
        this.commMsg = commMsg;
    }

    public AbstractCommMessage getCommMsg() {
        return commMsg;
    }

    public void setCommMsg(AbstractCommMessage commMsg) {
        this.commMsg = commMsg;
    }

    public NumeroTelephone getAppelant() {
        return this.commMsg.getAppelant();
    }

    public NumeroTelephone getAppele() {
        return this.commMsg.getAppele();
    }

}

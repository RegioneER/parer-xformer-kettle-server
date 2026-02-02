package it.eng.parer.kettle.model;

public class EsitoStatusTrasformazione extends AbstractEsito {
    public StatoTrasformazione getStatoTrasformazione() {
        return statoTrasformazione;
    }

    public void setStatoTrasformazione(StatoTrasformazione statoTrasformazione) {
        this.statoTrasformazione = statoTrasformazione;
    }

    private StatoTrasformazione statoTrasformazione;
}

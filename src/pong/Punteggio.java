// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 1: il Punteggio.
package pong;

import java.util.ArrayList;
import java.util.List;

public class Punteggio {

    private final int puntiPerVincere;
    private final int scartoMinimo; 
    private int puntiDestra;
    private int puntiSinistra;
    private final List<Lato> storia = new ArrayList<>();
    private boolean finita;
    private Lato vincitore;

    boolean repOK() {
        if (puntiPerVincere < 1 || scartoMinimo < 1) return false;
        if (puntiDestra < 0 || puntiSinistra < 0) return false;
        if (storia == null) return false;

        int contaDestra = 0;
        int contaSinistra = 0;
        for (Lato l : storia) {
            if (l == null) return false;
            if (l == Lato.DESTRA) contaDestra++;
            else if (l == Lato.SINISTRA) contaSinistra++;
        }
        if (puntiDestra != contaDestra || puntiSinistra != contaSinistra) return false;

        boolean condVittoriaDestra = (puntiDestra >= puntiPerVincere) && ((puntiDestra - puntiSinistra) >= scartoMinimo);
        boolean condVittoriaSinistra = (puntiSinistra >= puntiPerVincere) && ((puntiSinistra - puntiDestra) >= scartoMinimo);

        if (finita) {
            if (vincitore == null) return false;
            if (vincitore == Lato.DESTRA && !condVittoriaDestra) return false;
            if (vincitore == Lato.SINISTRA && !condVittoriaSinistra) return false;
        } else {
            if (vincitore != null) return false;
            if (condVittoriaDestra || condVittoriaSinistra) return false;
        }

        return true;
    }

    public Punteggio(int puntiPerVincere, int scartoMinimo) {
        if (puntiPerVincere < 1 || scartoMinimo < 1) {
            throw new IllegalArgumentException("puntiPerVincere e scartoMinimo devono essere >= 1");
        }

        this.puntiPerVincere = puntiPerVincere;
        this.scartoMinimo = scartoMinimo;
        this.puntiDestra = 0;
        this.puntiSinistra = 0;
        this.finita = false;
        this.vincitore = null;
    }

    public void segnaPunto(Lato chi) {
        if (chi == null) {
            throw new IllegalArgumentException("Il lato 'chi' non può essere null");
        }

        if (!finita) {
            if (chi == Lato.DESTRA) {
                puntiDestra++;
            } else {
                puntiSinistra++;
            }
            storia.add(chi);
            aggiornaStatoVittoria();
        }
    }

    public int getPunti(Lato chi) {
        if (chi == null) {
            throw new IllegalArgumentException("Il lato 'chi' non può essere null");
        }

        return (chi == Lato.DESTRA) ? puntiDestra : puntiSinistra;
    }

    public boolean isFinita() {
        return finita;
    }

    public Lato getVincitore() {
        return vincitore;
    }

    public void annullaUltimoPunto() {
        if (!storia.isEmpty()) {
            Lato ultimo = storia.remove(storia.size() - 1);

            if (ultimo == Lato.DESTRA) {
                puntiDestra--;
            } else {
                puntiSinistra--;
            }

            aggiornaStatoVittoria();
        }
    }

    public List<Lato> getStoria() {
        return List.copyOf(storia);
    }

    public void azzera() {
        puntiDestra = 0;
        puntiSinistra = 0;
        vincitore = null;
        finita = false;
        storia.clear();
    }

    private void aggiornaStatoVittoria() {
        if (puntiDestra >= puntiPerVincere && (puntiDestra - puntiSinistra) >= scartoMinimo) {
            finita = true;
            vincitore = Lato.DESTRA;
        } else if (puntiSinistra >= puntiPerVincere && (puntiSinistra - puntiDestra) >= scartoMinimo) {
            finita = true;
            vincitore = Lato.SINISTRA;
        } else {
            finita = false;
            vincitore = null;
        }
    }
}
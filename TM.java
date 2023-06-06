import java.util.ArrayList;
import java.util.List;


public class TM {

    private int anzahlZustaende;
    private int anzahlBandalphabet;
    private List<Integer> akzeptierteZustaende;
    private Konfiguration[][] uebergangsfunktion;
    private List<Character> x = new ArrayList<Character>();
    /*
     * (1) Zustand, indem sich die TM aktuell befindet (q1 ist immer
     *     Startzustand)
     * (2) Aktuelle Position des Lese- und Schreibkopfs der TM
     */
    private int zustand = 1;
    private int position = 0;

    /**
     * Konstruiere eine Turing-Maschine
     *
     * M = (Q, Sigma, Gamma, Delta, q1, F)
     *
     *		- Sigma = Gamma\{Blanksymbol}
     *
     * @param anzahlZustaende ( |Q| )
     * @param anzahlBandalphabet ( |Gamma| )
     * @param akzeptierteZustaende Dekodierte, akzeptierte Zustände ( F )
     * @param uebergangsfunktion ( Delta )
     */
    public TM(int anzahlZustaende, int anzahlBandalphabet, List<Integer> akzeptierteZustaende, Konfiguration[][] uebergangsfunktion)
    {
        this.anzahlZustaende = anzahlZustaende;
        this.anzahlBandalphabet = anzahlBandalphabet;
        this.akzeptierteZustaende = akzeptierteZustaende;
        this.uebergangsfunktion = uebergangsfunktion;
    }

    /**
     * Turing-Maschine führt die Berechnung für die Eingabe x durch
     *
     * @param x binärer Eingabestring
     * @return boolean Erfolgreiche Berechnung
     */
    public Boolean berechnung(String x)
    {
        // Eingabe x einlesen
        for(int i=0; i<x.length(); i++) {
            this.x.add(x.charAt(i));
        }

        try {
            while( !this.akzeptierteZustaende.contains(this.zustand) ) {

                /*
                 * Befindet sich der Lese- und Schreibkopf vor oder hinter dem
                 * Eingabewort x, füge dort das Blanksymbol hinzu, da das Band
                 * nicht von vornherein mit diesen belegt ist.
                 */
                if(this.position<0 || this.position>=this.x.size()) {
                    schreibeSymbolAufBand('_');
                }
                // Neue Konfiguration
                Konfiguration konfiguration = this.uebergangsfunktion[this.zustand-1][konvertiereSymbolZuKodierterLaenge( this.x.get(this.position) )-1];
                // Berechnung ausgeben
                //System.out.println("Delta(" + this.zustand + ", " + this.x.get(this.position) + ") = " + konfiguration + "; Band:" + this.x.toString());

                // Ist der aktuelle Zustand nicht definiert, halte an
                if(konfiguration == null) return false;

                schreibeSymbolAufBand(konfiguration.getSymbol());

                this.zustand = konfiguration.getZustand();
                this.position += konfiguration.getWertKopfbewegung();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gibt die Länge der Kodierung des übergebenen Symbols zurück.
     *
     * @param s Gelesenes Symbol
     * @return Länge der Kodierung des Symbols
     */
    private int konvertiereSymbolZuKodierterLaenge(Character s) {
        if(s=='0') {
            return 1;	// Kodierung: 0
        } else if(s=='1') {
            return 2;	// Kodierung: 00
        } else {
            return 3;	// Kodierung: 000 (Blanksymbol)
        }
    }

    /**
     * Schreibt das Symbol s auf das Band
     *
     * @param s Symbol
     * @throws Exception
     */
    private void schreibeSymbolAufBand(char s) throws Exception
    {
        // Symbol am Anfang einfügen, d.h. vor dem ersten Element
        if(this.position<0) {
            List<Character> tempX = this.x;
            this.x.clear();
            this.x.add(s);
            this.x.addAll(tempX);
            this.position = 0;
        }
        // Symbol am Ende anfügen
        else if(this.position>=this.x.size()) {
            this.x.add(s);
        }
        // Symbol ersetzen
        else {
            this.x.set(this.position, s);
        }
    }

    /**
     * Turing-Maschine als String ausgeben
     */
    public String toString()
    {
        return "M = (Q, Sigma, Gamma, Delta, q1, F)"
                + "\n    |Q| =" + this.anzahlZustaende
                + "\n    |Gamma| =" + this.anzahlBandalphabet
                + "\n    F =" + java.util.Arrays.toString( this.akzeptierteZustaende.toArray() )
                + "\n    Delta [Zustände][Symbole]:"
                + "\n    " + java.util.Arrays.deepToString(this.uebergangsfunktion)
                + "\n";
    }
}
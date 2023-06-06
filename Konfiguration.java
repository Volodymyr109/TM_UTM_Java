public class Konfiguration {

    private int zustand;
    private Character symbol;
    private Character kopfbewegung;

    /**
     * Konstruiere eine Konfiguration
     *
     * k = (q, alpha, beta)
     *
     * 		- q Element Zustände
     * 		- alpha Element Bandalphabet
     * 		- beta Element {l, r, 0}
     *
     * @param zustand Dekodierter Zustand
     * @param symbol Kodiertes Symbol
     * @param kopfbewegung Kodierte Kopfbewegung
     */
    public Konfiguration(int zustand, String symbol, String kopfbewegung)
    {
        this.zustand		= zustand;
        this.symbol		= dekodiereSymbol(symbol);
        this.kopfbewegung	= dekodiereKopfbewegung(kopfbewegung);
    }

    /**
     * Gibt den Zustand zurück
     *
     * @return Dekodierter Zustand
     */
    public Integer getZustand()
    {
        return this.zustand;
    }

    /**
     * Gibt das Symbol zurück
     *
     * @return Dekodiertes Symbol
     */
    public Character getSymbol()
    {
        return this.symbol;
    }

    /**
     * Gibt den Wert der Kopfbewegung zurück
     *
     * @return Wert der Kopfbewegung
     */
    public Integer getWertKopfbewegung()
    {
        if(this.kopfbewegung == 'l') {
            return -1;
        } else if(this.kopfbewegung == 'r') {
            return +1;
        } else {
            return 0;
        }
    }

    /**
     * Gibt die dekodierte Kopfbewegung zurück
     *
     * @param s Kodierte Kopfbewegung
     * @return Dekodierte Kopfbewegung
     */
    private Character dekodiereKopfbewegung(String s)
    {
        if(s.length() == 1) {		// Kodierung: 0
            return 'l';
        } else if(s.length() == 2) {	// Kodierung; 00
            return 'r';
        } else {			// Kodierung: 000
            return '0';
        }
    }

    /**
     * Gibt das dekodierte Symbol zurück
     *
     * @param s Kodierte Symbol
     * @return Dekodiertes Symbol
     */
    private char dekodiereSymbol(String s)
    {
        if(s.length() == 1) {		// Kodierung: 0
            return '0';
        } else if(s.length() == 2) {	// Kodierung: 00
            return '1';
        } else {
            return '_';		// Kodierung: 000 (Blanksymbol)
        }
    }

    /**
     * Gibt die Konfiguration als String aus
     */
    public String toString()
    {
        return "(" + this.getZustand() + ", " + this.getSymbol() + ", " + this.kopfbewegung + ")";
    }
}
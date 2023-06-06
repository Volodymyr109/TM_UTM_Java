import java.util.ArrayList;
import java.util.List;

/**
 * Universelle Turingmaschine
 *
 * Version 1.1
 */
public class UTM {

    /**
     * Main Methode
     *
     * @param args w#x mit w binäre Kodierung einer TM und x binäre Eingabe
     */
    public static void main(String[] args)
    {
        try {
            // Eingabe in w und x zerlegen
            String[] eingabe = args[0].split("#");
            String w = eingabe[0];
            String x = ""; // Entspricht: Lambda
            if(eingabe.length > 1) {
                x = eingabe[1];
            }
            // Turing-Maschine M_w
            TM Mw = konvertiereKodierungZuTm(w);
            //System.out.println(Mw);
            // Berechnung und Ausgabe
            if( Mw.berechnung(x) ) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Dekodiert die binäre Kodierung einer Turing-Maschine und gibt die
     * entsprechende Turing-Maschine zurück
     *
     * Kodierung
     * anzahlZustaende 1 anzahlBandalphabet 1 anzahlAkzeptierteZustaende 1 [akzeptierteZustaende] [Transitionen]
     *
     * @param w	Kodierung einer Turing-Maschine
     * @return Turing-Maschine
     * @throws Exception
     */
    private static TM konvertiereKodierungZuTm(String w) throws Exception
    {
        List<String> list = zerlegeKodierung(w);

        List<Integer> akzeptierteZustaende = new ArrayList<Integer>();
        Konfiguration[][] uebergangsfunktion;

        /*
         * Kardinalität (1) der Zustände, (2) des Bandalphabets und (3) der
         * akzeptieren Zustände dekodieren
         */
        int anzahlZustaende = list.get(0).length();
        int anzahlBandalphabet = list.get(1).length();
        int anzahlAkzeptierteZustaende = list.get(2).length();
        /*
         * Die akzeptierten Zustände dekodieren
         */
        for(int i=0; i<anzahlAkzeptierteZustaende; i++) {
            int zustandNummer = list.get(3+i).length();
            akzeptierteZustaende.add( zustandNummer );
        }
        /*
         * Transitionen dekodieren
         *
         * Sigma(q, a) = (p, b, e)
         * Kodierung: 0^q10^b10^p10^b10^e1
         *
         * 		- q: Zustand Element Q
         * 		- a: Symbol Element Gamma
         * 		- p: Zustand Element Q
         * 		- b: Symbol Element Gamma
         * 		- e: Kopfbewegung Element {l, r, 0}
         */
        uebergangsfunktion = new Konfiguration[anzahlZustaende][anzahlBandalphabet];
        for(int i=3 + anzahlAkzeptierteZustaende; i<list.size(); i++) {
            int q = list.get(i).length();	// Dekodiert
            String a = list.get(++i);		// Kodiert
            int p = list.get(++i).length(); // Dekodiert
            String b = list.get(++i);		// Kodiert
            String k = list.get(++i);		// Kodiert

            uebergangsfunktion[ q-1 ][ a.length()-1 ] = new Konfiguration(p, b, k);
        }

        return new TM(anzahlZustaende, anzahlBandalphabet, akzeptierteZustaende, uebergangsfunktion);
    }

    /**
     * Zerlegt die Kodierung einer Turing-Maschine
     *
     * @param s Kodierung einer Turing-Maschine
     * @return list Zerlegte Kodierung
     * @throws Exception
     */
    private static List<String> zerlegeKodierung(String w) throws Exception
    {
        List<String> list = new ArrayList<String>();
        String temp = "";

        for(int i=0; i<w.length(); i++) {
            if(w.charAt(i) == '1') {
                list.add( temp );
                temp = "";
            } else {
                temp += w.charAt(i);
            }
        }

        return list;
    }
}
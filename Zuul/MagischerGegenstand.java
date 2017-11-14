
/**
 * Beschreiben Sie hier die Klasse MagischerGegenstand.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class MagischerGegenstand extends Gegenstand
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String funktion = "";

    /**
     * Konstruktor für Objekte der Klasse MagischerGegenstand
     */
    public MagischerGegenstand(String beschreibung, int gewicht, String funktion)
    {
        // Instanzvariable initialisieren
        super(beschreibung, gewicht);
        this.funktion = funktion;
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    
    public String toString()
    {
        String toString="Gegenstand: Beschreibung: "+getGegenstandBeschreibung()+" Funktion: "+funktion+ ".\n";
        return toString;
    }
    
    public String zeigeFunktion()
    {
        return funktion;
    }

}

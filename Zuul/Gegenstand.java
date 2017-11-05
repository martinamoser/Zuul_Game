
/**
 * Beschreiben Sie hier die Klasse Gegenstand.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Gegenstand
{
     private String gegenstandBeschreibung;
     private int gewicht;
     

    /**
     * Konstruktor für Objekte der Klasse Gegenstand
     */
    public Gegenstand(String beschreibung, int gewicht)
    {
        gegenstandBeschreibung=beschreibung;
        this.gewicht=gewicht;
    }
    
    public String getGegenstandBeschreibung()
    {
             return gegenstandBeschreibung;
    }

    public int getGewicht()
    {
        return gewicht;
    }
    
    public String toString()
    {
        String toString="Gegenstand: Beschreibung: "+getGegenstandBeschreibung()+" Gewicht: "+getGewicht()+ ".\n";
        return toString;
    }
}

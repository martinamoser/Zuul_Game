import java.util.ArrayList;
import java.util.Stack;
/**
 * Beschreiben Sie hier die Klasse Spieler.
 * Jeder Spieler hat einen Namen und einen aktuellen Raum
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Spieler
{
    private String spielername;
    private int tragkraft;
    private ArrayList<Gegenstand> meineGegenstaende;
    private Raum aktuellerRaum;
    //private Raum ehemaligerRaum;
    private Raum naechsterRaum;
    public Stack<Befehl> undoStack;

    /**
     * Konstruktor für Objekte der Klasse Spieler
     */
    public Spieler(String name, int kraft)
    {
        spielername=name;
        tragkraft=kraft;
        meineGegenstaende= new ArrayList<>();
        aktuellerRaum = null;  // das Spiel startet draussen
        naechsterRaum = null;
        undoStack = new Stack<Befehl>();
        //ehemaligerRaum = draussen; // zurück zurück führt nach draussen
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    
    public Raum gibAktuellenRaum()
    {
        
        return aktuellerRaum;
    }
        public Raum gibNaechstenRaum()
    {
        
        return naechsterRaum;
    }
        public void setzeNaechstenRaum(Raum raum)
    {
        
        naechsterRaum=raum;
    }
    public void setzeAktuellenRaum(Raum raum)
    {
        aktuellerRaum=raum;
    }
    public Stack<Befehl> gibundoStack()
    {
        return undoStack;
    }
    public ArrayList<Gegenstand> gibmeineGegenstaende()
    {
        return meineGegenstaende;
    }
    public void gibListeMeinerGegenstaende()
    {
        String meineListe="";
        for(Gegenstand gegenstand: meineGegenstaende)
        {
            meineListe+=gegenstand;
            
        }
        System.out.println(meineListe); //ruft toString aus Klasse Gegenstand auf
    }
    public String gibNameSpieler()
    {
        return spielername;
    }
    public void gegenstandAufnehmen(Gegenstand gegenstand)
    {   
        meineGegenstaende.add(gegenstand);
        aktuellerRaum.gibGegenstände().remove(gegenstand);
      }
          public void gegenstandAblegen(Gegenstand gegenstand)
    {   
        meineGegenstaende.remove(gegenstand);
        aktuellerRaum.gibGegenstände().add(gegenstand);
      }
    public int gibTragkraft()
     {
         return tragkraft;
     }
     public int gibGewichtListeGegenstände()
     {  
         int gewichtAlleGegenstände=0;
        for (Gegenstand gegenstand: meineGegenstaende)
        {
            gewichtAlleGegenstände += gegenstand.getGewicht();

        }

        return gewichtAlleGegenstände;
     }
     public void dasGesamtgewichtBeträgt()
     {
         System.out.println("Das Gesamtgewicht beträgt: "+gibGewichtListeGegenstände()+ " Einheiten.");
        }

     public boolean take(String gegenstand)
     {  
        ArrayList<Gegenstand> liste = aktuellerRaum.gibGegenstände();
        if(liste.isEmpty())
        {
              System.out.println("In diesem Raum hat es momentan keine Gegenstände, "+spielername +".");
              return false  ;
        }
        
        
        for (Gegenstand g: liste)
        {
            if (g.getGegenstandBeschreibung().equalsIgnoreCase(gegenstand))
            {
                if(tragkraft<gibGewichtListeGegenstände()+g.getGewicht())
                {
                     System.out.println("Der Gegenstand ist zu schwer.");
                     return false;
                }
                
                gegenstandAufnehmen(g);
                if(g.getGegenstandBeschreibung().equalsIgnoreCase("muffin"))
                {
                    System.out.println("Gratulation "+spielername+", du hast das magische Muffin gefunden!");
                }
                return true;  
            }
        }

        System.out.println("Diesen Gegenstand gibt es hier nicht.");   
        return false;
    }
    public boolean drop(String gegenstand)
    {
        if(gibmeineGegenstaende().isEmpty())
        {
            System.out.println(spielername+", du hast im Moment gar keine Gegenstände.");
            return false;
        }
        for (int i=0; i<gibmeineGegenstaende().size(); i++)
        {
                if (gibmeineGegenstaende().get(i).getGegenstandBeschreibung().equalsIgnoreCase(gegenstand))
                {
                  gegenstandAblegen(gibmeineGegenstaende().get(i));
                  return true;
                } 
        }
        {
                    System.out.println("Diesen Gegenstand gibt es nicht.");
                    return false;
        }
    }
            
    public boolean go(String richtung)
    {
         // Wir versuchen, den Raum zu verlassen.
        setzeNaechstenRaum(gibAktuellenRaum().gibAusgang(richtung));

        if (gibNaechstenRaum() == null) {
            System.out.println("Dort ist keine Tür!");
            umsehen();
            return false;
        }

        else {
            setzeAktuellenRaum(gibNaechstenRaum());
            if(aktuellerRaum.gibBeschreibung().equalsIgnoreCase("im Keller"))
            { 
                undoStack.removeAllElements();
                System.out.println("Muhaha, dies war eine Falltüre, du bist nur ewig hier gefangen!");
                return false;
            } else {
            raumInfoAusgeben();
            return true;
            }
        }
            
        }
        public void raumInfoAusgeben()
        {
        System.out.println(gibAktuellenRaum().gibLangeBeschreibung());
        
    }
        public void umsehen()
    {
        System.out.println(gibAktuellenRaum().gibLangeBeschreibung());
    }
}
    

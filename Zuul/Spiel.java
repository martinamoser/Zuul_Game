import java.util.Stack;
/**
 *  Dies ist die Hauptklasse der Anwendung "Die Welt von Zuul".
 *  "Die Welt von Zuul" ist ein sehr einfaches, textbasiertes
 *  Adventure-Game. Ein Spieler kann sich in einer Umgebung bewegen,
 *  mehr nicht. Das Spiel sollte auf jeden Fall ausgebaut werden,
 *  damit es interessanter wird!
 * 
 *  Zum Spielen muss eine Instanz dieser Klasse erzeugt werden und
 *  an ihr die Methode "spielen" aufgerufen werden.
 * 
 *  Diese Instanz erzeugt und initialisiert alle anderen Objekte
 *  der Anwendung: Sie legt alle R�ume und einen Parser an und
 *  startet das Spiel. Sie wertet auch die Befehle aus, die der
 *  Parser liefert, und sorgt f�r ihre Ausf�hrung.
 * 
 * @author  Michael K�lling und David J. Barnes
 * @version 31.07.2011
 */

public class Spiel 
{
    private Parser parser;

    private Spieler spieler1;
    
        
    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public Spiel() 
    {
        raeumeAnlegen();
        parser = new Parser();
        
        
    }

    /**
     * Erzeuge alle R�ume und verbinde ihre Ausg�nge miteinander.
     */
    private void raeumeAnlegen()
    {
        Raum draussen, hoersaal, cafeteria, labor, buero;
      
        // die R�ume erzeugen
        draussen = new Raum("vor dem Haupteingang der Universit�t");
        hoersaal = new Raum("in einem Vorlesungssaal");
        cafeteria = new Raum("in der Cafeteria der Uni");
        labor = new Raum("in einem Rechnerraum");
        buero = new Raum("im Verwaltungsb�ro der Informatik");
        
        //Gegenst�nde setzen
        draussen.setzeGegenst�nde(new Gegenstand("Messer", 1));
        draussen.setzeGegenst�nde(new Gegenstand("Hammer", 5));
        labor.setzeGegenst�nde(new Gegenstand("Sichel", 3));
        labor.setzeGegenst�nde(new Gegenstand("Grabstein", 15));
        buero.setzeGegenst�nde(new Gegenstand("Schaufel", 4));
        cafeteria.setzeGegenst�nde(new Gegenstand("Pistole", 10));
        hoersaal.setzeGegenst�nde(new Gegenstand("Maffin", 0));
        hoersaal.setzeGegenst�nde(new Gegenstand("S�bel", 4));
        
        
        // die Ausg�nge initialisieren
        draussen.setzeAusgaenge("east", hoersaal);
        draussen.setzeAusgaenge("south", labor); 
        draussen.setzeAusgaenge("west", cafeteria);
        hoersaal.setzeAusgaenge("west", draussen);
        cafeteria.setzeAusgaenge("east", draussen);
        
        labor.setzeAusgaenge("north", draussen);
        labor.setzeAusgaenge("east", buero);
        buero.setzeAusgaenge("west", labor);
        
        spieler1 = new Spieler ("Hansi", 20); 
        spieler1.setzeAktuellenRaum(draussen);

        
    }

    /**
     * Die Hauptmethode zum Spielen. L�uft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen() 
    {   
             
        willkommenstextAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
        // und f�hren sie aus, bis das Spiel beendet wird.
                
        boolean beendet = false;
        while (! beendet) {
            Befehl befehl = parser.liefereBefehl();
            beendet = verarbeiteBefehl(befehl);
        }
        System.out.println("Danke f�r dieses Spiel. Auf Wiedersehen.");
    }

    /**
     * Einen Begr��ungstext f�r den Spieler ausgeben.
     */
    private void willkommenstextAusgeben()
    {
        System.out.println();
        System.out.println(spieler1.gibNameSpieler()+", willkommen zu Zuul!");
        System.out.println("Zuul ist ein neues, unglaublich langweiliges Spiel.");
        System.out.println("Tippen sie 'help', wenn Sie Hilfe brauchen.");
        System.out.println();
        raumInfoAusgeben();

    }

    /**
     * Verarbeite einen gegebenen Befehl (f�hre ihn aus).
     * @param befehl Der zu verarbeitende Befehl.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    public boolean verarbeiteBefehl(Befehl befehl) 
    {
        boolean moechteBeenden = false;

        if(befehl.istUnbekannt()) {
            sagenDassBefehlUnbekannt();
            return false;
        }
        String befehlswort = befehl.gibBefehlswort();
        if (befehlswort.equals("help")) {
            hilfstextAusgeben();
        }
        else if (befehlswort.equals("go")) {
            wechsleRaum(befehl);
        }
        else if (befehlswort.equals("quit")) {
            moechteBeenden = beenden(befehl);
        }
        else if (befehlswort.equals("look")) {
            umsehen();
        }
        else if (befehlswort.equals("eat")) {
        essen(befehl);
        }
        else if (befehlswort.equals("back")) {
        zur�ckgehen(befehl);    
        }
        else if (befehlswort.equals("take")) {
        take(befehl);
        }
        else if (befehlswort.equals("drop")) {
        drop(befehl);
        }
        else if (befehlswort.equals("status")) {
        spieler1.gibListeMeinerGegenstaende();
        spieler1.dasGesamtgewichtBetr�gt();
        }
        
        return moechteBeenden;
    }

    // Implementierung der Benutzerbefehle:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlsw�rter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Sie haben sich verlaufen. Sie sind allein.");
        System.out.println("Sie irren auf dem Unigel�nde herum.");
        System.out.println();
        System.out.println("Ihnen stehen folgende Befehle zur Verf�gung:");
        parser.zeigeBefehle();
    }
    private void take(Befehl befehl)
    {
        boolean zweitesWortBekannt=false;
        if(!befehl.hatZweitesWort()) {
            sagenDassBefehlUnbekannt();
            }  else if (spieler1.gibAktuellenRaum().gibGegenst�nde().isEmpty())
                {
                    System.out.println("In diesem Raum hat es momentan keine Gegenst�nde, "+spieler1.gibNameSpieler());
                } 
                else if (befehl.gibZweitesWort().equalsIgnoreCase("maffin"))
                {
                    System.out.println("Ein Maffin ist kein gew�hnlicher Gegenstand, ben�tzen Sie den Befehl: eat Maffin!");
                }
                
                 else {
            for (int i=0; i<spieler1.gibAktuellenRaum().gibGegenst�nde().size(); i++)
            {
                if (spieler1.gibAktuellenRaum().gibGegenst�nde().get(i).getGegenstandBeschreibung().equalsIgnoreCase(befehl.gibZweitesWort()))
                {
                    if(spieler1.gibTragkraft()<spieler1.gibGewichtListeGegenst�nde()+spieler1.gibAktuellenRaum().gibGegenst�nde().get(i).getGewicht())
                    {
                        System.out.println("Der Gegenstand ist zu schwer.");
                        
                    } else
                  spieler1.gegenstandAufnehmen(spieler1.gibAktuellenRaum().gibGegenst�nde().get(i));
                  zweitesWortBekannt=true;
                }
            }
        } if (zweitesWortBekannt==false&&!spieler1.gibAktuellenRaum().gibGegenst�nde().isEmpty()&&!befehl.gibZweitesWort().equalsIgnoreCase("maffin"))
                 {
                    System.out.println("Diesen Gegenstand gibt es hier nicht.");
                }
    }
    
        private void drop(Befehl befehl)
    {
        boolean zweitesWortBekannt=false;
        if(!befehl.hatZweitesWort()) {
            sagenDassBefehlUnbekannt();
        } else if(spieler1.gibmeineGegenstaende().isEmpty())
        {
            System.out.println(spieler1.gibNameSpieler()+", du hast im Moment gar keine Gegenst�nde.");
        }
        //wenn der Befehl g�ltig ist, testen, ob Gegenstand zu schwer
            else {
            for (int i=0; i<spieler1.gibmeineGegenstaende().size(); i++)
            {
                if (spieler1.gibmeineGegenstaende().get(i).getGegenstandBeschreibung().equalsIgnoreCase(befehl.gibZweitesWort()))
                {
                  spieler1.gegenstandAblegen(spieler1.gibmeineGegenstaende().get(i));
                  zweitesWortBekannt=true;
                } 
            }
        } 
        if (zweitesWortBekannt==false&&!spieler1.gibmeineGegenstaende().isEmpty())
                {
                    System.out.println("Diesen Gegenstand gibt es nicht.");
                }
                if (befehl.gibZweitesWort().equalsIgnoreCase("maffin"))
                {
                    System.out.println("Ein Maffin kann nicht mehr abgelegt werden.");
                }
    }

    /**
     * Versuche, in eine Richtung zu gehen. Wenn es einen Ausgang gibt,
     * wechsele in den neuen Raum, ansonsten gib eine Fehlermeldung
     * aus.
     */
    private void wechsleRaum(Befehl befehl) 
    {
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Wohin m�chten Sie gehen?");
            return;
        }

        String richtung = befehl.gibZweitesWort();

        // Wir versuchen, den Raum zu verlassen.
        spieler1.setzeNaechstenRaum(spieler1.gibAktuellenRaum().gibAusgang(richtung));

        if (spieler1.gibNaechstenRaum() == null) {
            System.out.println("Dort ist keine T�r!");
        }
        else {
            
            //ehemaligerRaum = aktuellerRaum; //ehemaligerRaum f�r back speichern
            spieler1.gibehemaligeRaeume().push(spieler1.gibAktuellenRaum());
            spieler1.setzeAktuellenRaum(spieler1.gibNaechstenRaum());
            //System.out.println(richtung);
            raumInfoAusgeben();
           
            }
            
        }
        public void zur�ckgehen(Befehl befehl)
        {        
            if(befehl.hatZweitesWort()) 
            {
            sagenDassBefehlUnbekannt();
            } else
            { if (spieler1.gibehemaligeRaeume().empty())
                {
                    System.out.println("Sie befinden sich wieder am Anfang des Spiels");
                } else
                {
                    spieler1.setzeAktuellenRaum(spieler1.gibehemaligeRaeume().pop());
                    raumInfoAusgeben();
                }
            }
               
            
            
            /* else {
              aktuellerRaum=ehemaligerRaum;
              raumInfoAusgeben();
            } */
            
        }
        private void raumInfoAusgeben()
        {
        System.out.println(spieler1.gibAktuellenRaum().gibLangeBeschreibung());
        
    }

    /**
     * "quit" wurde eingegeben. �berpr�fe den Rest des Befehls,
     * ob das Spiel wirklich beendet werden soll.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    public boolean beenden(Befehl befehl) 
    {
        if(befehl.hatZweitesWort()) {
            System.out.println("Was soll beendet werden?");
            return false;
        }
        else {
            return true;  // Das Spiel soll beendet werden.
        }
    }
    
    public void umsehen()
    {
        System.out.println(spieler1.gibAktuellenRaum().gibLangeBeschreibung());
    }
    //Befehl eat oder eat muffin
    
    
    public void essen(Befehl befehl)
      { boolean maffinImRaum=false;
          if(!befehl.hatZweitesWort()) 
          {
          System.out.println("Sie sind ein Fressack, �n Guete!");
        } else if (!befehl.gibZweitesWort().equalsIgnoreCase("maffin"))
        {
            sagenDassBefehlUnbekannt();
        } else
        {
            for (int i=0; i<spieler1.gibAktuellenRaum().gibGegenst�nde().size(); i++)
            {//falls das Magic Maffin im Raum ist:
                if (spieler1.gibAktuellenRaum().gibGegenst�nde().get(i).getGegenstandBeschreibung().equalsIgnoreCase(befehl.gibZweitesWort()))
                {
                   spieler1.setzeNeueTragkraft();
                   maffinImRaum=true;
 
                }
            }
            if(maffinImRaum==false)
                {
                    System.out.println("Sorry, hier gibt es kein Maffin!");
                }
         }

    }
    
    
    public void sagenDassBefehlUnbekannt()
    {
        System.out.println("Ich weiss nicht, was Sie meinen...");
    }
}

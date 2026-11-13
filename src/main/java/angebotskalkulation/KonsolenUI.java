package angebotskalkulation;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Steuert die menügeführte Konsolenanwendung.
 * Diese Klasse liest Eingaben und stellt Ergebnisse dar, berechnet aber keine Preise.
 */
public final class KonsolenUI {
    private final Scanner scanner;
    private final Angebotsrechner rechner;

    public KonsolenUI(Scanner scanner, Angebotsrechner rechner) {
        this.scanner = Objects.requireNonNull(scanner, "Der Scanner darf nicht null sein.");
        this.rechner = Objects.requireNonNull(rechner, "Der Angebotsrechner darf nicht null sein.");
    }

    /** Führt auf Wunsch mehrere unabhängige Angebotskalkulationen durch. */
    public void starten() {
        System.out.println("=== Angebotskalkulation für Firmenschulungen ===");

        try {
            boolean weitereAnfrage;
            do {
                Schulung schulung = liesSchulung();
                int teilnehmerzahl = liesTeilnehmerzahl();

                Anfrage anfrage = new Anfrage(schulung, teilnehmerzahl);
                Angebot angebot = rechner.berechne(anfrage);
                zeigeAngebot(angebot);

                if (frageTeilnehmervergleich()) {
                    int alternativeTeilnehmerzahl = liesTeilnehmerzahl(
                            "Alternative Teilnehmerzahl (1-15): ");
                    Anfrage alternativeAnfrage = new Anfrage(schulung, alternativeTeilnehmerzahl);
                    Angebot alternativesAngebot = rechner.berechne(alternativeAnfrage);
                    zeigeVergleich(new Angebotsvergleich(angebot, alternativesAngebot));
                }

                weitereAnfrage = frageWeitereAnfrage();
            } while (weitereAnfrage);

            System.out.println("Programm beendet.");
        } catch (NoSuchElementException endeDerEingabe) {
            // Etwa bei EOF in einer umgeleiteten Eingabe: keine Endlosschleife.
            System.out.println("\nKeine weitere Eingabe verfügbar. Programm beendet.");
        }
    }

    private void zeigeSchulungsmenue() {
        System.out.println("\nVerfügbare Schulungen:");
        Schulung[] schulungen = Schulung.values();
        for (int i = 0; i < schulungen.length; i++) {
            Schulung schulung = schulungen[i];
            System.out.printf("%d. %s (%d Tage)%n", i + 1,
                    schulung.getBezeichnung(), schulung.getDauerTage());
        }
    }

    private Schulung liesSchulung() {
        zeigeSchulungsmenue();
        Schulung[] schulungen = Schulung.values();
        while (true) {
            System.out.print("Schulung wählen (1-" + schulungen.length + "): ");
            System.out.flush();
            String eingabe = scanner.nextLine().trim();
            try {
                int auswahl = Integer.parseInt(eingabe);
                if (auswahl >= 1 && auswahl <= schulungen.length) {
                    return schulungen[auswahl - 1];
                }
            } catch (NumberFormatException nichtGanzzahlig) {
                // Die gemeinsame Fehlermeldung steht unterhalb des try-Blocks.
            }
            System.out.println("Ungültige Schulungsauswahl. Bitte eine Menünummer eingeben.");
        }
    }

    private int liesTeilnehmerzahl() {
        return liesTeilnehmerzahl("Teilnehmerzahl (1-15): ");
    }

    private int liesTeilnehmerzahl(String eingabeaufforderung) {
        while (true) {
            System.out.print(eingabeaufforderung);
            System.out.flush();
            String eingabe = scanner.nextLine().trim();
            try {
                int teilnehmerzahl = Integer.parseInt(eingabe);
                if (teilnehmerzahl >= 1 && teilnehmerzahl <= 15) {
                    return teilnehmerzahl;
                }
            } catch (NumberFormatException nichtGanzzahlig) {
                // Auch Texte und Dezimalzahlen führen zur erneuten Eingabe.
            }
            System.out.println("Ungültige Teilnehmerzahl. Bitte eine ganze Zahl von 1 bis 15 eingeben.");
        }
    }

    private void zeigeAngebot(Angebot angebot) {
        Anfrage anfrage = angebot.getAnfrage();
        System.out.println("\n--- Angebotskalkulation ---");
        System.out.println("Schulung: " + anfrage.getSchulung().getBezeichnung());
        System.out.println("Schulungsdauer: " + anfrage.getSchulung().getDauerTage() + " Tage");
        System.out.println("Teilnehmerzahl: " + anfrage.getTeilnehmerzahl());
        zeigeKostenposition("Dozentenkosten:", angebot.getDozentenkosten());
        zeigeKostenposition("Raumkosten:", angebot.getRaumkosten());
        zeigeKostenposition("Schulungsunterlagen:", angebot.getUnterlagenkosten());
        zeigeKostenposition("Verwaltungspauschale:", angebot.getVerwaltungspauschale());
        zeigeKostenposition("Zwischensumme:", angebot.getZwischensumme());
        System.out.println("Rabattsatz: " + angebot.getRabattsatz()
                .multiply(BigDecimal.valueOf(100)).stripTrailingZeros().toPlainString() + " %");
        zeigeKostenposition("Rabattbetrag:", angebot.getRabattbetrag());
        zeigeKostenposition("Angebotspreis:", angebot.getAngebotspreis());
        zeigeKostenposition("Preis pro Teilnehmer:", angebot.getPreisProTeilnehmer());
    }

    private void zeigeKostenposition(String bezeichnung, BigDecimal betrag) {
        System.out.printf("%-26s %15s%n", bezeichnung,
                String.format(Locale.GERMANY, "%,.2f €", betrag));
    }

    private void zeigeVergleich(Angebotsvergleich vergleich) {
        Angebot urspruenglich = vergleich.getUrspruenglichesAngebot();
        Angebot alternativ = vergleich.getAlternativesAngebot();
        System.out.println("\n--- Teilnehmervergleich ---");
        System.out.println("Schulung: " + urspruenglich.getAnfrage().getSchulung().getBezeichnung());
        System.out.println("Ursprüngliche Teilnehmerzahl: "
                + urspruenglich.getAnfrage().getTeilnehmerzahl());
        System.out.println("Alternative Teilnehmerzahl: "
                + alternativ.getAnfrage().getTeilnehmerzahl());
        zeigeKostenposition("Gesamtpreis ursprünglich:", urspruenglich.getAngebotspreis());
        zeigeKostenposition("Gesamtpreis alternativ:", alternativ.getAngebotspreis());
        zeigeKostenposition("Preis pro TN ursprünglich:", urspruenglich.getPreisProTeilnehmer());
        zeigeKostenposition("Preis pro TN alternativ:", alternativ.getPreisProTeilnehmer());
        zeigeKostenposition("Gesamtpreisdifferenz:", vergleich.getGesamtpreisdifferenz());
        zeigeKostenposition("Teilnehmerpreisdifferenz:", vergleich.getTeilnehmerpreisdifferenz());
        System.out.println("Differenz = Alternative minus ursprüngliches Angebot; "
                + "negativ bedeutet günstiger.");
    }

    private boolean frageTeilnehmervergleich() {
        while (true) {
            System.out.print("\nTeilnehmervergleich durchführen? (j/n): ");
            System.out.flush();
            String eingabe = scanner.nextLine().trim();
            if (eingabe.equalsIgnoreCase("j") || eingabe.equalsIgnoreCase("ja")) {
                return true;
            }
            if (eingabe.equalsIgnoreCase("n") || eingabe.equalsIgnoreCase("nein")) {
                return false;
            }
            System.out.println("Ungültige Eingabe. Bitte j oder n eingeben.");
        }
    }

    private boolean frageWeitereAnfrage() {
        while (true) {
            System.out.print("\nWeitere Anfrage berechnen? (j/n): ");
            System.out.flush();
            String eingabe = scanner.nextLine().trim();
            if (eingabe.equalsIgnoreCase("j") || eingabe.equalsIgnoreCase("ja")) {
                return true;
            }
            if (eingabe.equalsIgnoreCase("n") || eingabe.equalsIgnoreCase("nein")) {
                return false;
            }
            System.out.println("Ungültige Eingabe. Bitte j oder n eingeben.");
        }
    }
}

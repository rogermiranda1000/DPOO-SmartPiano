package model;

import controller.SongNotifier;
import org.jsoup.Jsoup;

import javax.sound.midi.InvalidMidiDataException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScrapping {
    public static final Pattern USER_PATTERN = Pattern.compile("^(?:by )?(.+?)(?: \\(\\d{4}.\\d{4}\\))?$"); // RegEx time \o.0/
    private static final String LINK = "https://www.mutopiaproject.org/cgibin/make-table.cgi?Instrument=Piano&startat=%d";
    private static final int FILES_PER_LINK = 10;

    private static final String PATH_SONG_NAME = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(1) > td:nth-child(1)";
    private static final String PATH_AUTHOR = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(1) > td:nth-child(2)";
    private static final String PATH_MIDI = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(4) > td:nth-child(2) > a";
    private static final String PATH_DATE = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(3) > td:nth-child(4)";

    /**
     * S'elimina el 'by' i la data de naixement/mort del usuari. Si no ho té es deixa tal qual.
     * @param complete String a segmentar
     * @return String segmentada
     */
    private static synchronized String getMatch(String complete) {
        // se elimina el 'by' y la fecha de nacimiento/muerte del usuario, si no lo tiene se queda igual
        Matcher matcher = WebScrapping.USER_PATTERN.matcher(complete);
        if (!matcher.matches()) return ""; // ???
        return matcher.group(1);
    }

    public static void getSongs(SongNotifier sn) {
        int desfase = 0;
        boolean end = false;
        while (!end) {
            String webContent = WebScrapping.getURLContent(String.format(WebScrapping.LINK, desfase));

            if (webContent.equalsIgnoreCase("")) end = true;
            else {
                for (int x = 1; x <= WebScrapping.FILES_PER_LINK && !end; x++) {
                    try {
                        String song = Jsoup.parse(webContent).selectFirst(String.format(WebScrapping.PATH_SONG_NAME, x)).text(),
                                author = Jsoup.parse(webContent).selectFirst(String.format(WebScrapping.PATH_AUTHOR, x)).text();
                        URL midi = new URL(Jsoup.parse(webContent).selectFirst(String.format(WebScrapping.PATH_MIDI, x)).attr("href"));
                        Date date = new SimpleDateFormat("yyyy/MM/dd").parse(Jsoup.parse(webContent).selectFirst(String.format(WebScrapping.PATH_DATE, x)).text());

                        sn.addSong(MIDIFactory.getSong(song, WebScrapping.getMatch(author), date, midi));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (InvalidMidiDataException | IOException ex) {
                        // .zip o fitxer eliminat; no es pot fer res
                    } catch (NullPointerException ex) {
                        // se han terminado las canciones
                        end = true;
                    }
                }

                desfase += WebScrapping.FILES_PER_LINK;
            }
        }
    }

    private static String getURLContent(String url) {
        StringBuilder sb = new StringBuilder();

        try {
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) sb.append(inputLine);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }
}

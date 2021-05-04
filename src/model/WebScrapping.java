package model;

import entities.Song;
import org.jsoup.Jsoup;

import javax.sound.midi.InvalidMidiDataException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WebScrapping {
    public static final String ANONYMOUS_USER = "Anonymous";
    private static final String LINK = "https://www.mutopiaproject.org/cgibin/make-table.cgi?Instrument=Piano&startat=%d";
    private static final int FILES_PER_LINK = 10;

    private static final String PATH_SONG_NAME = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(1) > td:nth-child(1)";
    private static final String PATH_AUTHOR = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(1) > td:nth-child(2)";
    private static final String PATH_MIDI = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(4) > td:nth-child(2) > a";
    private static final String PATH_DATE = "body > div.container > div > div > table > tbody > tr:nth-child(%d) > td > table > tbody > tr:nth-child(3) > td:nth-child(4)";

    public static Song[] getSongs() {
        ArrayList<Song> r = new ArrayList<>();

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

                        r.add(MIDIFactory.getSong(song, author.equalsIgnoreCase(WebScrapping.ANONYMOUS_USER) ? "" : author, date, midi));
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

        return r.toArray(new Song[0]);
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

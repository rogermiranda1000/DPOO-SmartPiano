package controller;

import entities.Song;

public interface RankingEvent {

    /**
     * Retorna el nombre de cançons escoltades les ultimes 24h
     * @return Array amb els valors de cançons desde (horaActual - 23h) fins l'hora actual
     */
    int[] getSongsStatistics();
    /**
     * Retorna el nombre de segons escoltades les ultimes 24h
     * @return Array amb els valors de segons desde (horaActual - 23h) fins l'hora actual
     */
    int[] getTimeStatistics();

    /**
     * Busca les 5 cançons més escoltades a la base de dades.
     * @param plays array de 5 enters per obtenir el nombre de reproduccions de les cançons.
     * @return llista de les 5cançons més escoltades
     */

    Song[] getTop5(int[] plays);
}
package controller;

import entities.Song;

public interface RankingEvent {

    /**
     * Retorna el nombre de cançons escoltades les ultimes 24h
     * @return Array amb els valors de cançons desde (horaActual - 23h) fins l'hora actual
     */
    int[] getSongsStadistics();
    /**
     * Retorna el nombre de segons escoltades les ultimes 24h
     * @return Array amb els valors de segons desde (horaActual - 23h) fins l'hora actual
     */
    int[] getTimeStadistics();


    Song[] getTop5(int[] plays);
}
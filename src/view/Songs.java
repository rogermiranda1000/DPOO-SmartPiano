package view;

import controller.SongRequest;
import controller.SongsEvent;
import entities.Song;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Songs extends JPanel implements ActionListener, SongsMenuNotifier {
    private final DefaultTableModel songs;
    private final JTable songsTable;

    private final DefaultTableModel playlists;
    private final JTable playlistsTable;

    private final SongsEvent event;
    private final SongRequest requestEvent;

    private JButton playSongButton, addSongButton, removeSongButton, backToSongsButton, addToPlaylistButton;
    private final JPanel mainContent;
    private final CardLayout cl;

    public Songs(SongsEvent songE, SongRequest requestE) {
        this.songs = new DefaultTableModel();
        this.songsTable = new JTable(this.songs);

        this.playlists = new DefaultTableModel();
        this.playlistsTable = new JTable(this.playlists);

        this.event = songE;
        this.requestEvent = requestE;

        mainContent = new JPanel(new CardLayout());
        mainContent.add(songsList(), "songs");
        mainContent.add(addToPlaylist(), "playlists");
        cl = (CardLayout) (mainContent.getLayout());
        /* DEFAULT VIEW */
        cl.show(mainContent, ("songs"));

        this.add(mainContent);
        this.setVisible(true);
    }

    public JPanel songsList(){
        JPanel songsList = new JPanel();
        Font f = new Font(null, Font.PLAIN, 16);
        //TODO: Listers de la taula: https://www.javatpoint.com/java-jtable
        this.setBackground(ColorConstants.BACKGROUND.getColor());

        // Create columns
        songs.addColumn("Title");
        songs.addColumn("Artist");
        songs.addColumn("Date");
        songs.addColumn("Duration");
        songs.addColumn("Punctuation");

        songsTable.setBounds(30, 40, 80, 90);
        songsTable.setFont(f);
        Dimension dim = new Dimension(20, 2);
        songsTable.setRowHeight(songsTable.getRowHeight() + 10);
        songsTable.setIntercellSpacing(new Dimension(dim));

        JScrollPane scrollPane = new JScrollPane(songsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1000, 700));

        songsList.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        songsList.setLayout(new BoxLayout(songsList, BoxLayout.Y_AXIS));
        songsList.add(scrollPane);
        songsList.add(songsBotPanel());
        songsList.setBackground(ColorConstants.BACKGROUND.getColor());
        return songsList;
    }

    public void reloadSongs() {
        this.clearSongsTable();
        for (Song s : this.event.getUserSongs()) this.addSongToTable(s);
    }

    public void reloadPlaylists() {
        this.clearPlaylistTable();
        for (String l : this.event.getPlaylists()) this.addPlaylistToTable(l);
    }

    public JPanel addToPlaylist(){
        JPanel playlistsList = new JPanel();
        Font f = new Font(null, Font.PLAIN, 16);
        //TODO: Listers de la taula: https://www.javatpoint.com/java-jtable
        this.setBackground(ColorConstants.BACKGROUND.getColor());

        // Create columns
        playlists.addColumn("Playlist");

        playlistsTable.setBounds(30, 40, 80, 90);
        playlistsTable.setFont(f);
        Dimension dim = new Dimension(20, 2);
        playlistsTable.setRowHeight(playlistsTable.getRowHeight() + 10);
        playlistsTable.setIntercellSpacing(new Dimension(dim));

        JScrollPane scrollPane = new JScrollPane(playlistsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1000, 700));

        playlistsList.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        playlistsList.setLayout(new BoxLayout(playlistsList, BoxLayout.Y_AXIS));
        playlistsList.add(scrollPane);
        playlistsList.add(playlistsBotPanel());
        playlistsList.setBackground(ColorConstants.BACKGROUND.getColor());

        return playlistsList;
    }

    public JPanel playlistsBotPanel() {
        Font f = new Font(null, Font.BOLD, 20);
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());

        backToSongsButton = new GenericButton(view.Icon.GOBACK.getIcon());
        addToPlaylistButton = new GenericButton("+");

        backToSongsButton.addActionListener(this);
        addToPlaylistButton.addActionListener(this);

        panel.add(backToSongsButton);
        panel.add(addToPlaylistButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }

    public JPanel songsBotPanel() {
        Font f = new Font(null, Font.BOLD, 20);
        JPanel panel = new JPanel();
        panel.setBackground(ColorConstants.MENU.getColor());

        playSongButton = new GenericButton(view.Icon.PLAY.getIcon());
        addSongButton = new GenericButton("+");
        removeSongButton = new GenericButton("x");

        playSongButton.addActionListener(this);
        addSongButton.addActionListener(this);
        removeSongButton.addActionListener(this);

        panel.add(playSongButton);
        panel.add(addSongButton);
        panel.add(removeSongButton);

        panel.setSize(new Dimension(800, 150));
        panel.setVisible(true);

        return panel;
    }

    private void addSongToTable(Song song) {
        songs.addRow(new Object[]{song.getName(), song.getArtist(), song.getDate(), (int)Math.floor(song.getDuration()), String.format("%.1f", song.getScore())});
    }

    private Song getSongFromTable(int row) {
        // cada fila té Object[]{song, song.getArtist(), song.getDate(), song.getDuration(), song.getScore()}
        return new Song((String) this.songs.getValueAt(row, 0), (String) this.songs.getValueAt(row, 1), (String) this.songs.getValueAt(row, 2));
    }

    private void clearSongsTable() {
        int count = this.songs.getRowCount();
        while (count > 0){
            this.songs.removeRow(count-1);
            count = this.songs.getRowCount();
        }
    }

    private void addPlaylistToTable(String title) {
        playlists.addRow(new Object[]{title});
    }

    private void clearPlaylistTable() {
        int rowCount = this.playlists.getRowCount();
        while (rowCount > 0) {
            this.playlists.removeRow(rowCount - 1);
            rowCount = this.playlists.getRowCount();
        }
    }

    private void popUpNoSongSelected() {
        JOptionPane.showMessageDialog(this, "You haven't selected a song. ლ(ಠ益ಠლ)");
    }

    private void popUpNoPlaylistSelected() {
        JOptionPane.showMessageDialog(this, "You haven't selected a playlist. ლ(ಠ益ಠლ)");
    }

    public void popUpSongsAdded() {
        JOptionPane.showMessageDialog(this, "Finished adding to playlist. (◕‿◕✿)");
    }

    /**
     * Donada una fila de la taula retorna la canço
     * @param pos Fila
     * @return Canço indicada a la fila
     */
    private Song getSong(int pos){
        return new Song((String) songsTable.getModel().getValueAt(pos, 0), (String) songsTable.getModel().getValueAt(pos, 1), (String) songsTable.getModel().getValueAt(pos, 2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedSongs = songsTable.getSelectedRows();
        int[] selectedPlaylists = playlistsTable.getSelectedRows();

        if (e.getSource() == playSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                this.requestEvent.requestSong(this.getSongFromTable(selectedSongs[0])); // TODO multiple
            }
        } else if (e.getSource() == addSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                cl.show(mainContent, ("playlists"));
            }
        } else if (e.getSource() == removeSongButton) {
            if (selectedSongs.length == 0) {
                popUpNoSongSelected();
            } else {
                for (int i = 0; i < selectedSongs.length; i++) {
                    this.event.deleteSong(this.getSong(selectedSongs[i]));
                }
            }
        } else if (e.getSource() == backToSongsButton) {
            cl.show(mainContent, ("songs"));
        } else if (e.getSource() == addToPlaylistButton) {
            if (selectedPlaylists.length == 0) this.popUpNoPlaylistSelected();
            else {
                for (int i = selectedPlaylists.length - 1; i >= 0; i--) {
                    // playlist
                    for (int j = selectedSongs.length - 1; j >= 0; j--) {
                        // song
                        this.event.addSongPlaylist(this.getSong(selectedSongs[j]), (String) playlistsTable.getModel().getValueAt(selectedPlaylists[i], 0));
                    }
                }
                this.popUpSongsAdded();
                cl.show(mainContent,("songs"));
            }
        }
    }

    @Override
    public void unableToDeleteSong(Song song) {
        JOptionPane.showMessageDialog(this, song.getName() + " can't be deleted!");
    }

    @Override
    public void songDeleted(Song song) {
        this.reloadSongs();
        JOptionPane.showMessageDialog(this, song.getName() + " was deleted.");
    }

    @Override
    public void unableToAddSong(Song song) {
        JOptionPane.showMessageDialog(this, "Error adding the song " + song.toString() + ".");
    }

    @Override
    public void songAdded(Song song) {
        // uwu
    }
}

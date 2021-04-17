package business;

public class BusinessFacade {
    private SongDAO songManager;
    private UserDAO userManager;

    public BusinessFacade(SongDAO songManager, UserDAO userManager) {
        this.songManager = songManager;
        this.userManager = userManager;
    }
}

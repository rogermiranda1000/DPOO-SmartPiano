package view;

public interface ConfigLoadNotifier {
    void setConfig(float songVolume, float pianoVolume);
    void setUserInformation(String name, String email);
}

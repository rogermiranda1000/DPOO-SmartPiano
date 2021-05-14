package controller;

public interface LoginEvent {
    void requestLogin(String user, String password);
    void requestRegister(String user, String email, String password);
}

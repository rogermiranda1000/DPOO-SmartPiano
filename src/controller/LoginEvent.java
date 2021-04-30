package controller;

public interface LoginEvent {
    boolean requestLogin(String user, String password);
    boolean requestRegister(String user, String email, String password);
}

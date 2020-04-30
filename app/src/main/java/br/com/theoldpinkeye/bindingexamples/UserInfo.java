package br.com.theoldpinkeye.bindingexamples;

public class UserInfo {

    private String name;
    private String password;
    private String email;
    private boolean accept;

    public UserInfo(String name, String password, String email, boolean accept) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.accept = accept;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isAccept() {
        return accept;
    }

    public UserInfo setAccept(boolean accept) {
        this.accept = accept;
        return this;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", accept=" + accept +
                '}';
    }
}

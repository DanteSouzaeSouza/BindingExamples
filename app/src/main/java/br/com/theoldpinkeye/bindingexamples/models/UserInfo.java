package br.com.theoldpinkeye.bindingexamples.models;

public class UserInfo {

    private String nome;
    private String password;
    private String email;
    private boolean accept;

    public UserInfo(String nome, String password, String email, boolean accept) {
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.accept = accept;
    }


    public String getNome() {
        return nome;
    }

    public UserInfo setNome(String nome) {
        this.nome = nome;
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
                "nome='" + nome + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", accept=" + accept +
                '}';
    }
}

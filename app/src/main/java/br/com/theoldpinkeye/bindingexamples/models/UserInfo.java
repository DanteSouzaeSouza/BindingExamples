package br.com.theoldpinkeye.bindingexamples.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {

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


    protected UserInfo(Parcel in) {
        nome = in.readString();
        password = in.readString();
        email = in.readString();
        accept = in.readByte() != 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeByte((byte) (accept ? 1 : 0));
    }
}

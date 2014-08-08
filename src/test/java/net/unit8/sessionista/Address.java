package net.unit8.sessionista;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kawasima
 * Date: 14/07/18
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public class Address implements Serializable {
    private String postalCd;
    private String prefecture;
    private String city;

    public String getPostalCd() {
        return postalCd;
    }

    public void setPostalCd(String postalCd) {
        this.postalCd = postalCd;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

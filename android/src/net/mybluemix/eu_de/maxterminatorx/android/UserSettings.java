package net.mybluemix.eu_de.maxterminatorx.android;

/**
 * Created by bus on 23/01/2018.
 */

public class UserSettings {
    public String adress,website, instagram,telephone;

    public UserSettings() {

    }

    public UserSettings(String adress, String website, String instagram, String telephone) {
        this.adress = adress;
        this.website = website;
        this.instagram = instagram;
        this.telephone = telephone;
    }



    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "adress='" + adress + '\'' +
                ", website='" + website + '\'' +
                ", instagram='" + instagram + '\'' +
                ", telephone=" + telephone +
                '}';
    }
}

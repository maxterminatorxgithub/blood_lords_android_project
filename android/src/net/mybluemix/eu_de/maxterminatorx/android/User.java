package net.mybluemix.eu_de.maxterminatorx.android;

import android.location.Location;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bus on 29/12/2017.
 */

public class User implements Serializable {
    private String password;
    private String email;
    private Boolean vipStatus;
    public String latitude,longtitude,key,username,location;
    private Integer score,wins,looses,fights;

    public User(){

    }
    public User(String username,String email,String password,String location){
        this.password = password;
        this.email = email;
        this.username = username;
        this.location = location;
        score = 0;
        fights = 0;
        wins = 0;
    }



    public User(String password, String email, String latitude, String longtitude,
                 Boolean vipStatus, Integer score,
                 Integer wins, Integer looses, Integer fights, String key) {
        this.password = password;
        this.email = email;
        this.vipStatus = vipStatus;
        this.score = score;
        this.wins = wins;
        this.looses = looses;
        this.fights = fights;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.key = key;

    }
    public Map<String,Object>toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("password",password);
        result.put("instagram",email);
        result.put("latitude",latitude);
        result.put("longtitude",longtitude);
        result.put("vipStatus",vipStatus);
        result.put("score",score);
        result.put("wins",wins);
        result.put("looses",looses);
        result.put("figths",fights);
        result.put("key",key);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Boolean vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLooses() {
        return looses;
    }

    public void setLooses(Integer looses) {
        this.looses = looses;
    }

    public Integer getFights() {
        return fights;
    }

    public void setFights(Integer fights) {
        this.fights = fights;
    }




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

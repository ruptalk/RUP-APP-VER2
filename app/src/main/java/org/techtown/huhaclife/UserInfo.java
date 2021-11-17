package org.techtown.huhaclife;

public class UserInfo {
    private String email;
    private String pw;
    private String name;
    private String plant;
    private int count;
    private int point;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlant() { return plant; }

    public void setPlant(String plant) { this.plant=plant; }

    public int getCount() { return count; }

    public void setCount(int count) { this.count=count; }
}

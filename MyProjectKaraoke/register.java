package MyProjectKaraoke;

import java.io.Serializable;

public class register implements Serializable {
    String name;
    String phonenum;
    String id;

    public register(String name, String phonenum, String id) {
        this.name = name;
        this.phonenum = phonenum;
        this.id = id;

    }

    public String printinfo() {
        return "Name: " + name + "\t" + "Phone Number: " + phonenum + "\t" + " Id: " + id + "\n";

    }
    
}
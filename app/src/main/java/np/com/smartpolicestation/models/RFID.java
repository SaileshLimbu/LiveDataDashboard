package np.com.smartpolicestation.models;

public class RFID {
    private String isAuth;
    private String timestamp;

    public RFID(){}

    public RFID(String isAuth, String timestamp) {
        this.isAuth = isAuth;
        this.timestamp = timestamp;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

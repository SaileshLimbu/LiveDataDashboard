package np.com.smartpolicestation.models;

public class EQ {
    private String magnitude;
    private String timestamp;

    public EQ() {
    }

    public EQ(String magnitude, String timestamp) {
        this.magnitude = magnitude;
        this.timestamp = timestamp;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

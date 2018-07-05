package madsmortensen.studior;

public class FirebaseMarker {

    public String studioName;
    public String studioDescription;
    public String studioAddress;
    public double latitude;
    public double longitude;


    //required empty constructor
    public FirebaseMarker() {
    }

    public FirebaseMarker(String studioName, String studioDescription, String studioAdress, double latitude, double longitude) {
        this.studioName = studioName;
        this.studioDescription = studioDescription;
        this.studioAddress = studioAdress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public String getStudioDescription() {
        return studioDescription;
    }

    public void setStudioDescription(String studioDescription) {
        this.studioDescription = studioDescription;
    }

    public String getStudioAddress() {
        return studioAddress;
    }

    public void setStudioAddress(String studioAddress) {
        this.studioAddress = studioAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}



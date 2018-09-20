package newton.scrapper;

import java.io.Serializable;

/**
 *
 * @author MICROSOFT
 */
@SuppressWarnings("ALL")
public class AdmitCard implements Serializable {

    private static final long serialVersionUID = -2678313044746846625L;
    private String Region;
    private String abbrName;
    private String addLine1;
    private String addLine2;
    private String addLine3;
    private String addLine4;
    private String addLine5;
    private String affNo;
    private String centerCode;
    private String district;
    private String image;
    private String latitude;
    private String longitude;
    private String name;
    private String rollno;
    private String schoolCode;
    private String state;
    private String studclass;

    /**
     *
     * @return
     */
    public String getStudclass() {
        return this.studclass;
    }

    /**
     *
     * @param studclass
     */
    public void setStudclass(String studclass) {
        this.studclass = studclass;
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public String getImage() {
        return this.image;
    }

    /**
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     */
    public String getAffNo() {
        return this.affNo;
    }

    /**
     *
     * @param affNo
     */
    public void setAffNo(String affNo) {
        this.affNo = affNo;
    }

    /**
     *
     * @return
     */
    public String getAddLine5() {
        return this.addLine5;
    }

    /**
     *
     * @param addLine5
     */
    public void setAddLine5(String addLine5) {
        this.addLine5 = addLine5;
    }

    /**
     *
     * @return
     */
    public String getAbbrName() {
        return this.abbrName;
    }

    /**
     *
     * @param abbrName
     */
    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }

    /**
     *
     * @return
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     */
    public String getRollno() {
        return this.rollno;
    }

    /**
     *
     * @param rollno
     */
    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    /**
     *
     * @return
     */
    public String getDistrict() {
        return this.district;
    }

    /**
     *
     * @param district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getCenterCode() {
        return this.centerCode;
    }

    /**
     *
     * @param centerCode
     */
    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    /**
     *
     * @return
     */
    public String getAddLine3() {
        return this.addLine3;
    }

    /**
     *
     * @param addLine3
     */
    public void setAddLine3(String addLine3) {
        this.addLine3 = addLine3;
    }

    /**
     *
     * @return
     */
    public String getRegion() {
        return this.Region;
    }

    /**
     *
     * @param Region
     */
    public void setRegion(String Region) {
        this.Region = Region;
    }

    /**
     *
     * @return
     */
    public String getAddLine4() {
        return this.addLine4;
    }

    /**
     *
     * @param addLine4
     */
    public void setAddLine4(String addLine4) {
        this.addLine4 = addLine4;
    }

    /**
     *
     * @return
     */
    public String getAddLine1() {
        return this.addLine1;
    }

    /**
     *
     * @param addLine1
     */
    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }

    /**
     *
     * @return
     */
    public String getAddLine2() {
        return this.addLine2;
    }

    /**
     *
     * @param addLine2
     */
    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }

    /**
     *
     * @return
     */
    public String getState() {
        return this.state;
    }

    /**
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getSchoolCode() {
        return this.schoolCode;
    }

    /**
     *
     * @param schoolCode
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    /**
     *
     * @return
     */
    public String getLongitude() {
        return this.longitude;
    }




    @Override
    public String toString() {
        return "AdmitCard{" + "Region=" + Region + ", abbrName=" + abbrName + ", addLine1=" + addLine1 + ", addLine2=" + addLine2 + ", addLine3=" + addLine3 + ", addLine4=" + addLine4 + ", addLine5=" + addLine5 + ", affNo=" + affNo + ", centerCode=" + centerCode + ", district=" + district + ", image=" + image + ", latitude=" + latitude + ", longitude=" + longitude + ", name=" + name + ", rollno=" + rollno + ", schoolCode=" + schoolCode + ", state=" + state + ", studclass=" + studclass + '}';
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

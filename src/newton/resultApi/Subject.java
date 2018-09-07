/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;

/**
 *
 * @author Meghdut Mandal
 */
public class Subject implements java.io.Serializable {

    private String name;
    private int marks;
    private String grade;
    private CBSEResult res;

    /**
     *
     * @param res
     */
    public Subject(CBSEResult res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "Subject{" + "name=" + name + ", marks=" + marks + ", grade=" + grade + '}';
    }

    /**
     *
     * @return
     */
    public CBSEResult getRes() {
        return res;
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
     * @param marks
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     *
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public int getMarks() {
        return marks;
    }

    /**
     *
     * @return
     */
    public String getGrade() {
        return grade;
    }

    /**
     *
     * @param res
     */
    public void setResult(CBSEResult res) {
        this.res = res;
    }

}

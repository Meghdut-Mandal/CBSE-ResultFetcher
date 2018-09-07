/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 * @author Meghdut Mandal
 */
@SuppressWarnings("UseOfObsoleteCollectionType")
public class CBSEResult {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        File fi = new File("E:\\CBSE\\HSMSCBSE2016\\");
        String totalWise = HtmlUnitClient.totalWise(Arrays.asList(fi.listFiles()), null);
        System.out.println(totalWise);
    }

    private String name;
    private String regno;
    private String status;//  Pass ,fail ,comp, improvement
    private List<List<String>> perInfo;
    private List<List<String>> subjMarks;
    ////6625902
    // sx 08423
    // centr 6219
    private List<Subject> subjects;

    /**
     *
     * @param name
     * @param regno
     * @param status
     */
    public CBSEResult(String name, String regno, String status) {
        this.name = name;
        this.regno = regno;
        this.status = status;
    }

    /**
     *
     * @param perInfo
     * @param subjMarks
     */
    public CBSEResult(List<List<String>> perInfo, List<List<String>> subjMarks) {
        this.perInfo = perInfo;
        this.subjMarks = subjMarks;
        name = null;
        this.perInfo.stream().forEach((s) -> {
            if (s.get(0).trim().contains("Name") && name == null) {
                name = s.get(1);
            }
            if (s.get(0).trim().contains("Roll No")) {
                regno = s.get(1);
            }
        });
        processSubject();
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
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getRegno() {
        return regno;
    }

    /**
     *
     * @param regno
     */
    public void setRegno(String regno) {
        this.regno = regno;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @param subjects
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     *
     * @return
     */
    public List<List<String>> getPerInfo() {
        return perInfo;
    }

    /**
     *
     * @param perInfo
     */
    public void setPerInfo(List<List<String>> perInfo) {
        this.perInfo = perInfo;
    }

    /**
     *
     * @return
     */
    public List<List<String>> getSubjMarks() {
        return subjMarks;
    }

    /**
     *
     * @param subjMarks
     */
    public void setSubjMarks(List<List<String>> subjMarks) {
        this.subjMarks = subjMarks;
    }

    /**
     *
     * @return
     */
    public int getBestof5() {
        int best = 0;
        List<Subject> mainSubjects = getMainSubjects();

        Subject eng = this.getSubjectByName("ENGLISH CORE");
        if (eng != null) {
            best = eng.getMarks();
            mainSubjects.remove(eng);
        } else {
            //  System.err.println(this);
        }
        mainSubjects.sort((a, b) -> {
            return b.getMarks() - a.getMarks();
        });
        try {
            for (int i = 0; i < 4; i++) {
                if (!mainSubjects.get(i).getName().equals("ENGLISH CORE")) {
                    best += mainSubjects.get(i).getMarks();
                }

            }
        } catch (java.lang.IndexOutOfBoundsException e) {

            // System.out.println("error" + this);
            return best;
        }
        return best;
    }

    @Override
    public String toString() {
        return "Result{" + this.perInfo.toString() + ", subjects=" + subjects + '}';
    }

    /**
     *
     * @param name
     * @return
     */
    public Subject getSubjectByName(String name) {

        for (Subject sub : subjects) {

            if (sub.getName() != null && sub.getName().contains(name)) {
                return sub;
            }
        }

        return null;

    }

    /**
     *
     * @return
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     *
     * @return
     */
    public List<Subject> getMainSubjects() {

        List<Subject> subjs = new java.util.ArrayList<>();
        this.getSubjects().stream().forEach((sub) -> {
            String name1 = sub.getName();
            if (name1 != null) {
                List<String> excludeList = Arrays.asList("WORK EXPERIENCE", "PHY & HEALTH EDUCA", "GENERAL STUDIES", "Additional Subject");

                if (!excludeList.stream().anyMatch(name1::contains)) {
                    subjs.add(sub);
                }
            }

        });

        return subjs;
    }

    private void processSubject() {
        subjects = new java.util.ArrayList<>();
        System.out.println("subjects " + subjects);
        int subNameIndex = 0;
        int SubMarksIndex = 0;
        int SubGradeIndex = 0;

        for (int i = 0; i < this.getSIColNames().size(); i++) {
            if (getSIColNames().get(i).contains("SUB NAME")) {
                subNameIndex = i;
            } else if (getSIColNames().get(i).contains("MARKS")) {
                SubMarksIndex = i;
            } else if (getSIColNames().get(i).contains("GRADE")) {
                SubGradeIndex = i;
            }
        }
        //  System.out.println("@indexes" + subNameIndex + " +" + SubMarksIndex + "+" + SubGradeIndex);
        ol:
        for (int i = 1; i < getSubjMarks().size(); i++) {

            List<String> singleSubject = getSubjMarks().get(i);
            Subject subject = new Subject(this);
            for (int j = 0; j < singleSubject.size(); j++) {

                if (j == subNameIndex) {
                    subject.setName(singleSubject.get(j));
                }
                if (j == SubMarksIndex) {

                    Scanner sc = new Scanner(singleSubject.get(j));
                    if (sc.hasNextInt()) {
                        subject.setMarks(sc.nextInt());

                    }

                }
                if (j == SubGradeIndex) {
                    subject.setGrade(singleSubject.get(j));
                }

            }
            if (subject.getName() != null) {
                subjects.add(subject);

            }
        }

    }

    /**
     *
     * @return
     */
    public Vector<String> genPIColNames() {

        //System.out.println(perInfo);
        Vector<String> columnNames = new Vector<>();
        perInfo.stream().forEach((row) -> {
            columnNames.add(row.get(0).trim());
        });
        //System.out.println(Arrays.toString(colNames));
        return columnNames;
    }

    /**
     *
     * @return
     */
    public Vector<Vector<String>> getPIRow() {
        Vector<Vector<String>> rowData = new Vector<>();

        Vector<String> row = new Vector<>();
        perInfo.stream().forEach((row11) -> {
            row.add(row11.get(1).trim());
        });
        rowData.add(row);
        //  System.out.println(row);
        return rowData;

    }

    /**
     *
     * @return
     */
    public Vector<String> getSIColNames() {
        Vector<String> columnNames = null;
        try {
            columnNames = new Vector<>();
            this.subjMarks.get(0).stream().map(str -> str.trim()).forEach(columnNames::add);
        } catch (java.lang.IndexOutOfBoundsException e) {
            //System.out.println(this);
            return null;
        }
        return columnNames;
    }

    /**
     *
     * @return
     */
    public Vector<Vector<String>> getSIRow() {
        Vector<Vector<String>> rowData = new Vector<>();
        for (int i = 1; i < subjMarks.size(); i++) {
            Vector<String> rowvec = new Vector<>();
            subjMarks.get(i).stream().map(str -> str.trim()).forEachOrdered(rowvec::add);
            rowData.add(rowvec);

        }
        return rowData;
    }

    /**
     *
     * @param resList
     * @return
     */
    public static String totalWise(List<CBSEResult> resList) {
        TextTableList tableList = new TextTableList(4, "Roll Number", "Name", "Total", "Percentage");
        resList.
                stream().
                sorted((res1, res2) -> res2.getBestof5() - res1.getBestof5()).
                forEach(res -> tableList
                        .addRow(res.getRegno(), res.getName(), res.getBestof5() + "", (res.getBestof5() / 5.00) + ""));

        return tableList.toString();
    }

    /**
     *
     * @param resList
     * @return
     */
    public static String subjectWise(List<CBSEResult> resList) {
        List<Subject> sublist = new java.util.ArrayList<>();
        resList.parallelStream().map(res -> res.getMainSubjects()).forEach(sublist::addAll);

        Map<String, List<Subject>> subjectsTable = sublist.parallelStream().collect(Collectors
                .groupingBy(Subject::getName));

        TextTableList tableList = new TextTableList(3, "Roll Number", "Student Name", "Marks");

        subjectsTable.keySet().stream().map((key) -> subjectsTable.get(key))
                .forEachOrdered(subs -> {
                    Subject get = subs.get(0);
                    tableList.addRow("---------", "-----------", "---------");
                    tableList.addRow("Subject :", get.getName(), "");

                    subs.parallelStream().
                    sorted((a, b) -> {
                        return b.getMarks() - a.getMarks();
                    }).
                    forEachOrdered(sub -> {
                        CBSEResult res = sub.getRes();
                        tableList.addRow(res.getRegno(), res.getName(), sub.getMarks() + "");

                    });
                });
        return tableList.toString();

    }

}

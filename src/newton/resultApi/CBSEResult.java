/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Meghdut Mandal
 */
@SuppressWarnings("UseOfObsoleteCollectionType")
public class CBSEResult {

    private String name;
    private String regno;
    private String status;//  Pass ,fail ,comp, improvement
    private List<List<String>> perInfo;
    private List<List<String>> subjMarks;
    ////6625902
    // sx 08423
    // centr 6219
    private List<newton.resultApi.Subject> subjects;
    /**
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
     * @param perInfo
     * @param subjMarks
     */
    public CBSEResult(List<List<String>> perInfo, List<List<String>> subjMarks) {
        this.perInfo = perInfo;

        this.subjMarks = subjMarks;
        name = null;
        this.perInfo.forEach((s) -> {
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
     * @param args
     */
    public static void main(String[] args) {

        File fi = new File("E:\\CBSE\\HSMSCBSE2016\\");
        HtmlUnitClient client = new HtmlUnitClient();
        String totalWise = client.totalWise(Arrays.asList(Objects.requireNonNull(fi.listFiles())), null);
        System.out.println(totalWise);
    }

    /**
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
     * @param resList
     * @return
     */
    public static String subjectWise(List<CBSEResult> resList) {
        List<Subject> sublist = new java.util.ArrayList<>();
        resList.parallelStream().map(CBSEResult::getMainSubjects).forEach(sublist::addAll);

        Map<String, List<Subject>> subjectsTable = sublist.parallelStream().collect(Collectors
                                                                                            .groupingBy(Subject::getName));

        TextTableList tableList = new TextTableList(3, "Roll Number", "Student Name", "Marks");

        subjectsTable.keySet().stream().map(subjectsTable::get)
                .forEachOrdered(subs -> {
                    Subject get = subs.get(0);
                    tableList.addRow("---------", "-----------", "---------");
                    tableList.addRow("Subject :", get.getName(), "");

                    subs.parallelStream().
                            sorted((a, b) -> b.getMarks() - a.getMarks()).
                            forEachOrdered(sub -> {
                                CBSEResult res = sub.getRes();
                                tableList.addRow(res.getRegno(), res.getName(), sub.getMarks() + "");

                            });
                });
        return tableList.toString();

    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getRegno() {
        return regno;
    }

    /**
     * @param regno
     */
    public void setRegno(String regno) {
        this.regno = regno;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return
     */
    public List<List<String>> getPerInfo() {
        return perInfo;
    }

    /**
     * @param perInfo
     */
    public void setPerInfo(List<List<String>> perInfo) {
        this.perInfo = perInfo;
    }

    /**
     * @return
     */
    private List<List<String>> getSubjMarks() {
        return subjMarks;
    }

    /**
     * @param subjMarks
     */
    public void setSubjMarks(List<List<String>> subjMarks) {
        this.subjMarks = subjMarks;
    }

    /**
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
        mainSubjects.sort((a, b) -> b.getMarks() - a.getMarks());
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
     * @return
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * @param subjects
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * @return
     */
    public List<Subject> getMainSubjects() {

        List<Subject> subjs = new java.util.ArrayList<>();
        this.getSubjects().forEach((sub) -> {
            String name1 = sub.getName();
            if (name1 != null) {
                List<String> excludeList = Arrays.asList("WORK EXPERIENCE", "PHY & HEALTH EDUCA", "GENERAL STUDIES", "Additional Subject");

                if (excludeList.stream().noneMatch(name1::contains)) {
                    subjs.add(sub);
                }
            }

        });

        return subjs;
    }

    private void processSubject() {
        subjects = new java.util.ArrayList<>();
        int subNameIndex = 0;
        int SubMarksIndex = 0;
        int SubGradeIndex = 0;

        List<String> siColNames = this.getSIColNames();
        for (int i = 0; i < siColNames.size(); i++) {
            if (siColNames.get(i).contains("SUB NAME")) {
                subNameIndex = i;
            } else if (siColNames.get(i).contains("MARKS")) {
                SubMarksIndex = i;
            } else if (siColNames.get(i).contains("GRADE")) {
                SubGradeIndex = i;
            }
        }
        final List<List<String>> subjMarks = getSubjMarks();
        for (int i = 1; i < subjMarks.size(); i++) {

            List<String> singleSubject = subjMarks.get(i);
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
     * @return
     */
    public Vector<String> genPIColNames() {

        return perInfo.stream()
                .map(row -> row.get(0).trim())
                .collect(Collectors.toCollection(Vector::new));
    }

    /**
     * @return
     */
    public Vector<Vector<String>> getPIRow() {
        Vector<Vector<String>> rowData = new Vector<>();

        Vector<String> rows = new Vector<>();
        perInfo.forEach((row11) -> rows.add(row11.get(1).trim()));
        rowData.add(rows);
        return rowData;

    }

    /**
     * @return
     */
    public List<String> getSIColNames() {
        try {
            return this.subjMarks.get(0).stream().map(String::trim).collect(Collectors.toList());

        } catch (java.lang.IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * @return
     */
    public List<List<String>> getSIRow() {
        List<List<String>> rowData = IntStream.range(1, subjMarks.size())
                .mapToObj(i -> subjMarks.get(i).stream()
                        .map(String::trim)
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toList());
        return rowData;
    }

}

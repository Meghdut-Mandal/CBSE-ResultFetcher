package newton.test;


import newton.resultApi.CBSEResult;
import newton.resultApi.ResultClient;
import newton.resultApi.Subject;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Analyser {
    public static void maing(String[] args) {
        File file = new File("C:\\myFiles\\devlp\\ResultsFetcher\\Results\\2011\\16\\161\\1610000.html");
        System.out.println(" page " + ResultClient.readFiletoText(file));


    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\myFiles\\devlp\\ResultsFetcher\\Results\\2011\\16");
        listFiles(file.toPath()).stream().forEach(System.out::println);
    }

    private static List<Path> listFiles(Path path) throws IOException {
        Deque<Path> stack = new ArrayDeque<>();
        final List<Path> files = new LinkedList<>();

        stack.push(path);

        while (!stack.isEmpty()) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(stack.pop());
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    stack.push(entry);
                } else {
                    files.add(entry);
                }
            }
            stream.close();
        }

        return files;
    }

    public static void maingh(String[] args) throws IOException {
        String str = "C:\\myFiles\\devlp\\ResultsFetcher\\Results\\2011\\16\\161\\";
        File file = new File(str);
        List<CBSEResult> collect = Files.list(file.toPath()).map(Path::toFile)
                .map(ResultClient::readFiletoText)
                .filter(kl -> !kl.endsWith("Not found"))
                .map(ResultClient::getResult)
                .filter(p -> p != null)
                .collect(Collectors.toList());
        Map<Integer, Long> frequencyTable = frequency(collect);
        frequencyTable.entrySet().stream()
                .map(en -> en.getKey() + ";" + en.getValue())
                .forEach(System.out::println);
        //frequencyTable.forEach((key, value) -> System.out.println(key + ";" + value));

    }

    public static Map<Integer, Long> frequency(List<CBSEResult> results) {
        return results.stream().parallel().map(CBSEResult::getMainSubjects).flatMap(List<Subject>::stream).map(Subject::getMarks).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}

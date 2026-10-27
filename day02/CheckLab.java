/*
 * SYNKOC Java Full Stack · Core · Day 02 · Lab checker.
 * Put this file in the same folder as your lab programs, then run:
 *     java CheckLab.java "Your Full Name"
 * It compiles and runs each of your programs with test inputs, and compares what they print.
 * It only reads your files; it never changes them. Copy everything it prints into the Day 02 Lab page.
 */
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

public class CheckLab {
    static final String LESSON = "JFS-M1-02";
    static final String[] TASKS = {"A1", "A2", "A3", "A4", "B", "C"};

    static String codeFor(String name, String task) {
        String s = String.join(" ", name.trim().toLowerCase(Locale.ROOT).split("\\s+")) + "|" + LESSON + "|" + task;
        long h = 0x811C9DC5L;
        for (int i = 0; i < s.length(); i++) {
            h ^= s.charAt(i);
            h = (h * 16777619L) & 0xFFFFFFFFL;
        }
        String out = Long.toString(h, 36).toUpperCase(Locale.ROOT);
        while (out.length() < 7) out = "0" + out;
        return out.substring(out.length() - 6);
    }

    /* the result of running one program */
    record Run(boolean compiled, String out, String err, boolean timedOut) {}

    static Run run(String file, String input) throws Exception {
        String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        ProcessBuilder pb = new ProcessBuilder(java, "-Duser.language=en", "-Duser.country=IN", file);
        Process p = pb.start();
        try (Writer w = new OutputStreamWriter(p.getOutputStream(), StandardCharsets.UTF_8)) { w.write(input); } catch (IOException e) { }
        ByteArrayOutputStream o = new ByteArrayOutputStream(), e = new ByteArrayOutputStream();
        Thread to = new Thread(() -> { try { p.getInputStream().transferTo(o); } catch (IOException x) { } });
        Thread te = new Thread(() -> { try { p.getErrorStream().transferTo(e); } catch (IOException x) { } });
        to.start(); te.start();
        boolean done = p.waitFor(20, TimeUnit.SECONDS);
        if (!done) p.destroyForcibly();
        to.join(2000); te.join(2000);
        String err = e.toString(StandardCharsets.UTF_8);
        boolean compiled = !err.contains("error: compilation failed");
        return new Run(compiled, o.toString(StandardCharsets.UTF_8).replace("\r", ""), err, !done);
    }

    static String norm(String s) { return s.trim().replaceAll("\\s+", " "); }
    static List<String> lines(String s) {
        List<String> r = new ArrayList<>();
        for (String l : s.split("\n")) if (!l.isBlank()) r.add(norm(l));
        return r;
    }
    static String firstErr(String err) {
        for (String l : err.split("\n")) if (l.contains("error:") && !l.contains("compilation failed")) return l.trim();
        for (String l : err.split("\n")) if (l.startsWith("Exception")) return l.trim();
        return err.trim().split("\n")[0];
    }
    /* runs the file, and returns null if it ran, or a tip saying why it did not */
    static String problem(String file, Run r) {
        if (r.timedOut) return file + " did not finish in 20 seconds. Does it wait for more input than the test gives it?";
        if (!r.compiled) return file + " does not compile: " + firstErr(r.err);
        if (r.err.contains("Exception")) return file + " crashed: " + firstErr(r.err);
        return null;
    }
    static String src(String file) throws IOException { return Files.readString(Path.of(file)); }
    static String noComments(String s) { return s.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\n]*", ""); }

    static List<String> checkA1() throws Exception {
        List<String> t = new ArrayList<>(); String f = "Variables.java";
        if (!Files.exists(Path.of(f))) { t.add("Create " + f + "."); return t; }
        String s = noComments(src(f));
        for (String type : new String[]{"String", "int", "double", "boolean", "char"})
            if (!Pattern.compile("\\b" + type + "\\s+\\w+\\s*=").matcher(s).find()) t.add("Declare a " + type + " variable, like: " + type + " name = ...;");
        if (s.contains("\"Course: Java Full Stack\"")) t.add("Print the variables, not fixed text: \"Course: \" + course");
        Run r = run(f, ""); String p = problem(f, r); if (p != null) { t.add(p); return t; }
        List<String> want = List.of("Course: Java Full Stack", "Seats: 30", "Fee: 14999.0", "Open: true", "Grade: A");
        if (!lines(r.out).equals(want)) t.add("Variables.java should print exactly these 5 lines: " + String.join(" | ", want) + ". It printed: " + String.join(" | ", lines(r.out)));
        return t;
    }

    static List<String> checkA2() throws Exception {
        List<String> t = new ArrayList<>(); String f = "Greeting.java";
        if (!Files.exists(Path.of(f))) { t.add("Create " + f + "."); return t; }
        String s = noComments(src(f));
        if (!s.contains("Scanner")) t.add("Read the input with a Scanner.");
        String[][] tests = {{"Priya Sharma\n21\n", "Hello, Priya Sharma! Next year you will be 22."}, {"Rahul\n19\n", "Hello, Rahul! Next year you will be 20."}};
        for (String[] k : tests) {
            Run r = run(f, k[0]); String p = problem(f, r); if (p != null) { t.add(p); return t; }
            if (!lines(r.out).stream().anyMatch(l -> l.endsWith(norm(k[1])))) { t.add("With the name " + k[0].split("\n")[0] + " and age " + k[0].split("\n")[1] + ", the last line should be: " + k[1]); break; }
        }
        return t;
    }

    static List<String> checkA3() throws Exception {
        List<String> t = new ArrayList<>(); String f = "Grade.java";
        if (!Files.exists(Path.of(f))) { t.add("Create " + f + "."); return t; }
        if (!noComments(src(f)).contains("else")) t.add("Use if, else if and else.");
        Object[][] tests = {{95, "Grade A"}, {90, "Grade A"}, {89, "Grade B"}, {75, "Grade B"}, {74, "Grade C"}, {60, "Grade C"}, {59, "Grade D"}, {40, "Grade D"}, {39, "Grade F"}, {0, "Grade F"}, {100, "Grade A"}, {101, "Invalid marks"}, {-5, "Invalid marks"}};
        for (Object[] k : tests) {
            Run r = run(f, k[0] + "\n"); String p = problem(f, r); if (p != null) { t.add(p); return t; }
            List<String> out = lines(r.out); String last = out.isEmpty() ? "" : out.get(out.size() - 1);
            if (!last.endsWith((String) k[1])) { t.add("For " + k[0] + " marks, the last line should end with \"" + k[1] + "\", but it was \"" + last + "\". Check the boundaries: 90, 75, 60, 40, and 0 to 100."); break; }
        }
        return t;
    }

    static List<String> checkA4() throws Exception {
        List<String> t = new ArrayList<>(); String f = "Receipt.java";
        if (!Files.exists(Path.of(f))) { t.add("Create " + f + "."); return t; }
        String s = noComments(src(f));
        if (!s.contains("printf") && !s.contains("String.format")) t.add("Format the amounts with printf (or String.format).");
        String[][] tests = {{"Java Full Stack\n14999\n", "Course : Java Full Stack", "Fee : Rs. 14,999.00", "GST : Rs. 2,699.82", "Total : Rs. 17,698.82"},
                            {"Cloud Basics\n4999.5\n", "Course : Cloud Basics", "Fee : Rs. 4,999.50", "GST : Rs. 899.91", "Total : Rs. 5,899.41"}};
        for (String[] k : tests) {
            Run r = run(f, k[0]); String p = problem(f, r); if (p != null) { t.add(p); return t; }
            List<String> out = lines(r.out);
            for (int i = 1; i < k.length; i++) {
                final String want = k[i];
                if (out.stream().noneMatch(l -> l.endsWith(want))) { t.add("For the course " + k[0].split("\n")[0] + " with fee " + k[0].split("\n")[1] + ", a line should read: " + want + " (spaces before the colon may differ)"); return t; }
            }
        }
        return t;
    }

    /* Part B: the rules from the lab page, worked out independently */
    static String money(double v) { return String.format(Locale.forLanguageTag("en-IN"), "%,.2f", v); }
    static List<String> expectB(int course, int months, String student) {
        String[] names = {"Java Full Stack", "Data Science", "Cloud Basics"}; double[] fees = {14999, 12999, 4999};
        if (course < 1 || course > 3) return List.of("Invalid course");
        if (months != 1 && months != 3 && months != 6) return List.of("Invalid plan");
        double fee = fees[course - 1], disc = student.equalsIgnoreCase("yes") ? fee * 0.10 : 0;
        double sur = (fee - disc) * (months == 3 ? 0.02 : months == 6 ? 0.05 : 0);
        double gst = (fee - disc + sur) * 0.18, total = fee - disc + sur + gst;
        return List.of("Course : " + names[course - 1], "Fee : Rs. " + money(fee), "Discount : Rs. " + money(disc), "Surcharge : Rs. " + money(sur), "GST : Rs. " + money(gst), "Total : Rs. " + money(total), "Per month : Rs. " + money(total / months));
    }
    static List<String> checkB() throws Exception {
        List<String> t = new ArrayList<>(); String f = "FeeCalculator.java";
        if (!Files.exists(Path.of(f))) { t.add("Create " + f + "."); return t; }
        Object[][] tests = {{1, 1, "yes"}, {2, 3, "no"}, {3, 6, "YES"}, {1, 6, "no"}, {2, 1, "Yes"}, {5, 1, "no"}, {1, 4, "no"}};
        for (Object[] k : tests) {
            int c = (int) k[0], m = (int) k[1]; String st = (String) k[2];
            Run r = run(f, c + "\n" + m + "\n" + st + "\n"); String p = problem(f, r); if (p != null) { t.add(p); return t; }
            List<String> out = lines(r.out);
            for (String want : expectB(c, m, st))
                if (out.stream().noneMatch(l -> l.endsWith(norm(want)))) { t.add("For course " + c + ", " + m + " month(s), student \"" + st + "\": a line should read \"" + want + "\". Check the rules on the lab page."); return t; }
        }
        return t;
    }

    static List<String> checkC() throws Exception {
        List<String> t = new ArrayList<>(); String f = "MyApp.java";
        if (!Files.exists(Path.of(f))) { t.add("Create " + f + "."); return t; }
        String s = noComments(src(f));
        if (!Pattern.compile("\\bString\\s+\\w+\\s*=").matcher(s).find()) t.add("Store your app's name in a String variable.");
        if (!Pattern.compile("\\bint\\s+\\w+\\s*=").matcher(s).find()) t.add("Store the number of pages in an int variable.");
        if (!s.contains("printf")) t.add("Print at least one line with printf.");
        Run r = run(f, ""); String p = problem(f, r); if (p != null) { t.add(p); return t; }
        List<String> out = lines(r.out); int n = 1;
        for (String l : out) if (l.matches("^" + n + "[.)] +\\S.{2,}")) n++;
        if (n <= 5) t.add("Print your five pages as a numbered list, 1. to 5., each with a page name (found " + (n - 1) + ").");
        if (out.stream().noneMatch(l -> l.toLowerCase().matches(".*pages?\\D*5\\b.*"))) t.add("Print the number of pages, for example: Pages: 5");
        if (out.size() < 8) t.add("Print at least 8 lines: the app name, its tagline, the page count, and the five pages.");
        return t;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args[0].trim().length() < 2) { System.out.println("Usage: java CheckLab.java \"Your Full Name\""); return; }
        String name = String.join(" ", args[0].trim().split("\\s+"));
        Map<String, List<String>> res = new LinkedHashMap<>();
        res.put("A1", checkA1()); res.put("A2", checkA2()); res.put("A3", checkA3()); res.put("A4", checkA4()); res.put("B", checkB()); res.put("C", checkC());
        System.out.println("=".repeat(60));
        System.out.println("SYNKOC-CHECK Day02-Lab name=" + name);
        for (String task : TASKS) {
            List<String> t = res.get(task);
            if (!t.isEmpty()) { System.out.println(task + " NOT YET"); for (String x : t) System.out.println("    - " + x); }
            else System.out.println(task + " PASS " + codeFor(name, task));
        }
        System.out.println("=".repeat(60));
        System.out.println("Copy everything between the lines into the lab page.");
    }
}

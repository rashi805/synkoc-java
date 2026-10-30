/*
 * SYNKOC Java Full Stack · Core · Day 09 · Lab checker.
 * Put this file in your day09 folder, next to the hello folder and your own app's folder.
 * Start your apps first (each one in its own terminal: ./mvnw spring-boot:run), then run:
 *     java CheckLab.java "Your Full Name"
 * It reads your project files and sends requests to your running apps, like a browser would.
 * It only reads; it never changes anything. Copy everything it prints into the Day 09 Lab page.
 */
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class CheckLab {
    static final String LESSON = "JFS-M4-02";
    static final String[] TASKS = {"A1", "A2", "A3", "A4", "B", "C"};
    static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4)).build();

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

    /* ---------- small helpers ---------- */
    record Resp(int status, String type, String body) {}
    static Resp get(int port, String path) {
        try {
            HttpRequest rq = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).timeout(Duration.ofSeconds(8)).header("Accept", "*/*").GET().build();
            HttpResponse<String> r = HTTP.send(rq, HttpResponse.BodyHandlers.ofString());
            return new Resp(r.statusCode(), r.headers().firstValue("Content-Type").orElse(""), r.body());
        } catch (IOException | InterruptedException e) { return null; }
    }
    static String read(Path p) { try { return Files.readString(p); } catch (IOException e) { return ""; } }
    static String noComments(String s) { return s.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\n]*", "").replaceAll("(?s)<!--.*?-->", ""); }
    static List<Path> javaFiles(Path project) {
        Path src = project.resolve("src/main/java");
        if (!Files.isDirectory(src)) return List.of();
        try (Stream<Path> s = Files.walk(src)) { return s.filter(p -> p.toString().endsWith(".java")).sorted().collect(Collectors.toList()); }
        catch (IOException e) { return List.of(); }
    }
    static Map<String, String> props(Path project) {
        Map<String, String> m = new HashMap<>();
        for (String l : read(project.resolve("src/main/resources/application.properties")).split("\n")) {
            l = l.trim(); if (l.isEmpty() || l.startsWith("#") || l.startsWith("!")) continue;
            int i = l.indexOf('='); if (i < 0) i = l.indexOf(':'); if (i < 0) continue;
            m.put(l.substring(0, i).trim(), l.substring(i + 1).trim());
        }
        return m;
    }
    static int port(Path project) {
        String v = props(project).get("server.port");
        try { return v == null ? 8080 : Integer.parseInt(v); } catch (NumberFormatException e) { return -1; }
    }
    static String tag(String pom, String tagName) {
        String body = pom.replaceAll("(?s)<parent>.*?</parent>", "").replaceAll("(?s)<dependencies>.*</dependencies>", "").replaceAll("(?s)<build>.*</build>", "");
        Matcher m = Pattern.compile("<" + tagName + ">\\s*([^<]+?)\\s*</" + tagName + ">").matcher(body);
        return m.find() ? m.group(1) : "";
    }
    static boolean isBoot(String pom) { return pom.contains("<artifactId>spring-boot-starter-parent</artifactId>"); }
    static boolean hasWeb(String pom) { return Pattern.compile("<artifactId>spring-boot-starter-web(mvc)?</artifactId>").matcher(pom).find(); }
    static String json(String body, String key) {
        Matcher m = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(body);
        return m.find() ? m.group(1) : null;
    }
    static String startTip(String folder, int port) {
        return "Nothing answered on port " + port + ". Start the app in its own terminal: cd " + folder + " && ./mvnw spring-boot:run , wait for \"Tomcat started on port " + port + "\", then run this checker again in another terminal.";
    }
    /* finds the main class: annotated with @SpringBootApplication and calling SpringApplication.run */
    static Path mainClass(Path project) {
        for (Path p : javaFiles(project)) { String s = noComments(read(p)); if (s.contains("@SpringBootApplication") && s.contains("SpringApplication.run")) return p; }
        return null;
    }
    static boolean mapped(Path project, String path) {
        Pattern p = Pattern.compile("@GetMapping\\(\\s*(?:value\\s*=\\s*|path\\s*=\\s*)?\\{?\\s*\"" + Pattern.quote(path) + "\"");
        for (Path f : javaFiles(project)) { String s = noComments(read(f)); if (s.contains("@RestController") && p.matcher(s).find()) return true; }
        return false;
    }

    /* ---------- the tasks ---------- */
    static final Path HELLO = Path.of("hello");
    static int helloPort = 8080;

    static List<String> checkA1() {
        List<String> t = new ArrayList<>();
        Path pomP = HELLO.resolve("pom.xml");
        if (!Files.exists(pomP)) { t.add("No hello/pom.xml here. Generate the project with the curl command in the lab, unzip it, and run this checker from the day09 folder (the one that contains hello)."); return t; }
        String pom = noComments(read(pomP));
        if (!isBoot(pom)) t.add("hello/pom.xml does not use spring-boot-starter-parent. Generate the project from start.spring.io (the lab's curl command).");
        if (!tag(pom, "groupId").equals("in.synkoc")) t.add("The project's groupId should be in.synkoc (it is \"" + tag(pom, "groupId") + "\").");
        if (!tag(pom, "artifactId").equals("hello")) t.add("The project's artifactId should be hello (it is \"" + tag(pom, "artifactId") + "\").");
        if (!hasWeb(pom)) t.add("Add the Spring Web dependency (spring-boot-starter-webmvc). Generate again with -d dependencies=web.");
        Matcher jv = Pattern.compile("<java.version>\\s*(\\d+)\\s*</java.version>").matcher(pom);
        if (!jv.find() || Integer.parseInt(jv.group(1)) < 17) t.add("Use Java 21 (or at least 17): <java.version>21</java.version> in pom.xml.");
        if (!Files.exists(HELLO.resolve("mvnw"))) t.add("The Maven wrapper hello/mvnw is missing. Unzip the whole project from start.spring.io.");
        Path m = mainClass(HELLO);
        if (m == null) t.add("No main class with @SpringBootApplication and SpringApplication.run(...) under hello/src/main/java.");
        else if (!m.toString().replace('\\', '/').endsWith("in/synkoc/hello/HelloApplication.java")) t.add("The main class should be src/main/java/in/synkoc/hello/HelloApplication.java (found " + m + ").");
        return t;
    }

    static List<String> checkA2() {
        List<String> t = new ArrayList<>();
        Path cls = HELLO.resolve("target/classes/in/synkoc/hello/HelloApplication.class");
        if (!Files.exists(cls)) t.add("hello/target/classes has no HelloApplication.class yet: run the app with Maven, cd hello && ./mvnw spring-boot:run");
        if (helloPort < 0) { t.add("server.port in application.properties is not a number."); return t; }
        Resp r = get(helloPort, "/synkoc-check-" + System.nanoTime());
        if (r == null) {
            Resp other = helloPort != 8080 ? get(8080, "/hello") : null;
            if (other != null && other.status() == 200) t.add("Your hello app is still answering on port 8080, but application.properties now says " + helloPort + ". Stop it (Ctrl+C) and start it again so it reads the new port.");
            else t.add(startTip("hello", helloPort));
        }
        return t;
    }

    static List<String> checkA3() {
        List<String> t = new ArrayList<>();
        if (!mapped(HELLO, "/hello")) t.add("Add a @RestController class in package in.synkoc.hello with a method marked @GetMapping(\"/hello\").");
        Resp r = get(helloPort, "/hello");
        if (r == null) { t.add(startTip("hello", helloPort)); return t; }
        if (r.status() == 404) t.add("GET /hello gives 404. Is the controller in the package in.synkoc.hello (or below it)? Did you restart the app after adding it?");
        else if (r.status() != 200) t.add("GET /hello answered with status " + r.status() + ", not 200.");
        else if (!r.body().trim().equals("Hello from SYNKOC!")) t.add("GET /hello should return exactly: Hello from SYNKOC!  (it returned: " + r.body().trim().replaceAll("\\s+", " ") + ")");
        return t;
    }

    static List<String> checkA4() {
        List<String> t = new ArrayList<>();
        String v = props(HELLO).get("server.port");
        if (v == null) { t.add("Set the port in hello/src/main/resources/application.properties: server.port=9090"); return t; }
        if (!v.equals("9090")) t.add("server.port should be 9090 (it is " + v + ").");
        else if (get(9090, "/hello") == null) t.add("application.properties says 9090, but nothing answers there. Stop the app (Ctrl+C) and start it again: ./mvnw spring-boot:run");
        return t;
    }

    static List<String> checkB() {
        List<String> t = new ArrayList<>();
        Resp a = get(helloPort, "/api/hello");
        if (a == null) { t.add(startTip("hello", helloPort)); return t; }
        if (a.status() != 200) t.add("GET /api/hello answered with status " + a.status() + ", not 200.");
        else if (!a.type().contains("json")) t.add("GET /api/hello should return JSON: return a record, like new Message(\"Hello from SYNKOC!\"), not a String.");
        else if (!"Hello from SYNKOC!".equals(json(a.body(), "message"))) t.add("GET /api/hello should return {\"message\":\"Hello from SYNKOC!\"} (it returned " + a.body().trim() + ").");
        Resp b = get(helloPort, "/api/time");
        if (b == null) return t;
        if (b.status() != 200) { t.add("GET /api/time answered with status " + b.status() + ", not 200."); return t; }
        if (!b.type().contains("json")) { t.add("GET /api/time should return JSON, with the fields date, time and zone."); return t; }
        String date = json(b.body(), "date"), time = json(b.body(), "time"), zone = json(b.body(), "zone");
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        if (!"Asia/Kolkata".equals(zone)) t.add("In /api/time, \"zone\" should be \"Asia/Kolkata\" (it is " + zone + ").");
        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) t.add("In /api/time, \"date\" should look like 2026-10-15 (pattern yyyy-MM-dd).");
        if (time == null || !time.matches("\\d{2}:\\d{2}:\\d{2}")) t.add("In /api/time, \"time\" should look like 14:05:09 (pattern HH:mm:ss).");
        if (t.isEmpty()) {
            LocalDateTime got = LocalDateTime.parse(date + "T" + time);
            long diff = Math.abs(Duration.between(got, now.toLocalDateTime()).getSeconds());
            if (diff > 120) t.add("/api/time says " + date + " " + time + ", but the time in India is now " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ". Use ZoneId.of(\"Asia/Kolkata\"): Codespaces runs on UTC.");
        }
        return t;
    }

    static List<String> checkC() {
        List<String> t = new ArrayList<>();
        List<Path> apps = new ArrayList<>();
        try (Stream<Path> s = Files.list(Path.of("."))) {
            s.filter(Files::isDirectory).filter(p -> !p.getFileName().toString().startsWith(".")).filter(p -> !p.getFileName().toString().equals("hello"))
             .filter(p -> Files.exists(p.resolve("pom.xml"))).sorted().forEach(apps::add);
        } catch (IOException e) { }
        if (apps.isEmpty()) { t.add("Create your own app's project in a new folder inside day09 (the lab's second curl command), next to hello."); return t; }
        Path app = apps.get(0); String folder = app.getFileName().toString();
        String pom = noComments(read(app.resolve("pom.xml")));
        if (!isBoot(pom) || !hasWeb(pom)) t.add(folder + "/pom.xml should be a Spring Boot project with Spring Web (generate it with -d dependencies=web).");
        if (mainClass(app) == null) t.add("No main class with @SpringBootApplication in " + folder + "/src/main/java.");
        String name = props(app).getOrDefault("spring.application.name", "");
        if (name.isEmpty()) t.add("Set spring.application.name in " + folder + "/src/main/resources/application.properties to your app's name.");
        int p = port(app);
        if (p == helloPort) t.add("Your app and hello both use port " + p + ". Give your app its own port, for example server.port=8080 (and hello 9090).");
        if (!mapped(app, "/")) t.add("Add a @RestController with a home endpoint: @GetMapping(\"/\").");
        if (!t.isEmpty()) return t;
        Resp r = get(p, "/");
        if (r == null) { t.add(startTip(folder, p)); return t; }
        if (r.status() != 200) { t.add("GET / on your app answered with status " + r.status() + ", not 200."); return t; }
        String body = r.body().replaceAll("<[^>]+>", " ").trim();
        String key = name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
        if (body.split("\\s+").length < 4) t.add("Your home endpoint should say what the app is, in at least 4 words.");
        else if (!key.isEmpty() && !body.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "").contains(key)) t.add("Your home endpoint's text should include your app's name, " + name + ".");
        return t;
    }

    public static void main(String[] args) {
        if (args.length < 1 || args[0].trim().length() < 2) { System.out.println("Usage: java CheckLab.java \"Your Full Name\""); return; }
        String name = String.join(" ", args[0].trim().split("\\s+"));
        helloPort = port(HELLO);
        Map<String, List<String>> res = new LinkedHashMap<>();
        res.put("A1", checkA1()); res.put("A2", checkA2()); res.put("A3", checkA3()); res.put("A4", checkA4()); res.put("B", checkB()); res.put("C", checkC());
        System.out.println("=".repeat(60));
        System.out.println("SYNKOC-CHECK Day09-Lab name=" + name);
        for (String task : TASKS) {
            List<String> t = res.get(task);
            if (!t.isEmpty()) { System.out.println(task + " NOT YET"); for (String x : t) System.out.println("    - " + x); }
            else System.out.println(task + " PASS " + codeFor(name, task));
        }
        System.out.println("=".repeat(60));
        System.out.println("Copy everything between the lines into the lab page.");
    }
}

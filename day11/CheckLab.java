/*  * SYNKOC Java Full Stack · Core · Day 11 · Lab checker.  * Put this file in your day11 folder, next to the events folder.  * Start both apps first, each in its own terminal (./mvnw spring-boot:run):  *   - events (Parts A and B) on port 8081  *   - your own app from Day 9 (Part C) on port 8080  * Then run:   java CheckLab.java "Your Full Name"  * It reads your project files and sends real requests to your running apps, including wrong ones on purpose.  * It only adds and then deletes one test event; it never changes your files. Copy everything it prints into the Day 11 Lab page.  */ import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
public class CheckLab {
    static final String LESSON = "JFS-M5-02";
    static final String[] TASKS = {
        "A1", "A2", "A3", "A4", "B", "C"
    }
    ;
    static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4)).build();
    static final int EV = 8081;
    static String codeFor(String name, String task) {
        String s = String.join(" ", name.trim().toLowerCase(Locale.ROOT).split("\\s+")) + "|" + LESSON + "|" + task;
        long h = 0x811C9DC5L;
        for (int i = 0;
        i < s.length();
        i++) {
            h ^= s.charAt(i);
            h = (h * 16777619L) & 0xFFFFFFFFL;
        }
        String out = Long.toString(h, 36).toUpperCase(Locale.ROOT);
        while (out.length() < 7) out = "0" + out;
        return out.substring(out.length() - 6);
    }
    /* ---------- HTTP ---------- */     record Resp(int status, String type, String location, String body) {
    }
    static Resp send(int port, String method, String path, String json, boolean asJson) {
        try {
            HttpRequest.Builder b = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).timeout(Duration.ofSeconds(8)).header("Accept", "application/json");
            if (json == null) b.method(method, HttpRequest.BodyPublishers.noBody());
            else {
                b.header("Content-Type", asJson ? "application/json" : "application/x-www-form-urlencoded");
                b.method(method, HttpRequest.BodyPublishers.ofString(json));
            }
            HttpResponse<String> r = HTTP.send(b.build(), HttpResponse.BodyHandlers.ofString());
            return new Resp(r.statusCode(), r.headers().firstValue("Content-Type").orElse(""), r.headers().firstValue("Location").orElse(""), r.body());
        }
        catch (IOException | InterruptedException e) {
            return null;
        }
    }
    static Resp get(int port, String path) {
        return send(port, "GET", path, null, true);
    }
    /* ---------- a tiny JSON reader (objects, arrays, strings, numbers, true/false/null) ---------- */     static Object parse(String s) {
        try {
            int[] i = {
                0
            }
            ;
            Object v = val(s, i);
            ws(s, i);
            return i[0] == s.length() ? v : null;
        }
        catch (RuntimeException e) {
            return null;
        }
    }
    static void ws(String s, int[] i) {
        while (i[0] < s.length() && Character.isWhitespace(s.charAt(i[0]))) i[0]++;
    }
    static Object val(String s, int[] i) {
        ws(s, i);
        char c = s.charAt(i[0]);
        if (c == '{') {
            Map<String, Object> m = new LinkedHashMap<>();
            i[0]++;
            ws(s, i);
            if (s.charAt(i[0]) == '}') {
                i[0]++;
                return m;
            }
            while (true) {
                ws(s, i);
                String k = (String) val(s, i);
                ws(s, i);
                if (s.charAt(i[0]++) != ':') throw new RuntimeException();
                m.put(k, val(s, i));
                ws(s, i);
                char d = s.charAt(i[0]++);
                if (d == '}') return m;
                if (d != ',') throw new RuntimeException();
            }
        }
        if (c == '[') {
            List<Object> l = new ArrayList<>();
            i[0]++;
            ws(s, i);
            if (s.charAt(i[0]) == ']') {
                i[0]++;
                return l;
            }
            while (true) {
                l.add(val(s, i));
                ws(s, i);
                char d = s.charAt(i[0]++);
                if (d == ']') return l;
                if (d != ',') throw new RuntimeException();
            }
        }
        if (c == '"') {
            StringBuilder b = new StringBuilder();
            i[0]++;
            while (true) {
                char d = s.charAt(i[0]++);
                if (d == '"') return b.toString();
                if (d == '\\') {
                    char e = s.charAt(i[0]++);
                    if (e == 'u') {
                        b.append((char) Integer.parseInt(s.substring(i[0], i[0] + 4), 16));
                        i[0] += 4;
                    }
                    else b.append(e == 'n' ? '\n' : e == 't' ? '\t' : e);
                }
                else b.append(d);
            }
        }
        Matcher m = Pattern.compile("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?|true|false|null").matcher(s);
        if (m.find(i[0]) && m.start() == i[0]) {
            i[0] = m.end();
            String t = m.group();
            return t.equals("null") ? null : t.equals("true") ? Boolean.TRUE : t.equals("false") ? Boolean.FALSE : (Object) Double.valueOf(t);
        }
        throw new RuntimeException();
    }
    @SuppressWarnings("unchecked") static Map<String, Object> obj(Object o) {
        return o instanceof Map ? (Map<String, Object>) o : null;
    }
    @SuppressWarnings("unchecked") static List<Object> arr(Object o) {
        return o instanceof List ? (List<Object>) o : null;
    }
    static Long num(Object o) {
        return o instanceof Double d && d == Math.rint(d) ? d.longValue() : null;
    }
    /* ---------- files ---------- */     static String read(Path p) {
        try {
            return Files.readString(p);
        }
        catch (IOException e) {
            return "";
        }
    }
    static String noComments(String s) {
        return s.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\n]*", "");
    }
    static List<Path> javaFiles(Path project) {
        Path src = project.resolve("src/main/java");
        if (!Files.isDirectory(src)) return List.of();
        try (Stream<Path> s = Files.walk(src)) {
            return s.filter(p -> p.toString().endsWith(".java")).sorted().collect(Collectors.toList());
        }
        catch (IOException e) {
            return List.of();
        }
    }
    static String allJava(Path project) {
        return javaFiles(project).stream().map(p -> noComments(read(p))).collect(Collectors.joining("\n"));
    }
    static Map<String, String> props(Path project) {
        Map<String, String> m = new HashMap<>();
        for (String l : read(project.resolve("src/main/resources/application.properties")).split("\n")) {
            l = l.trim();
            if (l.isEmpty() || l.startsWith("#") || l.startsWith("!")) continue;
            int i = l.indexOf('=');
            if (i < 0) continue;
            m.put(l.substring(0, i).trim(), l.substring(i + 1).trim());
        }
        return m;
    }
    static String startTip(String folder, int port) {
        return "Nothing answered on port " + port + ". Start the app in its own terminal (cd " + folder + " && ./mvnw spring-boot:run), wait for \"Tomcat started on port " + port + "\", then run this checker again.";
    }
    static String show(String s) {
        s = s.trim().replaceAll("\\s+", " ");
        return s.isEmpty() ? "(empty)" : s.length() > 140 ? s.substring(0, 140) + "…" : s;
    }
    /* ---------- the events API (Parts A and B) ---------- */     static final Path EVP = Path.of("events");
    static List<Object> list;
    static Long created = null;
    static Resp createdResp = null;
    static final Object[][] SEED = {
        {
            1L, "Hackathon", 120L
        }
        , {
            2L, "Robotics Workshop", 40L
        }
        , {
            3L, "Cultural Night", 300L
        }
    }
    ;
    static boolean isEvent(Object o, Object[] e) {
        Map<String, Object> m = obj(o);
        return m != null && e[0].equals(num(m.get("id"))) && e[1].equals(m.get("name")) && e[2].equals(num(m.get("seats")));
    }
    static List<String> checkA1() {
        List<String> t = new ArrayList<>();
        if (!Files.exists(EVP.resolve("pom.xml"))) {
            t.add("No events/pom.xml here. Generate the events project (the lab's curl command) and run this checker from the day11 folder.");
            return t;
        }
        String src = allJava(EVP);
        if (!Pattern.compile("\\brecord\\s+Event\\s*\\(").matcher(src).find()) t.add("Create a record called Event, with long id, String name and int seats.");
        if (!src.contains("@RequestMapping")) t.add("Put @RequestMapping(\"/api/events\") on your controller class.");
        if (!"8081".equals(props(EVP).get("server.port"))) t.add("Set server.port=8081 in events/src/main/resources/application.properties (your own app keeps 8080).");
        Resp r = get(EV, "/api/events");
        if (r == null) {
            t.add(startTip("events", EV));
            return t;
        }
        if (r.status() != 200) {
            t.add("GET /api/events answered " + r.status() + ", not 200.");
            return t;
        }
        list = arr(parse(r.body()));
        if (list == null || !r.type().contains("json")) {
            t.add("GET /api/events should return a JSON array of events. It returned: " + show(r.body()));
            return t;
        }
        for (Object[] e : SEED) if (list.stream().noneMatch(o -> isEvent(o, e))) t.add("The list should include {\"id\":" + e[0] + ",\"name\":\"" + e[1] + "\",\"seats\":" + e[2] + "}. Check the spelling and the field names id, name, seats.");
        return t;
    }
    static List<String> checkA2() {
        List<String> t = new ArrayList<>();
        Resp r = get(EV, "/api/events/2");
        if (r == null) {
            t.add(startTip("events", EV));
            return t;
        }
        if (r.status() != 200) {
            t.add("GET /api/events/2 answered " + r.status() + ", not 200. Add @GetMapping(\"/{id}\") with a @PathVariable long id.");
            return t;
        }
        if (!isEvent(parse(r.body()), SEED[1])) t.add("GET /api/events/2 should return {\"id\":2,\"name\":\"Robotics Workshop\",\"seats\":40}. It returned: " + show(r.body()));
        Resp r3 = get(EV, "/api/events/3");
        if (r3 != null && r3.status() == 200 && !isEvent(parse(r3.body()), SEED[2])) t.add("GET /api/events/3 should return Cultural Night: does your method use the id from the path?");
        return t;
    }
    static List<String> checkA3() {
        List<String> t = new ArrayList<>();
        if (!allJava(EVP).contains("@RequestBody")) t.add("Your POST method needs a parameter marked @RequestBody, for example EventRequest req.");
        createdResp = send(EV, "POST", "/api/events", "{\"name\": \"SYNKOC Check Event\", \"seats\": 25}", true);
        Resp r = createdResp;
        if (r == null) {
            t.add(startTip("events", EV));
            return t;
        }
        if (r.status() == 404 || r.status() == 405) {
            t.add("POST /api/events answered " + r.status() + ". Add a method with @PostMapping in the events controller.");
            return t;
        }
        Map<String, Object> m = obj(parse(r.body()));
        if (r.status() != 201 && r.status() != 200) {
            t.add("POST /api/events with {\"name\":\"SYNKOC Check Event\",\"seats\":25} answered " + r.status() + ": " + show(r.body()));
            return t;
        }
        if (m == null || !"SYNKOC Check Event".equals(m.get("name")) || !Long.valueOf(25).equals(num(m.get("seats"))) || num(m.get("id")) == null) {
            t.add("POST should return the new event as JSON, with its id, name and seats. It returned: " + show(r.body()));
            return t;
        }
        created = num(m.get("id"));
        if (list != null && list.stream().anyMatch(o -> obj(o) != null && created.equals(num(obj(o).get("id"))))) t.add("The new event got id " + created + ", which another event already has. Use a counter (nextId++) so every new event gets a new id.");
        Resp g = get(EV, "/api/events/" + created);
        if (g == null || g.status() != 200 || !"SYNKOC Check Event".equals(Optional.ofNullable(obj(parse(g.body()))).map(x -> x.get("name")).orElse(null))) t.add("After the POST, GET /api/events/" + created + " should return the new event. Did you add it to the list?");
        return t;
    }
    static List<String> checkA4() {
        List<String> t = new ArrayList<>();
        Resp r = get(EV, "/api/events/999999");
        if (r == null) {
            t.add(startTip("events", EV));
            return t;
        }
        if (r.status() != 404) t.add("GET /api/events/999999 should answer 404 (it answered " + r.status() + "). Return ResponseEntity.notFound().build() when no event matches.");
        if (createdResp != null) {
            if (createdResp.status() != 201) t.add("POST should answer 201 Created (it answered " + createdResp.status() + "). Return ResponseEntity.created(uri).body(event).");
            else if (created != null && !createdResp.location().endsWith("/api/events/" + created)) t.add("The 201 response needs a Location header: /api/events/" + created + " (it was \"" + createdResp.location() + "\").");
        }
        else t.add("Finish task 3 first: POST /api/events.");
        return t;
    }
    static List<String> checkB() {
        List<String> t = new ArrayList<>();
        Resp all = get(EV, "/api/events");
        if (all == null) {
            t.add(startTip("events", EV));
            return t;
        }
        List<Object> l = arr(parse(all.body()));
        Resp f = get(EV, "/api/events?minSeats=100");
        List<Object> fl = f == null ? null : arr(parse(f.body()));
        if (f == null || f.status() != 200 || fl == null) t.add("GET /api/events?minSeats=100 should answer 200 with a JSON array. Add @RequestParam(required = false) Integer minSeats.");
        else if (l != null) {
            List<Long> want = l.stream().map(CheckLab::obj).filter(Objects::nonNull).filter(m -> Optional.ofNullable(num(m.get("seats"))).orElse(0L) >= 100).map(m -> num(m.get("id"))).collect(Collectors.toList());
            List<Long> got = fl.stream().map(CheckLab::obj).filter(Objects::nonNull).map(m -> num(m.get("id"))).collect(Collectors.toList());
            if (!want.equals(got)) t.add("?minSeats=100 should return only the events with 100 seats or more (ids " + want + "). It returned ids " + got + ".");
            Resp none = get(EV, "/api/events?minSeats=100000");
            if (none == null || none.status() != 200 || !"[]".equals(none.body().replaceAll("\\s", ""))) t.add("?minSeats=100000 matches nothing: the answer should be an empty list [] with 200.");
        }
        Object[][] bad = {
            {
                "{\"seats\": 30}", "no name"
            }
            , {
                "{\"name\": \" \", \"seats\": 30}", "a blank name"
            }
            , {
                "{\"name\": \"Test\"}", "no seats"
            }
            , {
                "{\"name\": \"Test\", \"seats\": 0}", "0 seats"
            }
        }
        ;
        for (Object[] b : bad) {
            Resp r = send(EV, "POST", "/api/events", (String) b[0], true);
            if (r != null && r.status() != 400) {
                t.add("A POST with " + b[1] + " (" + b[0] + ") should answer 400, but answered " + r.status() + ". Name is required and seats must be 1 or more; use Integer seats so a missing value is null.");
                break;
            }
        }
        Resp nf = send(EV, "POST", "/api/events", "{\"name\": \"Test\", \"seats\": 10}", false);
        if (nf != null && nf.status() != 415) t.add("A POST sent as a form, not JSON, should be refused with 415 by Spring. Keep @RequestBody on the parameter.");
        if (created != null) {
            Resp d = send(EV, "DELETE", "/api/events/" + created, null, true);
            if (d == null || d.status() != 204) t.add("DELETE /api/events/" + created + " should answer 204 No Content (it answered " + (d == null ? "nothing" : d.status()) + "). Add @DeleteMapping(\"/{id}\").");
            else {
                Resp d2 = send(EV, "DELETE", "/api/events/" + created, null, true);
                if (d2 == null || d2.status() != 404) t.add("Deleting the same event again should answer 404 (it answered " + (d2 == null ? "nothing" : d2.status()) + ").");
            }
        }
        else t.add("Finish task 3 first: the checker deletes the event it created there.");
        return t;
    }
    /* ---------- Part C: the student's own app ---------- */     static Path ownApp() {
        List<Path> c = new ArrayList<>();
        for (Path dir : new Path[]{
            Path.of("."), Path.of("../day09")
        }
        ) {
            try (Stream<Path> s = Files.list(dir)) {
                s.filter(Files::isDirectory).filter(p -> !Set.of("events", "hello").contains(p.getFileName().toString())).filter(p -> !p.getFileName().toString().startsWith(".")).filter(p -> Files.exists(p.resolve("pom.xml"))).sorted().forEach(c::add);
            }
            catch (IOException e) {
            }
        }
        return c.isEmpty() ? null : c.get(0);
    }
    static List<String> checkC() {
        List<String> t = new ArrayList<>();
        Path app = ownApp();
        if (app == null) {
            t.add("Your own app's project was not found. Keep it in ../day09 (from Day 9) or in this day11 folder.");
            return t;
        }
        Map<String, String> p = props(app);
        String path = p.getOrDefault("synkoc.api.path", ""), sample = p.getOrDefault("synkoc.api.sample", "");
        int port = 8080;
        try {
            port = Integer.parseInt(p.getOrDefault("server.port", "8080"));
        }
        catch (NumberFormatException e) {
        }
        if (port == EV) t.add("Your app and events both use port 8081. Keep your app on 8080.");
        if (!path.matches("/api/[a-z][a-z0-9-]*")) t.add("In " + app + "/src/main/resources/application.properties, add the line synkoc.api.path=/api/<your resource>, for example synkoc.api.path=/api/events");
        if (obj(parse(sample)) == null) t.add("Also add synkoc.api.sample= with one JSON object your POST accepts, on one line, for example synkoc.api.sample={\"name\":\"Hackathon\",\"seats\":120}");
        String src = allJava(app);
        if (!src.contains("@PostMapping") || !src.contains("@RequestBody")) t.add("Add a POST method with @PostMapping and a @RequestBody record to your app's controller.");
        if (!src.contains("ResponseEntity")) t.add("Use ResponseEntity in your app's controller, for 404 and 201.");
        if (!t.isEmpty()) return t;
        Resp l = get(port, path);
        if (l == null) {
            t.add(startTip(app.toString(), port));
            return t;
        }
        if (l.status() != 200 || arr(parse(l.body())) == null) {
            t.add("GET " + path + " on your app should answer 200 with a JSON array (it answered " + l.status() + ": " + show(l.body()) + ").");
            return t;
        }
        Resp c = send(port, "POST", path, sample, true);
        Map<String, Object> m = c == null ? null : obj(parse(c.body()));
        if (c == null || c.status() != 201) {
            t.add("POST " + path + " with your sample should answer 201 (it answered " + (c == null ? "nothing" : c.status() + ": " + show(c.body())) + ").");
            return t;
        }
        if (m == null || num(m.get("id")) == null) {
            t.add("Your POST should return the created item as JSON, with an \"id\" chosen by the server.");
            return t;
        }
        Long id = num(m.get("id"));
        if (!c.location().endsWith(path + "/" + id)) t.add("Your 201 response needs a Location header: " + path + "/" + id + ".");
        Resp one = get(port, path + "/" + id);
        if (one == null || one.status() != 200 || obj(parse(one.body())) == null || !id.equals(num(obj(parse(one.body())).get("id")))) t.add("GET " + path + "/" + id + " should return the item you just created.");
        Resp nf = get(port, path + "/987654321");
        if (nf == null || nf.status() != 404) t.add("GET " + path + "/987654321 should answer 404 (it answered " + (nf == null ? "nothing" : nf.status()) + ").");
        Resp bad = send(port, "POST", path, "{}", true);
        if (bad == null || bad.status() != 400) t.add("A POST with an empty object {} should answer 400: check the required fields (it answered " + (bad == null ? "nothing" : bad.status()) + ").");
        return t;
    }
    public static void main(String[] args) {
        if (args.length < 1 || args[0].trim().length() < 2) {
            System.out.println("Usage: java CheckLab.java \"Your Full Name\"");
            return;
        }
        String name = String.join(" ", args[0].trim().split("\\s+"));
        Map<String, List<String>> res = new LinkedHashMap<>();
        res.put("A1", checkA1());
        res.put("A2", checkA2());
        res.put("A3", checkA3());
        res.put("A4", checkA4());
        res.put("B", checkB());
        res.put("C", checkC());
        System.out.println("=".repeat(60));
        System.out.println("SYNKOC-CHECK Day11-Lab name=" + name);
        for (String task : TASKS) {
            List<String> t = res.get(task);
            if (!t.isEmpty()) {
                System.out.println(task + " NOT YET");
                for (String x : t) System.out.println("    - " + x);
            }
            else System.out.println(task + " PASS " + codeFor(name, task));
        }
        System.out.println("=".repeat(60));
        System.out.println("Copy everything between the lines into the lab page.");
    }
}

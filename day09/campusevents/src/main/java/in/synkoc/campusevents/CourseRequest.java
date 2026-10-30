package in.synkoc.campusevents;

import java.math.BigDecimal;

public record CourseRequest(String title, BigDecimal fee, BigDecimal weeks, String level) {
}

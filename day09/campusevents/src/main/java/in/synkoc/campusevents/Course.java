package in.synkoc.campusevents;

import java.math.BigDecimal;

public record Course(long id, String title, BigDecimal fee, int weeks, String level) {
}

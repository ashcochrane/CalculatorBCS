package application.model;

import java.time.*;

import java.util.HashSet;
import java.util.Set;

public class Holidays {

    public Holidays() {
    }

    public Set<MonthDay> holidays = new HashSet<>();

    public Set<MonthDay> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<MonthDay> holidays) {
        this.holidays = holidays;
    }

    public void addHoliday(final MonthDay monthDay) {
        holidays.add(monthDay);
    }

    public boolean isHoliday(final LocalDate localDate) {
        return isWeekend(localDate) || holidays.contains(toMonthDay(localDate));
    }

    public void addHolidayFromTo(final LocalDate startInclusive, final LocalDate endInclusive) {
        for (LocalDate i = startInclusive; !i.isAfter(endInclusive); i = i.plusDays(1)) {
            holidays.add(toMonthDay(i));
        }
    }

    protected boolean isWeekend(final LocalDate localDate) {
        final DayOfWeek dow = localDate.getDayOfWeek();
        return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
    }

    public MonthDay toMonthDay(final LocalDate localDate) {
        return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
    }

    public LocalDate findEndDate(Job job) {
        int timePeriod = Integer.parseInt(job.getLmt().replaceAll("\\D", ""));
        LocalDate startDate = job.getStartDate();
        LocalDate tomorrow = startDate.plusDays(1);

        for (int i = 1; i < timePeriod; i++) {
            if (isHoliday(tomorrow)) {
                tomorrow = tomorrow.plusDays(1);
                i--;
            } else {
                tomorrow = tomorrow.plusDays(1);
            }
        }

        return tomorrow;

    }

}
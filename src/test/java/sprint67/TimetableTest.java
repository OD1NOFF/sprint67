package sprint67;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        assertEquals(singleTrainingSession, mondaySessions.get(0));

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        assertEquals(mondayChildTrainingSession, mondaySessions.get(0));
        assertEquals(13, mondaySessions.get(0).getTimeOfDay().getHours());
        assertEquals(0, mondaySessions.get(0).getTimeOfDay().getMinutes());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());

        assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours());
        assertEquals(0, thursdaySessions.get(0).getTimeOfDay().getMinutes());
        assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0));

        assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours());
        assertEquals(0, thursdaySessions.get(1).getTimeOfDay().getMinutes());
        assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1));

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> monday13Sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, monday13Sessions.size());
        assertEquals(singleTrainingSession, monday13Sessions.get(0));
        assertEquals(13, monday13Sessions.get(0).getTimeOfDay().getHours());
        assertEquals(0, monday13Sessions.get(0).getTimeOfDay().getMinutes());

    }

    @Test
    void testGetCountByCoaches_OneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Йога", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(3, result.get(coach));
    }

    @Test
    void testGetCountByCoaches_MultipleCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Coach coach3 = new Coach("Сидоров", "Сидор", "Сидорович");

        Group group = new Group("Фитнес", Age.ADULT, 45);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(9, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.SATURDAY, new TimeOfDay(11, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        assertEquals(3, result.size());

        List<Map.Entry<Coach, Integer>> entries = List.copyOf(result.entrySet());
        assertEquals(4, entries.get(0).getValue()); // coach1 первый (4 тренировки)
        assertEquals(2, entries.get(1).getValue()); // coach2 второй (2 тренировки)
        assertEquals(1, entries.get(2).getValue()); // coach3 третий (1 тренировка)

        assertEquals(4, result.get(coach1));
        assertEquals(2, result.get(coach2));
        assertEquals(1, result.get(coach3));
    }

    @Test
    void testGetCountByCoaches_EqualCounts() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Coach coach3 = new Coach("Сидоров", "Сидор", "Сидорович");

        Group group = new Group("Аэробика", Age.ADULT, 50);

        for (int i = 0; i < 2; i++) {
            timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                    DayOfWeek.MONDAY, new TimeOfDay(10 + i, 0)));
            timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                    DayOfWeek.TUESDAY, new TimeOfDay(10 + i, 0)));
            timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                    DayOfWeek.WEDNESDAY, new TimeOfDay(10 + i, 0)));
        }

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        assertEquals(3, result.size());
        assertEquals(2, result.get(coach1));
        assertEquals(2, result.get(coach2));
        assertEquals(2, result.get(coach3));
    }

    @Test
    void testGetCountByCoaches_EmptyTimetable() {
        Timetable timetable = new Timetable();

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCountByCoaches_MultipleSessionsSameDay() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Пилатес", Age.ADULT, 55);

        timetable.addNewTrainingSession(new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(18, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(2, result.get(coach));
    }

    @Test
    void testGetCountByCoaches_SameCoachDifferentGroups() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group1 = new Group("Йога для начинающих", Age.ADULT, 60);
        Group group2 = new Group("Йога продвинутых", Age.ADULT, 90);
        Group group3 = new Group("Детская йога", Age.CHILD, 45);

        timetable.addNewTrainingSession(new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group3, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(16, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(3, result.get(coach));
    }

}

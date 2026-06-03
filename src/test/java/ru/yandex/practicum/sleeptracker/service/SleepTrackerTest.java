package ru.yandex.practicum.sleeptracker.service;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.*;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleepTrackerTest {

    @Test
    void initWrongDateTimeFormat() {
        ArrayList<SleepingAnalysis> analyticFunctions = new ArrayList<>();
        analyticFunctions.add(new SessionNumberAnalysis());
        SleepTracker st = new SleepTracker("./src/test/resources/sleep_log_wrong_datetime.txt",
                analyticFunctions);
        st.init();

        List<SleepAnalysisResult<?>> results = st.getAnalytics();
        if (results.getFirst().getOrEmpty().isPresent()) {
            assertEquals(1,results.getFirst().getOrEmpty().get());
        } else {
            fail();
        }
    }

    @Test
    void initWrongSleepQuality() {
        ArrayList<SleepingAnalysis> analyticFunctions = new ArrayList<>();
        analyticFunctions.add(new SessionNumberAnalysis());
        SleepTracker st = new SleepTracker("./src/test/resources/sleep_log_wrong_quality.txt",
                analyticFunctions);
        st.init();

        List<SleepAnalysisResult<?>> results = st.getAnalytics();
        if (results.getFirst().getOrEmpty().isPresent()) {
            assertEquals(1,results.getFirst().getOrEmpty().get());
        } else {
            fail();
        }
    }

    @Test
    void initSameSessionStartTime() {
        ArrayList<SleepingAnalysis> analyticFunctions = new ArrayList<>();
        analyticFunctions.add(new SessionNumberAnalysis());
        SleepTracker st = new SleepTracker("./src/test/resources/sleep_log_check_doubles.txt",
                analyticFunctions);
        st.init();

        List<SleepAnalysisResult<?>> results = st.getAnalytics();
        if (results.getFirst().getOrEmpty().isPresent()) {
            assertEquals(1,results.getFirst().getOrEmpty().get());
        } else {
            fail();
        }
    }

    @Test
    void initSkipIncorrectLines() {
        ArrayList<SleepingAnalysis> analyticFunctions = new ArrayList<>();
        analyticFunctions.add(new SessionNumberAnalysis());
        SleepTracker st = new SleepTracker("./src/test/resources/sleep_log_wrong_lines_one_correct.txt",
                analyticFunctions);
        st.init();

        List<SleepAnalysisResult<?>> results = st.getAnalytics();
        if (results.getFirst().getOrEmpty().isPresent()) {
            assertEquals(1,results.getFirst().getOrEmpty().get());
        } else {
            fail();
        }
    }

    @Test
    void getAnalytics() {
        ArrayList<SleepingAnalysis> analyticFunctions = new ArrayList<>();
        analyticFunctions.add(new SessionNumberAnalysis());
        analyticFunctions.add(new SessionMaxDurationAnalysis());
        analyticFunctions.add(new SessionMinDurationAnalysis());
        analyticFunctions.add(new SessionAvgDurationAnalysis());
        analyticFunctions.add(new SessionBadQualityCounterAnalysis());
        analyticFunctions.add(new UserChronotypeAnalysis());
        analyticFunctions.add(new SessionSleeplessNightsAnalysis());

        SleepTracker st = new SleepTracker("./src/test/resources/sleep_log.txt",
                analyticFunctions);
        st.init();

        List<SleepAnalysisResult<?>> results = st.getAnalytics();
        assertEquals(7, results.size());
    }
}
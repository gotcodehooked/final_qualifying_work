package com.rseu.final_qualifying_work;


import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import com.rseu.final_qualifying_work.screens.MainActivity;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void clickMenuGroup() {
        onView(withId(R.id.groupsFragment)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.groupFloatingActionButton)).perform(click());
    }

    @Test
    public void clickMenuLesson() {
        onView(withId(R.id.lessonsFragment)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.fb_lessonsFragment)).perform(click());
    }

    @Test
    public void clickMenuDiscipline() {
        onView(withId(R.id.disciplineFragment)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.disciplineFloatingActionButton)).perform(click());
    }

    @Test
    public void clickMenuReport() {
        onView(withId(R.id.reportFragment)).perform(click()).check(matches(isDisplayed()));
    }


    @Test
    public void useAppContext() {
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals("com.rseu.final_qualifying_work", context.getPackageName());
    }
}

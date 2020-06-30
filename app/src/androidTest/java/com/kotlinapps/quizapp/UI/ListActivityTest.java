package com.kotlinapps.quizapp.UI;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.kotlinapps.quizapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    @Rule
    public IntentsTestRule<ListActivity> activityTestRule = new IntentsTestRule<>(ListActivity.class);

    @Test
    public void activityLaunch(){
        onView(withId(R.id.addState)).perform(click());
        onView(withId(R.id.titleTV)).check(matches(isDisplayed()));
    }
}

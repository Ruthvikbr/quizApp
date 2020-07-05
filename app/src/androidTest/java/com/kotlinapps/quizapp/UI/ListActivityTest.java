package com.kotlinapps.quizapp.UI;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import com.kotlinapps.quizapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    @Rule
    public IntentsTestRule<ListActivity> activityTestRule = new IntentsTestRule<>(ListActivity.class);

    @Test
    public void activityLaunch(){
        Espresso.onView(ViewMatchers.withId(R.id.addState)).perform(
                click()
        );
        intended(hasComponent(addActivity.class.getName()));

    }
}

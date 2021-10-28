package com.project.swapper;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GraphActivityTest {
    private Model model;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void graphActivityTest() {
        ViewInteraction view = onView(
                allOf(withId(R.id.graph),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        view.check(matches(isDisplayed()));
    }

    @Test
    public void testRefreshButtonExist() {
        model = Model.getInstance();
        if (model.networkIsConnectedToWIFI()) {
            // Click Main Switch Button
            ViewInteraction mainSwitch = onView(withId(R.id.Switch));
            mainSwitch.perform(click());

            // Click WiFi image button
            ViewInteraction wiFiImage = onView((withId(R.id.wifi)));
            wiFiImage.perform(click());

            // Click graph image button
            ViewInteraction graphButton = onView(withId(R.id.graph));
            graphButton.perform(click());

            // Check refresh button exist
            ViewInteraction refreshButton = onView(withId(R.id.refresh));
            refreshButton.check(matches(isDisplayed()));
        }
    }

    @Test
    public void testCorrectGraphViewTitleText() {
        model = Model.getInstance();
        if (model.networkIsConnectedToWIFI()) {
            // Click Main Switch Button
            ViewInteraction mainSwitch = onView(withId(R.id.Switch));
            mainSwitch.perform(click());

            // Click WiFi image button
            ViewInteraction wiFiImage = onView((withId(R.id.wifi)));
            wiFiImage.perform(click());

            // Click graph image button
            ViewInteraction graphButton = onView(withId(R.id.graph));
            graphButton.perform(click());

            // Check display the correct graph title
            ViewInteraction graphTitle = onView(withId(R.id.graphtitle));
            graphTitle.check(matches(withText("WiFi Strengths (100 - abs(dBm))")));
        }
    }

    @Test
    public void testGraphViewExist() {
        model = Model.getInstance();
        if (model.networkIsConnectedToWIFI()) {
            // Click Main Switch Button
            ViewInteraction mainSwitch = onView(withId(R.id.Switch));
            mainSwitch.perform(click());

            // Click WiFi image button
            ViewInteraction wiFiImage = onView((withId(R.id.wifi)));
            wiFiImage.perform(click());

            // Click graph image button
            ViewInteraction graphButton = onView(withId(R.id.graph));
            graphButton.perform(click());

            // Check graph view exist
            ViewInteraction graphView = onView(withId(R.id.graph));
            graphView.check(matches(isDisplayed()));
        }
    }
}

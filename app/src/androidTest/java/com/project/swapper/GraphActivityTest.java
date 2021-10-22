package com.project.swapper;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.project.swapper.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class GraphActivityTest {

    @Test
    public void testLaunchGraphActivity() {
        // Launch main activity
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        // Click image button
        ViewInteraction imageButton = onView(withId(R.id.graph_view));
        imageButton.perform(click());

        // Graph layout is displayed
        ViewInteraction linearLayout = onView(withId(R.id.graph_layout));
        linearLayout.check(matches(isDisplayed()));
    }

    @Test
    public void testWiFiStrengthsTextGraphActivity() {
        // Launch main activity
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        // Click image button
        ViewInteraction imageButton = onView(withId(R.id.graph_view));
        imageButton.perform(click());

        // Text is WiFi Strengths (dBm)
        ViewInteraction textView = onView(withId(R.id.graphtitle));
        textView.check(matches(withText("WiFi Strengths (dBm)")));
    }
}

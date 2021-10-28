package com.project.swapper;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.net.wifi.ScanResult;
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

import java.util.List;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddNetworkActivityTest {
    private Model model;
    private List<ScanResult> waps;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAllElementsExist() {
        model = Model.getInstance();
        if (model.networkIsConnectedToWIFI()) {

            // Scan the network
            waps = model.networkScan();

            // Click Main Switch Button
            ViewInteraction mainSwitch = onView(withId(R.id.Switch));
            mainSwitch.perform(click());

            // Click WiFi image button
            ViewInteraction wiFiImage = onView((withId(R.id.wifi)));
            wiFiImage.perform(click());

            // Get all ScanResult
            for (ScanResult s : model.networkScan()) {
                waps.add(s);
            }

            // Click first list
            DataInteraction linearLayout = onData(anything())
                    .inAdapterView(allOf(withId(R.id.network_list),
                            childAtPosition(
                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                    1)))
                    .atPosition(0);

            linearLayout.perform(click());

            // SSID Text displayed
            ViewInteraction textView = onView(withId(R.id.saved_ssid));
            textView.check(matches(isDisplayed()));

            // Password field displayed
            ViewInteraction passwordField = onView(withId(R.id.password));
            passwordField.check(matches(isDisplayed()));

            // Confirm button displayed
            ViewInteraction confirmButton = onView(withId(R.id.confirm));
            confirmButton.check(matches(isDisplayed()));
        }

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

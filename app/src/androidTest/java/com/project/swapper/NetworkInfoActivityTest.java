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
import com.project.swapper.view.NetworkInfoActivity;

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
public class NetworkInfoActivityTest {
    private Model model;
    private List<ScanResult> waps;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRefreshAndGraphViewButtonExist() {
        model = Model.getInstance();

        if (model.networkIsConnectedToWIFI()) {
            // Click Main Switch Button
            ViewInteraction switchCompat = onView(withId(R.id.Switch));
            switchCompat.perform(click());

            // Click WiFi image button
            ViewInteraction imageButton = onView((withId(R.id.wifi)));
            imageButton.perform(click());

            // Refresh button is displayed
            ViewInteraction refreshButton = onView(withId(R.id.refresh));
            refreshButton.check(matches(isDisplayed()));

            // Graph button is displayed
            ViewInteraction graphButton = onView(withId(R.id.graph));
            graphButton.check(matches(isDisplayed()));

        }
    }

    @Test
    public void testAllFirstListElementsExist() {
        model = Model.getInstance();
        waps = model.networkScan();

        if (model.networkIsConnectedToWIFI()) {
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

            // Save only the first value
            String ssidString = waps.get(0).SSID;
            String bssidString = waps.get(0).BSSID;
            int dBm = waps.get(0).level;
            String dBmString = dBm + "dBm";

            // Matches ssid from the model
            ViewInteraction ssidTextView = onView(
                    allOf(withId(R.id.SSID), withText(ssidString),
                            withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))));
            ssidTextView.check(matches(isDisplayed()));

            // Matches bssid from the model
            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.BSSID), withText(bssidString),
                            withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                            isDisplayed()));
            textView2.check(matches(isDisplayed()));

            // Matches imageView
            ViewInteraction imageView = onView(
                    allOf(withId(R.id.connected),
                            withParent(withParent(withId(R.id.network_list))),
                            isDisplayed()));
            imageView.check(matches(isDisplayed()));

            // Matches dBm from the model
            ViewInteraction textView3 = onView(
                    allOf(withId(R.id.signal), withText(dBmString),
                            withParent(withParent(withId(R.id.network_list))),
                            isDisplayed()));
            textView3.check(matches(isDisplayed()));

        }
    }




}

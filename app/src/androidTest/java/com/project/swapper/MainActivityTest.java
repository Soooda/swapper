package com.project.swapper;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.net.wifi.ScanResult;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Model model;
    private List<ScanResult> waps;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testMainComponentsExist() {
        // Logo
        ViewInteraction logo = onView(withId(R.id.logo_img));
        logo.check(matches(isDisplayed()));

        // App Switch
        ViewInteraction switch_ = onView(withId(R.id.Switch));
        switch_.check(matches(isDisplayed()));

        // Service Text
        ViewInteraction textView = onView(withId(R.id.service_status));
        textView.check(matches(isDisplayed()));

        // Low battery Switch
        ViewInteraction lowBatteryModeSwitch = onView(withId(R.id.battery));
        lowBatteryModeSwitch.check(matches(isDisplayed()));

        // WiFi imageButton
        ViewInteraction wiFiImageButton = onView(withId(R.id.wifi));
        wiFiImageButton.check(matches(isDisplayed()));
    }

    @Test
    public void testSwitchOnIfConnectedToWiFi() {
        model = Model.getInstance();

        if (model.networkIsConnectedToWIFI()) {
            ViewInteraction switchCompat = onView(withId(R.id.Switch));
            switchCompat.perform(click());
        }
    }

    @Test
    public void testDisplayWIFIIsDisabledIfSwitchIsClickedAndWiFiIsDisabled() {
        model = Model.getInstance();

        if (!model.networkIsConnectedToWIFI()) {
            ViewInteraction switchCompat = onView(withId(R.id.Switch));
            switchCompat.perform(click());

            ViewInteraction textView = onView(withId(R.id.service_status));
            textView.check(matches(withText("WIFI is disabled!")));
        }
    }

    @Test
    public void testClickLowBatteryMode() {
        ViewInteraction lowBatterySwitch = onView(withId(R.id.battery));
        lowBatterySwitch.perform(click());

        lowBatterySwitch.check(matches((isChecked())));
    }

    @Test
    public void testPleaseEnableWiFiToastIfNotConnectedToWiFI() {
        model = Model.getInstance();

        if (!model.networkIsConnectedToWIFI()) {
            ViewInteraction imageButton = onView(withId(R.id.wifi));
            imageButton.perform(click());

            MainActivity activity = activityTestRule.getActivity();

            String enableWiFi = "Please enable WIFI first!";

            onView(withText(enableWiFi)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        }
    }


}

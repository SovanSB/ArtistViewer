package com.yandex.mobile_school.artistviewer.tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;
import android.test.ActivityInstrumentationTestCase2;

import com.yandex.mobile_school.artistviewer.R;
import com.yandex.mobile_school.artistviewer.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Sovan on 24.04.2016.
 */
public class SimpleReloadTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;

    public SimpleReloadTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public SimpleReloadTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testDropUpdateDatabase() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
       // openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_drop))
                .perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_refresh))
                .perform(click());
        IdlingResource resource = new ElapsedTimeIdlingResource(1000);
        registerIdlingResources(resource);
        onView(withText("183"))
                .check(matches(withText("183 альбома • 450 песен")));
//        IdlingResource openAct = new ElapsedTimeIdlingResource(1000);
//        registerIdlingResources(openAct);
//        onView(withId(R.id.textViewGenres2))


    }

}

package mainpackage.traderevtest;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mainpackage.traderevtest.view.MainActivity;
import mainpackage.traderevtest.view.UnsplashPhotoDetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by deeppandya on 2017-11-30.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UnsplashDetailTest {
    @Rule
    public ActivityTestRule<UnsplashPhotoDetailActivity> mActivityRule =
            new ActivityTestRule(UnsplashPhotoDetailActivity.class);

    @Test
    public void checkForBackButton() {
        onView(withId(android.R.id.home)).perform(click());
    }

    @Test
    public void checkForViewPagerSwipe(){
        onView(withId(R.id.viewpager)).perform(swipeRight());
        onView(withId(R.id.viewpager)).perform(swipeLeft());
    }

    @Test
    public void checkForViewPagerSwipeToTheEnd() {
        onView(withId(R.id.viewpager)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
    }
}

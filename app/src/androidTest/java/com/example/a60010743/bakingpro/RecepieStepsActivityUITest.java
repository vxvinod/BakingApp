package com.example.a60010743.bakingpro;

import android.support.annotation.NonNull;
import android.support.test.espresso.core.internal.deps.guava.base.Preconditions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static android.support.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
public class RecepieStepsActivityUITest {
    @Rule
    public ActivityTestRule<MainActivity> recepieStepsActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void testRecipeIngListView(){
        onData(anything()).inAdapterView(withId(R.id.baking_grid_view)).atPosition(0).
                onChildView(withId(R.id.recepie_item_gridview)).perform(click());

        onView(withRecyclerView(R.id.ingRecyclerView).atPosition(1))
                .check(matches(hasDescendant(withText("unsalted butter: 226 G"))));

    }
    @Test
    public void testRecepieStepListView(){
        onData(anything()).inAdapterView(withId(R.id.baking_grid_view)).atPosition(0).
                onChildView(withId(R.id.recepie_item_gridview)).perform(click());

        onView(withRecyclerView(R.id.recepieStepsRv).atPosition(1))
                .check(matches(hasDescendant(withText("Starting prep"))));
    }

    @Test
    public void testRecepieDetailsCheck(){
        onData(anything()).inAdapterView(withId(R.id.baking_grid_view)).atPosition(0).
                onChildView(withId(R.id.recepie_item_gridview)).perform(click());

        onView(withRecyclerView(R.id.recepieStepsRv).atPosition(1))
                .perform(click());
        onView(withId(R.id.detail_video_view)).check(matches(isDisplayed()));
        onView(withId(R.id.shortDesc)).check(matches(isDisplayed()));
        onView(withId(R.id.description)).check(matches(isDisplayed()));

    }




    //helper methods
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        Preconditions.checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }


}

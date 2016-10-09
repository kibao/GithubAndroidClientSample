package pl.kibao.githubclient.utils;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;

public class ViewAssertion {
    /**
     * Asserts if view inside parent is visible
     *
     * @param viewId
     * @param parentViewId
     */
    public static void viewIsVisible(int viewId, int parentViewId) {
        viewVisibleCheck(viewId, parentViewId, false);
    }

    /**
     * Asserts if view inside parent is not visible
     *
     * @param viewId
     * @param parentViewId
     */
    public static void viewIsNotVisible(int viewId, int parentViewId) {
        viewVisibleCheck(viewId, parentViewId, false);
    }

    /**
     * Asserts if view inside parent view is visible or not
     *
     * @param viewId       View id
     * @param parentViewId Parent View id
     * @param isVisible    true if visible, otherwise false
     */
    public static void viewVisibleCheck(int viewId, int parentViewId, boolean isVisible) {
        android.support.test.espresso.ViewAssertion assertion;
        if (isVisible) {
            assertion = matches(isDisplayed());
        } else {
            assertion = doesNotExist();
        }
        onViewInsideParentVisible(viewId, parentViewId).check(assertion);
    }

    /**
     * Returns ViewInteraction for visible view (with viewId) inside parent view (with parentViewId)
     *
     * @param viewId       View id
     * @param parentViewId Parent View id
     * @return
     */
    public static ViewInteraction onViewInsideParentVisible(int viewId, int parentViewId) {
        Matcher<View> visible = withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);
        Matcher<View> parentMatcher = withParent(withId(parentViewId));
        return onView(Matchers.allOf(withId(viewId), parentMatcher, visible));
    }
}

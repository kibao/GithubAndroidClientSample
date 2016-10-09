package pl.kibao.githubclient.utils;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RecyclerViewMatcher {
    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public static RecyclerViewMatcher withRecyclerView(int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public Matcher<View> atPosition(final int position) {
        return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {
        return new TypeSafeMatcher<View>() {
            Resources resources;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                            recyclerViewId);
                    }
                }
                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {
                this.resources = view.getResources();
                if (childView == null) {
                    RecyclerView recyclerView =
                        (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        childView = recyclerView.getLayoutManager().findViewByPosition(position);
                        if (childView == null) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                if (targetViewId == -1) {
                    return view == childView;
                } else {
                    return view.equals(childView.findViewById(targetViewId));
                }
            }
        };
    }
}
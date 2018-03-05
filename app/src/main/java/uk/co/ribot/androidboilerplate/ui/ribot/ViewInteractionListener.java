package uk.co.ribot.androidboilerplate.ui.ribot;

import android.support.annotation.Nullable;
import android.view.View;

public interface ViewInteractionListener {
    void onItemClick(@Nullable String email, View view);
}

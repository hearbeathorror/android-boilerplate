package uk.co.ribot.androidboilerplate.ui.ribot;

import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface RiboMvpView  extends MvpView {
    void showDetails(Ribot ribot);

    void showError(String message);
}

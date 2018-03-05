package uk.co.ribot.androidboilerplate.ribot;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory;
import uk.co.ribot.androidboilerplate.ui.ribot.RiboMvpView;
import uk.co.ribot.androidboilerplate.ui.ribot.RiboPresenter;
import uk.co.ribot.androidboilerplate.util.RxSchedulersOverrideRule;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RiboPresenterTest {
    private static final String EMAIL = "email_test@ribot.co.uk";
    @Mock
    RiboMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;

    private RiboPresenter mRiboPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mRiboPresenter = new RiboPresenter(mMockDataManager);
        mRiboPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mRiboPresenter.detachView();
    }

    @Test
    public void loadRibotDetail() {
        Ribot ribot = TestDataFactory.makeRibot("_test");
        when(mMockDataManager.getRibot(EMAIL))
                .thenReturn(Observable.just(ribot));

        mRiboPresenter.loadRibotDetail(EMAIL);
        verify(mMockMainMvpView).showDetails(ribot);
        verify(mMockMainMvpView, never()).showError("");
    }

    @Test
    public void loadRibotsFails() {
        when(mMockDataManager.getRibot(EMAIL))
                .thenReturn(Observable.<Ribot>error(new RuntimeException("Error")));

        mRiboPresenter.loadRibotDetail(EMAIL);
        verify(mMockMainMvpView).showError("Error");
        verify(mMockMainMvpView, never()).showDetails(ArgumentMatchers.<Ribot>any());
    }
}

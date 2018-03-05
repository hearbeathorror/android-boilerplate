package uk.co.ribot.androidboilerplate.ui.ribot;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.RxUtil;

@ConfigPersistent
public class RiboPresenter extends BasePresenter<RiboMvpView> {
    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public RiboPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(RiboMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void loadRibotDetail(@NonNull final String email) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.getRibot(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Ribot>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Ribot ribot) {
                        getMvpView().showDetails(ribot);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

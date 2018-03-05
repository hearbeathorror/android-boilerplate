package uk.co.ribot.androidboilerplate.ui.ribot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.util.ConstantsIntentExtra;
import uk.co.ribot.androidboilerplate.util.DialogFactory;
import uk.co.ribot.androidboilerplate.util.StringUtils;

public class RibotActivity extends BaseActivity implements RiboMvpView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txt_user_name) TextView txtUserName;
    @BindView(R.id.txt_email) TextView txtUserEmail;
    @BindView(R.id.txt_user_bio) TextView txtUserBio;
    @BindView(R.id.txt_birth_date) TextView txtBirthDate;
    @BindView(R.id.img_user) ImageView imgUser;
    @BindView(R.id.frame_image) View viewColor;
    @BindView(R.id.fab_favorite) FloatingActionButton fabFavorite;

    @Inject RiboPresenter riboPresenter;

    String email;

    public static Intent getStartIntent(Context context, String email) {
        Intent intent = new Intent(context, RibotActivity.class);
        intent.putExtra(ConstantsIntentExtra.EXTRA_RIBOT, email);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ribot);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        riboPresenter.attachView(this);

        email = getIntent().getStringExtra(ConstantsIntentExtra.EXTRA_RIBOT);

        if (TextUtils.isEmpty(email)) {
            showError("Value cannot be null!");
        }

        setupToolbar();
        riboPresenter.loadRibotDetail(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ribot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                // do nothing for now
                // call presenter methods
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        riboPresenter.detachView();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                supportFinishAfterTransition();
            }
        });
    }

    @Override
    public void showDetails(final Ribot ribot) {
        txtUserName.setText(StringUtils.getFullName(ribot.profile().name().first(), ribot.profile().name().last()));
        txtBirthDate.setText(StringUtils.getBirthDate(ribot.profile().dateOfBirth()));
        txtUserBio.setText(ribot.profile().bio());
        txtUserEmail.setText(ribot.profile().email());
        Glide.with(getApplicationContext())
                .load(ribot.profile().avatar())
                .apply(new RequestOptions()
                        .override(480, 180)
                        .placeholder(new ColorDrawable(Color.parseColor(ribot.profile().hexColor()))))
                .into(imgUser);

        viewColor.setBackgroundColor(Color.parseColor(ribot.profile().hexColor()));
    }

    @Override
    public void showError(final String message) {
        DialogFactory.createSimpleOkErrorDialog(this,"", message).show();
    }

    @OnClick(R.id.txt_email) void onEmailClicked() {
        final String email = txtUserEmail.getText().toString();
        if (!StringUtils.isEmpty(email)) {
            final Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(txtUserEmail.getText().toString()));

            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                ActivityCompat.startActivity(getApplicationContext(), emailIntent, null);
            } else {
                showError(getString(R.string.error_no_intent_found));
            }
        }
    }

    @OnClick(R.id.fab_favorite) void onFavClicked() {
        fabFavorite.setActivated(!fabFavorite.isActivated());
    }
}

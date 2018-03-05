package uk.co.ribot.androidboilerplate.ui.main;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.ui.ribot.ViewInteractionListener;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class RibotsAdapter extends RecyclerView.Adapter<RibotsAdapter.RibotViewHolder> {

    private List<Ribot> mRibots;
    private static final int SIZE = 120;
    private ViewInteractionListener listener;

    @Inject
    public RibotsAdapter() {
        mRibots = new ArrayList<>();
    }

    public void setViewInteractionListener(ViewInteractionListener listener) {
        this.listener = listener;
    }

    public void setRibots(List<Ribot> ribots) {
        mRibots = ribots;
    }

    @Override
    public RibotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ribot, parent, false);
        return new RibotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RibotViewHolder holder, int position) {
        Ribot ribot = mRibots.get(position);
        holder.nameTextView.setText(String.format("%s %s",
                ribot.profile().name().first(), ribot.profile().name().last()));
        holder.emailTextView.setText(ribot.profile().email());

        if (TextUtils.isEmpty(ribot.profile().avatar())) {
            holder.hexColorView.setBackgroundColor(Color.parseColor(ribot.profile().hexColor()));
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(ribot.profile().avatar())
                    .apply(new RequestOptions()
                            .placeholder(new ColorDrawable(Color.parseColor(ribot.profile().hexColor())))
                            .override(SIZE))
                    .into(holder.hexColorView);
        }
    }

    @Override
    public int getItemCount() {
        return mRibots.size();
    }

    class RibotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_hex_color) ImageView hexColorView;
        @BindView(R.id.text_name) TextView nameTextView;
        @BindView(R.id.text_email) TextView emailTextView;

        public RibotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_view) void onClick() {
            final int position = getAdapterPosition();
            if (position == NO_POSITION) {
                return;
            }
            listener.onItemClick(mRibots.get(position).profile().email(), itemView);
        }
    }
}

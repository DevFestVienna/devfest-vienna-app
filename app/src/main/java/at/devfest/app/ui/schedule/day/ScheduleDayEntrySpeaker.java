package at.devfest.app.ui.schedule.day;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import at.devfest.app.R;
import at.devfest.app.data.app.model.Speaker;
import at.devfest.app.ui.core.picasso.CircleTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleDayEntrySpeaker extends LinearLayout {

    @BindView(R.id.schedule_day_entry_speaker_photo)
    ImageView photo;
    @BindView(R.id.schedule_day_entry_speaker_name)
    TextView name;

    public ScheduleDayEntrySpeaker(Context context, Speaker speaker, Picasso picasso) {
        super(context);
        setOrientation(HORIZONTAL);
        int padding = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics()));
        setPadding(0, padding, 0, padding);

        LayoutInflater.from(context).inflate(R.layout.schedule_day_entry_speaker, this);
        ButterKnife.bind(this, this);
        bind(speaker, picasso);
    }

    private void bind(Speaker speaker, Picasso picasso) {
        String photoUrl = speaker.getPhoto();
        if (!TextUtils.isEmpty(photoUrl)) {
            Drawable placeholder = null;
            if (speaker.getThumbnail() != null) {
                byte[] decodedThumbnail = Base64.decode(speaker.getThumbnail(), 0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedThumbnail, 0, decodedThumbnail.length);
                placeholder = new BitmapDrawable(bitmap);
            }
            if (placeholder != null) {
                picasso.load(photoUrl).placeholder(placeholder).transform(new CircleTransformation()).into(photo);
            }
            else {
                picasso.load(photoUrl).transform(new CircleTransformation()).into(photo);
            }
        }

        name.setText(speaker.getName());
    }
}

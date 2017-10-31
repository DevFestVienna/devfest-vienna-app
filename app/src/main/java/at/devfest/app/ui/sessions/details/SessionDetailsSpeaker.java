package at.devfest.app.ui.sessions.details;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import at.devfest.app.R;
import at.devfest.app.data.app.model.Speaker;
import at.devfest.app.ui.core.picasso.CircleTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SessionDetailsSpeaker extends FrameLayout {

    @BindView(R.id.session_details_speaker_photo)
    ImageView photo;
    @BindView(R.id.session_details_speaker_name)
    TextView name;
    @BindView(R.id.session_details_speaker_title)
    TextView title;

    public SessionDetailsSpeaker(Context context, Speaker speaker, Picasso picasso) {
        super(context);

        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        setForeground(ta.getDrawable(0));
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.session_details_speaker, this);
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
        title.setText(speaker.getTitle());
    }
}

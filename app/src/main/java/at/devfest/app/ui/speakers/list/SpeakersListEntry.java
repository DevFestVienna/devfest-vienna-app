package at.devfest.app.ui.speakers.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import at.devfest.app.R;
import at.devfest.app.data.app.model.Speaker;
import at.devfest.app.ui.core.recyclerview.BaseViewHolder;
import butterknife.BindView;

public class SpeakersListEntry extends BaseViewHolder {

    private final Picasso picasso;
    @BindView(R.id.speakers_list_entry_photo)
    ImageView photo;
    @BindView(R.id.speakers_list_entry_name)
    TextView name;

    public SpeakersListEntry(ViewGroup parent, Picasso picasso) {
        super(parent, R.layout.speakers_list_entry);
        this.picasso = picasso;
    }

    public void bindSpeaker(Speaker speaker) {
        String photoUrl = speaker.getPhoto();
        if (TextUtils.isEmpty(photoUrl)) {
            photo.setImageDrawable(null);
        } else {
            Drawable placeholder = null;
            if (speaker.getThumbnail() != null) {
                byte[] decodedThumbnail = Base64.decode(speaker.getThumbnail(), 0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedThumbnail, 0, decodedThumbnail.length);
                placeholder = new BitmapDrawable(bitmap);
            }
            if (placeholder != null) {
                picasso.load(photoUrl).placeholder(placeholder).into(photo);
            }
            else {
                picasso.load(photoUrl).into(photo);
            }
        }
        name.setText(speaker.getName());
    }
}

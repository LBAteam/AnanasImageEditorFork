package iamutkarshtiwari.github.io.ananas.editimage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import iamutkarshtiwari.github.io.ananas.R;
import iamutkarshtiwari.github.io.ananas.editimage.fragment.StickerFragment;
import iamutkarshtiwari.github.io.ananas.editimage.model.StickerCatalog;
import iamutkarshtiwari.github.io.ananas.editimage.model.StickerPack;

public class StickerTypeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final StickerPack[] stickerPacks;
    private final StickerFragment stickerFragment;
    private final ImageClick imageClick = new ImageClick();

    public StickerTypeAdapter(StickerFragment fragment) {
        super();
        this.stickerFragment = fragment;
        stickerPacks = StickerCatalog.getPacks();
    }

    public class ImageHolder extends ViewHolder {
        public ImageView icon;
        public TextView text;

        ImageHolder(View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(R.id.icon);
            this.text = itemView.findViewById(R.id.text);
        }
    }

    @Override
    public int getItemCount() {
        return stickerPacks.length;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_sticker_type_item, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        ImageHolder imageHolder = (ImageHolder) holder;
        StickerPack stickerPack = stickerPacks[position];
        imageHolder.text.setText(stickerPack.getNameResId());
        imageHolder.text.setTag(stickerPack);
        imageHolder.text.setOnClickListener(imageClick);
    }

    private final class ImageClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            Object data = v.getTag();
            if (data instanceof StickerPack) {
                stickerFragment.showStickerDetails((StickerPack) data);
            }
        }
    }
}

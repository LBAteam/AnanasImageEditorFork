package iamutkarshtiwari.github.io.ananas.editimage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import iamutkarshtiwari.github.io.ananas.R;
import iamutkarshtiwari.github.io.ananas.editimage.adapter.viewholders.StickerViewHolder;
import iamutkarshtiwari.github.io.ananas.editimage.fragment.StickerFragment;

public class StickerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final StickerFragment stickerFragment;
    private final ImageClick imageClick = new ImageClick();
    private final List<Integer> stickerResIds = new ArrayList<>();

    public StickerAdapter(StickerFragment fragment) {
        super();
        this.stickerFragment = fragment;
    }

    @Override
    public int getItemCount() {
        return stickerResIds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_sticker_item, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        StickerViewHolder stickerViewHolder = (StickerViewHolder) viewHolder;
        int stickerResId = stickerResIds.get(position);

        stickerViewHolder.image.setImageResource(stickerResId);
        stickerViewHolder.image.setTag(stickerResId);
        stickerViewHolder.image.setOnClickListener(imageClick);
    }

    public void setStickers(@DrawableRes int[] newStickerResIds) {
        stickerResIds.clear();
        if (newStickerResIds != null) {
            for (int stickerResId : newStickerResIds) {
                stickerResIds.add(stickerResId);
            }
        }
        this.notifyDataSetChanged();
    }

    private final class ImageClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            Object data = v.getTag();
            if (data instanceof Integer) {
                stickerFragment.selectedStickerItem((Integer) data);
            }
        }
    }

}

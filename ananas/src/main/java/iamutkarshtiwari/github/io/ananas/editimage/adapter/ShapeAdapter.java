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
import iamutkarshtiwari.github.io.ananas.editimage.fragment.ShapeFragment;

public class ShapeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final ShapeFragment shapeFragment;
    private final ImageClick imageClick = new ImageClick();
    private final List<Integer> shapeResIds = new ArrayList<>();

    public ShapeAdapter(ShapeFragment fragment) {
        super();
        this.shapeFragment = fragment;
    }

    @Override
    public int getItemCount() {
        return shapeResIds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_shape_item, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        StickerViewHolder shapeViewHolder = (StickerViewHolder) viewHolder;
        int shapeResId = shapeResIds.get(position);

        shapeViewHolder.image.setImageResource(shapeResId);
        shapeViewHolder.image.setTag(shapeResId);
        shapeViewHolder.image.setOnClickListener(imageClick);
    }

    public void setShapes(@DrawableRes int[] newShapeResIds) {
        shapeResIds.clear();
        if (newShapeResIds != null) {
            for (int shapeResId : newShapeResIds) {
                shapeResIds.add(shapeResId);
            }
        }
        notifyDataSetChanged();
    }

    private final class ImageClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            Object data = v.getTag();
            if (data instanceof Integer) {
                shapeFragment.selectedShapeItem((Integer) data);
            }
        }
    }
}

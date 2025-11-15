package iamutkarshtiwari.github.io.ananas.editimage.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import iamutkarshtiwari.github.io.ananas.R;
import iamutkarshtiwari.github.io.ananas.editimage.adapter.viewholders.StickerViewHolder;
import iamutkarshtiwari.github.io.ananas.editimage.fragment.StickerFragment;

public class StickerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final StickerFragment stickerFragment;
    private final ImageClick imageClick = new ImageClick();
    private final List<String> pathList = new ArrayList<>();

    private static final Map<String, Integer> stickerMap = new HashMap<>();
    static {
        // type1
        stickerMap.put("type1_1", R.drawable.type1_1);
        stickerMap.put("type1_2", R.drawable.type1_2);
        stickerMap.put("type1_3", R.drawable.type1_3);
        stickerMap.put("type1_4", R.drawable.type1_4);
        stickerMap.put("type1_5", R.drawable.type1_5);

        // type2
        stickerMap.put("type2_1", R.drawable.type2_1);
        stickerMap.put("type2_2", R.drawable.type2_2);
        stickerMap.put("type2_3", R.drawable.type2_3);
        stickerMap.put("type2_4", R.drawable.type2_4);
        stickerMap.put("type2_5", R.drawable.type2_5);

        // type3
        stickerMap.put("type3_1", R.drawable.type3_1);
        stickerMap.put("type3_2", R.drawable.type3_2);
        stickerMap.put("type3_3", R.drawable.type3_3);
        stickerMap.put("type3_4", R.drawable.type3_4);
        stickerMap.put("type3_5", R.drawable.type3_5);

        // type4
        stickerMap.put("type4_1", R.drawable.type4_1);
        stickerMap.put("type4_2", R.drawable.type4_2);
        stickerMap.put("type4_3", R.drawable.type4_3);
        stickerMap.put("type4_4", R.drawable.type4_4);
        stickerMap.put("type4_5", R.drawable.type4_5);

        // type5
        stickerMap.put("type5_1", R.drawable.type5_1);
        stickerMap.put("type5_2", R.drawable.type5_2);
        stickerMap.put("type5_3", R.drawable.type5_3);

        // type6
        stickerMap.put("type6_1", R.drawable.type6_1);
        stickerMap.put("type6_2", R.drawable.type6_2);
        stickerMap.put("type6_3", R.drawable.type6_3);
        stickerMap.put("type6_4", R.drawable.type6_4);
        stickerMap.put("type6_5", R.drawable.type6_5);
    }

    public StickerAdapter(StickerFragment fragment) {
        super();
        this.stickerFragment = fragment;
    }

    @Override
    public int getItemCount() {
        return pathList.size();
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
        String path = pathList.get(position);

        Drawable drawable = loadStickerDrawable(path);
        stickerViewHolder.image.setImageDrawable(drawable);
        stickerViewHolder.image.setTag("drawable/" + path);
        stickerViewHolder.image.setOnClickListener(imageClick);
    }

    public void addStickerImages(String folderPath, int stickerCount) {
        pathList.clear();
        for (int i = 0; i < stickerCount; i++) {
            pathList.add(folderPath + "_" + (i + 1));
        }
        this.notifyDataSetChanged();
    }

    private final class ImageClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            String data = (String) v.getTag();
            stickerFragment.selectedStickerItem(data);
        }
    }

    private Drawable loadStickerDrawable(String name) {
        if (stickerFragment != null) {
            Context context = stickerFragment.getContext();
            if (context == null) {
                context = stickerFragment.getActivity();
            }
            if (context != null) {
                Resources res = context.getResources();
                String pkg = context.getPackageName();

                if (res != null) {
                    // Primary lookup: by name from the sticker map (e.g., type1_1)
                    Integer mappedResId = stickerMap.get(name);
                    if (mappedResId != null) {
                        try {
                            return AppCompatResources.getDrawable(context, mappedResId);
                        }
                        catch (Resources.NotFoundException ignored) {
                        }
                    }

                    // Secondary lookup: by name frm drawable exactly as constructed (e.g., type1_1)
                    int resId = res.getIdentifier(name, "drawable", pkg);
                    if (resId != 0) {
                        try {
                            return AppCompatResources.getDrawable(context, resId);
                        }
                        catch (Resources.NotFoundException ignored) {
                        }
                    }
                }

                // Fallback: placeholder so we never crash
                return AppCompatResources.getDrawable(context, R.drawable.ic_close);
            }
        }

        // Absolute last fallback
        return new ColorDrawable(Color.WHITE);
    }

}

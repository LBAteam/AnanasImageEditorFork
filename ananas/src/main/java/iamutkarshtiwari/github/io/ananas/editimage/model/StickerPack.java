package iamutkarshtiwari.github.io.ananas.editimage.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public final class StickerPack {
    @StringRes
    private final int nameResId;
    @DrawableRes
    private final int[] stickerResIds;

    public StickerPack(@StringRes int nameResId, @DrawableRes int... stickerResIds) {
        this.nameResId = nameResId;
        this.stickerResIds = stickerResIds;
    }

    @StringRes
    public int getNameResId() {
        return nameResId;
    }

    @DrawableRes
    public int[] getStickerResIds() {
        return stickerResIds.clone();
    }
}

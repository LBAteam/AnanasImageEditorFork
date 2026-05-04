package iamutkarshtiwari.github.io.ananas.editimage.model;

import iamutkarshtiwari.github.io.ananas.R;

public final class ShapeCatalog {
    private static final StickerPack[] PACKS = new StickerPack[]{
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_shape_pack_arrows,
                    R.drawable.shape_arrow_01,
                    R.drawable.shape_arrow_02,
                    R.drawable.shape_arrow_03,
                    R.drawable.shape_arrow_04,
                    R.drawable.shape_arrow_05,
                    R.drawable.shape_arrow_06,
                    R.drawable.shape_arrow_07,
                    R.drawable.shape_arrow_08,
                    R.drawable.shape_arrow_09,
                    R.drawable.shape_arrow_10
            ),
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_shape_pack_numbers,
                    R.drawable.shape_number_01,
                    R.drawable.shape_number_02,
                    R.drawable.shape_number_03,
                    R.drawable.shape_number_04,
                    R.drawable.shape_number_05,
                    R.drawable.shape_number_06,
                    R.drawable.shape_number_07,
                    R.drawable.shape_number_08,
                    R.drawable.shape_number_09,
                    R.drawable.shape_number_10,
                    R.drawable.shape_number_11,
                    R.drawable.shape_number_12,
                    R.drawable.shape_number_13,
                    R.drawable.shape_number_14,
                    R.drawable.shape_number_15,
                    R.drawable.shape_number_16,
                    R.drawable.shape_number_17,
                    R.drawable.shape_number_18,
                    R.drawable.shape_number_19,
                    R.drawable.shape_number_20,
                    R.drawable.shape_number_21,
                    R.drawable.shape_number_22,
                    R.drawable.shape_number_23,
                    R.drawable.shape_number_24
            ),
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_shape_pack_other,
                    R.drawable.shape_other_01,
                    R.drawable.shape_other_02,
                    R.drawable.shape_other_03,
                    R.drawable.shape_other_04,
                    R.drawable.shape_other_05,
                    R.drawable.shape_other_06,
                    R.drawable.shape_other_08,
                    R.drawable.shape_other_09,
                    R.drawable.shape_other_10,
                    R.drawable.shape_other_11,
                    R.drawable.shape_other_12,
                    R.drawable.shape_other_13,
                    R.drawable.shape_other_14,
                    R.drawable.shape_other_15,
                    R.drawable.shape_other_16,
                    R.drawable.shape_other_18,
                    R.drawable.shape_other_19,
                    R.drawable.shape_other_20
            )
    };

    private ShapeCatalog() {
    }

    public static StickerPack[] getPacks() {
        return PACKS.clone();
    }
}

package iamutkarshtiwari.github.io.ananas.editimage.model;

import iamutkarshtiwari.github.io.ananas.R;

public final class ShapeCatalog {
    private static final StickerPack[] PACKS = new StickerPack[]{
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_shape_pack_arrows,
                    R.drawable.shape_arrow_01, // red thick right
                    R.drawable.shape_arrow_02, // red thick both directions
                    R.drawable.shape_arrow_05, // red simple right
                    R.drawable.shape_arrow_06, // red simple thick both directions
                    R.drawable.shape_arrow_09, // red very thick right
                    R.drawable.shape_arrow_03, // blue thick right
                    R.drawable.shape_arrow_04, // blue thick both directions
                    R.drawable.shape_arrow_07, // blue simple right
                    R.drawable.shape_arrow_08, // blue simple thick both directions
                    R.drawable.shape_arrow_10  // blue very thick right
            ),
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_shape_pack_numbers,
                    R.drawable.shape_number_01, // red 1
                    R.drawable.shape_number_02, // red 2
                    R.drawable.shape_number_03, // red 3
                    R.drawable.shape_number_04, // red 4
                    R.drawable.shape_number_05, // red 5
                    R.drawable.shape_number_06, // red 6
                    R.drawable.shape_number_07, // red 7
                    R.drawable.shape_number_08, // red 8
                    R.drawable.shape_number_09, // red 9
                    R.drawable.shape_number_10, // red 0
                    R.drawable.shape_number_21, // red +
                    R.drawable.shape_number_22, // red -
                    R.drawable.shape_number_11, // blue 1
                    R.drawable.shape_number_12, // blue 2
                    R.drawable.shape_number_13, // blue 3
                    R.drawable.shape_number_14, // blue 4
                    R.drawable.shape_number_15, // blue 5
                    R.drawable.shape_number_16, // blue 6
                    R.drawable.shape_number_17, // blue 7
                    R.drawable.shape_number_18, // blue 8
                    R.drawable.shape_number_19, // blue 9
                    R.drawable.shape_number_20, // blue 0
                    R.drawable.shape_number_23, // blue +
                    R.drawable.shape_number_24  // blue -
            ),
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_shape_pack_other,
                    R.drawable.shape_other_01, // red circle
                    R.drawable.shape_other_02, // red ellipse
                    R.drawable.shape_other_03, // red square
                    R.drawable.shape_other_04, // red rectangle
                    R.drawable.shape_other_05, // red rounded square
                    R.drawable.shape_other_06, // red rounded rectangle
                    R.drawable.shape_other_08, // red triangle
                    R.drawable.shape_other_09, // red star
                    R.drawable.shape_other_10, // red pentagon
                    R.drawable.shape_other_11, // blue circle
                    R.drawable.shape_other_12, // blue ellipse
                    R.drawable.shape_other_13, // blue square
                    R.drawable.shape_other_14, // blue rectangle
                    R.drawable.shape_other_15, // blue rounded square
                    R.drawable.shape_other_16, // blue rounded rectangle
                    R.drawable.shape_other_18, // blue triangle
                    R.drawable.shape_other_19, // blue star
                    R.drawable.shape_other_20  // blue pentagon
            )
    };

    private ShapeCatalog() {
    }

    public static StickerPack[] getPacks() {
        return PACKS.clone();
    }
}

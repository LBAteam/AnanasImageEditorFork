package iamutkarshtiwari.github.io.ananas.editimage.model;

import iamutkarshtiwari.github.io.ananas.R;

public final class StickerCatalog {
    // Add bundled sticker packs here; user-facing pack names live in strings.xml.
    private static final StickerPack[] PACKS = new StickerPack[]{
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_sticker_pack_faces,
                    R.drawable.type1_1,
                    R.drawable.type1_2,
                    R.drawable.type1_3,
                    R.drawable.type1_4,
                    R.drawable.type1_5,
                    R.drawable.type2_1,
                    R.drawable.type2_2,
                    R.drawable.type2_3,
                    R.drawable.type2_4,
                    R.drawable.type2_5,
                    R.drawable.type3_1,
                    R.drawable.type3_2,
                    R.drawable.type3_3,
                    R.drawable.type3_4,
                    R.drawable.type3_5,
                    R.drawable.type6_1,
                    R.drawable.type6_2
            ),
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_sticker_pack_animals,
                    R.drawable.type4_1,
                    R.drawable.type6_3,
                    R.drawable.type6_4,
                    R.drawable.type6_5
            ),
            new StickerPack(
                    R.string.iamutkarshtiwari_github_io_ananas_sticker_pack_words,
                    R.drawable.type5_1,
                    R.drawable.type5_2,
                    R.drawable.type5_3,
                    R.drawable.type4_2,
                    R.drawable.type4_3,
                    R.drawable.type4_4,
                    R.drawable.type4_5
            )
    };

    private StickerCatalog() {
    }

    public static StickerPack[] getPacks() {
        return PACKS.clone();
    }
}

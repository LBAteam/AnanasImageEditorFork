package iamutkarshtiwari.github.io.ananas.editimage.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.LinkedHashMap;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import iamutkarshtiwari.github.io.ananas.BaseActivity;
import iamutkarshtiwari.github.io.ananas.R;
import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ModuleConfig;
import iamutkarshtiwari.github.io.ananas.editimage.adapter.StickerAdapter;
import iamutkarshtiwari.github.io.ananas.editimage.adapter.StickerTypeAdapter;
import iamutkarshtiwari.github.io.ananas.editimage.model.StickerPack;
import iamutkarshtiwari.github.io.ananas.editimage.utils.Matrix3;
import iamutkarshtiwari.github.io.ananas.editimage.view.StickerItem;
import iamutkarshtiwari.github.io.ananas.editimage.view.StickerView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StickerFragment extends BaseEditFragment {
    public static final int INDEX = ModuleConfig.INDEX_STICKER;
    public static final String TAG = StickerFragment.class.getName();

    private View mainView;
    private ViewFlipper flipper;
    private StickerView stickerView;
    private StickerAdapter stickerAdapter;
    private RecyclerView typeList;
    private RecyclerView stickerList;
    private LinearLayoutManager typeListLayoutManager;
    private LinearLayoutManager stickerListLayoutManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Dialog loadingDialog;

    public static StickerFragment newInstance() {
        return new StickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_edit_image_sticker_type,
                null);
        loadingDialog = BaseActivity.getLoadingDialog(getActivity(), R.string.iamutkarshtiwari_github_io_ananas_saving_image,
                false);
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.stickerView = activity.stickerView;
        flipper = mainView.findViewById(R.id.flipper);
        flipper.setInAnimation(activity, R.anim.in_bottom_to_top);
        flipper.setOutAnimation(activity, R.anim.out_bottom_to_top);

        typeList = mainView
                .findViewById(R.id.stickers_type_list);
        typeList.setHasFixedSize(true);
        typeListLayoutManager = new LinearLayoutManager(activity);
        typeListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeList.setLayoutManager(typeListLayoutManager);
        typeList.setAdapter(new StickerTypeAdapter(this));

        stickerList = mainView.findViewById(R.id.stickers_list);
        stickerList.setHasFixedSize(true);
        stickerListLayoutManager = new LinearLayoutManager(activity);
        stickerListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stickerList.setLayoutManager(stickerListLayoutManager);
        stickerAdapter = createStickerAdapter();

        View backToMenu = mainView.findViewById(R.id.back_to_main);
        backToMenu.setOnClickListener(new BackToMenuClick());

        View backToType = mainView.findViewById(R.id.back_to_type);
        backToType.setOnClickListener(v -> showStickerTypes());
    }

    @Override
    public void onShow() {
        activity.mode = EditImageActivity.MODE_STICKERS;
        showStickerTypes();
        activity.stickerFragment.getStickerView().setVisibility(
                View.VISIBLE);
        activity.showInstrumentAction();
    }

    public void showStickerDetails(StickerPack stickerPack) {
        if (stickerPack == null) {
            return;
        }
        stickerAdapter = createStickerAdapter();
        stickerAdapter.setStickers(stickerPack.getStickerResIds());
        resetStickerListPosition();
        if (flipper.getDisplayedChild() != 1) {
            flipper.setDisplayedChild(1);
        }
    }

    public void selectedStickerItem(@DrawableRes int stickerResId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), stickerResId);
        if (bitmap != null) {
            stickerView.addBitImage(bitmap);
        }
    }

    private StickerView getStickerView() {
        return stickerView;
    }

    private final class BackToMenuClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            activity.onInstrumentBackPressed();
        }
    }

    @Override
    public void onPause() {
        compositeDisposable.clear();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void backToMain() {
        activity.mode = EditImageActivity.MODE_NONE;
        activity.bottomGallery.setCurrentItem(0);
        showStickerTypes();
        stickerView.clear();
        stickerView.setVisibility(View.GONE);
        activity.showMainAction();
    }

    private void showStickerTypes() {
        resetTypeListPosition();
        resetStickerListPosition();
        if (flipper.getDisplayedChild() != 0) {
            flipper.setDisplayedChild(0);
        }
    }

    private void resetTypeListPosition() {
        if (typeList != null && typeListLayoutManager != null) {
            typeList.stopScroll();
            typeListLayoutManager.scrollToPositionWithOffset(0, 0);
            typeList.post(() -> typeListLayoutManager.scrollToPositionWithOffset(0, 0));
        }
    }

    private void resetStickerListPosition() {
        if (stickerList != null && stickerListLayoutManager != null) {
            stickerList.stopScroll();
            stickerListLayoutManager.scrollToPositionWithOffset(0, 0);
            stickerList.post(() -> stickerListLayoutManager.scrollToPositionWithOffset(0, 0));
        }
    }

    private StickerAdapter createStickerAdapter() {
        StickerAdapter adapter = new StickerAdapter(this);
        if (stickerList != null) {
            stickerList.getRecycledViewPool().clear();
            stickerList.setAdapter(adapter);
        }
        return adapter;
    }

    public void applyStickers() {
        compositeDisposable.clear();

        Disposable saveStickerDisposable = applyStickerToImage(activity.getMainBit())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscriber -> loadingDialog.show())
                .doFinally(() -> loadingDialog.dismiss())
                .subscribe(bitmap -> {
                    if (bitmap == null) {
                        return;
                    }

                    stickerView.clear();
                    activity.changeMainBitmap(bitmap, true);
                    backToMain();
                }, e -> {
                    Toast.makeText(getActivity(), R.string.iamutkarshtiwari_github_io_ananas_save_error, Toast.LENGTH_SHORT).show();
                });

        compositeDisposable.add(saveStickerDisposable);
    }

    private Single<Bitmap> applyStickerToImage(Bitmap mainBitmap) {
        return Single.fromCallable(() -> {
            EditImageActivity context = (EditImageActivity) requireActivity();
            Matrix touchMatrix = context.mainImage.getImageViewMatrix();

            Bitmap resultBitmap = Bitmap.createBitmap(mainBitmap).copy(
                    Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(resultBitmap);

            float[] data = new float[9];
            touchMatrix.getValues(data);
            Matrix3 cal = new Matrix3(data);
            Matrix3 inverseMatrix = cal.inverseMatrix();
            Matrix m = new Matrix();
            m.setValues(inverseMatrix.getValues());
            handleImage(canvas, m);
            return resultBitmap;
        });
    }

    private void handleImage(Canvas canvas, Matrix m) {
        LinkedHashMap<Integer, StickerItem> addItems = stickerView.getBank();
        for (Integer id : addItems.keySet()) {
            StickerItem item = addItems.get(id);
            item.matrix.postConcat(m);
            canvas.drawBitmap(item.bitmap, item.matrix, null);
        }
    }
}

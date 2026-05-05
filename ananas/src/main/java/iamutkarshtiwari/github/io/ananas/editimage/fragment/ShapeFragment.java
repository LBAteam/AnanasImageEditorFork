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
import iamutkarshtiwari.github.io.ananas.editimage.adapter.ShapeAdapter;
import iamutkarshtiwari.github.io.ananas.editimage.adapter.ShapeTypeAdapter;
import iamutkarshtiwari.github.io.ananas.editimage.model.StickerPack;
import iamutkarshtiwari.github.io.ananas.editimage.utils.Matrix3;
import iamutkarshtiwari.github.io.ananas.editimage.view.StickerItem;
import iamutkarshtiwari.github.io.ananas.editimage.view.StickerView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShapeFragment extends BaseEditFragment {
    public static final int INDEX = ModuleConfig.INDEX_SHAPE;
    public static final String TAG = ShapeFragment.class.getName();

    private View mainView;
    private ViewFlipper flipper;
    private StickerView stickerView;
    private ShapeAdapter shapeAdapter;
    private RecyclerView typeList;
    private RecyclerView shapeList;
    private LinearLayoutManager typeListLayoutManager;
    private LinearLayoutManager shapeListLayoutManager;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Dialog loadingDialog;

    public static ShapeFragment newInstance() {
        return new ShapeFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_edit_image_sticker_type, null);
        loadingDialog = BaseActivity.getLoadingDialog(
                getActivity(),
                R.string.iamutkarshtiwari_github_io_ananas_saving_image,
                false
        );
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stickerView = activity.stickerView;
        flipper = mainView.findViewById(R.id.flipper);
        flipper.setInAnimation(activity, R.anim.in_bottom_to_top);
        flipper.setOutAnimation(activity, R.anim.out_bottom_to_top);

        typeList = mainView.findViewById(R.id.stickers_type_list);
        typeList.setHasFixedSize(true);
        typeListLayoutManager = new LinearLayoutManager(activity);
        typeListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeList.setLayoutManager(typeListLayoutManager);
        typeList.setAdapter(new ShapeTypeAdapter(this));

        shapeList = mainView.findViewById(R.id.stickers_list);
        shapeList.setHasFixedSize(true);
        shapeListLayoutManager = new LinearLayoutManager(activity);
        shapeListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        shapeList.setLayoutManager(shapeListLayoutManager);
        shapeAdapter = createShapeAdapter();

        View backToMenu = mainView.findViewById(R.id.back_to_main);
        backToMenu.setOnClickListener(new BackToMenuClick());

        View backToType = mainView.findViewById(R.id.back_to_type);
        backToType.setOnClickListener(v -> showShapeTypes());
    }

    @Override
    public void onShow() {
        activity.mode = EditImageActivity.MODE_SHAPES;
        showShapeTypes();
        stickerView.setVisibility(View.VISIBLE);
        activity.showInstrumentAction();
    }

    public void showShapeDetails(StickerPack shapePack) {
        if (shapePack == null) {
            return;
        }
        shapeAdapter = createShapeAdapter();
        shapeAdapter.setShapes(shapePack.getStickerResIds());
        resetShapeListPosition();
        if (flipper.getDisplayedChild() != 1) {
            flipper.setDisplayedChild(1);
        }
    }

    public void selectedShapeItem(@DrawableRes int shapeResId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), shapeResId);
        if (bitmap != null) {
            stickerView.addBitImage(bitmap);
        }
    }

    @Override
    public void backToMain() {
        activity.mode = EditImageActivity.MODE_NONE;
        activity.bottomGallery.setCurrentItem(MainMenuFragment.INDEX);
        showShapeTypes();
        stickerView.clear();
        stickerView.setVisibility(View.GONE);
        activity.showMainAction();
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

    public void applyShapes() {
        compositeDisposable.clear();

        Disposable saveShapeDisposable = applyShapeToImage(activity.getMainBit())
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
                }, e -> Toast.makeText(getActivity(), R.string.iamutkarshtiwari_github_io_ananas_save_error, Toast.LENGTH_SHORT).show());

        compositeDisposable.add(saveShapeDisposable);
    }

    private void showShapeTypes() {
        resetTypeListPosition();
        resetShapeListPosition();
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

    private void resetShapeListPosition() {
        if (shapeList != null && shapeListLayoutManager != null) {
            shapeList.stopScroll();
            shapeListLayoutManager.scrollToPositionWithOffset(0, 0);
            shapeList.post(() -> shapeListLayoutManager.scrollToPositionWithOffset(0, 0));
        }
    }

    private ShapeAdapter createShapeAdapter() {
        ShapeAdapter adapter = new ShapeAdapter(this);
        if (shapeList != null) {
            shapeList.getRecycledViewPool().clear();
            shapeList.setAdapter(adapter);
        }
        return adapter;
    }

    private Single<Bitmap> applyShapeToImage(Bitmap mainBitmap) {
        return Single.fromCallable(() -> {
            EditImageActivity context = (EditImageActivity) requireActivity();
            Matrix touchMatrix = context.mainImage.getImageViewMatrix();

            Bitmap resultBitmap = Bitmap.createBitmap(mainBitmap).copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(resultBitmap);

            float[] data = new float[9];
            touchMatrix.getValues(data);
            Matrix3 cal = new Matrix3(data);
            Matrix3 inverseMatrix = cal.inverseMatrix();
            Matrix matrix = new Matrix();
            matrix.setValues(inverseMatrix.getValues());
            handleImage(canvas, matrix);
            return resultBitmap;
        });
    }

    private void handleImage(Canvas canvas, Matrix matrix) {
        LinkedHashMap<Integer, StickerItem> addItems = stickerView.getBank();
        for (Integer id : addItems.keySet()) {
            StickerItem item = addItems.get(id);
            item.matrix.postConcat(matrix);
            canvas.drawBitmap(item.bitmap, item.matrix, null);
        }
    }

    private final class BackToMenuClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            activity.onInstrumentBackPressed();
        }
    }
}

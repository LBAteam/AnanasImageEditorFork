package iamutkarshtiwari.github.io.ananas.editimage.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import iamutkarshtiwari.github.io.ananas.R;
import iamutkarshtiwari.github.io.ananas.editimage.adapter.ColorPickerAdapter;
import iamutkarshtiwari.github.io.ananas.editimage.interfaces.OnTextEditorListener;

public class TextEditorDialogFragment extends DialogFragment {

    public static final String TAG = TextEditorDialogFragment.class.getSimpleName();
    private static final String EXTRA_INPUT_TEXT = "extra_input_text";
    private static final String EXTRA_COLOR_CODE = "extra_color_code";
    private static final String EXTRA_TEXT_SIZE = "extra_text_size";
    private static final String EXTRA_TEXT_ALIGNMENT = "extra_text_alignment";
    private static final int FONT_SIZE_STEP_OFFSET_SP = 12;

    private EditText addTextEditText;
    private InputMethodManager inputMethodManager;
    private int colorCode;
    private float textSize;
    private int textAlignment;
    private OnTextEditorListener onTextEditorListener;

    //Show dialog with provide text and text color
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity,
                                                @NonNull String inputText,
                                                @ColorInt int initialColorCode,
                                                float textSize,
                                                int textAlignment) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, initialColorCode);
        args.putFloat(EXTRA_TEXT_SIZE, textSize);
        args.putInt(EXTRA_TEXT_ALIGNMENT, textAlignment);
        TextEditorDialogFragment fragment = new TextEditorDialogFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity, "", ContextCompat.getColor(appCompatActivity, R.color.white), getDefaultTextSize(appCompatActivity), Gravity.CENTER);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Window window = dialog.getWindow();
            if (window != null) {
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_text_sticker, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTextEditText = view.findViewById(R.id.add_text_edit_text);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView addTextDoneTv = view.findViewById(R.id.add_text_done_tv);
        SeekBar addTextFontSizeSeekBar = view.findViewById(R.id.add_text_font_size_seek_bar);
        ImageView alignLeft = view.findViewById(R.id.add_text_align_left);
        ImageView alignCenter = view.findViewById(R.id.add_text_align_center);
        ImageView alignRight = view.findViewById(R.id.add_text_align_right);

        //Setup the color picker for text color
        RecyclerView addTextColorPickerRecyclerView = view.findViewById(R.id.add_text_color_picker_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        addTextColorPickerRecyclerView.setLayoutManager(layoutManager);
        addTextColorPickerRecyclerView.setHasFixedSize(true);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(getContext());

        //This listener will change the text color when clicked on any color from picker
        colorPickerAdapter.setOnColorPickerClickListener(colorCode -> {
            this.colorCode = colorCode;
            addTextEditText.setTextColor(colorCode);
        });

        addTextColorPickerRecyclerView.setAdapter(colorPickerAdapter);
        addTextEditText.setText(getArguments().getString(EXTRA_INPUT_TEXT));
        colorCode = getArguments().getInt(EXTRA_COLOR_CODE);
        final float defaultTextSize = getDefaultTextSize(requireContext());
        textSize = getArguments().getFloat(EXTRA_TEXT_SIZE, defaultTextSize);
        textAlignment = getArguments().getInt(EXTRA_TEXT_ALIGNMENT, Gravity.CENTER);
        addTextEditText.setTextColor(colorCode);
        addTextEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        applyTextAlignment(addTextEditText, textAlignment);
        updateAlignmentButtons(alignLeft, alignCenter, alignRight);
        updateHintVisibility();
        addTextEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && TextUtils.isEmpty(addTextEditText.getText())) {
                addTextEditText.setHint(null);
            }
            return false;
        });
        final int minTextSize = Math.max(1, Math.round(defaultTextSize) - FONT_SIZE_STEP_OFFSET_SP);
        final int maxTextSize = Math.round(defaultTextSize) + FONT_SIZE_STEP_OFFSET_SP;
        textSize = Math.max(minTextSize, Math.min(maxTextSize, textSize));
        addTextEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        addTextFontSizeSeekBar.setMax(maxTextSize - minTextSize);
        addTextFontSizeSeekBar.setProgress(Math.round(textSize) - minTextSize);
        addTextFontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize = minTextSize + progress;
                addTextEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        alignLeft.setOnClickListener(v -> {
            textAlignment = Gravity.START;
            applyTextAlignment(addTextEditText, textAlignment);
            updateAlignmentButtons(alignLeft, alignCenter, alignRight);
        });
        alignCenter.setOnClickListener(v -> {
            textAlignment = Gravity.CENTER;
            applyTextAlignment(addTextEditText, textAlignment);
            updateAlignmentButtons(alignLeft, alignCenter, alignRight);
        });
        alignRight.setOnClickListener(v -> {
            textAlignment = Gravity.END;
            applyTextAlignment(addTextEditText, textAlignment);
            updateAlignmentButtons(alignLeft, alignCenter, alignRight);
        });
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Make a callback on activity when user is done with text editing
        addTextDoneTv.setOnClickListener(view1 -> {
            inputMethodManager.hideSoftInputFromWindow(view1.getWindowToken(), 0);

            String inputText = addTextEditText.getText().toString();
            if (!TextUtils.isEmpty(inputText) && onTextEditorListener != null) {
                onTextEditorListener.onDone(inputText, colorCode, textSize, textAlignment);
            }
            dismiss();
        });
    }

    //Callback to listener if user is done with text editing
    public void setOnTextEditorListener(OnTextEditorListener onTextEditorListener) {
        this.onTextEditorListener = onTextEditorListener;
    }

    private void updateHintVisibility() {
        if (TextUtils.isEmpty(addTextEditText.getText())) {
            addTextEditText.setHint(R.string.iamutkarshtiwari_github_io_ananas_input_hint);
        } else {
            addTextEditText.setHint(null);
        }
    }

    private static float getDefaultTextSize(@NonNull Context context) {
        return context.getResources().getDimension(R.dimen.text_sticker_size) / context.getResources().getDisplayMetrics().scaledDensity;
    }

    private void applyTextAlignment(@NonNull TextView textView, int alignment) {
        textView.setGravity(getGravityForAlignment(alignment));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
    }

    private int getGravityForAlignment(int alignment) {
        if (alignment == Gravity.START) {
            return Gravity.START | Gravity.CENTER_VERTICAL;
        }
        if (alignment == Gravity.END) {
            return Gravity.END | Gravity.CENTER_VERTICAL;
        }
        return Gravity.CENTER;
    }

    private void updateAlignmentButtons(@NonNull ImageView alignLeft, @NonNull ImageView alignCenter, @NonNull ImageView alignRight) {
        alignLeft.setSelected(textAlignment == Gravity.START);
        alignCenter.setSelected(textAlignment == Gravity.CENTER);
        alignRight.setSelected(textAlignment == Gravity.END);

        alignLeft.setColorFilter(textAlignment == Gravity.START ? ContextCompat.getColor(requireContext(), R.color.black) : ContextCompat.getColor(requireContext(), R.color.white));
        alignCenter.setColorFilter(textAlignment == Gravity.CENTER ? ContextCompat.getColor(requireContext(), R.color.black) : ContextCompat.getColor(requireContext(), R.color.white));
        alignRight.setColorFilter(textAlignment == Gravity.END ? ContextCompat.getColor(requireContext(), R.color.black) : ContextCompat.getColor(requireContext(), R.color.white));
    }
}

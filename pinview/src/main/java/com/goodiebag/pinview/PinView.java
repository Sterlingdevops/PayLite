/*
MIT License
Copyright (c) 2017 GoodieBag
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.goodiebag.pinview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD;


/**
 * This class implements a pinview for android.
 * It can be used as a widget in android to take passwords/OTP/pins etc.
 * It is extended from a LinearLayout, implements TextWatcher, FocusChangeListener and OnKeyListener.
 * Supports drawableItem/selectors as a background for each pin box.
 * A listener is wired up to monitor complete data entry.
 * Can toggle cursor visibility.
 * Supports numpad and text keypad.
 * Flawless focus change to the consecutive pinbox.
 * Date : 11/01/17
 *
 * @author Krishanu
 * @author Pavan
 * @author Koushik
 */
public class PinView extends LinearLayout implements TextWatcher, View.OnFocusChangeListener, View.OnKeyListener {
    private final float DENSITY = getContext().getResources().getDisplayMetrics().density;
    OnClickListener mClickListener;
    View currentFocus = null;
    InputFilter filters[] = new InputFilter[1];
    LayoutParams params;
    /**
     * Attributes
     */
    private int mPinLength = 4;
    private List<EditText> editTextList = new ArrayList<>();
    private int mPinWidth = 50;
    private int mPinHeight = 50;
    private int mSplitWidth = 20;
    private boolean mCursorVisible = false;
    private boolean mDelPressed = false;
    private boolean mSwapBackground = false;
    @DrawableRes
    private int mPinBackground = R.drawable.sample_background;
    private int mPinTextBackground = R.drawable.sample_background;
    private boolean mPassword = false;
    private String mHint = "";
    private InputType inputType = InputType.NUMBER;
    private boolean finalNumberPin = false;
    private PinViewEventListener mListener;
    private boolean fromSetValue = false;
    private boolean mForceKeyboard = true;

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        init(context, attrs, defStyleAttr);
    }

    /**
     * A method to take care of all the initialisations.
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.removeAllViews();
        mPinHeight *= DENSITY;
        mPinWidth *= DENSITY;
        mSplitWidth *= DENSITY;
        setWillNotDraw(false);
        initAttributes(context, attrs, defStyleAttr);
        params = new LayoutParams(mPinWidth, mPinHeight);
        setOrientation(HORIZONTAL);
        createEditTexts();
        requestPinEntryFocus();
        super.setOnClickListener(view -> {
            boolean focused = false;
            for (EditText editText : editTextList) {
                if (editText.length() == 0) {
                    editText.requestFocus();
                    focused = true;
                    break;
                }
            }
            if (!focused && editTextList.size() > 0) { // Focus the last view
                editTextList.get(editTextList.size() - 1).requestFocus();
            }
            if (mClickListener != null) {
                mClickListener.onClick(PinView.this);
            }
        });
        updateEnabledState();
    }

    /**
     * Creates editTexts and adds it to the pinview based on the pinLength specified.
     */

    private void createEditTexts() {
        removeAllViews();
        editTextList.clear();
        EditText editText;
        for (int i = 0; i < mPinLength; i++) {
            editText = new EditText(getContext());
            editTextList.add(i, editText);
            this.addView(editText);
            generateOneEditText(editText, "" + i);
        }
        setTransformation();
    }

    /**
     * This method gets the attribute values from the XML, if not found it takes the default values.
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PinView, defStyleAttr, 0);
            mPinTextBackground = array.getResourceId(R.styleable.PinView_pinTextBackground, mPinTextBackground);
            mPinBackground = array.getResourceId(R.styleable.PinView_pinBackground, mPinBackground);
            mSwapBackground = array.getBoolean(R.styleable.PinView_swapBackground, mSwapBackground);
            mCursorVisible = array.getBoolean(R.styleable.PinView_cursorVisible, mCursorVisible);
            mForceKeyboard = array.getBoolean(R.styleable.PinView_forceKeyboard, mForceKeyboard);
            mSplitWidth = (int) array.getDimension(R.styleable.PinView_splitWidth, mSplitWidth);
            mPinHeight = (int) array.getDimension(R.styleable.PinView_pinHeight, mPinHeight);
            mPinWidth = (int) array.getDimension(R.styleable.PinView_pinWidth, mPinWidth);
            mPassword = array.getBoolean(R.styleable.PinView_password, mPassword);
            mPinLength = array.getInt(R.styleable.PinView_pinLength, mPinLength);
            mHint = array.getString(R.styleable.PinView_hint);
            InputType[] its = InputType.values();
            inputType = its[array.getInt(R.styleable.PinView_inputType, 0)];
            array.recycle();
        }
    }

    /**
     * Takes care of styling the editText passed in the param.
     * tag is the index of the editText.
     *
     * @param styleEditText
     * @param tag
     */
    @SuppressLint("ClickableViewAccessibility")
    private void generateOneEditText(EditText styleEditText, String tag) {
        params.setMargins(mSplitWidth / 2, mSplitWidth / 2, mSplitWidth / 2, mSplitWidth / 2);
        filters[0] = new InputFilter.LengthFilter(1);
        styleEditText.setFilters(filters);
        styleEditText.setLayoutParams(params);
        styleEditText.setGravity(Gravity.CENTER);
        styleEditText.setCursorVisible(mCursorVisible);

        if (!mCursorVisible) {
            styleEditText.setClickable(false);
            styleEditText.setHint(mHint);

            styleEditText.setOnTouchListener((view, motionEvent) -> {
                // When back space is pressed it goes to delete mode and when u click on an edit Text it should get out of the delete mode
                mDelPressed = false;
                return false;
            });
        }
        styleEditText.setBackgroundResource(mPinBackground);
        styleEditText.setPadding(0, 0, 0, 0);
        styleEditText.setTag(tag);
        styleEditText.setInputType(android.text.InputType.TYPE_NULL);
        styleEditText.setTextIsSelectable(true);
        styleEditText.addTextChangedListener(this);
        styleEditText.setOnFocusChangeListener(this);
        styleEditText.setOnKeyListener(this);
    }

    private int getKeyboardInputType() {
        int it;
        switch (inputType) {
            case NUMBER:
                it = TYPE_CLASS_NUMBER | TYPE_NUMBER_VARIATION_PASSWORD;
                break;
            case TEXT:
                it = TYPE_CLASS_TEXT;
                break;
            default:
                it = TYPE_CLASS_TEXT;
        }
        return it;
    }

    /**
     * Returns the value of the Pinview
     *
     * @return
     */

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        for (EditText et : editTextList) {
            sb.append(et.getText().toString());
        }
        return sb.toString();
    }

    /**
     * Sets the value of the Pinview
     *
     * @param value
     */
    public void setValue(@NonNull String value) {
        String regex = "[0-9]*"; // Allow empty string to clear the fields
        fromSetValue = true;
        if (inputType == InputType.NUMBER && !value.matches(regex))
            return;
        int lastTagHavingValue = -1;
        for (int i = 0; i < editTextList.size(); i++) {
            if (value.length() > i) {
                lastTagHavingValue = i;
                editTextList.get(i).setText(((Character) value.charAt(i)).toString());
            } else {
                editTextList.get(i).setText("");
                EditText currentEditText = editTextList.get(i);
                currentEditText.setBackgroundResource(mPinBackground);
            }
        }
        if (mPinLength > 0) {
            if (lastTagHavingValue < mPinLength - 1) {
                currentFocus = editTextList.get(lastTagHavingValue + 1);
            } else {
                currentFocus = editTextList.get(mPinLength - 1);
                if (inputType == InputType.NUMBER || mPassword)
                    finalNumberPin = true;
                if (mListener != null)
                    mListener.onDataEntered(this, false);
            }
            currentFocus.requestFocus();
        }
        fromSetValue = false;
        updateEnabledState();
    }

    /**
     * Requsets focus on current pin view and opens keyboard if forceKeyboard is enabled.
     *
     * @return the current focused pin view. It can be used to open softkeyboard manually.
     */
    public View requestPinEntryFocus() {
        int currentTag = Math.max(0, getIndexOfCurrentFocus());
        EditText currentEditText = editTextList.get(currentTag);
        if (currentEditText != null) {
            currentEditText.requestFocus();
        }
        return currentEditText;
    }

    /**
     * Clears the values in the Pinview
     */
    public void clearValue() {
        setValue("");
    }

    @Override
    public void onFocusChange(View view, boolean isFocused) {
        if (isFocused && !mCursorVisible) {
            if (mDelPressed) {
                currentFocus = view;
                mDelPressed = false;
                return;
            }
            for (final EditText editText : editTextList) {
                if (editText.length() == 0) {
                    if (editText != view) {
                        editText.requestFocus();
                    } else {
                        currentFocus = view;
                    }
                    return;
                }
            }
            if (editTextList.get(editTextList.size() - 1) != view) {
                editTextList.get(editTextList.size() - 1).requestFocus();
            } else {
                currentFocus = view;
            }
        } else if (isFocused && mCursorVisible) {
            currentFocus = view;
        } else {
            view.clearFocus();
        }
    }

    /**
     * Handles the character transformation for password inputs.
     */
    private void setTransformation() {
        if (mPassword) {
            for (EditText editText : editTextList) {
                editText.removeTextChangedListener(this);
                editText.setTransformationMethod(mSwapBackground ? new PinTransformationMethod() : new CustomPasswordTransformationMethod());
                editText.addTextChangedListener(this);
            }
        } else {
            for (EditText editText : editTextList) {
                editText.removeTextChangedListener(this);
                editText.setTransformationMethod(null);
                editText.addTextChangedListener(this);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * Fired when text changes in the editTexts.
     * Backspace is also identified here.
     *
     * @param charSequence
     * @param start
     * @param i1
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int i1, int count) {
        appendText(charSequence);
    }

    public void append(String text) {
        editTextList.get(getIndexOfCurrentFocus()).append(text);
    }

    public void appendText(CharSequence charSequence) {
        if (charSequence.length() == 1 && currentFocus != null) {
            final int currentTag = getIndexOfCurrentFocus();
            if (currentTag < mPinLength - 1) {
                if (mSwapBackground) {
                    editTextList.get(currentTag).setBackground(ContextCompat.getDrawable(getContext(), mPinTextBackground));
                }

                long delay = 1;
                if (mPassword) delay = 25;
                this.postDelayed(() -> {
                    EditText nextEditText = editTextList.get(currentTag + 1);
                    nextEditText.setEnabled(true);
                    nextEditText.requestFocus();
                }, delay);
            } else {
                //Last Pin box has been reached.
                if (mSwapBackground) {
                    editTextList.get(currentTag).setBackground(ContextCompat.getDrawable(getContext(), mPinTextBackground));
                }
            }
            if (currentTag == mPinLength - 1 && inputType == InputType.NUMBER || currentTag == mPinLength - 1 && mPassword) {
                finalNumberPin = true;
            }

        } else if (charSequence.length() == 0) {
            int currentTag = getIndexOfCurrentFocus();
            mDelPressed = true;
            //For the last cell of the non password text fields. Clear the text without changing the focus.
            if (editTextList.get(currentTag).getText().length() > 0)
                editTextList.get(currentTag).setText("");
        }

        for (int index = 0; index < mPinLength; index++) {
            if (editTextList.get(index).getText().length() < 1)
                break;
            if (!fromSetValue && index + 1 == mPinLength && mListener != null)
                mListener.onDataEntered(this, true);
        }
        updateEnabledState();
    }

    /**
     * Disable views ahead of current focus, so a selector can change the drawing of those views.
     */
    private void updateEnabledState() {
        int currentTag = Math.max(0, getIndexOfCurrentFocus());
        for (int index = 0; index < editTextList.size(); index++) {
            EditText editText = editTextList.get(index);
            editText.setEnabled(index <= currentTag);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * Monitors keyEvent.
     *
     * @param view
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if ((keyEvent.getAction() == KeyEvent.ACTION_UP) && (i == KeyEvent.KEYCODE_DEL)) {
            backSpaceClicked();
            return true;
        }
        return false;
    }

    public void backSpaceClicked() {
        // Perform action on Del press
        int currentTag = getIndexOfCurrentFocus();
        //Last tile of the number pad. Clear the edit text without changing the focus.
        if (inputType == InputType.NUMBER && currentTag == mPinLength - 1 && finalNumberPin ||
                (mPassword && currentTag == mPinLength - 1 && finalNumberPin)) {
            if (editTextList.get(currentTag).length() > 0) {
                editTextList.get(currentTag).setText("");
                editTextList.get(currentTag).setBackground(ContextCompat.getDrawable(getContext(), mPinBackground));
            }
            finalNumberPin = false;
        } else if (currentTag > 0) {
            mDelPressed = true;
            if (editTextList.get(currentTag).length() == 0) {
                //Takes it back one tile
                editTextList.get(currentTag - 1).requestFocus();
                //Clears the tile it just got to
                editTextList.get(currentTag).setText("");
                editTextList.get(currentTag - 1).setBackground(ContextCompat.getDrawable(getContext(), mPinBackground));
            } else {
                //If it has some content clear it first
                editTextList.get(currentTag).setText("");
                editTextList.get(currentTag - 1).setBackground(ContextCompat.getDrawable(getContext(), mPinBackground));
            }
        } else {
            //For the first cell
            if (editTextList.get(currentTag).getText().length() > 0) {
                editTextList.get(currentTag).setText("");
                editTextList.get(currentTag).setBackground(ContextCompat.getDrawable(getContext(), mPinBackground));
            }
        }
//        editTextList.get(currentTag).setBackground(ContextCompat.getDrawable(getContext(), mPinBackground));
    }

    /**
     * Getters and Setters
     */
    private int getIndexOfCurrentFocus() {
        return editTextList.indexOf(currentFocus);
    }

    public int getSplitWidth() {
        return mSplitWidth;
    }

    public void setSplitWidth(int splitWidth) {
        this.mSplitWidth = splitWidth;
        int margin = splitWidth / 2;
        params.setMargins(margin, margin, margin, margin);

        for (EditText editText : editTextList) {
            editText.setLayoutParams(params);
        }
    }

    public int getPinHeight() {
        return mPinHeight;
    }

    public void setPinHeight(int pinHeight) {
        this.mPinHeight = pinHeight;
        params.height = pinHeight;
        for (EditText editText : editTextList) {
            editText.setLayoutParams(params);
        }
    }

    public int getPinWidth() {
        return mPinWidth;
    }

    public void setPinWidth(int pinWidth) {
        this.mPinWidth = pinWidth;
        params.width = pinWidth;
        for (EditText editText : editTextList) {
            editText.setLayoutParams(params);
        }
    }

    public int getPinLength() {
        return mPinLength;
    }

    public void setPinLength(int pinLength) {
        this.mPinLength = pinLength;
        createEditTexts();
    }

    public boolean isPassword() {
        return mPassword;
    }

    public void setPassword(boolean password) {
        this.mPassword = password;
        setTransformation();
    }

    public String getHint() {
        return mHint;
    }

    public void setHint(String mHint) {
        this.mHint = mHint;
        for (EditText editText : editTextList)
            editText.setHint(mHint);
    }

    public
    @DrawableRes
    int getPinBackground() {
        return mPinBackground;
    }

    public void setPinBackgroundRes(@DrawableRes int res) {
        this.mPinBackground = res;
        for (EditText editText : editTextList)
            editText.setBackground(ContextCompat.getDrawable(getContext(), res));
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mClickListener = l;
    }

    public InputType getInputType() {
        return inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
        int it = getKeyboardInputType();
        for (EditText editText : editTextList) {
            editText.setInputType(it);
        }
    }

    public void setPinViewEventListener(PinViewEventListener listener) {
        this.mListener = listener;
    }

    public enum InputType {
        TEXT, NUMBER
    }

    /**
     * Interface for onDataEntered event.
     */

    public interface PinViewEventListener {
        void onDataEntered(PinView pinView, boolean fromUser);
    }

    /**
     * A class to implement the transformation mechanism
     */

    private class PinTransformationMethod implements TransformationMethod {

        private char BULLET = '\u2022';
        private char SPACE = ' ';

        @Override
        public CharSequence getTransformation(CharSequence source, final View view) {
            return new PasswordCharSequence(source);
        }

        @Override
        public void onFocusChanged(final View view, final CharSequence sourceText, final boolean focused, final int direction, final Rect previouslyFocusedRect) {

        }

        private class PasswordCharSequence implements CharSequence {
            private final CharSequence source;

            PasswordCharSequence(@NonNull CharSequence source) {
                this.source = source;
            }

            @Override
            public int length() {
                return source.length();
            }

            @Override
            public char charAt(int index) {
                return mSwapBackground ? SPACE : BULLET;
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return new PasswordCharSequence(this.source.subSequence(start, end));
            }
        }
    }

    public class CustomPasswordTransformationMethod extends PasswordTransformationMethod {

        PasswordCharSequence passwordCharSequence;
        View view;
        Handler handler;
        Runnable runnable;

        private char BULLET = '\u2022';
        private char SPACE = ' ';
        private int timeBeforeMask = 100;

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            passwordCharSequence = new PasswordCharSequence(source);
            handler = new Handler();
            this.view = view;
            return passwordCharSequence;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            super.onTextChanged(s, start, before, count);
            if (before <= 0) {
                passwordCharSequence.showLast = true;
                handler.removeCallbacks(runnable);

                runnable = () -> {
                    passwordCharSequence.showLast = false;
                    view.requestLayout();
                };

                handler.postDelayed(runnable, timeBeforeMask);
            } else {
                handler.removeCallbacks(runnable);

                passwordCharSequence.showLast = false;
                view.requestLayout();
            }
        }

        private class PasswordCharSequence implements CharSequence {
            boolean showLast = true;
            private CharSequence mSource;

            PasswordCharSequence(CharSequence source) {
                mSource = source;
            }

            public char charAt(int index) {
                //This is the check which makes sure the last character is shown
                if (index != mSource.length() - 1)
                    return mSwapBackground ? SPACE : BULLET;
                else if (showLast) return mSource.charAt(index);
                else return mSwapBackground ? SPACE : BULLET;
            }

            public int length() {
                return mSource.length();
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
}

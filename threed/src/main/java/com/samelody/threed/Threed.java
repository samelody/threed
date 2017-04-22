/*
 * Copyright (c) 2017-present Samelody.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.samelody.threed;

import android.content.res.TypedArray;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.text.TextUtils.isEmpty;
import static com.samelody.threed.R.styleable.MoreTextView;
import static com.samelody.threed.R.styleable.MoreTextView_moreText;
import static com.samelody.threed.R.styleable.MoreTextView_moreTextColor;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

/**
 * The three dots.
 *
 * @author Belin Wu
 */
public final class Threed implements OnClickListener {

    public static final String TAG = Threed.class.getSimpleName();
    public static final char THREE_DOTS = '\u2026';

    private TextView textView;
    private String text;
    private int maxLines;
    private boolean stale;
    private boolean ellipsized;
    private boolean ignoretextChanged;
    private String moreText;
    private int moreTextColor;
    private OnEllipsizeChangeListener listener;

    public static void init(TextView view, AttributeSet attrs, int defStyleAttr) {
        Threed t = getThreed(view);
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, MoreTextView, 0, defStyleAttr);
        t.moreText = a.getString(MoreTextView_moreText);
        t.moreTextColor = a.getColor(MoreTextView_moreTextColor, view.getTextColors().getDefaultColor());
        a.recycle();
        view.setEllipsize(TruncateAt.END);
        if (!isEmpty(t.moreText)) {
            view.setOnClickListener(t);
        }
    }

    private static Threed getThreed(TextView view) {
        if (view == null) {
            throw new IllegalArgumentException("view must be non-null");
        }
        Object tag = view.getTag(R.id.threed);
        if (tag instanceof Threed) {
            return (Threed) tag;
        }
        Threed t = new Threed();
        t.textView = view;
        view.setTag(R.id.threed, t);
        return t;
    }

    public static void setMaxLines(TextView view, int maxLines) {
        Threed t = getThreed(view);
        t.maxLines = maxLines;
        t.stale = true;
    }

    public static void onTextChanged(TextView view, CharSequence text) {
        Threed t = getThreed(view);
        if (!t.ignoretextChanged) {
            t.text = text.toString();
            t.stale = true;
        }
    }

    public static void onDraw(TextView view) {
        Threed t = getThreed(view);
        if (!t.stale) {
            return;
        }
        t.ellipsize();
        t.stale = false;
    }

    public static void setOnEllipsizeChangeListener(TextView view, OnEllipsizeChangeListener listener) {
        Threed t = getThreed(view);
        t.listener = listener;
    }

    private void ellipsize() {
        boolean ellipsized = false;
        Layout layout = textView.getLayout();
        if (layout == null) {
            return;
        }
        int lineCount = layout.getLineCount();
        CharSequence result = text;
        if (lineCount > 0) {
            int line = lineCount - 1;
            int ellipsisCount = layout.getEllipsisCount(line);
            ellipsized = ellipsisCount > 0;
            if (ellipsized) {
                Log.d(TAG, "layoutText:" + layout.getText());
                String resultString = result.toString();
                int lineStart = layout.getLineStart(line);
                int ellipsisStart = lineStart + layout.getEllipsisStart(line);
                Log.d(TAG, "lineCount:" + lineCount + ",ellipsisCount=" + ellipsisCount + ",lineStart=" + lineStart + ",ellipsisStart=" + ellipsisStart);
                resultString = resultString.substring(0, ellipsisStart);
                if (!isEmpty(this.moreText)) {
                    TextPaint paint = textView.getPaint();
                    String moreText = THREE_DOTS + this.moreText;
                    float moreWidth = paint.measureText(moreText, 0, moreText.length());
                    float maxWidth = layout.getWidth() - moreWidth;
                    int length = paint.breakText(result, 0, result.length(), true, maxWidth, null);
                    Log.d(TAG, "lineStart=" + lineStart + ",lineLength=" + (ellipsisStart - lineStart) + ",length=" + length);
                    int breakIndex = lineStart + min(length, ellipsisStart - lineStart);
                    resultString = resultString.substring(0, breakIndex) + moreText;
                    SpannableString spannable = new SpannableString(resultString);
                    int start = breakIndex + 1;
                    int end = start + this.moreText.length();
                    spannable.setSpan(new ForegroundColorSpan(moreTextColor), start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
                    result = spannable;
                } else {
                    result = resultString + THREE_DOTS;
                }
            }
        }
        if (!result.equals(textView.getText())) {
            try {
                ignoretextChanged = true;
                textView.setText(result);
            } finally {
                ignoretextChanged = false;
            }
        }
        this.ellipsized = ellipsized;
        if (listener != null) {
            listener.onEllipsizeChange(textView, ellipsized);
        }
    }

    @Override
    public final void onClick(View v) {
        if (ellipsized) {
            textView.setMaxLines(MAX_VALUE);
            textView.setText(text);
        }
        ellipsized = false;
    }
}

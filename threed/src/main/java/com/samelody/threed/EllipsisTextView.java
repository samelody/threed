package com.samelody.threed;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 *
 * @author Belin Wu
 */
public class EllipsisTextView extends AppCompatTextView {

    public EllipsisTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public EllipsisTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EllipsisTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EllipsisTextView, defStyle, 0);
        a.recycle();
    }
}

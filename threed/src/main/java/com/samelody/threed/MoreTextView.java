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

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * The text view to show more text when clicked.
 *
 * @author Belin Wu
 */
public class MoreTextView extends AppCompatTextView {

    public MoreTextView(Context context) {
        this(context, null);
    }

    public MoreTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public MoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Threed.init(this, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        Threed.onTextChanged(this, text);
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        Threed.setMaxLines(this, maxLines);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Threed.onDraw(this); // must before super.onDraw(canvas);
        super.onDraw(canvas);
    }
}

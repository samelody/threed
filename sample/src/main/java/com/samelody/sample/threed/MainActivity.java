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
package com.samelody.sample.threed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.samelody.threed.MoreTextView;
import com.samelody.threed.OnEllipsizeChangeListener;
import com.samelody.threed.Threed;

/**
 * @author Belin Wu
 */
public class MainActivity extends AppCompatActivity implements OnEllipsizeChangeListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
        MoreTextView text1 = (MoreTextView) findViewById(R.id.text1);
        Threed.setOnEllipsizeChangeListener(text1, this);
        MoreTextView text2 = (MoreTextView) findViewById(R.id.text2);
        Threed.setOnEllipsizeChangeListener(text2, this);
    }

    @Override
    public void onEllipsizeChange(TextView view, boolean ellipsized) {
        switch (view.getId()) {
            case R.id.text1:
                view.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.text2:
                if (!ellipsized) {
                    Toast.makeText(this, "ellipsized=false", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}

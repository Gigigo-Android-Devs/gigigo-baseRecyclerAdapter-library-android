package com.gigigo.baserecycleradapter.debouncedlisteners;

import android.view.View;

public interface DebouncedListener {
  boolean onDebouncedClick(View v, int position);
}

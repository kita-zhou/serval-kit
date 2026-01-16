// Copyright 2025 The Lynx Authors. All rights reserved.
// Licensed under the Apache License Version 2.0 that can be found in the
// LICENSE file in the root directory of this source tree.
package com.lynx.servalkit.demoMarkdown;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class customTypingDotsDrawable extends Drawable {
  private static final int DOT_COUNT = 3;
  private static final int DOT_RADIUS = 8;
  private static final int DOT_SPACING = 20;
  private static final int ANIMATION_DURATION = 600;  // animation cycle 600ms

  private final Paint[] dotPaints;
  private final long startTime;

  public customTypingDotsDrawable() {
    // initialize paint for three dots
    dotPaints = new Paint[DOT_COUNT];
    int[] colors = {Color.RED, Color.YELLOW, Color.BLUE};

    for (int i = 0; i < DOT_COUNT; i++) {
      dotPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
      dotPaints[i].setColor(colors[i]);
      dotPaints[i].setStyle(Paint.Style.FILL);
    }

    startTime = SystemClock.uptimeMillis();
  }

  @Override
  public void draw(@NonNull Canvas canvas) {
    // compute animation progress
    long elapsed =
        (SystemClock.uptimeMillis() - startTime) % ANIMATION_DURATION;
    float progress = elapsed / (float)ANIMATION_DURATION;

    // get drawable bounds
    Rect bounds = getBounds();
    float centerY = bounds.centerY();
    float startX = bounds.centerX() - (DOT_COUNT - 1) * DOT_SPACING / 2f;

    // draw three dots
    for (int i = 0; i < DOT_COUNT; i++) {
      float phase = (progress + i / (float)DOT_COUNT) % 1f;
      // use sine function to create vertical motion
      float yOffset = (float)Math.sin(phase * 2 * Math.PI) * 10;

      canvas.drawCircle(startX + i * DOT_SPACING, centerY + yOffset, DOT_RADIUS,
                        dotPaints[i]);
    }

    // trigger next animation frame
    invalidateSelf();
  }

  @Override
  public void setAlpha(int alpha) {
    for (Paint paint : dotPaints) {
      paint.setAlpha(alpha);
    }
  }

  @Override
  public void setColorFilter(@Nullable ColorFilter colorFilter) {
    for (Paint paint : dotPaints) {
      paint.setColorFilter(colorFilter);
    }
  }

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }
}

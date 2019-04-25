package com.xiaopo.flying.sticker;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author wupanjie
 */
public abstract class Sticker {

  public static final int SCALE_BASELINE_SELF = 0;
  public static final int SCALE_BASELINE_PARENT_WIDTH = 1;
  public static final int SCALE_BASELINE_PARENT_HEIGHT = 2;

  //初始化时的缩放 相对于父类容器
  private float mInitialScale;
  //初始化时的缩放 以宽度还是以高度为基准
  private int mInitialScaleBaseline = SCALE_BASELINE_SELF;

  private final float[] matrixValues = new float[9];
  private final float[] unrotatedWrapperCorner = new float[8];
  private final float[] unrotatedPoint = new float[2];
  private final float[] boundPoints = new float[8];
  private final float[] mappedBounds = new float[8];
  private final RectF trappedRect = new RectF();
  private final Rect containerBounds = new Rect();
  private final Matrix matrix = new Matrix();
  private boolean isFlippedHorizontally;
  private boolean isFlippedVertically;


  public void setInitialScale(float scale, @IntRange(to = SCALE_BASELINE_PARENT_HEIGHT, from = SCALE_BASELINE_SELF) int scaleBaseline) {
      mInitialScale = scale;
      mInitialScaleBaseline = scaleBaseline;
  }

    public float getInitialScale() {
        return mInitialScale;
    }

    public int getInitialScaleBaseline() {
        return mInitialScaleBaseline;
    }

    public boolean isFlippedHorizontally() {
    return isFlippedHorizontally;
  }

  @NonNull public Sticker setFlippedHorizontally(boolean flippedHorizontally) {
    isFlippedHorizontally = flippedHorizontally;
    return this;
  }

  public boolean isFlippedVertically() {
    return isFlippedVertically;
  }

  @NonNull public Sticker setFlippedVertically(boolean flippedVertically) {
    isFlippedVertically = flippedVertically;
    return this;
  }

  @NonNull public Matrix getMatrix() {
    return matrix;
  }

  public Sticker setMatrix(@Nullable Matrix matrix) {
    this.matrix.set(matrix);
    return this;
  }

  public abstract void draw(@NonNull Canvas canvas);

  public abstract int getOriginalWidth();

  public abstract int getOriginalHeight();

  public abstract Sticker setDrawable(@NonNull Drawable drawable);

  @NonNull public abstract Drawable getDrawable();

  @NonNull public abstract Sticker setAlpha(@IntRange(from = 0, to = 255) int alpha);

  public float[] getBoundPoints() {
    float[] points = new float[8];
    getBoundPoints(points);
    return points;
  }

  public void getBoundPoints(@NonNull float[] points) {
    if (!isFlippedHorizontally) {
      if (!isFlippedVertically) {
        points[0] = 0f;
        points[1] = 0f;
        points[2] = getOriginalWidth();
        points[3] = 0f;
        points[4] = 0f;
        points[5] = getOriginalHeight();
        points[6] = getOriginalWidth();
        points[7] = getOriginalHeight();
      } else {
        points[0] = 0f;
        points[1] = getOriginalHeight();
        points[2] = getOriginalWidth();
        points[3] = getOriginalHeight();
        points[4] = 0f;
        points[5] = 0f;
        points[6] = getOriginalWidth();
        points[7] = 0f;
      }
    } else {
      if (!isFlippedVertically) {
        points[0] = getOriginalWidth();
        points[1] = 0f;
        points[2] = 0f;
        points[3] = 0f;
        points[4] = getOriginalWidth();
        points[5] = getOriginalHeight();
        points[6] = 0f;
        points[7] = getOriginalHeight();
      } else {
        points[0] = getOriginalWidth();
        points[1] = getOriginalHeight();
        points[2] = 0f;
        points[3] = getOriginalHeight();
        points[4] = getOriginalWidth();
        points[5] = 0f;
        points[6] = 0f;
        points[7] = 0f;
      }
    }
  }

  @NonNull public float[] getMappedBoundPoints() {
    float[] dst = new float[8];
    getMappedPoints(dst, getBoundPoints());
    return dst;
  }

  @NonNull public float[] getMappedPoints(@NonNull float[] src) {
    float[] dst = new float[src.length];
    matrix.mapPoints(dst, src);
    return dst;
  }

  public void getMappedPoints(@NonNull float[] dst, @NonNull float[] src) {
    matrix.mapPoints(dst, src);
  }

  @NonNull public RectF getBound() {
    RectF bound = new RectF();
    getBound(bound);
    return bound;
  }

  public void getBound(@NonNull RectF dst) {
    dst.set(0, 0, getOriginalWidth(), getOriginalHeight());
  }

  @NonNull public RectF getMappedBound() {
    RectF dst = new RectF();
    getMappedBound(dst, getBound());
    return dst;
  }

  public void getMappedBound(@NonNull RectF dst, @NonNull RectF bound) {
    matrix.mapRect(dst, bound);
  }

  @NonNull public PointF getCenterPoint() {
    PointF center = new PointF();
    getCenterPoint(center);
    return center;
  }

  public void getCenterPoint(@NonNull PointF dst) {
    dst.set(getOriginalWidth() * 1f / 2, getOriginalHeight() * 1f / 2);
  }

  @NonNull public PointF getMappedCenterPoint() {
    PointF pointF = getCenterPoint();
    getMappedCenterPoint(pointF, new float[2], new float[2]);
    return pointF;
  }

  public void getMappedCenterPoint(@NonNull PointF dst, @NonNull float[] mappedPoints,
      @NonNull float[] src) {
    getCenterPoint(dst);
    src[0] = dst.x;
    src[1] = dst.y;
    getMappedPoints(mappedPoints, src);
    dst.set(mappedPoints[0], mappedPoints[1]);
  }

  public float getCurrentScale() {
    return getMatrixScale(matrix);
  }

  public float getCurrentHeight() {
    return getMatrixScale(matrix) * getOriginalHeight();
  }

  public float getCurrentWidth() {
    return getMatrixScale(matrix) * getOriginalWidth();
  }

  /**
   * This method calculates scale value for given Matrix object.
   */
  public float getMatrixScale(@NonNull Matrix matrix) {
    return (float) Math.sqrt(Math.pow(getMatrixValue(matrix, Matrix.MSCALE_X), 2) + Math.pow(
        getMatrixValue(matrix, Matrix.MSKEW_Y), 2));
  }

  /**
   * @return - current image rotation angle.
   */
  public float getCurrentAngle() {
    return getMatrixAngle(matrix);
  }

  /**
   * This method calculates rotation angle for given Matrix object.
   */
  public float getMatrixAngle(@NonNull Matrix matrix) {
    return (float) Math.toDegrees(-(Math.atan2(getMatrixValue(matrix, Matrix.MSKEW_X),
        getMatrixValue(matrix, Matrix.MSCALE_X))));
  }

  public float getMatrixValue(@NonNull Matrix matrix, @IntRange(from = 0, to = 9) int valueIndex) {
    matrix.getValues(matrixValues);
    return matrixValues[valueIndex];
  }

  public boolean contains(float x, float y) {
    return contains(new float[] { x, y });
  }

  public boolean contains(@NonNull float[] point) {
    Matrix tempMatrix = new Matrix();
    tempMatrix.setRotate(-getCurrentAngle());
    getBoundPoints(boundPoints);
    getMappedPoints(mappedBounds, boundPoints);
    tempMatrix.mapPoints(unrotatedWrapperCorner, mappedBounds);
    tempMatrix.mapPoints(unrotatedPoint, point);
    StickerUtils.trapToRect(trappedRect, unrotatedWrapperCorner);
    return trappedRect.contains(unrotatedPoint[0], unrotatedPoint[1]);
  }


  protected void setContainerBound(int width, int height) {
      this.containerBounds.set(0,0, width, height);
  }

  public Rect getContainerBound() {
      return containerBounds;
  }

  public void release() {
  }
}

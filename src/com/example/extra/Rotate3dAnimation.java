package com.example.extra;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {
	// ��ת������ Ĭ��Ϊ ABSOLUTE
    private int mPivotXType = ABSOLUTE;
    private int mPivotYType = ABSOLUTE;

    private float mPivotXValue = 0.0f;
    private float mPivotYValue = 0.0f;

    private float mFromDegrees;
    private float mToDegrees;
    private float mPivotX;
    private float mPivotY;
    private Camera mCamera;
    private int mRollType;

    /**
     * ��ת��
     */
    public static final int ROLL_BY_X = 0;
    public static final int ROLL_BY_Y = 1;
    public static final int ROLL_BY_Z = 2;




    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mPivotX = 0.0f;
        mPivotY = 0.0f;
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees,
            float pivotX, float pivotY) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;

        mPivotXType = ABSOLUTE;
        mPivotYType = ABSOLUTE;
        mPivotXValue = pivotX;
        mPivotYValue = pivotY;
        initializePivotPoint();
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees,
            int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;

        mPivotXValue = pivotXValue;
        mPivotXType = pivotXType;
        mPivotYValue = pivotYValue;
        mPivotYType = pivotYType;
        initializePivotPoint();
    }

    private void initializePivotPoint()
    {
        if (mPivotXType == ABSOLUTE)
        {
            mPivotX = mPivotXValue;
        }
        if (mPivotYType == ABSOLUTE)
        {
            mPivotY = mPivotYValue;
        }
    }

    // Animation���еĳ�ʼ������ �е�������onMeasure
    @Override
    public void initialize(int width, int height, int parentWidth,
            int parentHeight)
    {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
        mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
         final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final Matrix matrix = t.getMatrix();

            mCamera.save();
            switch (mRollType) {
                case ROLL_BY_X:
                //��X����ת
                    mCamera.rotateX(degrees);
                    break;
                case ROLL_BY_Y:
                //��Y����ת
                    mCamera.rotateY(degrees);
                    break;
                case ROLL_BY_Z:
                //��Z����ת
                    mCamera.rotateZ(degrees);
                    break;
            }
            mCamera.getMatrix(matrix);
            mCamera.restore();
            matrix.preTranslate(-mPivotX, -mPivotY);
            matrix.postTranslate(mPivotX, mPivotY);
    }

}

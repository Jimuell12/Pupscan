package com.example.pupscan_dogbreedscanner.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import org.tensorflow.lite.gpu.CompatibilityList;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.Rot90Op;
import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.vision.classifier.Classifications;
import org.tensorflow.lite.task.vision.classifier.ImageClassifier;

import java.io.IOException;
import java.util.List;

/** Author: JimFlojera */
public class ImageClassifierHelper {
    public static final String TAG = "ImageClassifierHelper";
    private static final int DELEGATE_CPU = 0;
    private static final int DELEGATE_GPU = 1;
    private static final int DELEGATE_NNAPI = 2;

    // Predefined parameters
    private static final float THRESHOLD = 0.0f;
    private static final int NUM_THREADS = 4;
    private static final int MAX_RESULTS = 5;

    private final Context context;
    private final ClassifierListener imageClassifierListener;
    private ImageClassifier imageClassifier;

    /** Author: JimFlojera */
    public ImageClassifierHelper(
            Context context,
            String modelName,
            ClassifierListener imageClassifierListener) {
        this.context = context;
        setupImageClassifier(modelName);
        this.imageClassifierListener = imageClassifierListener;
    }

    public static ImageClassifierHelper create(
            Context context,
            String modelName,
            ClassifierListener listener) {
        return new ImageClassifierHelper(
                context,
                modelName,
                listener
        );
    }

    private void setupImageClassifier(String modelName) {
        ImageClassifier.ImageClassifierOptions.Builder optionsBuilder =
                ImageClassifier.ImageClassifierOptions.builder()
                        .setScoreThreshold(THRESHOLD)
                        .setMaxResults(MAX_RESULTS);

        BaseOptions.Builder baseOptionsBuilder =
                BaseOptions.builder().setNumThreads(NUM_THREADS);

        // Use CPU delegate by default
        switch (DELEGATE_CPU) {
            case DELEGATE_CPU:
                // Default
                break;
            case DELEGATE_GPU:
                if (new CompatibilityList().isDelegateSupportedOnThisDevice()) {
                    baseOptionsBuilder.useGpu();
                } else {
                    imageClassifierListener.onError("GPU is not supported on "
                            + "this device");
                }
                break;
            case DELEGATE_NNAPI:
                baseOptionsBuilder.useNnapi();
        }

        try {
            imageClassifier =
                    ImageClassifier.createFromFileAndOptions(
                            context,
                            modelName,
                            optionsBuilder.build());
        } catch (IOException e) {
            imageClassifierListener.onError("Image classifier failed to "
                    + "initialize. See error logs for details");
            Log.e(TAG, "TFLite failed to load model with error: "
                    + e.getMessage());
        }
    }

    public void classify(Bitmap image, int imageRotation) {
        if (imageClassifier == null) {
            // Handle the case when imageClassifier is not initialized
            imageClassifierListener.onError("Image classifier is not initialized");
            return;
        }

        long inferenceTime = SystemClock.uptimeMillis();

        ImageProcessor imageProcessor =
                new ImageProcessor.Builder().add(new Rot90Op(-imageRotation / 90)).build();

        TensorImage tensorImage =
                imageProcessor.process(TensorImage.fromBitmap(image));

        List<Classifications> result = imageClassifier.classify(tensorImage);

        inferenceTime = SystemClock.uptimeMillis() - inferenceTime;
        imageClassifierListener.onResults(result, inferenceTime);
    }

    public void clearImageClassifier() {
        imageClassifier = null;
    }

    public interface ClassifierListener {
        void onError(String error);

        void onResults(List<Classifications> results, long inferenceTime);
    }
}

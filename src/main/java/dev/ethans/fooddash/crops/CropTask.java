package dev.ethans.fooddash.crops;

public interface CropTask {

    void run(Crop crop);

    boolean isCancelled();

    void cancel();
}

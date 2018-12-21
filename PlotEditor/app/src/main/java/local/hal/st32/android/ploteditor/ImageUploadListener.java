package local.hal.st32.android.ploteditor;

public interface ImageUploadListener {
    abstract public void postCompletion(byte[] response);
    abstract public void postFialure();
}

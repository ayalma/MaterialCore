package material.core.view;

import android.support.annotation.NonNull;



public interface InputView extends ValidStateView {
    /**
     * Performs validation
     */
    void validate();

    /**
     * Adds a listener
     *
     * @param listener cannot be null
     */
    void addOnValidateListener(@NonNull OnValidateListener listener);

    /**
     * Removes a listener
     *
     * @param listener cannot be null
     */
    void removeOnValidateListener(@NonNull OnValidateListener listener);

    /**
     * Removes all listeners
     */
    void clearOnValidateListeners();
}

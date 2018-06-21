package material.core.view;


import material.core.view.OnTransformationChangedListener;

public interface TransformationView {
    void addOnTransformationChangedListener(OnTransformationChangedListener listener);

    void removeOnTransformationChangedListener(OnTransformationChangedListener listener);

    void clearOnTransformationChangedListeners();

}

package View;

/**
 * Interface for the View in our MVVM architecture.
 * It defines some basic operations that the view controller should implement.
 */
public interface IView {
    void showMessage(String message);
    void showError(String errorMessage);
    void setLoading(boolean isLoading);
    void clearFields();
    void closeWindow();
}
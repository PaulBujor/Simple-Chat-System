package dk.via.utility;

import java.beans.PropertyChangeListener;

public interface PropertyChangeSubject {
    public void addListener(PropertyChangeListener listener);
    public void removeListener(PropertyChangeListener listener);
    public void addListener(String eventID, PropertyChangeListener listener);
    public void removeListener(String eventID, PropertyChangeListener listener);
}
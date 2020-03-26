package dk.via.viewmodel;

import dk.via.model.Model;
import javafx.beans.property.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewModel implements PropertyChangeListener
{
    private StringProperty userName;
    private IntegerProperty IPAddress;
    private StringProperty host;

  private BooleanProperty connected;

  private Model model;

  public LoginViewModel(Model model)
  {
    this.model = model;
    this.userName = new SimpleStringProperty();
    this.IPAddress = new SimpleIntegerProperty();
    this.host = new SimpleStringProperty();
this.connected = new SimpleBooleanProperty(false);

  }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {

    }
}

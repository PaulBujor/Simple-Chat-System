package dk.via.viewmodel;

import dk.via.model.Model;
import javafx.application.Platform;
import javafx.beans.property.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class LoginViewModel implements PropertyChangeListener
{
  private StringProperty userName;
  private IntegerProperty port;
  private StringProperty host;

  private BooleanProperty connected;

  private Model model;

  public LoginViewModel(Model model)
  {
    this.model = model;
    this.userName = new SimpleStringProperty();
    this.port = new SimpleIntegerProperty();
    this.host = new SimpleStringProperty();
    this.connected = new SimpleBooleanProperty(false);

  }

  public void connect() throws IOException
  {
    connectedProperty().setValue(model.connect(hostProperty().get(),port.get(),userNameProperty().get()));
  }


  public StringProperty userNameProperty()
  {
    return userName;
  }



  public IntegerProperty portProperty()
  {
    return port;
  }



  public StringProperty hostProperty()
  {
    return host;
  }


  public BooleanProperty connectedProperty()
  {
    return connected;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}

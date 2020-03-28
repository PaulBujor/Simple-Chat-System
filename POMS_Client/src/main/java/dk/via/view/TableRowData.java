package dk.via.view;

import dk.via.utility.Message;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRowData {

  private StringProperty userMessage;

  public TableRowData(Message message){
    this.userMessage = new SimpleStringProperty(message.getMessage());
  }

  public StringProperty getUserMessage(){
    return userMessage;
  }

}

package misc;

public class TestMe {

  private int int_value;
  private String string_value;
  
  public static void main(String[] args)
  {
    TestMe testMe = new TestMe();
    testMe.setInt_value(1);
    testMe.setString_value("test");

    int integer = testMe.getInt_value();
    String string = testMe.getString_value();

    String toString = testMe.toString();
  }

  public TestMe()
  {
  }

  public int getInt_value()
  {
    return int_value;
  }

  public String getString_value()
  {
    return string_value;
  }

  public void setInt_value(int value)
  {
    int_value = value;
  }

  public void setString_value(String value)
  {
    string_value = value;
  }

  public String toString()
  {
    return "String value: " + string_value + " int value: " + int_value;
  }
  
}


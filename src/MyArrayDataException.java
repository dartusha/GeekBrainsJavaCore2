--Exception class
public class MyArrayDataException extends RuntimeException{
    private int x, y; 
    private String value;

    public MyArrayDataException(Throwable e) {
        super(e);
    }

    public MyArrayDataException(String str) {
        super(str);
    }

    public MyArrayDataException(int x, int y, String value) {
        super();
        this.x=x;
        this.y=y;
        this.value=value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getValue() {
        return value;
    }

    public MyArrayDataException() {
        super();
    }
}

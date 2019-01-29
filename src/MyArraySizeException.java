public class MyArraySizeException extends RuntimeException{
    public MyArraySizeException(Throwable e) {
            super(e);
        }

    //Т.е. можно еще и сразу передавать сообщение об ошибке в случае срабатывания. Но при обработке throw-catch не выводится
    public MyArraySizeException(String str) {
        super(str);
    }

    public MyArraySizeException() {
        super();
    }
    }

package Results;

public class ErrorResult
{
    private String message;

    public ErrorResult(String msg)
    {
        this.message = msg;
    }

    public String getMessage()
    {
        return this.message;
    }
}

public class Cell {
    private Status status;


    public Cell() {
        this.status = null;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    enum Status {
        BOTH_CLOSED,
        RIGHT_ONLY,
        DOWN_ONLY,
        BOTH_OPEN
    }



}

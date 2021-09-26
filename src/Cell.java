public class Cell {
    private Type type;
    private Status status;

    public Cell() {
        this.type = null;
        this.status = null;
    }

    public Cell(Type inType, Status inStatus) {
        this();
        this.type = inType;
        this.status = inStatus;
    }

    enum Type {
        START,
        FINISH,
        INNER
    }

    enum Status {
        BOTH_CLOSED,
        RIGHT_ONLY,
        DOWN_ONLY,
        BOTH_OPEN
    }

    public Status getStatus() {
        return this.status;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "type=" + type +
                ", status=" + status +
                '}';
    }
}



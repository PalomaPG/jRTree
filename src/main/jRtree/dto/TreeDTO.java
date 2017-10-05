package dto;

public class TreeDTO extends AbstractDTO{

    public int capacity;
    public long rootPtr;

    public TreeDTO(int capacity, long rootPtr) {
        this.capacity = capacity;
        this.rootPtr = rootPtr;
    }
}

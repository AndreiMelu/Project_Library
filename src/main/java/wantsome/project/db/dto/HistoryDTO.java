package wantsome.project.db.dto;

import java.time.LocalDate;
import java.util.Objects;

public class HistoryDTO {
    private int id;
    private int itemId;
    private int userId;
    private HistoryState state;
    private LocalDate lentDate;
    private LocalDate returnDate;


    public HistoryDTO(int id, int itemId, int userId, HistoryState state, LocalDate lentDate, LocalDate returnDate) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
        this.lentDate = lentDate;
        this.returnDate = returnDate;
        this.state = state;
    }
//Getters

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getLentDate() {
        return lentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public HistoryState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "HistoryDTO{" +
                "id=" + id +
                ", item_id=" + itemId +
                ", user_id=" + userId +
                ", lent_Date=" + lentDate +
                ", return_Date=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryDTO)) return false;
        HistoryDTO that = (HistoryDTO) o;
        return getId() == that.getId() &&
                getItemId() == that.getItemId() &&
                getUserId() == that.getUserId() &&
                getLentDate().equals(that.getLentDate()) &&
                Objects.equals(getReturnDate(), that.getReturnDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItemId(), getUserId(), getLentDate(), getReturnDate());
    }
}

package pl.ias.pas.hotelroom.pasrest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbVisibility;
import java.beans.BeanProperty;
import java.text.DateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Reservation {

    @EqualsAndHashCode.Include
    private UUID id;
    private Long startDate;
    private Long endDate;
    private UUID userId;
    private UUID roomId;

    public Reservation(UUID id, UUID userId, UUID roomId, Long startDate, Long endDate) {
        this(id, userId, roomId, startDate);
        this.endDate = endDate;
    }

    public Reservation(UUID id, UUID userId, UUID roomId, Long startDate) {
        this(id, userId, roomId);
        this.startDate = startDate;
    }

    public Reservation(UUID id, UUID userId, UUID roomId) {
        this.id = id;
        this.startDate = System.currentTimeMillis();
        this.endDate = null;
        this.userId = userId;
        this.roomId = roomId;
    }

    public void setUserId(String userId) {
        this.userId = UUID.fromString(userId);
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setRoomId(String roomId) {
        this.roomId = UUID.fromString(roomId);
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    @JsonbTransient
    public Date getActualStartDate() {
        return new Date(startDate);
    }

    @JsonbTransient
    public Date getActualEndDate() {
        return new Date(endDate);
    }



//    public void setEndDate(Date endDate) {
//        if(this.startDate.after(endDate)) {
//            throw new RuntimeException();
//        }
//        this.endDate = endDate;
//    }

    // nwm co zrobić, żeby te Depracted byłoy ignorowane przy beanach
    @Deprecated
    @JsonbTransient
    public void setActive(boolean active) {
        return;
    }
    @Deprecated
    @JsonbTransient
    public void setActualStartDate(String date) {
        return;
    }
    @Deprecated
    @JsonbTransient
    public void setActualEndDate(String date) {
        return;
    }

    @JsonbTransient
    public boolean isActive() {
        if (this.endDate == 0) {
            return true;
        }

        return this.startDate < this.endDate;
//        return new Date(System.currentTimeMillis()).after(endDate);
    }
}

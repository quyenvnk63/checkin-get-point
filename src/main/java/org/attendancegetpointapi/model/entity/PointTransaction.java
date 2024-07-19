package org.attendancegetpointapi.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "point_transaction")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointTransaction extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int balance;
}

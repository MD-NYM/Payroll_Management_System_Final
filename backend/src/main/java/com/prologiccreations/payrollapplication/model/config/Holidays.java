package com.prologiccreations.payrollapplication.model.config;

import java.util.LinkedList;
import java.util.List;

import com.prologiccreations.payrollapplication.model.super_classes.ApprovableEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "holidays")
public class Holidays extends ApprovableEntity {

    private Integer year;
    private Integer month;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "holiday_dates", joinColumns = @JoinColumn(name = "holidays_id"))
    @Column(name = "dates", nullable = false)
    private List<String> dates = new LinkedList<>();

}

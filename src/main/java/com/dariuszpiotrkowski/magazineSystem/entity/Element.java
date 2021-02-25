package com.dariuszpiotrkowski.magazineSystem.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="component")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="part_number")
    @NotNull(message = "To pole nie może być puste")
    @Size(min = 1, message = "To pole nie może być puste")
    private String partNumber;

    @Column(name="type")
    private String type;

    @Column(name="parameters")
    private String parameters;

    @Column(name="number")
    @Min(value = 0)
    @Max(value = 100000000)
    private int number;

    public Element() {

    }

    public Element(int id, @NotNull(message = "To pole nie może być puste") @Size(min = 1, message = "To pole nie może być puste") String partNumber, String type, String parameters, @Min(value = 0) @Max(value = 100000000) int number) {
        this.id = id;
        this.partNumber = partNumber;
        this.type = type;
        this.parameters = parameters;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

package com.dariuszpiotrkowski.magazineSystem.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="number")
    @NotNull(message = "To pole nie może pozostać puste")
    @Size(min = 1, message = "To pole nie może pozostać puste")
    private String number;

    @Column(name="amount")
    @Min(value = 0)
    @Max(value = 1000000)
    private int amount;

    @Column(name="stage")
    private String stage;

    @Column(name="completed")
    @Min(value = 0)
    @Max(value = 1000000)
    private int completedNumber;

    @Column(name="price")
    @Min(value = 0)
    @Max(value = 100000000)
    private double price;

    @Column(name="bom_path")
    private String bomPath;

    @Column(name="to_buy_path")
    private String toBuyPath;

    @Column(name="receive_date")
    private String receiveDate;

    @Column(name="deadline")
    private String deadline;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="employee_id")
    private User user;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="project_id")
    private Project project;

    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public int getCompletedNumber() {
        return completedNumber;
    }

    public void setCompletedNumber(int completedNumber) {
        this.completedNumber = completedNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getBomPath() {
        return bomPath;
    }

    public void setBomPath(String bomPath) {
        this.bomPath = bomPath;
    }

    public String getToBuyPath() {
        return toBuyPath;
    }

    public void setToBuyPath(String toBuyPath) {
        this.toBuyPath = toBuyPath;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}

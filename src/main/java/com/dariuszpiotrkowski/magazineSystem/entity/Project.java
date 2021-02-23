package com.dariuszpiotrkowski.magazineSystem.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="project")
public class Project {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="comments")
    private String comments;

    @Column(name="THTcomments")
    private String thtComments;

    @Column(name="SMTcomments")
    private String smtComments;

    @Column(name="doc_path")
    private String docPath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="pcb_id")
    private Pcb pcb;

    @OneToMany(mappedBy = "project",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Order> orders;

    public Project() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getThtComments() {
        return thtComments;
    }

    public void setThtComments(String thtComments) {
        this.thtComments = thtComments;
    }

    public String getSmtComments() {
        return smtComments;
    }

    public void setSmtComments(String smtComments) {
        this.smtComments = smtComments;
    }

    public Pcb getPcb() {
        return pcb;
    }

    public void setPcb(Pcb pcb) {
        this.pcb = pcb;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }
}

package application.model;

public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer status;
    public void getUser(){

    }
    public User(String name, String password, Integer status){
        this.name = name;
        this.password = password;
        this.status = status;
    }
    public User(Integer id, String name, String password, Integer status){
        this.id = id;
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStatus() {
        return status;
    }
}

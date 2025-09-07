public class User {
    private int uid;       // 用户ID
    private String name;   // 用户名
    private String pwd;    // 密码

    public User(int uid, String name, String pwd) {
        this.uid = uid;
        this.name = name;
        this.pwd = pwd;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}

import java.util.List;

public class UserDao {
    // 注册用户
    public int reg(String name, String pwd) {
        List<User> users = CF.readUsers();
        // 简单判断用户名是否已存在
        for (User user : users) {
            if (user.getName().equals(name)) {
                return 0; // 用户名已存在，注册失败
            }
        }
        // 生成新用户ID（简单自增，实际可更复杂）
        int newUid = users.isEmpty() ? 1 : users.get(users.size() - 1).getUid() + 1;
        users.add(new User(newUid, name, pwd));
        CF.writeUsers(users);
        return 1; // 注册成功
    }

    // 用户登录
    public int login(String name, String pwd) {
        List<User> users = CF.readUsers();
        for (User user : users) {
            if (user.getName().equals(name)) {
                if (user.getPwd().equals(pwd)) {
                    return 1; // 登录成功
                } else {
                    return 2; // 密码错误
                }
            }
        }
        return 0; // 用户不存在
    }

    // 根据用户名查询用户ID
    public int getId(String name) {
        List<User> users = CF.readUsers();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user.getUid();
            }
        }
        return -1; // 用户不存在
    }
}

package swt.project.db;

public interface IUserDAO {
	public void add(String userName, String password);
	public boolean check(String userName, String password);

}

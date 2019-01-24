package swt.project.db;

public interface IUserDAO {
	public void put(String userName, String password);
	public boolean check(String userName, String password);

}

package swt.project.db;

public interface IExchangeWithDB {
	public void putToDB(String word, String translate);
	public void delFromDB(String word);
}

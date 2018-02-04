package main;

import javax.sql.DataSource;

public interface myDAO {
	public void setDataSource(DataSource ds);
	   
	public void create(Pojo obj);
	   
	public Pojo read(long id);   
	
	public void delete(long id);
	
	public void update(Pojo obj);
	
}

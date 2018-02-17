package main;

import java.math.BigInteger;

import javax.sql.DataSource;

public interface myDAO {
	public void setDataSource(DataSource ds);
	   
	public void create(Pojo obj);
	   
	public Pojo read(BigInteger id);   
	
	public void delete(BigInteger id);
	
	public void update(Pojo obj);
	
}

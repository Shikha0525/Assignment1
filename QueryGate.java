package com.qait.AdvanceCourse.AdvanceCourseMaven;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class QueryGate
{
	WebDriver driver;
	/*private static Logger log = (Logger) LogFactory
		       .getLog(QueryGate.class);*/
	public QueryGate(WebDriver driver)
	{
		this.driver=driver;
	}
	public void verify() throws ClassNotFoundException, SQLException
	{
		String hostUrl="jdbc:mysql://10.0.1.86/tatoc";
		String userName="tatocuser";
		String password="tatoc01";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(hostUrl,userName,password);
		java.sql.Statement stmt = conn.createStatement();
		WebElement symbol=driver.findElement(By.id("symboldisplay"));
		String symt=symbol.getText();
		
		String query="SELECT identity.id,credentials.id,name,passkey,symbol FROM identity INNER JOIN credentials ON identity.id=credentials.id";
		ResultSet rs = stmt.executeQuery(query);
		
		while (rs.next()) 
		{
			String symb= rs.getString("symbol");
			
			String  name = rs.getString("name");
			String pass= rs.getString("passkey");
			
			if(symb.equalsIgnoreCase(symt))
			{
				
				driver.findElement(By.id("name")).sendKeys(name);
				driver.findElement(By.id("passkey")).sendKeys(pass);
				driver.findElement(By.id("submit")).click();
				break;
			}
			
		}
		String url="http://10.0.1.86/tatoc/advanced/video/player";
		Assert.assertEquals(url, driver.getCurrentUrl());

	}
}
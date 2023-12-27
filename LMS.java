import java.sql.*;
import java.util.*;
import javax.swing.*;
public class LMS {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		Connection con=null;
		Statement s=null;
		ResultSet r=null;
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","student","student");
			System.out.println("DatabasE connection successful");
			s=con.createStatement();
			Util.display("WELCOME TO LIBRARY");
			String [] options= {"ADMIN","STUDENT","EXIT"};
			boolean flg=true;
			while(flg==true)
			{
				int no=Util.oInput("LIBRARY MANAGEMENT SYSTEM","SELECT ONE OPTION",options);
				if(no==2)
					break;
				if(no == 0)
				{
					//Checking login credentials
					String password=Util.sInput("ENTER YOUR PASSWORD");
					if(password.equals("password"))
					{
						//show Admin Options
						boolean flag=true;
						while(flag == true)
						{
							String [] adminoptions= {"ADD A NEW BOOK","ADD A NEW STUDENT","ISSUE A BOOK","RETURN A BOOK","SHOW RECORDS","LOGOUT","EXIT"};
							int opt=Util.oInput("LIBRARY MANAGEMENT SYSTEM", "SELECT ONE CHOICE", adminoptions);
							switch(opt)
							{
							case 0:
								String ISBN = Util.sInput("Enter Book ISBN No");
								String Title =Util.sInput("Enter Book Title");
								String Author =Util.sInput("Enter Book Author Name");
								String Quantity = Util.sInput("Enter Quantity of This Book");
								String Price = Util.sInput("Enter Price of This Book");
								int rows_affected=s.executeUpdate("insert into BOOK (ISBN,Title,Author,Quantity,Price) values " + "('" + ISBN + "'," + "'" + Title + "'," + "'" + Author + "'," + "'" + Quantity + "'," + "'" + Price + "')");
								Util.display("Book Added Successfully!");
								break;
							case 1:
								String Student_id=Util.sInput("Enter Student id");
								String Name= Util.sInput("Enter Student Name");
								String Course =Util.sInput("Enter Course Name");
								String Branch = Util.sInput("Enter Branch");
								String State = Util.sInput("Enter State");
								int rows_Affected=s.executeUpdate("insert into STUDENT (Student_id,Name,Course,Branch,State) values " + "('" + Student_id + "'," + "'" +Name + "'," + "'" + Course + "'," + "'" + Branch + "'," + "'" + State + "')");
								Util.display("Student Added Successfully!");
								break;
							case 2:
								String Student_Id=Util.sInput("Enter Student id");
								String tmp=null;
								ResultSet check=s.executeQuery("select Name from STUDENT where Student_id = " + Student_Id);
								if(check.next())
									tmp=check.getString("Name");
								if(tmp==null)
								{
									Util.display("Do new Registration in the library by selecting Add New student");
									break;
								}
								String ISBN_NO = Util.sInput("Enter Book ISBN No");
								ResultSet check1=s.executeQuery("select Quantity from BOOK where ISBN = " + ISBN_NO);
								if(check.next())
									tmp=check1.getString("Quantity");
								if(tmp==null || tmp=="0")
								{
									Util.display("The Book selected is not available currently in the library!");
									break;
								}
								int count=Integer.parseInt(tmp); //Quantity of the corresponding book
								count--;
								String issue_date=Util.sInput("Enter Today's Date in dd/mm/yyyy format");
								String returndate=null;
								int Rows_affected=s.executeUpdate("insert into RECORD (Student_id,ISBN,Issue_date,Return_date) values " + "('" + Student_Id + "'," + "'" +ISBN_NO + "'," + "'" +issue_date  + "'," + "'" +returndate + "')");
								
								Util.display("Book Issued Successfully!");
								int y=s.executeUpdate("update BOOK set Quantity = " + Integer.toString(count) + "where ISBN = " + ISBN_NO);
								break;
							case 3:
								String StudentId=Util.sInput("Enter Student id");
								String ISBNNO = Util.sInput("Enter Book ISBN No");
								String return_date=Util.sInput("Enter Return Date in dd/mm/yyyy format");
								int Row_affected=s.executeUpdate("update RECORD set Return_date = " + return_date + "where Student_id = " + StudentId + "and ISBN = " + ISBNNO);
		
								Util.display("Book Received Successfully!");
								ResultSet check2=s.executeQuery("select Quantity from BOOK where ISBN = " + ISBNNO);
								tmp="0";
								if(check2.next())
									tmp=check2.getString("Quantity");
								int cnt=Integer.parseInt(tmp);
								cnt++;
								int l=s.executeUpdate("update BOOK set Quantity = " + Integer.toString(cnt) + "where ISBN = " + ISBNNO);
								break;
							case 4:
								ResultSet data=s.executeQuery("select r.student_id, s.name, r.isbn, r.issue_date, r.return_date from record r inner join student s on r.Student_id = s.student_id ;");
								String all="\n ALL BOOKS \n";
								int tot2=1;
								while(data.next())
								{
									String h=Integer.toString(tot2);
									all+= "\n" + h + ". " + "STUDENT ID: "+ data.getString("r.student_id") + "----" +"STUDENT_NAME: "+ data.getString("s.name") + "----" +"ISBN: "+data.getString("r.isbn") + "----"+"ISSUE_DATE: "+data.getString("r.issue_date")+"----"+"RETURN_DATE: "+data.getString("r.return_date")+"\n";
									tot2++;
								}
								Util.display(all);
								break;
							case 5:
								flag=false;
								break;
							case 6:
								flg=flag=false;
								break;
							
							default:
								break;
							}
						}
					}
					else
					{
						Util.display("WRONG PASSWORD");
						continue;
					}
				}
				else
				{
					//show Student Options
					String [] student_options= {"SEARCH BOOK","SEE AVAILABLE BOOKS","GO BACK TO MAIN MENU","EXIT"};
					boolean fg=true;
					while(fg)
					{
						int op=Util.oInput("LIBRARY MANAGEMENT SYSTEM", "SELECT ONE CHOICE", student_options);
						switch(op)
						{
						case 0:
							String s_book=Util.sInput("ENTER BOOK NAME TO BE SEARCHED");
							ResultSet check3=s.executeQuery("select Quantity from BOOK where Title = " + s_book);
							String tmp="0";
							if(check3.next())
								tmp=check3.getString("Quantity");
							if(tmp==null || tmp=="0")
								Util.display("The Book selected is not available in the library!");
							else
								Util.display("The Book selected is available in the library!");
							break;
						case 1:
							ResultSet data=s.executeQuery("select * from BOOK");
							String all="\n ALL BOOKS \n";
							int tot=1;
							while(data.next())
							{
								String h=Integer.toString(tot);
								all+= "\n" + h + ". " + data.getString("Title") + " by - " + data.getString("Author");
								tot++;
							}
							Util.display(all);
							break;
						case 2:
							fg=false;
							break;
						case 3:
							fg=flg=false;
							break;
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();		
			}
	}
	public static class Util
	{
		public static int iInput(String msg)
		{
			int d=0;
			try
			{
				d=Integer.parseInt(JOptionPane.showInputDialog(null,msg));
			}
			catch(Exception e){}

			return d;
		}
		public static double dInput(String msg)
		{
			double d=0.0;
			try
			{
				d=Double.parseDouble(JOptionPane.showInputDialog(null,msg));
			}
			catch (Exception e){}
			return d;
		}
		public static void display(String s)
		{
			JOptionPane.showMessageDialog(null,s);
		}
		public static String sInput(String msg)
		{
			String s=JOptionPane.showInputDialog(null,msg);
			return s;
		}
		public static int oInput(String title,String msg,String [] option)
		{
			int opt=JOptionPane.showOptionDialog(null,msg,title,JOptionPane.YES_OPTION,JOptionPane.PLAIN_MESSAGE,null,option,option[0]);
			return opt;
		}
	}
	
}


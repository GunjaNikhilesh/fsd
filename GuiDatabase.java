import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class GuiDatabase extends Frame implements ActionListener
{
	Label label;
	Label id;
	TextField idtext;
	Label employee_name;
	TextField employee_text;
	TextField text;
	Button btn;
	Button save;
	TextArea textarea;
	String url="jdbc:oracle:thin:@ntsocietyserver:1521:oralbrce";
	String username="scott";
	String pwd="tiger";
	
	
	GuiDatabase()
	{
		setSize(600,400);
		setVisible(true);
		//setLayout(new FlowLayout());
		//setResizable(false);
		label=new Label("Enter employee number to search :");
		text=new TextField(15);
		btn = new Button("Search");
		save = new Button("Save");
		textarea = new TextArea(10,20);
		
		id=new Label("Enter  employee id :");
		employee_name=new Label("Enter  employee name :");
		
		idtext=new TextField(15);
		employee_text=new TextField(30);
		//adding components
		add(label);
		add(text);
		add(btn);
		add(textarea);
		add(id);
		add(employee_name);
		add(idtext);
		add(employee_text);
		add(save);
		text.setForeground(Color.GREEN);
		btn.setBackground(Color.PINK);
		label.setBounds(50,50,200,20);
		text.setBounds(270,50,200,30);
		btn.setBounds(220,90,70,25);
		textarea.setBounds(200,130,150,50);
		id.setBounds(50,200,200,22);
		idtext.setBounds(270,200,200,32);
		employee_name.setBounds(50,250,200,22);
		employee_text.setBounds(270,250,200,30);	
		save.setBounds(220,290,150,40);
		//registering listener
		btn.addActionListener(this);
		save.addActionListener(this);
		
		
	}
public void actionPerformed(ActionEvent e)
{
	try{
	//registering driver
	Class.forName("oracle.jdbc.driver.OracleDriver");
	System.out.println("Driver Registered Successfully");

	//establish the connection
	Connection connect=DriverManager.getConnection(url,username,pwd);
	System.out.println("connection established succesfully");
	//statement creation
	String query="SELECT * FROM nikhilesh WHERE eid=?";
	PreparedStatement stmt=connect.prepareStatement(query);
	

	//reading input from textfield
	
	String empnum=text.getText();
	stmt.setString(1,empnum);
	ResultSet result=stmt.executeQuery();
	int x=0;
	while(result.next())
	{
	textarea.setForeground(Color.RED);
	textarea.setBackground(Color.GREEN);
	System.out.println(result.getString(1)+"  "+result.getString(2));
	textarea.setText(result.getString(1)+"\t"+result.getString(2)+"\n");
	x++;
	}//while end



	if(x==0)
		{
		System.out.println("record not found");
		textarea.setText("record not found");
		textarea.setBackground(Color.CYAN);
		textarea.setForeground(Color.RED);
		}




	

	//inserting data into database
	
	int empid=Integer.parseInt(idtext.getText());
	System.out.println(empid);
	String empname=employee_text.getText();
	System.out.println(empname);

	String insertquery="INSERT INTO nikhilesh VALUES(?,?)";
	PreparedStatement ps=connect.prepareStatement(insertquery);
	ps.setInt(1,empid);
	ps.setString(2,empname);
	int i=ps.executeUpdate();
	System.out.println(i+"records inserted");
	//closing the connection
	connect.close();
	
	}//try end
	catch(Exception ex)
	{
	System.out.println(e);
	}
}

public static void main(String args[])
{
GuiDatabase gui = new GuiDatabase();
}
}
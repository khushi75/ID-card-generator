import java.sql.*;
import java.io.Console;
import java.io.*;
import java.util.Scanner;

public class details {//Main Class

	public static String getSpace(int c) { // Adding Spaces For Decoration
		String spaces = "  ";
		for (int i = 0; i < 12 - c; i++) {
			spaces += " ";
		}
		return spaces;
	}

	public static void main(String[] args) throws Exception { //Main Method
		String jdbcurl = "jdbc:mysql://localhost:3306/sampledb";
		String username = "root";
		String password = "Khushi_31";

		String t="";//for rerun
		
		do {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(jdbcurl, username, password);

		Scanner sc1 = new Scanner(System.in);
			
		System.out.println("...List of Operations...");//List of Operations
		System.out.println("1: Insert Entry");
		System.out.println("2: View Entry");
		System.out.println("3: Modify Entry");
		System.out.println("4: Delete Entry");

		int choice;
		System.out.println("Enter the number of your desired operation:");
		choice = sc1.nextInt();

		// Insert Entry in Database
		if (choice == 1) {
			System.out.println("Enter following Details:");
			System.out.println("Name:");
			String Name = sc1.next();

			System.out.println("Address:");
			String Address = sc1.next();

			System.out.println("City:");
			String City = sc1.next();

			System.out.println("BirthDate:");
			String BirthDate = sc1.next();

			System.out.println("Blood Group:");
			String Blood_group = sc1.next();

			System.out.println("Branch:");
			String Branch = sc1.next();

			System.out.println("Passout_Year:");
			int Passout_year = sc1.nextInt();

			System.out.println("Contact:");
			int Contact = sc1.nextInt();
			sc1.nextLine();

			System.out.println("Mail:");
			String Mail = sc1.next();

			System.out.println("Photo:");
			String Photo = sc1.next();

			System.out.println("Sign:");
			String Sign = sc1.next();

			try {

				String sql = "INSERT INTO sampledb.student (Name, Address, City, BirthDate, Blood_group, Branch, Passout_year, Contact, Mail, photo, sign)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, Name);
				statement.setString(2, Address);
				statement.setString(3, City);
				statement.setString(4, BirthDate);
				statement.setString(5, Blood_group);
				statement.setString(6, Branch);
				statement.setInt(7, Passout_year);
				statement.setInt(8, Contact);
				statement.setString(9, Mail);
				statement.setString(10, Photo);
				statement.setString(11, Sign);

				int rows = statement.executeUpdate();
				if (rows > 0) {
					System.out.println("A new entry has been inserted successfully");
				}

				connection.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		// View Records using Name
		else if (choice == 2) {
			System.out.println("Enter the student name whose data you want to view	 : ");
			Scanner sc2 = new Scanner(System.in);
			String name = sc2.nextLine();

			String sql = "SELECT * FROM student WHERE Name = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);

			try {

				ResultSet rs = pstmt.executeQuery();

				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"ID       Name         Address        City      BirthDate   Blood_group  Branch      Passout_year    Contact      Mail            photo        sign");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------------------------");

				while (rs.next()) { // Particular Record
					System.out.println("  " + rs.getInt("ID") + "  " + rs.getString("Name")
							+ details.getSpace(rs.getString("Name").length()) + rs.getString("Address")
							+ details.getSpace(rs.getString("Address").length()) + rs.getString("City")
							+ details.getSpace(rs.getString("City").length()) + rs.getString("BirthDate")
							+ details.getSpace(rs.getString("BirthDate").length()) + rs.getString("Blood_group")
							+ details.getSpace(rs.getString("Blood_group").length()) + rs.getString("Branch")
							+ details.getSpace(rs.getString("Branch").length()) + rs.getString("Passout_year")
							+ details.getSpace(rs.getString("Passout_year").length()) + rs.getString("Contact")
							+ details.getSpace(rs.getString("Contact").length()) + rs.getString("Mail")
							+ details.getSpace(rs.getString("Mail").length()) + rs.getString("photo")
							+ details.getSpace(rs.getString("photo").length()) + rs.getString("sign")
							+ details.getSpace(rs.getString("sign").length()));
				}
				System.out.println();
				pstmt.close();
				rs.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}

		// Modify operation
		else if (choice == 3) {
			System.out.println("Enter the student name whose data you want to Update: ");
			Scanner sc3 = new Scanner(System.in);
			String name = sc3.nextLine();
			System.out.println(
					"Enter the choice you want to modify :1] Name 2]Address 3]City 4]BirthDate 5]Blood_group 6]Branch 7]Passout_year 8]Contact 9]Mail 10]photo 11]sign");
			int ch = sc3.nextInt();
			sc3.nextLine();
			System.out.println("Enter correct detail: ");
			String newr = sc3.nextLine();
			String oldr = "";

			switch (ch) {
			case 1:
				oldr = "Name";
				break;
			case 2:
				oldr = "Address";
				break;
			case 3:
				oldr = "City";
				break;
			case 4:
				oldr = "BirthDate";
				break;
			case 5:
				oldr = "Blood_group";
				break;
			case 6:
				oldr = "Branch";
				break;
			case 7:
				oldr = "Passout_year";
				break;
			case 8:
				oldr = "Contact";
				break;
			case 9:
				oldr = "Mail";
				break;
			case 10:
				oldr = "Photo";
				break;
			case 11:
				oldr = "Sign";
				break;

			}

			String sql = "UPDATE student SET " + oldr + " = ? WHERE Name = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, newr);
			pstmt.setString(2, name);

			int rows = pstmt.executeUpdate();

			if (rows > 0) {
				System.out.println("Your details has been Updated Successfully.");
			}
			connection.close();
		}

		// Delete the record with s_id
		else if (choice == 4) {
			int s_id;

			System.out.print("Enter Student ID to Delete the Record : ");

			Scanner sc4 = new Scanner(System.in);
			s_id = sc4.nextInt();

			try {
				String sql = "DELETE FROM student WHERE ID = ?";
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, s_id);

				int rows = pstmt.executeUpdate();

				if (rows > 0) {
					System.out.println("Record has been Deleted Successfully.");
				}
				connection.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			System.out.println("Please Enter Valid Operation Choice");
		}
		
	System.out.print("You want to continue...Y/N");
	t = sc1.next();
}while(t=="Y" || t!="y");//Currently not working properly
	}//Ending of Main Method
}//Ending of Main Class
	

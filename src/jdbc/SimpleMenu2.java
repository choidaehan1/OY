package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SimpleMenu2 {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/firm";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mysql";
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			boolean exit = false;

			while (!exit) {
				System.out.println("1. 데이터 보기");
				System.out.println("2. 데이터 삽입");
				System.out.println("3. 데이터 업데이트");
				System.out.println("4. 데이터 삭제");
				System.out.println("5. 종료");
				System.out.print("선택하세요: ");

				String choice = scanner.nextLine();

				switch (choice) {
				case "1":
					viewData(connection);
					break;
				case "2":
					insertData(connection);
					break;
				case "3":
					updateData(connection);
					break;
				case "4":
					deleteData(connection);
					break;
				case "5":
					exit = true;
					break;
				default:
					System.out.println("유효하지 않은 선택. 다시 시도하세요.");
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void viewData(Connection connection) {
		String sql = "select * from emp";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				System.out.print(rs.getInt("empno") + "\t");
				System.out.print(rs.getString("ename") + "\t");
				System.out.print(rs.getString("job") + "\t");
				System.out.print(rs.getInt("mgr") + "\t");
				System.out.print(rs.getString("hiredate") + "\t");
				System.out.print(rs.getDouble("sal") + "\t");
				System.out.print(rs.getDouble("comm") + "\t");
				System.out.println(rs.getInt("deptno") + "\t");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertData(Connection connection) {
		System.out.print("사번:");
		String empno = scanner.nextLine();
		System.out.print("이름:");
		String ename = scanner.nextLine();
		System.out.print("직무:");
		String job = scanner.nextLine();
		System.out.print("매니저사번:");
		String mgr = scanner.nextLine();
		System.out.print("입사년도:");
		String hiredate = scanner.nextLine();
		System.out.print("월급:");
		String sal = scanner.nextLine();
		System.out.print("보너스:");
		String comm = scanner.nextLine();
		System.out.print("경력:");
		String deptno = scanner.nextLine();

		String sql = String.format("insert into emp values ('%s','%s','%s','%s','%s','%s','%s','%s')", empno, ename,
				job, mgr, hiredate, sal, comm, deptno);
		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("데이터 삽입 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateData(Connection connection) {
		System.out.print("수정할 직원의 사번을 입력하세요: ");
		String empno = scanner.nextLine();
		System.out.print("새로운 월급을 입력하세요: ");
		String newSal = scanner.nextLine();
		System.out.println("새로운 보너스를 입력하세요");
		String newcomm = scanner.nextLine();
		String sql = String.format("update emp set sal = '%s',comm ='%s' where empno = '%s'", newSal, newcomm, empno);
		try (Statement stmt = connection.createStatement()) {
			int result = stmt.executeUpdate(sql);
			if (result >= 1) {
				System.out.println("데이터 업데이트 성공! 업데이트된 행의 수: " + result);
			} else {
				System.out.println("데이터 업데이트 실패! 해당하는 직원이 없거나 업데이트할 정보가 올바르지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteData(Connection connection) {
		System.out.print("수정할 직원의 사번을 입력하세요: ");
		String empno = scanner.nextLine();

		String sql = String.format("delete from emp where empno = %s", empno);
		try (Statement stmt = connection.createStatement()) {
			int result = stmt.executeUpdate(sql);
			if (result >= 1) {
				System.out.println("데이터 삭제 성공! 업데이트된 행의 수: " + result);
			} else {
				System.out.println("데이터 삭제 실패! 해당하는 직원이 없거나 삭제할 정보가 올바르지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
package ConnectData;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getProfile{
    private Connection connection;
    private String userId;
    private String getdoctorid;

    public getProfile(Connection connection, String userId) {
        this.connection = connection;
        this.userId = userId;
    }

    String tablename;

    public void deftype() {
        char userType = userId.charAt(0);
        switch (userType) {
            case 'P': // Bệnh nhân
                tablename = "Patients";
                break;
            case 'D': // Bác sĩ
                tablename = "Doctors";
                break;
            case 'M': // Quản lý
                tablename = "Managers";
                break;
        }
    }

    public String getFullName() {
        deftype();
        String query = "SELECT FullName FROM " + tablename + " WHERE CustomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("FullName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGender() {
        deftype();
        String query = "SELECT Gender FROM " + tablename + " WHERE CustomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Gender");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDateofBirth() {
        deftype();
        String query = "SELECT DateOfBirth FROM " + tablename + " WHERE CustomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("DateOfBirth");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhoneNumber() {
        deftype();
        String query = "SELECT PhoneNumber FROM " + tablename + " WHERE CustomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("PhoneNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAddress() {
        deftype();
        String query = "SELECT Address FROM " + tablename + " WHERE CustomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getDiseasename() {
        deftype();
        if (!"Patients".equals(tablename)) {
            return "Không có thông tin";
        }
        String query = "SELECT DiseaseName FROM " + tablename + " WHERE CustomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("DiseaseName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAttending() {
        deftype();
        String query;
        String role = "DoctorID";
        String querygetdoctorname = "SELECT FullName FROM Doctors WHERE DoctorID = ?";
        if (tablename.equals("Doctors") || tablename.equals("Managers")) {
            role = "HospitalName";
            tablename = "Hospitals";
            query = "SELECT " + role + " FROM " + tablename;
            try (PreparedStatement stmt = connection.prepareStatement(query)){
                ResultSet resultSet = stmt.executeQuery();
                if(resultSet.next()){
                    return resultSet.getString("HospitalName");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            query = "SELECT " + role + " FROM " + tablename + " WHERE CustomID = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                if ("Patients".equals(tablename)) {
                    stmt.setString(1, userId);
                }
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    getdoctorid = (rs.getString(role));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try (PreparedStatement getDoctorname = connection.prepareStatement(querygetdoctorname)) {
                getDoctorname.setString(1, getdoctorid);
                ResultSet rs = getDoctorname.executeQuery();
                if (rs.next()) {
                    return rs.getString("FullName");

                }
                System.out.println("Role: " + role + ", Table: " + tablename + ", Query: " + querygetdoctorname);


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    public String getSpecialization(){
        deftype();
        String query;
        if("Managers".equals(tablename)){
            query = "SELECT HospitalName FROM Hospitals";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("HospitalName");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            query = "SELECT Specialization FROM " + tablename + " WHERE CustomID = ?";}
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if(!"Managers".equals(tablename)){
                stmt.setString(1, userId);}
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Specialization");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    }



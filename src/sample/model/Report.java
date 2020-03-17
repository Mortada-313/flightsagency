package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;

public class Report {
    String month,totalCount,totalWinnings,employee,count,winnings;
    public Report(String month, String totalCount, String totalWinnings, String employee, String count, String winnings) {
        this.month = month;
        this.totalCount = totalCount;
        this.totalWinnings = totalWinnings;
        this.employee = employee;
        this.count = count;
        this.winnings = winnings;
    }

    public static ObservableList<Object> loadReports(Connection connection){
        boolean flag1=true,flag2=true;
        String month,totalCount,totalWinnings,employee,count,winnings;
        ObservableList<Object> reports= FXCollections.observableArrayList();
        try{
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT created_at, count(id), sum(earnings) FROM reservations GROUP BY MONTH(created_at)");
            ResultSet resultSet2 = connection.createStatement().executeQuery("SELECT a.full_name, count(r.id), sum(r.earnings) FROM agents AS a, reservations AS r" +
                    " WHERE r.agents_id=a.id GROUP BY r.agents_id");
            while(flag1||flag2) {
                if (flag1 = resultSet1.next()) {
                    month=resultSet1.getString(1); totalCount=resultSet1.getString(2); totalWinnings=resultSet1.getString(3);
                } else
                    month = totalCount = totalWinnings = null;
                if (flag2 = resultSet2.next()) {
                    employee=resultSet2.getString(1); count=resultSet2.getString(2); winnings=resultSet2.getString(3);
                } else
                    employee = count = winnings = null;
                if(month!=null)
                    month=month.split("-")[0]+" - "+month.split("-")[1];
                reports.add(new Report(month,totalCount,totalWinnings,employee,count,winnings));
            }
        }
        catch (Exception e){e.printStackTrace();}
        return reports;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalWinnings() {
        return totalWinnings;
    }

    public void setTotalWinnings(String totalWinnings) {
        this.totalWinnings = totalWinnings;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getWinnings() {
        return winnings;
    }

    public void setWinnings(String winnings) {
        this.winnings = winnings;
    }
}

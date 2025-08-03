package com.cams.doa;

import com.cams.dto.AccountDto;
import com.cams.exception.AccountNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AccountDao {
    public int getId() {
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/account_db";
            String username = "root";
            String password = "T-1234567";
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT COUNT(*) FROM accounts";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            resultSet.next();
            result = resultSet.getInt(1);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public int creatAccount(AccountDto accountDto) {
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/account_db";
            String username = "root";
            String password = "T-1234567";
            Connection connection = DriverManager.getConnection(url, username, password);
            String insertQuery = "INSERT into accounts values" + "(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, accountDto.getId());
            preparedStatement.setString(2, accountDto.getName());
            preparedStatement.setDouble(3, accountDto.getBalance());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Optional<AccountDto> displayAccount(AccountDto accountDto) throws AccountNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/account_db";
            String username = "root";
            String password = "T-1234567";
            String selectQuery = "SELECT * FROM accounts WHERE id=? and name=?";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, accountDto.getId());
                preparedStatement.setString(2, accountDto.getName());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Double balance = resultSet.getDouble(3);
                        AccountDto resultAccountDto = new AccountDto(accountDto.getId(), accountDto.getName(), balance);
                        return Optional.of(resultAccountDto);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public int deposit(AccountDto accountDto, Double amount) {
        int result;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/account_db";
            String username = "root";
            String password = "T-1234567";
            Connection connection = DriverManager.getConnection(url, username, password);
            String selectQuery = "UPDATE accounts SET amount = amount + ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountDto.getId());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int withdraw(AccountDto accountDto, Double amount) {
        int result;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/account_db";
            String username = "root";
            String password = "T-1234567";
            Connection connection = DriverManager.getConnection(url, username, password);
            String selectQuery = "UPDATE accounts SET amount = amount - ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountDto.getId());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<AccountDto> sortAccounts(int columnIndex) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/account_db";
            String username = "root";
            String password = "T-1234567";
            String selectQuery = "SELECT * FROM accounts ORDER BY ?";
            try (Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, columnIndex);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while(resultSet.next()) {
                        Integer id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        Double balance = resultSet.getDouble(3);
                        accountDtoList.add(new AccountDto(id, name, balance));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return accountDtoList;
    }
}

package com.gregspitz.flashcardserver.data.db;

import com.gregspitz.flashcardserver.model.Flashcard;
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FlashcardDatabase {

    private static final String TABLE_NAME = "flashcard";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_FRONT = "front";

    private static final String COLUMN_BACK = "back";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME
            + " (" + COLUMN_ID + " varchar(36) not null primary key, "
            + COLUMN_FRONT + " varchar(300), "
            + COLUMN_BACK + " varchar(1000))";

    private static final String INSERT_PS = "insert into " + TABLE_NAME
            + "(" + COLUMN_ID + ", " + COLUMN_FRONT + ", " + COLUMN_BACK
            + ") values (?, ?, ?)";

    private static final String SELECT_FLASHCARDS = "select " + COLUMN_ID
            + ", " + COLUMN_FRONT + ", " + COLUMN_BACK + " from " + TABLE_NAME;

    private EmbeddedDriver driver;
    private Connection connection;
    private Statement statement;

    public FlashcardDatabase(String connectionString) throws SQLException {
        driver = new EmbeddedDriver();
        connection = driver.connect(connectionString, new Properties());
        statement = connection.createStatement();
    }

    public void createTable() throws SQLException {
        if (!checkForTable()) {
            statement.execute(CREATE_TABLE);
        }
    }

    public void insert(Flashcard flashcard) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PS);
        preparedStatement.setString(1, flashcard.getId());
        preparedStatement.setString(2, flashcard.getFront());
        preparedStatement.setString(3, flashcard.getBack());
        preparedStatement.executeUpdate();
    }

    public List<Flashcard> getFlashcards() throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_FLASHCARDS);
        List<Flashcard> flashcards = new ArrayList<>();
        while (resultSet.next()) {
            flashcards.add(new Flashcard(resultSet.getString(1),
                    resultSet.getString(2), resultSet.getString(3)));
        }
        return flashcards;
    }

    public Flashcard getFlashcardById(String id) throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_FLASHCARDS + " where id = '" + id + "'");
        if (resultSet.next()) {
            return new Flashcard(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3));
        } else {
            return null;
        }
    }

    private boolean checkForTable() throws SQLException {
        try {
            statement.execute("update " + TABLE_NAME
                    + " set " + COLUMN_FRONT + " = 'TEST ENTRY' where 1=3");
        }  catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            if (theError.equals("42X05")) {
                return false;
            }  else if (theError.equals("42X14") || theError.equals("42821"))  {
                // TODO: update exception type
                throw sqle;
            } else {
                // TODO: update exception type
                throw sqle;
            }
        }
        return true;
    }

    public void close() {
        if (driver.getClass().getTypeName().equals("org.apache.derby.jdbc.EmbeddedDriver")) {
            boolean gotSQLExc = false;
            try {
                driver.connect("jdbc:derby:;shutdown=true", new Properties());
            } catch (SQLException se)  {
                if ( se.getSQLState().equals("XJ015") ) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            }  else  {
                System.out.println("Database shut down normally");
            }
        } else {
            System.err.println("Wrong driver type.");
        }
    }
}

package org.safris.test.maven.plugin.xdb.xde;

import static org.safris.xdb.xde.DML.AND;
import static org.safris.xdb.xde.DML.CASE_WHEN;
import static org.safris.xdb.xde.DML.EQ;
import static org.safris.xdb.xde.DML.GT;
import static org.safris.xdb.xde.DML.INSERT;
import static org.safris.xdb.xde.DML.LEFT_OUTER;
import static org.safris.xdb.xde.DML.LT;
import static org.safris.xdb.xde.DML.LTEQ;
import static org.safris.xdb.xde.DML.MAX;
import static org.safris.xdb.xde.DML.MIN;
import static org.safris.xdb.xde.DML.PLUS;
import static org.safris.xdb.xde.DML.SELECT;
import static org.safris.xdb.xde.DML.UPDATE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.safris.xdb.xde.Column;
import org.safris.xdb.xde.EntityDataSource;
import org.safris.xdb.xde.EntityDataSources;
import org.safris.xdb.xde.Table;
import org.safris.xdb.xde.csql.select.SELECT;
import org.safris.xdb.xde.csql.update.UPDATE;

import xdb.xde.survey;

import com.mysql.jdbc.Driver;

public class FormTest {
  static {
    try {
      Class.forName(Driver.class.getName());
    }
    catch (final ClassNotFoundException e) {
      throw new ExceptionInInitializerError(e);
    }

    EntityDataSources.register(survey.class, PreparedStatement.class, new EntityDataSource() {
      public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/survey?user=survey&password=xqTMy2FluQ");
      }
    });
  }

  public static void main(final String[] args) throws SQLException {
    final FormTest test = new FormTest();
    test.testSELECT1();
    test.testSELECT2();
    test.testSELECT3();
    test.testSELECT4();
    test.testSELECT5();
    test.testSELECT6();
    test.testSELECT7();
    test.testSELECT8();
    test.testSELECT9();
    test.testSELECT10();
  }

  public void testSELECT1() throws SQLException {
    final survey.Dish d = new survey.Dish();
    final SELECT<survey.Dish> select = SELECT(d).FROM(d).WHERE(EQ(d.id, 7));
    final List<survey.Dish[]> rows = select.execute();
  }

  public void testSELECT2() throws SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Dish d = new survey.Dish();
    final survey.MealDish md = new survey.MealDish();
    final SELECT<Table> select = SELECT(m, d).FROM(m, md, d).WHERE(AND(EQ(m.id, 3), EQ(m.id, md.mealId), EQ(md.dishId, d.id)));
    final List<Table[]> rows = select.execute();
  }

  public void testSELECT3() throws SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    final survey.MealSurvey ms = new survey.MealSurvey();
    // SELECT MIN(m.created_on) FROM meal m LEFT JOIN unsubscribed u ON u.email = m.email LEFT JOIN meal_survey ms ON ms.meal_id = m.id WHERE u.email IS NULL AND ms.meal_id IS NULL AND m.sent = 0 AND m.skipped = 0
    final SELECT<Column<Date>> select = SELECT(MIN(m.createdOn)).FROM(m).JOIN(LEFT_OUTER, u).ON(EQ(u.email, m.email)).JOIN(LEFT_OUTER, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, null), EQ(ms.mealId, null), EQ(m.sent, false), EQ(m.skipped, false)));
    final List<Column<Date>[]> rows = select.execute();
  }

  public void testSELECT4() throws SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    final survey.MealSurvey ms = new survey.MealSurvey();
    final SELECT<Column<Date>> select = SELECT(MAX(m.createdOn)).FROM(m).JOIN(LEFT_OUTER, u).ON(EQ(u.email, m.email)).JOIN(LEFT_OUTER, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, null), EQ(ms.mealId, null)));
    final List<Column<Date>[]> rows = select.execute();
  }

  public void testSELECT5() throws SQLException {
    final survey.MealAudit ma = new survey.MealAudit();
    final LocalDateTime date = new LocalDateTime();
    UPDATE(ma).SET(ma.rangeTo, CASE_WHEN(GT(ma.rangeTo, date)).THEN(ma.rangeTo).ELSE(date)).execute();
  }

  public void testSELECT6() throws SQLException {
    final survey.MealAudit ma = new survey.MealAudit();
    final SELECT<survey.MealAudit> select = SELECT(ma).FROM(ma);
    select.execute();
  }

  public void testSELECT7() throws SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Dish d = new survey.Dish();
    final survey.MealDish md = new survey.MealDish();
    final survey.MealSurvey ms = new survey.MealSurvey();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    // SELECT m.*, d.* FROM dish d, meal_dish md, meal m LEFT JOIN unsubscribed u ON u.email = m.email LEFT JOIN meal_survey ms ON ms.meal_id = m.id WHERE
    // u.email IS NULL AND ms.meal_id IS NULL AND ? <= m.created_on AND m.created_on < ? AND m.id = md.meal_id AND md.dish_id = d.id ORDER BY m.created_on,
    // m.order_id

    final LocalDateTime from = new LocalDateTime();
    final LocalDateTime to = new LocalDateTime();
    final SELECT<Table> select = SELECT(m, d).FROM(md, d, m).JOIN(LEFT_OUTER, u).ON(EQ(u.email, m.email)).JOIN(LEFT_OUTER, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, null), EQ(ms.mealId, null), LTEQ(from, m.createdOn), LT(m.createdOn, to), EQ(m.id, md.mealId), EQ(md.dishId, d.id))).ORDER_BY(m.createdOn, m.orderId);
    final List<Table[]> rows = select.execute();
  }

  public void testSELECT8() throws SQLException {
    INSERT(new survey.MealDish(33, 57, (short)1)).execute();
  }

  public void testSELECT9() throws SQLException {
    final survey.MealDish md = new survey.MealDish();
    final UPDATE update = UPDATE(md).SET(md.quantity, PLUS(md.quantity, (short)1)).WHERE(AND(EQ(md.mealId, 7), EQ(md.dishId, 66)));
    update.execute();
  }

  public void testSELECT10() throws SQLException {
    final survey.Meal m = new survey.Meal();
    UPDATE(m).SET(m.sent, true).WHERE(EQ(m.id, 5)).execute();
  }
}
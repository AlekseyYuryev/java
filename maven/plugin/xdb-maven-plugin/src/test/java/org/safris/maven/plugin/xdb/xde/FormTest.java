/* Copyright (c) 2015 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.maven.plugin.xdb.xde;

import static org.safris.xdb.xde.DML.AND;
import static org.safris.xdb.xde.DML.CASE_WHEN;
import static org.safris.xdb.xde.DML.EQ;
import static org.safris.xdb.xde.DML.GT;
import static org.safris.xdb.xde.DML.INSERT;
import static org.safris.xdb.xde.DML.LEFT;
import static org.safris.xdb.xde.DML.LT;
import static org.safris.xdb.xde.DML.LTE;
import static org.safris.xdb.xde.DML.MAX;
import static org.safris.xdb.xde.DML.MIN;
import static org.safris.xdb.xde.DML.PLUS;
import static org.safris.xdb.xde.DML.SELECT;
import static org.safris.xdb.xde.DML.UPDATE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.joda.time.LocalDateTime;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.xde.Aggregate;
import org.safris.xdb.xde.Entity;
import org.safris.xdb.xde.EntityDataSource;
import org.safris.xdb.xde.EntityRegistry;
import org.safris.xdb.xde.RowIterator;
import org.safris.xdb.xde.spec.select.SELECT;
import org.safris.xdb.xde.spec.update.UPDATE;

import cf.xde.survey;

@SuppressWarnings("unused")
public class FormTest extends LoggableTest {
  static {
    EntityRegistry.register(survey.class, PreparedStatement.class, new EntityDataSource() {
      @Override
      public Connection getConnection() throws SQLException {
        // TODO: Replace with Derby
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
//    test.testUPDATE1();
//    test.testUPDATE2();
//    test.testINSERT1();
  }

  public void testSELECT1() throws SQLException {
    final survey.Dish d = new survey.Dish();
    final SELECT<survey.Dish> select = SELECT(d, d).FROM(d).WHERE(EQ(d.id, 7));
    final RowIterator<survey.Dish> rows = select.execute();
  }

  public void testSELECT2() throws SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Dish d = new survey.Dish();
    final survey.MealDish md = new survey.MealDish();
    final SELECT<Entity> select = SELECT(m, d).FROM(m, md, d).WHERE(AND(EQ(m.id, 3), EQ(m.id, md.mealId), EQ(md.dishId, d.id)));
    final RowIterator<Entity> rows = select.execute();
  }

  public void testSELECT3() throws SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    final survey.MealSurvey ms = new survey.MealSurvey();
    // SELECT MIN(m.created_on) FROM meal m LEFT JOIN unsubscribed u ON u.email = m.email LEFT JOIN meal_survey ms ON ms.meal_id = m.id WHERE u.email IS NULL AND ms.meal_id IS NULL AND m.sent = 0 AND m.skipped = 0
    final SELECT<Aggregate<LocalDateTime>> select = SELECT(MIN(m.createdOn), MAX(m.createdOn)).FROM(m).JOIN(LEFT, u).ON(EQ(u.email, m.email)).JOIN(LEFT, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, (String)null), EQ(ms.mealId, (Integer)null), EQ(m.sent, false), EQ(m.skipped, false)));
    final RowIterator<Aggregate<LocalDateTime>> rows = select.execute();
  }

  public void testSELECT4() throws SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    final survey.MealSurvey ms = new survey.MealSurvey();
    final SELECT<MAX<LocalDateTime>> select = SELECT(MAX(m.createdOn)).FROM(m).JOIN(LEFT, u).ON(EQ(u.email, m.email)).JOIN(LEFT, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, (String)null), EQ(ms.mealId, (Integer)null)));
    final RowIterator<MAX<LocalDateTime>> rows = select.execute();
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
    final SELECT<Entity> select = SELECT(m, d).FROM(md, d, m).JOIN(LEFT, u).ON(EQ(u.email, m.email)).JOIN(LEFT, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, (String)null), EQ(ms.mealId, (Integer)null), LTE(from, m.createdOn), LT(m.createdOn, to), EQ(m.id, md.mealId), EQ(md.dishId, d.id))).ORDER_BY(m.createdOn, m.orderId);
    final RowIterator<Entity> rows = select.execute();
  }

  public void testUPDATE1() throws SQLException {
    INSERT(new survey.MealDish(33, 57, (short)1)).execute();
  }

  public void testUPDATE2() throws SQLException {
    final survey.MealDish md = new survey.MealDish();
    final UPDATE update = UPDATE(md).SET(md.quantity, PLUS(md.quantity, (short)1)).WHERE(AND(EQ(md.mealId, 7), EQ(md.dishId, 66)));
    update.execute();
  }

  public void testINSERT1() throws SQLException {
    final survey.Meal m = new survey.Meal();
    UPDATE(m).SET(m.sent, true).WHERE(EQ(m.id, 5)).execute();
  }
}
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

import static org.safris.xdb.entities.DML.ALL;
import static org.safris.xdb.entities.DML.AND;
import static org.safris.xdb.entities.DML.AVG;
import static org.safris.xdb.entities.DML.CASE_WHEN;
import static org.safris.xdb.entities.DML.EQ;
import static org.safris.xdb.entities.DML.GT;
import static org.safris.xdb.entities.DML.IN;
import static org.safris.xdb.entities.DML.INSERT;
import static org.safris.xdb.entities.DML.LT;
import static org.safris.xdb.entities.DML.LTE;
import static org.safris.xdb.entities.DML.MAX;
import static org.safris.xdb.entities.DML.MIN;
import static org.safris.xdb.entities.DML.PLUS;
import static org.safris.xdb.entities.DML.SELECT;
import static org.safris.xdb.entities.DML.UPDATE;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.safris.commons.lang.Resources;
import org.safris.commons.test.LoggableTest;
import org.safris.commons.xml.XMLException;
import org.safris.xdb.entities.DML.OUTER;
import org.safris.xdb.entities.Entity;
import org.safris.xdb.entities.EntityDataSource;
import org.safris.xdb.entities.EntityRegistry;
import org.safris.xdb.entities.RowIterator;
import org.safris.xdb.entities.type.DateTime;
import org.safris.xdb.entities.type.MediumInt;
import org.safris.xdb.entities.model.select.SELECT;
import org.safris.xdb.entities.model.update.UPDATE;
import org.safris.xdb.xdd.xe.$xdd_data;
import org.safris.xsb.runtime.Bindings;
import org.xml.sax.InputSource;

import xdb.ddl.survey;

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

  public static void main(final String[] args) throws IOException, SQLException {
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

  public void testSELECT1() throws IOException, SQLException {
    final survey.Dish d = new survey.Dish();
    final SELECT<survey.Dish> select = SELECT(d, d).FROM(d).WHERE(EQ(d.id, 7));
    final RowIterator<survey.Dish> rows = select.execute();
  }

  public void testSELECT2() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Dish d = new survey.Dish();
    final survey.MealDish md = new survey.MealDish();
    final SELECT<Entity> select = SELECT(m, d).FROM(m, md, d).WHERE(AND(EQ(m.id, 3), EQ(m.id, md.mealId), EQ(md.dishId, d.id)));
    final RowIterator<Entity> rows = select.execute();
  }

  public void testSELECT3() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    final survey.MealSurvey ms = new survey.MealSurvey();
    // SELECT MIN(m.created_on) FROM meal m LEFT JOIN unsubscribed u ON u.email = m.email LEFT JOIN meal_survey ms ON ms.meal_id = m.id WHERE u.email IS NULL AND ms.meal_id IS NULL AND m.sent = 0 AND m.skipped = 0
    final SELECT<DateTime> select = SELECT(MIN(m.createdOn), MAX(m.createdOn)).FROM(m).JOIN(OUTER.LEFT, u).ON(EQ(u.email, m.email)).JOIN(OUTER.LEFT, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, (String)null), EQ(ms.mealId, (Integer)null), EQ(m.sent, false), EQ(m.skipped, false)));
  }

  public void testSELECT4() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    final survey.MealSurvey ms = new survey.MealSurvey();
    final SELECT<DateTime> select = SELECT(MAX(m.createdOn)).FROM(m).JOIN(OUTER.LEFT, u).ON(EQ(u.email, m.email)).JOIN(OUTER.LEFT, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, (String)null), EQ(ms.mealId, (Integer)null)));
  }

  public void testSELECT5() throws IOException, SQLException {
    final survey.MealAudit ma = new survey.MealAudit();
    final LocalDateTime date = LocalDateTime.now();
    UPDATE(ma).SET(ma.rangeTo, CASE_WHEN(GT(ma.rangeTo, date)).THEN(ma.rangeTo).ELSE(date)).execute();
  }

  public void testSELECT6() throws IOException, SQLException {
    final survey.MealAudit ma = new survey.MealAudit();
    final SELECT<survey.MealAudit> select = SELECT(ma).FROM(ma);
    select.execute();
  }

  public void testSELECT7() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.Dish d = new survey.Dish();
    final survey.MealDish md = new survey.MealDish();
    final survey.MealSurvey ms = new survey.MealSurvey();
    final survey.Unsubscribed u = new survey.Unsubscribed();
    // SELECT m.*, d.* FROM dish d, meal_dish md, meal m LEFT JOIN unsubscribed u ON u.email = m.email LEFT JOIN meal_survey ms ON ms.meal_id = m.id WHERE
    // u.email IS NULL AND ms.meal_id IS NULL AND ? <= m.created_on AND m.created_on < ? AND m.id = md.meal_id AND md.dish_id = d.id ORDER BY m.created_on,
    // m.order_id

    final LocalDateTime from = LocalDateTime.now();
    final LocalDateTime to = LocalDateTime.now();
    final SELECT<Entity> select = SELECT(m, d).FROM(md, d, m).JOIN(OUTER.LEFT, u).ON(EQ(u.email, m.email)).JOIN(OUTER.LEFT, ms).ON(EQ(ms.mealId, m.id)).WHERE(AND(EQ(u.email, (String)null), EQ(ms.mealId, (Integer)null), LTE(from, m.createdOn), LT(m.createdOn, to), EQ(m.id, md.mealId), EQ(md.dishId, d.id))).ORDER_BY(m.createdOn, m.orderId);
    final RowIterator<Entity> rows = select.execute();
  }

  // uncorrelated subquery in the SELECT
  public void testSELECT8() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();

    final SELECT<MediumInt> select =
      SELECT(
        m.orderId,
        SELECT(AVG(m.orderId)).FROM(m).WHERE(EQ(m.orderId, 101)).AS(new MediumInt())).
      FROM(m).WHERE(EQ(m.orderId, 101));
  }

  // uncorrelated subquery in the WHERE
  public void testSELECT9() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();

    final SELECT<survey.Meal> select =
      SELECT(m).
      FROM(m).WHERE(IN(m.orderId,
        SELECT(MAX(m.orderId)).FROM(m).WHERE(GT(m.createdOn, LocalDateTime.parse("2015-01-01T00:00:00")))));
  }

  // correlated subquery in the WHERE
  public void testSELECT10() throws IOException, SQLException {
    final survey.Meal m1 = new survey.Meal();
    final survey.Meal m2 = new survey.Meal();

    final SELECT<MediumInt> select =
      SELECT(m1.orderId).
      FROM(m1).WHERE(EQ(
        SELECT(m2.orderId).FROM(m2).WHERE(EQ(m2.orderId, m1.orderId)), m1.orderId));
  }

  // doubly-correlated subquery in the WHERE
  public void testSELECT11() throws IOException, SQLException {
    final survey.Meal m1 = new survey.Meal();
    final survey.Meal m2 = new survey.Meal();
    final MediumInt maxId = new MediumInt();

    final SELECT<MediumInt> select =
      SELECT(
          m1.orderId,
          maxId).
      FROM(m1).WHERE(EQ(
        SELECT(m2.orderId).FROM(m2).WHERE(GT(m2.orderId, m1.orderId)).AS(maxId), m1.orderId));
  }

  // doubly-correlated subquery in the FROM
  public SELECT<MediumInt> testSELECT12() throws IOException, SQLException {
    final survey.Meal m1 = new survey.Meal();
    final survey.Meal m2 = new survey.Meal();
    final survey.Meal maxId = new survey.Meal();

    return SELECT(
          m1.orderId,
          maxId.orderId).
      FROM(m1, SELECT(m2).FROM(m2).WHERE(GT(m2.orderId, m1.orderId)).AS(maxId)).WHERE(EQ(maxId.orderId, m1.orderId));
  }

  public void testSELECT13() throws IOException, SQLException {
    testSELECT12().UNION(ALL, testSELECT12());
  }

  public void testSELECT15() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();

    final SELECT<survey.Meal> select =
      INSERT(new survey.Meal()).
      SELECT(m).
      FROM(m).WHERE(IN(m.orderId,
        SELECT(MAX(m.orderId)).FROM(m).WHERE(GT(m.createdOn, LocalDateTime.parse("2015-01-01T00:00:00")))));
  }

  // Need to implement this too: UPDATE table1 dest, (SELECT * FROM table2 where id=x) src SET dest.col1 = src.col1 where dest.id=x ;
  public void testSELECT16() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.MealDish md = new survey.MealDish();
    final UPDATE update = UPDATE(m).SET(m.orderId, SELECT(MAX(m.orderId)).FROM(m)).WHERE(AND(EQ(md.mealId, 7), EQ(md.dishId, 66)));
  }

  public void testSELECT17() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();
    final survey.MealDish md = new survey.MealDish();
    final UPDATE update = UPDATE(m).SET(m.orderId, SELECT(MAX(m.orderId)).FROM(m)).WHERE(AND(EQ(md.mealId, 7), EQ(md.dishId, 66)));
  }

  public void testUPDATE1() throws IOException, SQLException {
    INSERT(new survey.MealDish(33, 57)).execute();
  }

  public void testUPDATE2() throws IOException, SQLException {
    final survey.MealDish md = new survey.MealDish();
    final UPDATE update = UPDATE(md).SET(md.quantity, PLUS(md.quantity, (short)1)).WHERE(AND(EQ(md.mealId, 7), EQ(md.dishId, 66)));
    update.execute();
  }

  public void testINSERT1() throws IOException, SQLException {
    final survey.Meal m = new survey.Meal();
    UPDATE(m).SET(m.sent, true).WHERE(EQ(m.id, 5)).execute();
  }

  public void testINSERT2() throws IOException, SQLException, XMLException {
    final $xdd_data data = ($xdd_data)Bindings.parse(new InputSource(Resources.getResource("world.xdd").getURL().openStream()));
    INSERT(data);
  }
}
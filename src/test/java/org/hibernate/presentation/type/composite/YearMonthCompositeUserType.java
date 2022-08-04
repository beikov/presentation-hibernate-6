package org.hibernate.presentation.type.composite;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;

public class YearMonthCompositeUserType implements CompositeUserType<YearMonth> {

	public static class YearMonthEmbeddable {
		int month;
		int year;
	}

	@Override
	public Object getPropertyValue(YearMonth component, int property) throws HibernateException {
		switch ( property ) {
			case 0:
				return component.getMonthValue();
			case 1:
				return component.getYear();
		}
		throw new HibernateException( "Illegal component property: " + property );
	}

	@Override
	public YearMonth instantiate(ValueAccess values, SessionFactoryImplementor sessionFactory) {
		Integer month = values.getValue( 0, Integer.class );
		Integer year = values.getValue( 1, Integer.class );
		return year == null || month == null ? null : YearMonth.of( year, month );
	}

	@Override
	public Class<?> embeddable() {
		return YearMonthEmbeddable.class;
	}

	@Override
	public Class<YearMonth> returnedClass() {
		return YearMonth.class;
	}

	@Override
	public boolean equals(YearMonth x, YearMonth y) {
		return Objects.equals( x, y );
	}

	@Override
	public int hashCode(YearMonth x) {
		return Objects.hashCode( x );
	}

	@Override
	public YearMonth deepCopy(YearMonth value) {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(YearMonth value) {
		return value;
	}

	@Override
	public YearMonth assemble(Serializable cached, Object owner) {
		return (YearMonth) cached;
	}

	@Override
	public YearMonth replace(YearMonth detached, YearMonth managed, Object owner) {
		return managed;
	}
}

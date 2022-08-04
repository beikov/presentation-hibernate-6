package org.hibernate.presentation.array;

import jakarta.persistence.Tuple;
import org.hibernate.presentation.model.Forum;
import org.hibernate.presentation.model.Post;
import org.hibernate.presentation.model.TopicTag;
import org.hibernate.query.SelectionQuery;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrate some array mapping features
 */
@DomainModel( annotatedClasses = { Forum.class, Post.class } )
@SessionFactory
public class ArrayExampleTest {
	@Test
	public void demonstrateSetAsArray(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			final SelectionQuery<Tuple> query = session.createSelectionQuery(
				"select f.name, p.topicTags " +
					"from Forum f " +
					"left join f.posts p " +
					"order by f.id, p.id",
				Tuple.class
			);
			final List<Tuple> results = query.list();
			assertThat( results ).hasSize( 2 );
			assertThat( results.get( 0 ).get(0) ).isEqualTo( "first forum" );
			assertThat( results.get( 0 ).get(1) ).isEqualTo( EnumSet.of(TopicTag.SPORTS) );
			assertThat( results.get( 1 ).get(0) ).isEqualTo( "first forum" );
			assertThat( results.get( 1 ).get(1) ).isEqualTo( EnumSet.of(TopicTag.PROGRAMMING, TopicTag.MOVIES) );
		} );
	}

	@BeforeEach
	public void createData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			final Forum forum = new Forum( 1, "first forum" );
			session.persist( forum );

			Post p1 = new Post(1, "Welcome!", forum);
			Post p2 = new Post(2, "Hi!", forum);
			p1.setTopicTags(EnumSet.of(TopicTag.SPORTS));
			p2.setTopicTags(EnumSet.of(TopicTag.PROGRAMMING, TopicTag.MOVIES));
			session.persist(p1);
			session.persist(p2);
		} );
	}

	@AfterEach
	public void dropData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.createMutationQuery( "delete Post" ).executeUpdate();
			session.createMutationQuery( "delete Forum" ).executeUpdate();
		} );
	}
}
